package com.alecjarett.ballardcs.rpgu;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by bal_awyeasting on 5/6/2016.
 */
public class ActivitiesAdapter extends ArrayAdapter<RPGuActivity> {

    private ActivityType activityType;

    public ActivitiesAdapter(Activity context, List<RPGuActivity> activityList, ActivityType activityType){
        super(context,0,activityList);
        this.activityType = activityType;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View root = LayoutInflater.from(getContext()).inflate(R.layout.list_item_activity, parent, false);
        //TODO: Finish adapter
        return root;
    }

    public enum ActivityType{Daily,Weekly,Monthly}
}
