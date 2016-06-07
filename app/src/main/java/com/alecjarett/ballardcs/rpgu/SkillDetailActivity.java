package com.alecjarett.ballardcs.rpgu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class SkillDetailActivity extends AppCompatActivity {
    public String skill;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skill_detail_activity);
        Bundle extras = getIntent().getExtras();
        skill = extras.getString("Skill");
    }



}
