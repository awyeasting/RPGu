package com.alecjarett.ballardcs.rpgu.MainFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alecjarett.ballardcs.rpgu.R;

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

        LinearLayout dailies = (LinearLayout) root.findViewById(R.id.dailies_linear_layout);

        //TODO: Finish item loading

        return root;
    }

}