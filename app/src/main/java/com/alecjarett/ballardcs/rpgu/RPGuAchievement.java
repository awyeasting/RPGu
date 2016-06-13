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

    public RPGuAchievement(int quantityToDo, int quantityDone, String label, String description, int xp, String categoryLabel){
        this.quantityToDo=quantityToDo;
        this.quantityDone=quantityDone;
        this.label=label;
        this.description=description;
        this.xp=xp;
        this.categoryLabel=categoryLabel;
        this.id = UUID.randomUUID().toString();
    }

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
