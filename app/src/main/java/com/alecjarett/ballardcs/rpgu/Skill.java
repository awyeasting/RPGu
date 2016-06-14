package com.alecjarett.ballardcs.rpgu;

import java.io.Serializable;

/**
 * Skills that the user levels up
 */
public class Skill implements Serializable{
    private String skillLabel;
    private int xp;

    /**
     * Create a skill object with data parsed in
     * @param skillLabel The skill's name
     * @param xp The current xp of the skill
     */
    public Skill(String skillLabel, int xp){
        this.skillLabel = skillLabel;
        this.xp = xp;
    }

    /**
     * Gets skill label
     * @return Skill label
     */
    public String getSkillLabel() { return skillLabel; }

    /**
     * Get current xp
     * @return Current xp value
     */
    public int getXP() { return xp; }


}
