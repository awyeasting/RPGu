package com.alecjarett.ballardcs.rpgu.MainFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alecjarett.ballardcs.rpgu.R;
import com.alecjarett.ballardcs.rpgu.Skill;
import com.alecjarett.ballardcs.rpgu.SkillsListAdapter;

import java.util.ArrayList;

/**
 * Created by Alec Yeasting on 3/31/2016.
 */
public class StatsFragment extends Fragment{

    private SkillsListAdapter skillsAdapter;

    public StatsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //The root view
        //Used to find views in fragment_stats
        View root = inflater.inflate(R.layout.fragment_stats, container, false);

        ArrayList<Skill> skills = new ArrayList<Skill>();
            skills.add(new Skill("Wisdom",9001));

        skillsAdapter = new SkillsListAdapter(getActivity(), skills);
        //TODO: Put skill information into adapter and develop skill information hierarchy

        //Find the list of skills in the stats page, then set adapter
        ListView skillsListView = (ListView) root.findViewById(R.id.skills_list_view);
        skillsListView.setAdapter(skillsAdapter);

        return root;
    }

}