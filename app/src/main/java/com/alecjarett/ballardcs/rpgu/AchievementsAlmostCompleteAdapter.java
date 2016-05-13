package com.alecjarett.ballardcs.rpgu;

    import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;
/**
 * Created by LundHopkins on 5/12/2016.
 */
    public class AchievementsAlmostCompleteAdapter extends ArrayAdapter<RPGuAchievement> {

        public AchievementsAlmostCompleteAdapter(Activity context, List<RPGuAchievement> achievementList){
            super(context,0,achievementList);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            RPGuAchievement achievement = getItem(position);
            View root = LayoutInflater.from(getContext()).inflate(R.layout.list_item_achievements_almost_complete, parent, false);

            //Set the achievement category text
            TextView achievementCategoryLabel = (TextView) root.findViewById(R.id.achievement_label);
            achievementCategoryLabel.setText(achievement.getLabel());

            //Set the activity description text
            TextView achievementDescription = (TextView) root.findViewById(R.id.achievement_description);
            achievementDescription.setText(achievement.getDescription());

            //Set the achievement xp gain
            TextView xpGained = (TextView) root.findViewById(R.id.achievement_xp_gain);
            xpGained.setText("+"+achievement.getXp()+" xp");

            if(achievement.getQuantityToDo() > 1) {
                ProgressBar progressBar = (ProgressBar) root.findViewById(R.id.achievement_progress_bar);
                progressBar.setProgress(100 * achievement.getQuantityDone() / achievement.getQuantityToDo());
                progressBar.setVisibility(View.VISIBLE);

                TextView quantityDone = (TextView) root.findViewById(R.id.achievement_quantity_done);
                quantityDone.setText(achievement.getQuantityDone()+" /");
                quantityDone.setVisibility(View.VISIBLE);

                TextView quantityToDo = (TextView) root.findViewById(R.id.achievement_quantity_to_do);
                quantityToDo.setText(" " + achievement.getQuantityToDo());
                quantityToDo.setVisibility(View.VISIBLE);

            }

            if(position == this.getCount()-1) {
                View dividerLine = root.findViewById(R.id.divider_line);
                dividerLine.setVisibility(View.GONE);
            }

            return root;
        }
    }

