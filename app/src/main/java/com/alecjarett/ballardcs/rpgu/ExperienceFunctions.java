package com.alecjarett.ballardcs.rpgu;

/**
 * Created by Alec Yeasting on 5/3/2016.
 */
public class ExperienceFunctions {

    /**
     * Returns the level for a given amount of experience
     * @param experience The experience a skill is at
     * @return The level experience translates to
     */
    public static int getLevel(int experience){
        //Based on the equation:
        //      experience = 50(level^2 - level)
        return (int)Math.floor((1+Math.sqrt(1-4*(-experience/50)))/2);
    }

    /**
     * Returns the experience required for a given level
     * @param level The level to find the requirement for
     * @return The minimum experience required for a given level
     */
    public static int getExperience(int level){
        //Based on the equation:
        //      experience = 50(level^2 - level)
        return 50 *(level*level - level);
    }
}
