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

    //MainActivity holds all user skills, activities, and achievements in lists.
    private List<Skill> skillsList;
    private List<RPGuActivity> dailiesList;
    private List<RPGuActivity> weekliesList;
    private List<RPGuActivity> monthliesList;
    private List<RPGuAchievement> achievementsList;

    /**
     * Called when app is created, sets up UI for main page.
     * @param savedInstanceState The saved instance state from last app use
     */
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

    /**
     * Sets up the UI for 'swipe' layout
     * @param viewPager the viewPager created in onCreate
     */
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

    /**
     * Loads current activities for user into a list.
     * @return The list of current activities
     */
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

    /**
     * Loads daily activities from database for user
     * @return List of daily activities for user
     */
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

    /**
     * Loads weekly activities from database for user
     * @return List of weekly activities for user
     */
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

    /**
     * Loads monthly activities from database for user
     * @return List of monthly activities for user
     */
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

    /**
     * Generates user activities from a generalized list of activities
     * @param skills The skills that the user trains
     * @param type The activity type (Daily, Weekly, Monthly) for the activities to generate
     * @return The list of activities to be presented to the user
     */
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

    /**
     * Loads current achievements for user into a list.
     * @return The list of current achievements
     */
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

    /**
     * Generates user achievements from a generalized list of achievements
     * @return The list of achievements to be presented to the user
     */
    private List<RPGuAchievement> generateBaseAchievements() {
        List<RPGuAchievement> achievements = new ArrayList<RPGuAchievement>();

        //TODO: generate achievements


        achievements.add(new RPGuAchievement(15, 0, "Balance", "Stand on one foot for 30 seconds", 100000, "balance"));
        achievements.add(new RPGuAchievement(20, 0, "Cooking", "Cook a main dish", 200000, "cooking"));
        achievements.add(new RPGuAchievement(15, 0, "Running", "Run a mile", 300000, "endurance"));
        achievements.add(new RPGuAchievement(20, 0, "Yoga", "Do 5 leg stretches for each leg", 50000, "flexibility"));
        achievements.add(new RPGuAchievement(30, 0, "Strength", "Do 10 push-ups", 150000, "strength"));
        achievements.add(new RPGuAchievement(15, 0, "Reading", "Run an online article", 250000, "wisdom"));

        return achievements;
    }

    /**
     * Loads Skills objects with current XP values for UI use
     * @return The list of Skills for the user
     */
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
            editor.commit();
        }
        return skillsList;
    }

    /**
     * Gives the user XP in a certain Skill for completing an action
     * @param skillName The skill that the XP should be added to
     * @param xp The XP that the user should gain from an action
     * @return
     */
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
                        editor.commit();
                        return true;
                    }
                }
            } else {
                Log.e("hi,", "Invalid skillName input in addXPToSkill,"+skillName );
            }
        } catch (Exception e) {
            Log.e("hi,", "Error in addXPToSkill");
        }
        return false;
    }

    /**
     * Finds the number of days between two dates
     * @param startDate The first date as a Calender object
     * @param endDate The second date as a Calender object
     * @return The number of days in between startDate and endDate
     */
    public static int daysBetween(Calendar startDate, Calendar endDate) {
        long start = startDate.getTimeInMillis();
        long end = endDate.getTimeInMillis();
        long diff = end - start + 1;
        Long daysBetween = diff / 86400000;
        return daysBetween.intValue();
    }

    /**
     * Converts a time in milliseconds to a Calender object
     * @param millis The timestamp in milliseconds
     * @return The Calender object set at time 'millis'
     */
    public static Calendar convertMillisToCalender(long millis){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(t);
        return c;
    }

    /**
     * Converts a Calender object to a time in milliseconds
     * @param c The Calender object set at a specific time
     * @return The timestamp in milliseconds in that calender object
     */
    public static long convertCalenderToMillis(Calendar c){
        return c.getTimeInMillis();
    }

    /**
     * Gets the current date as a Calender object
     * @return
     */
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

    /**
     * Sets current day to active, helps user know how long they have been active
     */
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

    /**
     * Gets the level title for a given level
     * @param level The level of the user
     * @return The level title for the given level
     */
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

    /**
     * Class for 'swipe' layout in main UI
     */
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