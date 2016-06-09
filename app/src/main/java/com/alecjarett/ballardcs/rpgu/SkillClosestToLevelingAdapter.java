package com.alecjarett.ballardcs.rpgu;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Alec Yeasting on 5/11/2016.
 */
public class SkillClosestToLevelingAdapter {

    private final Activity context;
    private final Skill skill;

    public SkillClosestToLevelingAdapter (Activity context, Skill skill) {
        this.context = context;
        this.skill = skill;
    }

    public View getView (ViewGroup parent) {
        Skill skill = this.skill;

        int currentXP = skill.getXP();
        int skillLevel = ExperienceFunctions.getLevel(currentXP);
        int nextLevelXP = ExperienceFunctions.getExperience(skillLevel + 1);
        int levelBaseXP = ExperienceFunctions.getExperience(skillLevel);

        View root = LayoutInflater.from(getContext()).inflate(R.layout.list_item_skill, parent, false);

        //Load image icon based upon the skill name (lowercase) + "_icon"
        ImageView skillIcon = (ImageView) root.findViewById(R.id.skill_icon_image);
        try {
            skillIcon.setImageResource(this.getContext()
                    .getResources()
                    .getIdentifier(skill.getSkillLabel().toLowerCase()+"_icon",
                            "drawable",
                            this.getContext().getPackageName()));
        }catch (Exception e){
            Log.e("hi,", "problem loading skill image");
        }

        //Set the skill name text
        TextView skillLabel = (TextView) root.findViewById(R.id.skill_name);
        skillLabel.setText(skill.getSkillLabel());

        //Set the level number text
        TextView levelLabel = (TextView) root.findViewById(R.id.level_signifier_text);
        levelLabel.setText("Level " + skillLevel + " " + ((MainActivity)getContext()).getLevelTitle(skillLevel));

        //Set the exp to next level text
        TextView expToNextLevel = (TextView) root.findViewById(R.id.experience_to_next_level);
        expToNextLevel.setText(nextLevelXP-currentXP + " xp to go");

        //Set current level text
        TextView currentLevel = (TextView) root.findViewById(R.id.current_level_progress);
        currentLevel.setText(skillLevel+"");

        //Set next level text
        TextView nextLevel = (TextView) root.findViewById(R.id.next_level_progress);
        nextLevel.setText((skillLevel+1)+"");

        //Load progress bar
        ProgressBar progressBar = (ProgressBar)root.findViewById(R.id.skill_progress_bar);

        double levelProgress = (double)(currentXP-levelBaseXP)/(double)(nextLevelXP-levelBaseXP);

        progressBar.setProgress((int)(levelProgress*100));


        return root;
    }

    private Activity getContext(){ return context; }
}
