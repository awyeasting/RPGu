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
 * Created by bal_awyeasting on 5/6/2016.
 */
public class ActivitiesAdapter extends ArrayAdapter<RPGuActivity> {

    private ActivityType activityType;

    public ActivitiesAdapter(Activity context, List<RPGuActivity> activityList, ActivityType activityType){
        super(context,0,activityList);
        this.activityType=activityType;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        final RPGuActivity activity = getItem(position);
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

        Button activityButton = (Button) root.findViewById(R.id.activity_button);
        int activityActionsToDo = activity.getQuantityToDo() - activity.getQuantityDone();
        if(activityActionsToDo == 1){
            activityButton.setText("Finish");
        }else if(activityActionsToDo == 0){
            activityButton.setText("Done");
            activityButton.setEnabled(false);
        }else{
            activityButton.setText("+");
        }
        activityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button activityButton = (Button) v.findViewById(R.id.activity_button);
                int activityActionsToDo = activity.getQuantityToDo() - activity.getQuantityDone();
                if(activityActionsToDo>=1){
                    activity.increaseQuantityDone();
                }

                if(activity.getQuantityToDo() > 1) {
                    ProgressBar progressBar = (ProgressBar) ((LinearLayout) (v.getParent().getParent().getParent())).findViewById(R.id.activity_progress_bar);
                    progressBar.setProgress(100 * activity.getQuantityDone() / activity.getQuantityToDo());
                    progressBar.setVisibility(View.VISIBLE);
                    TextView quantityDone = (TextView) ((LinearLayout) (v.getParent().getParent().getParent())).findViewById(R.id.activity_quantity_done);
                    quantityDone.setText(activity.getQuantityDone() + " /");
                    quantityDone.setVisibility(View.VISIBLE);
                }

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
                if(activityActionsToDo == 0){
                    activityButton.setText("Done");
                    activityButton.setEnabled(false);
                }else if(activityActionsToDo == 1){
                    activityButton.setText("Finish");
                }else{
                    activityButton.setText("+");
                }
                ((MainActivity)getContext()).saveDayAsActive();
            }
        });

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

        return root;
    }

    public enum ActivityType{Daily,Weekly,Monthly}
}
