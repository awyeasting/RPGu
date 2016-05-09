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

    public ActivitiesFragment() {
        // Required empty public constructor
    }

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
        //

        //TODO: finish loading activities from a given list

        //Get the Linear Layout for the daily activities to load into
        LinearLayout dailies = (LinearLayout) root.findViewById(R.id.dailies_linear_layout);

        //Get the list of daily activities to load
        List<RPGuActivity> activities = new ArrayList<RPGuActivity>();
            activities.add(new RPGuActivity(1, 1, "Running", "Run a mile", 1000));

        //Adapter for the list to the LinearLayout
        ActivitiesAdapter adapter = new ActivitiesAdapter(getActivity(),activities, ActivitiesAdapter.ActivityType.Daily);

        //Add list items to the LinearLayout
        final int adapterLength = adapter.getCount();
        for(int i = 0; i<adapterLength; i++){
            View item = adapter.getView(i,null,null);
            dailies.addView(item);
        }

        return root;
    }

}