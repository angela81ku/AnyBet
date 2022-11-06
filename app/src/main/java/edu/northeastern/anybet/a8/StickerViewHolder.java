package edu.northeastern.anybet.a8;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.anybet.R;

public class StickerViewHolder extends RecyclerView.ViewHolder{
    public TextView stickerId;
    public TextView stickerCount;
    public ImageView img;

    public StickerViewHolder(@NonNull View itemView) {
        super(itemView);
//        this.name = itemView.findViewById(R.id.name);
//        this.age = itemView.findViewById(R.id.age);
//        this.stickerId = itemView.findViewById(R.id.tvSentSticker);
        this.stickerCount = itemView.findViewById(R.id.tvSentStickerCount);
        this.img = itemView.findViewById(R.id.imageView2);
    }
}
