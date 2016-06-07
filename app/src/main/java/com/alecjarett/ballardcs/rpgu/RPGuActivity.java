package com.alecjarett.ballardcs.rpgu;

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

    public RPGuActivity(int quantityToDo,int quantityDone,String label,String description, int xp, String categoryLabel){
        this.quantityToDo=quantityToDo;
        this.quantityDone=quantityDone;
        this.label=label;
        this.description=description;
        this.xp=xp;
        this.categoryLabel=categoryLabel;
    }

    public RPGuActivity(int quantityToDo,int quantityDone,String label,String description, int xp, String categoryLabel, ActivitiesAdapter.ActivityType activityType){
        this.quantityToDo=quantityToDo;
        this.quantityDone=quantityDone;
        this.label=label;
        this.description=description;
        this.xp=xp;
        this.categoryLabel=categoryLabel;
        this.activityType=activityType;
    }

    public int getQuantityToDo() { return quantityToDo; }
    public int getQuantityDone() { return quantityDone; }
    public String getLabel() { return label; }
    public String getDescription() { return description; }
    public int getXp() { return xp; }
    public String getCategoryLabel() { return categoryLabel; }
    public ActivitiesAdapter.ActivityType getActivityType() { return activityType; }

}
