package com.alecjarett.ballardcs.rpgu.MainFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alecjarett.ballardcs.rpgu.MainActivity;
import com.alecjarett.ballardcs.rpgu.R;
import com.alecjarett.ballardcs.rpgu.Skill;
import com.alecjarett.ballardcs.rpgu.SkillsListAdapter;

import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alec Yeasting on 3/31/2016.
 */
public class StatsFragment extends Fragment{

    private SkillsListAdapter skillsAdapter;
    private MainActivity parent;

    public StatsFragment(){}

    public void setParentActivity(MainActivity parent){
        this.parent=parent;
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

        List<Skill> skills = parent.loadSkills();

        skillsAdapter = new SkillsListAdapter(getActivity(), skills);
        //TODO: Put skill information into adapter and develop skill information hierarchy

        //Find the LinearLayout for the skills
        LinearLayout skillsLinearLayout = (LinearLayout) root.findViewById(R.id.skills_linear_layout);

        int count = skillsAdapter.getCount();
        for(int i = 0; i < count; i++){
            skillsLinearLayout.addView(skillsAdapter.getView(i,null,null));
        }

        return root;
    }

}