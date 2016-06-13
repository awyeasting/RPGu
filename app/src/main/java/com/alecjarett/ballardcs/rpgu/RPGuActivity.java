package com.alecjarett.ballardcs.rpgu;

import java.util.UUID;

/**
 * Created by bal_awyeasting on 5/6/2016.
 */
public class RPGuActivity {

    private int quantityToDo;
    private int quantityDone;
    private String label;
    private String description;
    private int xp;
    private String categoryLabel; //skill for activity
    private ActivitiesAdapter.ActivityType activityType;
    private String id;

    /**
     * Generates a completely new activity that does not connect to an existing activity
     * @param quantityToDo The total times the action must be done to complete it
     * @param quantityDone The number of times the action has been completed
     * @param label The label that briefly describes an activity
     * @param description The description of an activity
     * @param xp The xp gained from completion
     * @param categoryLabel The skill that the xp will go to on completion
     */
    public RPGuActivity(int quantityToDo,int quantityDone,String label,String description, int xp, String categoryLabel, ActivitiesAdapter.ActivityType activityType){
        this.quantityToDo=quantityToDo;
        this.quantityDone=quantityDone;
        this.label=label;
        this.description=description;
        this.xp=xp;
        this.categoryLabel=categoryLabel;
        this.activityType=activityType;
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Generates a new activity that connects to an existing activity
     * @param quantityToDo The total times the action must be done to complete it
     * @param quantityDone The number of times the action has been completed
     * @param label The label that briefly describes an activity
     * @param description The description of an activity
     * @param xp The xp gained from completion
     * @param categoryLabel The skill that the xp will go to on completion
     * @param id The id of an existing achievement
     */
    public RPGuActivity(int quantityToDo,int quantityDone,String label,String description, int xp, String categoryLabel, ActivitiesAdapter.ActivityType activityType, String id){
        this.quantityToDo=quantityToDo;
        this.quantityDone=quantityDone;
        this.label=label;
        this.description=description;
        this.xp=xp;
        this.categoryLabel=categoryLabel;
        this.activityType=activityType;
        this.id =id;
    }

    public void increaseQuantityDone(){
        quantityDone++;
    }
    public int getQuantityToDo() { return quantityToDo; }
    public int getQuantityDone() { return quantityDone; }
    public String getLabel() { return label; }
    public String getDescription() { return description; }
    public int getXp() { return xp; }
    public String getCategoryLabel() { return categoryLabel; }
    public ActivitiesAdapter.ActivityType getActivityType() { return activityType; }
    public String getId() { return id; }

}
