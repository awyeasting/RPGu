package com.alecjarett.ballardcs.rpgu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

        //Get some data about skill
        int currentXP = skill.getXP();
        int skillLevel = ExperienceFunctions.getLevel(currentXP);
        int nextLevelXP = ExperienceFunctions.getExperience(skillLevel + 1);
        int levelBaseXP = ExperienceFunctions.getExperience(skillLevel);

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
        TextView currentLevelAndLevelTitle = (TextView) findViewById(R.id.skill_detail_level_text_view);
        currentLevelAndLevelTitle.setText("Level " + skillLevel + " " + ((MainActivity)getParent()).getLevelTitle(skillLevel));

        //Set current xp earned
        TextView xp = (TextView) findViewById(R.id.skill_detail_xp_text_view);
        xp.setText("XP:\n" + currentXP);

        //Set xp needed until next level up
        TextView xpToLevel = (TextView) findViewById(R.id.skill_detail_level_up_xp_text_view);
        xpToLevel.setText("XP To Next Level:\n" + ExperienceFunctions.getExperienceToNextLevel(currentXP));

        //Set up progress bar and textviews next to it
        ProgressBar progressBar = (ProgressBar)(findViewById(R.id.skill_detail_progress_bar));

        double levelProgress = ((double)(currentXP-levelBaseXP))/((double)(nextLevelXP-levelBaseXP));

        progressBar.setProgress((int) (levelProgress * 100));

        TextView currentLevel = (TextView) findViewById(R.id.skill_detail_current_level_progress);
        currentLevel.setText(""+skillLevel);

        TextView nextLevel = (TextView) findViewById(R.id.skill_detail_next_level_progress);
        nextLevel.setText(""+skillLevel+1);
    }

}
