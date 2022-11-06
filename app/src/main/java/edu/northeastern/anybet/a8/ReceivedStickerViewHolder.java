package edu.northeastern.anybet.a8;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.anybet.R;

public class ReceivedStickerViewHolder extends RecyclerView.ViewHolder{
    public TextView sender;
    public TextView time;
    public ImageView img;


    public ReceivedStickerViewHolder(@NonNull View itemView) {
        super(itemView);
        this.sender = itemView.findViewById(R.id.tvSender);
        this.time = itemView.findViewById(R.id.tvReceivedTime);
        this.img = itemView.findViewById(R.id.imageView);
    }
}
