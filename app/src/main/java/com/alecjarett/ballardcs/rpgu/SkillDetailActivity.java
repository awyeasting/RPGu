package com.alecjarett.ballardcs.rpgu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


public class SkillDetailActivity extends AppCompatActivity {
    public String skill;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skill_detail_activity);
        Bundle extras = getIntent().getExtras();
        Skill skill = (Skill)(extras.get("Skill"));


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

        TextView skillName = (TextView) findViewById(R.id.detail_page_skill_title);
        skillName.setText(skill.getSkillLabel());

        TextView currentLevelAndLevelTitle = (TextView) findViewById(R.id.detail_page_level_and_level_title);
        currentLevelAndLevelTitle.setText("Level " + ExperienceFunctions.getLevel(skill.getXP()) + ((MainActivity)getParent()).getLevelTitle(ExperienceFunctions.getLevel(skill.getXP())));

        TextView xp = (TextView) findViewById(R.id.detail_page_xp);
        xp.setText("XP: " + skill.getXP());

        TextView xpToLevel = (TextView) findViewById(R.id.detail_page_xp_to_next_level);
        xpToLevel.setText("XP To Next Level: " + ExperienceFunctions.getExperienceToNextLevel(skill.getXP()));
    }

}
