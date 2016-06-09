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
        List<RPGuActivity> currentActivities = new ArrayList<RPGuActivity>();
            currentActivities.add(new RPGuActivity(1, 0, "Running", "Run a mile in my shoes", 10000, "endurance", ActivitiesAdapter.ActivityType.Daily));
        return currentActivities;
    }

    public List<RPGuActivity> loadCurrentWeeklies() {
        List<RPGuActivity> currentActivities = new ArrayList<RPGuActivity>();
            currentActivities.add(new RPGuActivity(4, 3, "Running", "Run a mile in my shoes", 10000, "endurance", ActivitiesAdapter.ActivityType.Weekly));
        return currentActivities;
    }

    public List<RPGuActivity> loadCurrentMonthlies() {
        List<RPGuActivity> currentActivities = new ArrayList<RPGuActivity>();
            currentActivities.add(new RPGuActivity(16, 8, "Running", "Run a mile in my shoes", 10000, "endurance", ActivitiesAdapter.ActivityType.Monthly));
        return currentActivities;
    }

    public List<RPGuAchievement> loadCurrentAchievements() {
        List<RPGuAchievement> currentAchievements = new ArrayList<RPGuAchievement>();
            currentAchievements.add(new RPGuAchievement(10, 8, "Running", "Run a marathon", 100000, "endurance"));
            currentAchievements.add(new RPGuAchievement(1, 0, "Running", "Run a marathon", 100000, "endurance"));
        return currentAchievements;
    }

    public List<Skill> loadSkills() {
        List<Skill> skills = new ArrayList<Skill>();

        skills.add(new Skill("Balance",110));
        skills.add(new Skill("Cooking",310));
        skills.add(new Skill("Endurance",510));
        skills.add(new Skill("Flexibility",710));
        skills.add(new Skill("Strength", 910));
        skills.add(new Skill("Wisdom", 1010));

        return skills;
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