package com.alecjarett.ballardcs.rpgu;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
        RPGuActivity activity = getItem(position);
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
            Log.e("hi,", "problem loading skill image");
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
