package com.alecjarett.ballardcs.rpgu;

/**
 * Created by Alec Yeasting on 4/29/2016.
 */
public class Skill {
    private String skillLabel;
    private int xp;

    public Skill(String skillLabel, int xp){
        this.skillLabel = skillLabel;
        this.xp = xp;
    }

    public String getSkillLabel() { return skillLabel; }
    public int getXP() { return xp; }

}
