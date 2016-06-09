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
    private String id;

    public RPGuActivity(int quantityToDo,int quantityDone,String label,String description, int xp, String categoryLabel, ActivitiesAdapter.ActivityType activityType){
        this.quantityToDo=quantityToDo;
        this.quantityDone=quantityDone;
        this.label=label;
        this.description=description;
        this.xp=xp;
        this.categoryLabel=categoryLabel;
        this.activityType=activityType;
        this.id = System.currentTimeMillis() + "";
    }

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
