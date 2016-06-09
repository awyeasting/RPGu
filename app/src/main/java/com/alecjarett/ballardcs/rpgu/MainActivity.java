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

        for(RPGuActivity activity : loadCurrentDailies())
            currentActivities.add(activity);
        for(RPGuActivity activity : loadCurrentWeeklies())
            currentActivities.add(activity);
        for(RPGuActivity activity : loadCurrentMonthlies())
            currentActivities.add(activity);

        return currentActivities;
    }

    public List<RPGuActivity> loadCurrentDailies() {

        //If dailies haven't already been loaded then load them
        if (dailiesList == null) {

            ActivitiesDBHandler dbHandler = new ActivitiesDBHandler(getApplicationContext(), null, null, 1);
            List<RPGuActivity> dailies = dbHandler.getAllDailies();

            //if there aren't any saved dailies then generate them and save them
            if(dailies.size() == 0) {
                dailies.add(new RPGuActivity(1, 0, "Running", "Run a mile in my shoes", 10000, "endurance", ActivitiesAdapter.ActivityType.Daily));

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

            ActivitiesDBHandler dbHandler = new ActivitiesDBHandler(getApplicationContext(), null, null, 1);
            List<RPGuActivity> weeklies = dbHandler.getAllWeeklies();

            //if there aren't any saved dailies then generate them and save them
            if(weeklies.size() == 0) {
                weeklies.add(new RPGuActivity(4, 3, "Running", "Run a mile in my shoes", 10000, "endurance", ActivitiesAdapter.ActivityType.Weekly));

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

            ActivitiesDBHandler dbHandler = new ActivitiesDBHandler(getApplicationContext(), null, null, 1);
            List<RPGuActivity> monthlies = dbHandler.getAllMonthlies();

            //if there aren't any saved dailies then generate them and save them
            if(monthlies.size() == 0) {
                monthlies.add(new RPGuActivity(16, 8, "Running", "Run a mile in my shoes", 10000, "endurance", ActivitiesAdapter.ActivityType.Monthly));

                for(RPGuActivity monthly : monthlies){
                    dbHandler.addMonthly(monthly);
                }
            }

            monthliesList = monthlies;
        }
        return monthliesList;
    }

    public List<RPGuAchievement> loadCurrentAchievements() {
        List<RPGuAchievement> currentAchievements = new ArrayList<RPGuAchievement>();
            currentAchievements.add(new RPGuAchievement(10, 8, "Running", "Run a marathon", 100000, "endurance"));
            currentAchievements.add(new RPGuAchievement(1, 0, "Running", "Run a marathon", 100000, "endurance"));
        return currentAchievements;
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

    public int getDate() {
        Calendar c = Calendar.getInstance();
        return ((int)c.getTimeInMillis())*1000*60*60*24;
    }

    public int getDaysActive(){
        SharedPreferences prefs = getSharedPreferences(
                getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        return 1;
        //logic of this method is unfinished
    }

    public void saveDayAsActive(){
        int currentDate = getDate();
        SharedPreferences prefs = getSharedPreferences(
                getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if(prefs.getInt("first_date_active", 0) == 0) {
            editor.putInt("first_date_active", currentDate);
            editor.putInt("last_date_active", currentDate);
        }else{
            editor.putInt("last_date_active", currentDate);
        }
        if(prefs.getInt("last_date_active", 1) - prefs.getInt("first_day_active",0) > 1){
            editor.putInt("first_date_active", currentDate);
        }
        //logic of this method is unfinished
        editor.commit();
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