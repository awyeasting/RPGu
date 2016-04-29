package com.alecjarett.ballardcs.rpgu;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Alec Yeasting on 4/29/2016.
 */
public class SkillsListAdapter extends ArrayAdapter<Skill> {

    public SkillsListAdapter(Activity context, List<Skill> skillsList){
        super(context,0,skillsList);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Skill skill = getItem(position);
        View root = LayoutInflater.from(getContext()).inflate(R.layout.list_item_skill, parent, false);

        TextView skillLabel = (TextView) root.findViewById(R.id.skill_name);
        skillLabel.setText(skill.getSkillLabel());

        //TODO: Add more of the same
        return root;
    }
}
