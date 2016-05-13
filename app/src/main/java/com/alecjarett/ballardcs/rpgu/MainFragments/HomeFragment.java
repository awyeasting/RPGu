package com.alecjarett.ballardcs.rpgu.MainFragments;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alecjarett.ballardcs.rpgu.AchievementsAlmostCompleteAdapter;
import com.alecjarett.ballardcs.rpgu.ActivitiesInProgressAdapter;
import com.alecjarett.ballardcs.rpgu.R;
import com.alecjarett.ballardcs.rpgu.RPGuAchievement;
import com.alecjarett.ballardcs.rpgu.RPGuActivity;
import com.alecjarett.ballardcs.rpgu.Skill;
import com.alecjarett.ballardcs.rpgu.SkillClosestToLevelingAdapter;

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
            activities.add(new RPGuActivity(10, 8, "Running", "Run a mile in my shoes", 10000, "endurance"));
            activities.add(new RPGuActivity(0, 1, "Running", "Run a mile in my shoes", 10000, "endurance"));
        ActivitiesInProgressAdapter activitiesInProgressAdapter = new ActivitiesInProgressAdapter(getActivity(),activities);

        int activitiesCount = activitiesInProgressAdapter.getCount();
        for(int i = 0; i < activitiesCount; i++){
            activitiesAlmostComplete.addView(activitiesInProgressAdapter.getView(i, null, null));
        }

        //Set the closest skill to leveling up
        LinearLayout closestSkillToLevel = (LinearLayout) root.findViewById(R.id.skill_closest_to_leveling);
        Skill closestSkill = new Skill("Wisdom",190);
        SkillClosestToLevelingAdapter skillClosestToLevelingAdapter = new SkillClosestToLevelingAdapter(getActivity(),closestSkill);

        closestSkillToLevel.addView(skillClosestToLevelingAdapter.getView(null));

        //Set activities almost complete
        LinearLayout achievementsAlmostComplete = (LinearLayout) root.findViewById(R.id.achievements_almost_complete);
        List<RPGuAchievement> achievements = new ArrayList<RPGuAchievement>();
        achievements.add(new RPGuAchievement(10, 8, "Running", "Run a marathon", 100000, "endurance"));
        achievements.add(new RPGuAchievement(0, 1, "Running", "Run a marathon", 100000, "endurance"));
        AchievementsAlmostCompleteAdapter achievementsAlmostCompleteAdapter = new AchievementsAlmostCompleteAdapter(getActivity(),achievements);

        int achievementsCount = achievementsAlmostCompleteAdapter.getCount();
        for(int i = 0; i < achievementsCount; i++){
            achievementsAlmostComplete.addView(achievementsAlmostCompleteAdapter.getView(i, null, null));
        }

        return root;
    }

}