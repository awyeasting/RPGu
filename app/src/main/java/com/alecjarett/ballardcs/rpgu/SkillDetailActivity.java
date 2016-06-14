package com.alecjarett.ballardcs.rpgu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


public class SkillDetailActivity extends AppCompatActivity {
    public String skill;

    //When a SkillDetailActivity is created:
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set main view
        setContentView(R.layout.skill_detail_activity);
        //Get skill data passed in to detail activity
        Bundle extras = getIntent().getExtras();
        Skill skill = (Skill)(extras.get("Skill"));

        //Set skill icon
        ImageView icon = (ImageView) findViewById(R.id.detail_page_icon);
        try {
            icon.setImageResource(this
                    .getResources()
                    .getIdentifier((skill.getSkillLabel()).toLowerCase() + "_icon",
                            "drawable",
                            this.getPackageName()));
        }catch (Exception e){
            Log.e("hi,", "problem loading skill image");
        }

        //Set skill name
        TextView skillName = (TextView) findViewById(R.id.detail_page_skill_title);
        skillName.setText(skill.getSkillLabel());

        //Set current skill level and level title
        TextView currentLevelAndLevelTitle = (TextView) findViewById(R.id.detail_page_level_and_level_title);
        currentLevelAndLevelTitle.setText("Level " + ExperienceFunctions.getLevel(skill.getXP()) + " " + ((MainActivity)getParent()).getLevelTitle(ExperienceFunctions.getLevel(skill.getXP())));

        //Set current xp earned
        TextView xp = (TextView) findViewById(R.id.detail_page_xp);
        xp.setText("XP: " + skill.getXP());

        //Set xp needed until next level up
        TextView xpToLevel = (TextView) findViewById(R.id.detail_page_xp_to_next_level);
        xpToLevel.setText("XP To Next Level: " + ExperienceFunctions.getExperienceToNextLevel(skill.getXP()));
    }

}
