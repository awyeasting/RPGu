package com.alecjarett.ballardcs.rpgu.MainFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alecjarett.ballardcs.rpgu.MainActivity;
import com.alecjarett.ballardcs.rpgu.R;
import com.alecjarett.ballardcs.rpgu.Skill;
import com.alecjarett.ballardcs.rpgu.SkillDetailActivity;
import com.alecjarett.ballardcs.rpgu.SkillsListAdapter;

import java.util.List;

/**
 * Created by Alec Yeasting on 3/31/2016.
 */
public class StatsFragment extends Fragment{

    private SkillsListAdapter skillsAdapter;

    public StatsFragment(){}

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

        List<Skill> skills = ((MainActivity)getActivity()).loadSkills();

        skillsAdapter = new SkillsListAdapter(getActivity(), skills);
        //TODO: Put skill information into adapter and develop skill information hierarchy

        //Find the LinearLayout for the skills
        LinearLayout skillsLinearLayout = (LinearLayout) root.findViewById(R.id.skills_linear_layout);

        int count = skillsAdapter.getCount();
        for(int i = 0; i < count; i++){
            skillsLinearLayout.addView(skillsAdapter.getView(i, null, null));
            skillsLinearLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Log.i("hi, ", "skills element " + ((Skill)v.getTag()).getSkillLabel() + " clicked");
                    Intent intent = new Intent(getActivity(), SkillDetailActivity.class);
                    intent.putExtra("Skill", ((Skill)v.getTag()));
                    startActivity(intent);
                }
            });
        }

        return root;
    }

}