package com.alecjarett.ballardcs.rpgu;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.alecjarett.ballardcs.rpgu.MainFragments.ActivitiesFragment;
import com.alecjarett.ballardcs.rpgu.MainFragments.HomeFragment;
import com.alecjarett.ballardcs.rpgu.MainFragments.StatsFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private List<Skill> skillsList;
    private List<RPGuActivity> dailiesList;
    private List<RPGuActivity> weekliesList;
    private List<RPGuActivity> monthliesList;
    private List<RPGuAchievement> achievementsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        HomeFragment homeFragment = new HomeFragment();
        StatsFragment statsFragment = new StatsFragment();
        ActivitiesFragment activitiesFragment = new ActivitiesFragment();

        adapter.addFragment(homeFragment, "Home");
        adapter.addFragment(statsFragment, "Stats");
        adapter.addFragment(activitiesFragment, "Activities");

        viewPager.setAdapter(adapter);
    }

    public List<RPGuActivity> loadCurrentActivities() {
        List<RPGuActivity> currentActivities = new ArrayList<RPGuActivity>();

        List<RPGuActivity> dailies = loadCurrentDailies();
        for(RPGuActivity activity : dailies)
            currentActivities.add(activity);

        List<RPGuActivity> weeklies = loadCurrentWeeklies();
        for(RPGuActivity activity : weeklies)
            currentActivities.add(activity);

        List<RPGuActivity> monthlies = loadCurrentMonthlies();
        for(RPGuActivity activity : monthlies)
            currentActivities.add(activity);

        return currentActivities;
    }

    public List<RPGuActivity> loadCurrentDailies() {

        //If dailies haven't already been loaded then load them
        if (dailiesList == null) {

            ActivitiesDBHandler dbHandler =
                    new ActivitiesDBHandler(getApplicationContext(), null, null, 1);
            List<RPGuActivity> dailies = dbHandler.getAllDailies();

            //if there aren't any saved dailies then generate them and save them
            if(dailies.size() == 0) {
                dailies = generateActivities(loadSkills(), ActivitiesAdapter.ActivityType.Daily);

                for(RPGuActivity daily : dailies){
                    dbHandler.addDaily(daily);
                }
            }

            dailiesList = dailies;
        }
        return dailiesList;
    }

    public List<RPGuActivity> loadCurrentWeeklies() {

        //If weeklies haven't already been loaded then load them
        if (weekliesList == null) {

            ActivitiesDBHandler dbHandler =
                    new ActivitiesDBHandler(getApplicationContext(), null, null, 1);
            List<RPGuActivity> weeklies = dbHandler.getAllWeeklies();

            //if there aren't any saved dailies then generate them and save them
            if(weeklies.size() == 0) {
                weeklies = generateActivities(loadSkills(), ActivitiesAdapter.ActivityType.Weekly);

                for(RPGuActivity weekly : weeklies){
                    dbHandler.addWeekly(weekly);
                }
            }

            weekliesList = weeklies;
        }
        return weekliesList;
    }

    public List<RPGuActivity> loadCurrentMonthlies() {

        //If monthlies haven't already been loaded then load them
        if (monthliesList == null) {

            ActivitiesDBHandler dbHandler =
                    new ActivitiesDBHandler(getApplicationContext(), null, null, 1);
            List<RPGuActivity> monthlies = dbHandler.getAllMonthlies();

            //if there aren't any saved dailies then generate them and save them
            if(monthlies.size() == 0) {
                monthlies = generateActivities(loadSkills(), ActivitiesAdapter.ActivityType.Monthly);

                for(RPGuActivity monthly : monthlies){
                    dbHandler.addMonthly(monthly);
                }
            }

            monthliesList = monthlies;
        }
        return monthliesList;
    }


    public List<RPGuActivity> generateActivities(List<Skill> skills, ActivitiesAdapter.ActivityType type) {

        List<RPGuActivity> activities = new ArrayList<RPGuActivity>();

        for(Skill skill : skills) {

            int quantity = 1;

            switch (type) {
                case Daily:
                    quantity = 1;
                    break;
                case Weekly:
                    //Generate a quantity from 3-5
                    quantity = 3 + (int)Math.floor(Math.random()*3);
                    break;
                case Monthly:
                    //Generate a quantity from 7-11
                    quantity = 7 + (int)Math.floor(Math.random()*5);
                    break;
            }

            String label = "";
            String description = "";
            int xp = 0;
            String categoryLabel = skill.getSkillLabel();

            switch (skill.getSkillLabel().toLowerCase()) {
                case "balance":
                    label = "Single Leg Deadlift";
                    description = "Do 6 single leg deadlifts";
                    break;
                case "cooking":
                    label = "Baking";
                    description = "Bake a cake";
                    break;
                case "endurance":
                    label = "Running";
                    description = "Run a mile";
                    break;
                case "flexibility":
                    label = "Stretching";
                    description = "Do a standing forward bend";
                    break;
                case "strength":
                    label = "Lifting";
                    description = "Lift 10 lb weights until failure";
                    break;
                case "wisdom":
                    label = "Reading";
                    if(quantity == 1) {
                        description = "Read a world news article";
                    }else if(quantity < 6){
                        description = "Read a 100+ page book";
                    }else {
                        description = "Read a 200+ page book";
                    }
                    break;
            }

            xp = (int)(110 * Math.pow((quantity), 2));
            if(skill.getSkillLabel().equals("wisdom")){
                quantity = 1;
            }

            activities.add(new RPGuActivity(quantity, 0, label, description, xp, categoryLabel, type));
        }

        return activities;
    }

    public List<RPGuAchievement> loadCurrentAchievements() {

        //If achievements haven't already been loaded then load them
        if (achievementsList == null) {

            AchievementsDBHandler dbHandler =
                    new AchievementsDBHandler(getApplicationContext(), null, null, 1);
            List<RPGuAchievement> achievements = dbHandler.getAllAchievements();

            //if there aren't any saved achievements then generate them and save them
            if(achievements.size() == 0) {
                achievements = generateBaseAchievements();

                for(RPGuAchievement achievement : achievements){
                    dbHandler.addAchievement(achievement);
                }
            }
            achievementsList = achievements;
        }
        return achievementsList;
    }

    private List<RPGuAchievement> generateBaseAchievements() {
        List<RPGuAchievement> achievements = new ArrayList<RPGuAchievement>();

        //TODO: generate achievements

        achievements.add(new RPGuAchievement(10, 8, "Running", "Run a marathon", 100000, "endurance"));
        achievements.add(new RPGuAchievement(1, 0, "Running", "Run a marathon", 100000, "endurance"));

        return achievements;
    }

    public List<Skill> loadSkills() {

        if(skillsList == null) {
            List<Skill> skills = new ArrayList<Skill>();

            SharedPreferences prefs = getSharedPreferences(
                    getString(R.string.preference_file_key),
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            //Gets the xp value for each skill from shared preferences
            //      if there's no xp value then put in zero
            int balanceXP = prefs.getInt("balance_xp", -1);
            if (balanceXP == -1) {
                editor.putInt("balance_xp", 0);
                balanceXP = 0;
            }
            int cookingXP = prefs.getInt("cooking_xp", -1);
            if (cookingXP == -1) {
                editor.putInt("cooking_xp", 0);
                cookingXP = 0;
            }
            int enduranceXP = prefs.getInt("endurance_xp", -1);
            if (enduranceXP == -1) {
                editor.putInt("endurance_xp", 0);
                enduranceXP = 0;
            }
            int flexibilityXP = prefs.getInt("flexibility_xp", -1);
            if (flexibilityXP == -1) {
                editor.putInt("flexibility_xp", 0);
                flexibilityXP = 0;
            }
            int strengthXP = prefs.getInt("strength_xp", -1);
            if (strengthXP == -1) {
                editor.putInt("strength_xp", 0);
                strengthXP = 0;
            }
            int wisdomXP = prefs.getInt("wisdom_xp", -1);
            if (wisdomXP == -1) {
                editor.putInt("wisdom_xp", 0);
                wisdomXP = 0;
            }

            //Set the skills values
            skills.add(new Skill("Balance", balanceXP));
            skills.add(new Skill("Cooking", cookingXP));
            skills.add(new Skill("Endurance", enduranceXP));
            skills.add(new Skill("Flexibility", flexibilityXP));
            skills.add(new Skill("Strength", strengthXP));
            skills.add(new Skill("Wisdom", wisdomXP));

            skillsList = skills;
        }
        return skillsList;
    }

    public boolean addXPToSkill(String skillName, int xp){
        SharedPreferences prefs = getSharedPreferences(
                getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        int oldXP;

        try {
            oldXP = prefs.getInt(skillName.toLowerCase() + "_xp", -1);
            if(oldXP != -1) {
                for (int i = 0; i < skillsList.size(); i++) {
                    if (skillsList.get(i).getSkillLabel().toLowerCase().equals(skillName.toLowerCase())) {
                        skillsList.add(new Skill(skillsList.get(i).getSkillLabel(), oldXP + xp));
                        skillsList.remove(i);
                        editor.putInt(skillName.toLowerCase() + "_xp", oldXP + xp);
                        return true;
                    }
                }
            } else {
                Log.e("hi,", "Invalid skillName input in addXPToSkill");
            }
        } catch (Exception e) {
            Log.e("hi,", "Error in addXPToSkill");
        }
        return false;
    }

    public static int daysBetween(Calendar startDate, Calendar endDate) {
        long start = startDate.getTimeInMillis();
        long end = endDate.getTimeInMillis();
        long diff = end - start + 1;
        Long daysBetween = diff / 86400000;
        return daysBetween.intValue();
    }


    public static Calendar convertMillisToCalender(long t){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(t);
        return c;
    }

    public static long convertCalenderToMillis(Calendar c){
        return c.getTimeInMillis();
    }


    public final Calendar getDate() {
        return Calendar.getInstance();
    }

    public int getDaysActive(){
        SharedPreferences prefs = getSharedPreferences(
                getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);

        int daysActive = (daysBetween(convertMillisToCalender(prefs.getLong("first_date_active", 0)),convertMillisToCalender(prefs.getLong("last_date_active", 0 ))))+1;
        return daysActive;
    }

    public void saveDayAsActive() {
        long currentDate = convertCalenderToMillis(getDate());
        SharedPreferences prefs = getSharedPreferences(
                getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if(prefs.getLong("first_date_active", 0) == 0) {
            editor.putLong("first_date_active", currentDate);
            editor.putLong("last_date_active", currentDate);
            editor.commit();
        }else{
            int daysBetweenTodayAndLastActiveDay = (daysBetween(convertMillisToCalender(prefs.getLong("last_date_active", 0)),getDate()));
            if(daysBetweenTodayAndLastActiveDay<=1){
                editor.putLong("last_date_active", currentDate);
                editor.commit();
            }else if(daysBetweenTodayAndLastActiveDay > 1)
                editor.putLong("first_date_active", currentDate);
            editor.putLong("last_date_active", currentDate);
                editor.commit();
            }

    }



    public static String getLevelTitle(int level){
        int levelValue = (int)(Math.ceil(((double)level)/10));
        switch (levelValue) {
            case 1:
                return "Novice";
            case 2:
                return "Apprentice";
            case 3:
                return "Initiate";
            case 4:
                return "Journeyman";
            case 5:
                return "Adept";
            case 6:
                return "Master";
            case 7:
                return "Grandmaster";
            case 8:
                return "Legend";
            case 9:
                return "Exalted";
            case 10:
                return "Transcendent";
            default:
                return "error!";

        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}