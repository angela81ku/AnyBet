package edu.northeastern.anybet.finalProject;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.anybet.R;

public class BetViewHolder extends RecyclerView.ViewHolder {
    public TextView betTitle;
    public TextView betUser1;
    public TextView betUser2;
    public TextView betAmount;
    public TextView endTime;


    public BetViewHolder(@NonNull View itemView, BetClickListener listener) {
        super(itemView);
        this.betTitle = itemView.findViewById(R.id.tvHomeBetTitle);
        this.betUser1 = itemView.findViewById(R.id.tvHomeUser1);
        this.betUser2 = itemView.findViewById(R.id.tvHomeUser2);
        this.betAmount = itemView.findViewById(R.id.tvHomeAmount);
        this.endTime = itemView.findViewById(R.id.tvHomeEndTime);

        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (listener != null) {
                    int position = getLayoutPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onBetClick(position);
                    }
                }
            }
        });
    }
}
