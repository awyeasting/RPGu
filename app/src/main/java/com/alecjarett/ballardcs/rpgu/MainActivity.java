package com.alecjarett.ballardcs.rpgu;

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
            homeFragment.setParentActivity(this);
        StatsFragment statsFragment = new StatsFragment();
            statsFragment.setParentActivity(this);
        ActivitiesFragment activitiesFragment = new ActivitiesFragment();
            activitiesFragment.setParentActivity(this);

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
        skills.add(new Skill("Wisdom",1010));

        return skills;
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