package com.alecjarett.ballardcs.rpgu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SkillDetailActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skill_detail_activity);
        Bundle extras = getIntent().getExtras();
        String skill = extras.getString("Skill");
        TextView skillView = (TextView) findViewById(R.id.skill_detail_text);
        skillView.setText(skill);
    }


}
