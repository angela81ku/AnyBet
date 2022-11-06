package edu.northeastern.anybet.a8;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.anybet.R;

public class ReceivedStickerViewHolder extends RecyclerView.ViewHolder{
    public TextView stickerId;
    public TextView sender;
    public TextView time;

    public ReceivedStickerViewHolder(@NonNull View itemView) {
        super(itemView);
        this.stickerId = itemView.findViewById(R.id.tvReceivedSticker);
        this.sender = itemView.findViewById(R.id.tvSender);
        this.time = itemView.findViewById(R.id.tvReceivedTime);
    }
}
