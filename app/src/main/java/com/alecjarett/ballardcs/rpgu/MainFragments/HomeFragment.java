package com.alecjarett.ballardcs.rpgu.MainFragments;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alecjarett.ballardcs.rpgu.ActivitiesAdapter;
import com.alecjarett.ballardcs.rpgu.R;
import com.alecjarett.ballardcs.rpgu.RPGuActivity;
import com.alecjarett.ballardcs.rpgu.Skill;
import com.alecjarett.ballardcs.rpgu.SkillsListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alec Yeasting on 3/31/2016.
 */
public class HomeFragment extends Fragment{

    public HomeFragment() {
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
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //Set days active circle color
        View daysActiveCircle = root.findViewById(R.id.days_active_circle);
        ((GradientDrawable)daysActiveCircle.getBackground()).setColor(getContext().getResources().getColor(R.color.colorPrimary));

        //Set activities almost complete
        LinearLayout activitiesAlmostComplete = (LinearLayout) root.findViewById(R.id.activities_almost_complete);
        List<RPGuActivity> activities = new ArrayList<RPGuActivity>();
            activities.add(new RPGuActivity(8, 10, "Running", "Run a mile in my shoes", 10000, "endurance", RPGuActivity.ActivityType.Weekly));
        ActivitiesAdapter activitiesAdapter = new ActivitiesAdapter(getActivity(),activities);

        int activitiesCount = activitiesAdapter.getCount();
        for(int i = 0; i < activitiesCount; i++){
            activitiesAlmostComplete.addView(activitiesAdapter.getView(i,null,null));
        }

        //Set the closest skill to leveling up
        LinearLayout closestSkillToLevel = (LinearLayout) root.findViewById(R.id.skill_closest_to_leveling);
        List<Skill> closestSkill = new ArrayList<Skill>();
            closestSkill.add(new Skill("Wisdom",190));
        SkillsListAdapter skillAdapter = new SkillsListAdapter(getActivity(),closestSkill);

        if(skillAdapter.getCount()>0)
            closestSkillToLevel.addView(skillAdapter.getView(0,null,null));

        return root;
    }

}