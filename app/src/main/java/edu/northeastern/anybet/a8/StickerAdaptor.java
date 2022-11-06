package edu.northeastern.anybet.a8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.anybet.R;
import edu.northeastern.anybet.a8.realtimeDatabase.models.Sticker;

public class StickerAdaptor extends RecyclerView.Adapter<StickerViewHolder>{
    private final List<Sticker> stickers;
    private final Context context;

    public StickerAdaptor(List<Sticker> stickers, Context context) {
        this.stickers = stickers;
        this.context = context;
    }

    @NonNull
    @Override
    public StickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StickerViewHolder(LayoutInflater.from(context).inflate(R.layout.sent_sticker_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull StickerViewHolder holder, int position) {
        holder.stickerId.setText(stickers.get(position).getStickerId() + ":");
        holder.stickerCount.setText(String.valueOf(stickers.get(position).getCount()));
    }

    @Override
    public int getItemCount() {
        return stickers.size();
    }
}
