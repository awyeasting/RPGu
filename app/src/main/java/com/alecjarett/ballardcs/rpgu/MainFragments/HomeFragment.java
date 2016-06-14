package com.alecjarett.ballardcs.rpgu.MainFragments;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alecjarett.ballardcs.rpgu.AchievementsAlmostCompleteAdapter;
import com.alecjarett.ballardcs.rpgu.ActivitiesInProgressAdapter;
import com.alecjarett.ballardcs.rpgu.ExperienceFunctions;
import com.alecjarett.ballardcs.rpgu.MainActivity;
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

    public HomeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        //Days active circle loading
        loadDaysActiveCircle(root);

        //Activities in progress loading
        loadActivitiesInProgress(root);

        //Skill closest to leveling loading
        loadSkillClosestToLeveling(root);

        //Achievements almost complete loading
        loadAchievementsAlmostComplete(root);


        return root;
    }

    /**
     * Loads the proper number and day word in the home page days active circle
     * @param root an instance of R.layout.fragment_home
     */
    private void loadDaysActiveCircle(View root) {
        View daysActiveCircle = root.findViewById(R.id.days_active_circle);
        ((GradientDrawable)daysActiveCircle.getBackground()).setColor(getContext().getResources().getColor(R.color.colorPrimary));

        TextView activeFor = (TextView)root.findViewById(R.id.active_for_days_number);
        activeFor.setText("" + ((MainActivity) getActivity()).getDaysActive());

        String dayOrDays = "";
        if(((MainActivity)getActivity()).getDaysActive() == 1){
            dayOrDays = "Day!";
        }else if(((MainActivity)getActivity()).getDaysActive()>1){
            dayOrDays = "Days!";
        }
        TextView dayOrDaysTextView = (TextView)root.findViewById(R.id.day_or_days_text);
        dayOrDaysTextView.setText(dayOrDays);
    }

    /**
     * Loads the activities in progress for the home page ui
     * @param root an instance of R.layout.fragment_home
     */
    private void loadActivitiesInProgress(View root) {
        LinearLayout activitiesAlmostComplete = (LinearLayout) root.findViewById(R.id.activities_almost_complete);
        List<RPGuActivity> activities = new ArrayList<RPGuActivity>();
        activities = ((MainActivity)getActivity()).loadCurrentActivities();

        // Filter out the activities that aren't in progress
        List<RPGuActivity> inProgressActivities = new ArrayList<RPGuActivity>();
        for (RPGuActivity activity : activities){
            if(activity.getQuantityDone() > 0 && activity.getQuantityToDo()-activity.getQuantityDone() > 0)
                inProgressActivities.add(activity);
        }
        ActivitiesInProgressAdapter activitiesInProgressAdapter = new ActivitiesInProgressAdapter(getActivity(),inProgressActivities);

        int activitiesCount = activitiesInProgressAdapter.getCount();
        for(int i = 0; i < activitiesCount; i++){
            activitiesAlmostComplete.addView(activitiesInProgressAdapter.getView(i, null, null));
        }
    }

    /**
     * Loads the skill that is closest to leveling in the home page ui
     * @param root an instance of R.layout.fragment_home
     */
    private void loadSkillClosestToLeveling(View root) {
        LinearLayout closestSkillLinearLayout = (LinearLayout) root.findViewById(R.id.skill_closest_to_leveling);

        List<Skill> skills = ((MainActivity)getActivity()).loadSkills();
        Skill closestSkill = null;
        for(Skill s : skills){
            if(closestSkill==null){
                closestSkill = s;
            }else if(ExperienceFunctions.getExperienceToNextLevel(s.getXP())<ExperienceFunctions.getExperienceToNextLevel(closestSkill.getXP())){
                closestSkill = s;
            }
        }

        SkillClosestToLevelingAdapter skillClosestToLevelingAdapter = new SkillClosestToLevelingAdapter(getActivity(),closestSkill);
        closestSkillLinearLayout.addView(skillClosestToLevelingAdapter.getView(null));
    }

    /**
     * Loads the achievements almost complete in the home page ui
     * @param root an instance of R.layout.fragment_home
     */
    private void loadAchievementsAlmostComplete(View root) {
        LinearLayout achievementsAlmostComplete = (LinearLayout) root.findViewById(R.id.achievements_almost_complete);
        List<RPGuAchievement> achievements = ((MainActivity)getActivity()).loadCurrentAchievements();

        AchievementsAlmostCompleteAdapter achievementsAlmostCompleteAdapter = new AchievementsAlmostCompleteAdapter(getActivity(),achievements);

        int achievementsCount = achievementsAlmostCompleteAdapter.getCount();
        for(int i = 0; i < achievementsCount; i++){
            achievementsAlmostComplete.addView(achievementsAlmostCompleteAdapter.getView(i, null, null));
        }
    }
}