package com.alecjarett.ballardcs.rpgu.MainFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alecjarett.ballardcs.rpgu.ActivitiesAdapter;
import com.alecjarett.ballardcs.rpgu.MainActivity;
import com.alecjarett.ballardcs.rpgu.R;
import com.alecjarett.ballardcs.rpgu.RPGuActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Alec Yeasting on 3/31/2016.
 */
public class ActivitiesFragment extends Fragment{

    public ActivitiesFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_activities,container,false);

        //                                                                      //
        //                          Activities Loading                          //
        //                                                                      //

        //Get the LinearLayouts for the activities to load into
        LinearLayout dailies = (LinearLayout) root.findViewById(R.id.dailies_linear_layout);
        LinearLayout weeklies = (LinearLayout) root.findViewById(R.id.weeklies_linear_layout);
        LinearLayout monthlies = (LinearLayout) root.findViewById(R.id.monthlies_linear_layout);

        //Get the lists of activities to load
        List<RPGuActivity> dailyActivities = ((MainActivity)getActivity()).loadCurrentDailies();
        List<RPGuActivity> weeklyActivities = ((MainActivity)getActivity()).loadCurrentWeeklies();
        List<RPGuActivity> monthlyActivities = ((MainActivity)getActivity()).loadCurrentMonthlies();

        //Adapter for the list to the LinearLayout
        ActivitiesAdapter dailiesAdapter = new ActivitiesAdapter(getActivity(), dailyActivities, ActivitiesAdapter.ActivityType.Daily);
        ActivitiesAdapter weekliesAdapter = new ActivitiesAdapter(getActivity(), weeklyActivities, ActivitiesAdapter.ActivityType.Weekly);
        ActivitiesAdapter monthliesAdapter = new ActivitiesAdapter(getActivity(), monthlyActivities, ActivitiesAdapter.ActivityType.Monthly);

        //Add dailies list items to the LinearLayout
        final int dailiesAdapterLength = dailiesAdapter.getCount();
        for(int i = 0; i<dailiesAdapterLength; i++){
            View item = dailiesAdapter.getView(i,null,null);
            dailies.addView(item);
        }

        //Add weeklies list items to the LinearLayout
        final int weekliesAdapterLength = weekliesAdapter.getCount();
        for(int i = 0; i<weekliesAdapterLength; i++){
            View item = weekliesAdapter.getView(i,null,null);
            weeklies.addView(item);
        }

        //Add monthlies list items to the LinearLayout
        final int monthliesAdapterLength = monthliesAdapter.getCount();
        for(int i = 0; i<monthliesAdapterLength; i++){
            View item = monthliesAdapter.getView(i,null,null);
            monthlies.addView(item);
        }

        return root;
    }

}