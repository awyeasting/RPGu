package com.alecjarett.ballardcs.rpgu;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

/**
 * Creates an adapter for loading activities into a View from a List of RPGuActivities
 */
public class ActivitiesAdapter extends ArrayAdapter<RPGuActivity> {

    private ActivityType activityType;

    public ActivitiesAdapter(Activity context, List<RPGuActivity> activityList, ActivityType activityType){
        super(context,0,activityList);
        this.activityType=activityType;
    }

    //For every item in list:
    public View getView(int position, View convertView, ViewGroup parent){

        //Get the item currently selected
        final RPGuActivity activity = getItem(position);

        //Set the main view for that Activity
        View root = LayoutInflater.from(getContext()).inflate(R.layout.list_item_activity, parent, false);

        //Set the activity category text
        TextView activityCategoryLabel = (TextView) root.findViewById(R.id.activity_category_name);
        activityCategoryLabel.setText(activity.getLabel());

        //Set the activity description text
        TextView activityDescription = (TextView) root.findViewById(R.id.activity_description);
        activityDescription.setText(activity.getDescription());

        //Set the activity xp gain
        TextView xpGained = (TextView) root.findViewById(R.id.activity_xp_gain);
        xpGained.setText("+"+activity.getXp()+" xp");

        //Set the activity icon based on the skill for the activity
        ImageView activityCategoryIcon = (ImageView) root.findViewById(R.id.activity_icon_image);
        try {
            activityCategoryIcon.setImageResource(this.getContext()
                    .getResources()
                    .getIdentifier(activity.getCategoryLabel().toLowerCase() + "_icon_white",
                            "drawable",
                            this.getContext().getPackageName()));
        }catch (Exception e){
            activityCategoryIcon.setImageResource(R.drawable.problem_loading_icon_white);
        }
        View iconBackground = root.findViewById(R.id.activity_icon_circle);

        //Set the activity icon color based on the type of activity
        int backgroundColor;
        switch (activityType){
            case Weekly:
                backgroundColor = R.color.weekliesColor;
                break;
            case Monthly:
                backgroundColor = R.color.monthliesColor;
                break;
            default:
                backgroundColor = R.color.dailiesColor;
                break;
        }
        ((GradientDrawable)iconBackground.getBackground()).setColor(getContext().getResources().getColor(backgroundColor));

        /**
         * ActivityButton - Button clicked when an activity is complete or partially complete
         */
        Button activityButton = (Button) root.findViewById(R.id.activity_button);
        int activityActionsToDo = activity.getQuantityToDo() - activity.getQuantityDone();

        //Set text of button on initial view based on activity quantity completed:
        //If there's one action to do, set button text to "Finish"
        if(activityActionsToDo == 1){
            activityButton.setText("Finish");
        }
        //If there are no actions to do, set the text to "Done" and make the button gray
        else if(activityActionsToDo == 0){
            activityButton.setText("Done");
            activityButton.setEnabled(false);
        }
        //In all other cases, there is more than 1 action to do, so set button text to "+"
        else{
            activityButton.setText("+");
        }

        //Set initial text on progress bar and in textviews next to it
        if(activity.getQuantityToDo() > 1) {
            ProgressBar progressBar = (ProgressBar) root.findViewById(R.id.activity_progress_bar);
            progressBar.setProgress(100 * activity.getQuantityDone() / activity.getQuantityToDo());
            progressBar.setVisibility(View.VISIBLE);

            TextView quantityDone = (TextView) root.findViewById(R.id.activity_quantity_done);
            quantityDone.setText(activity.getQuantityDone()+" /");
            quantityDone.setVisibility(View.VISIBLE);

            TextView quantityToDo = (TextView) root.findViewById(R.id.activity_quantity_to_do);
            quantityToDo.setText(" " + activity.getQuantityToDo());
            quantityToDo.setVisibility(View.VISIBLE);

        }


        //Set a click listener to the button
        activityButton.setOnClickListener(new View.OnClickListener() {


        //When it is clicked:
            @Override
            public void onClick(View v) {
                Button activityButton = (Button) v.findViewById(R.id.activity_button);
                int activityActionsToDo = activity.getQuantityToDo() - activity.getQuantityDone();

                //Increase quantity done by 1 on UI
                if(activityActionsToDo>=1){
                    activity.increaseQuantityDone();
                }

                //Increase quantity done by 1 on the progress bar and textview next to it
                if(activity.getQuantityToDo() > 1) {
                    ProgressBar progressBar = (ProgressBar) ((LinearLayout) (v.getParent().getParent().getParent())).findViewById(R.id.activity_progress_bar);
                    progressBar.setProgress(100 * activity.getQuantityDone() / activity.getQuantityToDo());
                    progressBar.setVisibility(View.VISIBLE);
                    TextView quantityDone = (TextView) ((LinearLayout) (v.getParent().getParent().getParent())).findViewById(R.id.activity_quantity_done);
                    quantityDone.setText(activity.getQuantityDone() + " /");
                    quantityDone.setVisibility(View.VISIBLE);
                }

                //Increase quantity done by 1 in database
                ActivitiesDBHandler dbHandler = new ActivitiesDBHandler(getContext(), null, null, 1);
                switch (activity.getActivityType()) {
                    case Daily:
                        dbHandler.updateDaily(activity.getId(), activity.getQuantityDone());
                        break;
                    case Weekly:
                        dbHandler.updateWeekly(activity.getId(), activity.getQuantityDone());
                        break;
                    case Monthly:
                        dbHandler.updateMonthly(activity.getId(), activity.getQuantityDone());
                        break;
                }

                activityActionsToDo = activity.getQuantityToDo() - activity.getQuantityDone();

                //Set the text on the button again now that it has been clicked:
                //If there are no actions to do, set the text to "Done" and make the button gray, and add XP to skill based on activity
                if(activityActionsToDo == 0){
                    ((MainActivity)getContext()).addXPToSkill(activity.getCategoryLabel(), activity.getXp());
                    activityButton.setText("Done");
                    activityButton.setEnabled(false);
                }
                //If there's one action to do, set button text to "Finish"
                else if(activityActionsToDo == 1){
                    activityButton.setText("Finish");
                }
                //In all other cases, there is more than 1 action to do, so set button text to "+"
                else{
                    activityButton.setText("+");
                }
                ((MainActivity)getContext()).saveDayAsActive();
            }
        });

        return root;
    }

    public enum ActivityType{Daily,Weekly,Monthly}
}