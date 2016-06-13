package com.alecjarett.ballardcs.rpgu;

import java.util.UUID;

/**
 * Created by LundHopkins on 5/12/2016.
 */
public class RPGuAchievement {

    private int quantityToDo;
    private int quantityDone;
    private String label;
    private String description;
    private int xp;
    private String categoryLabel; //skill for achievement
    private String id;

    /**
     * Generates a completely new achievement that does not connect to an existing achievement
     * @param quantityToDo The total times the action must be done to complete it
     * @param quantityDone The number of times an achievement has been completed
     * @param label The label that briefly describes an achievement
     * @param description The description of an achievement
     * @param xp The xp gained from completion
     * @param categoryLabel The skill that the xp will go to on completion
     */
    public RPGuAchievement(int quantityToDo, int quantityDone, String label, String description, int xp, String categoryLabel){
        this.quantityToDo=quantityToDo;
        this.quantityDone=quantityDone;
        this.label=label;
        this.description=description;
        this.xp=xp;
        this.categoryLabel=categoryLabel;
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Generates a new achievement that connects to an existing achievement
     * @param quantityToDo The total times the action must be done to complete it
     * @param quantityDone The number of times an achievement has been completed
     * @param label The label that briefly describes an achievement
     * @param description The description of an achievement
     * @param xp The xp gained from completion
     * @param categoryLabel The skill that the xp will go to on completion
     * @param id The id of an existing achievement
     */
    public RPGuAchievement(int quantityToDo, int quantityDone, String label, String description, int xp, String categoryLabel, String id){
        this.quantityToDo=quantityToDo;
        this.quantityDone=quantityDone;
        this.label=label;
        this.description=description;
        this.xp=xp;
        this.categoryLabel=categoryLabel;
        this.id=id;
    }

    public int getQuantityToDo() { return quantityToDo; }
    public int getQuantityDone() { return quantityDone; }
    public String getLabel() { return label; }
    public String getDescription() { return description; }
    public int getXp() { return xp; }
    public String getCategoryLabel() { return categoryLabel; }
    public String getId() { return id; }
}
