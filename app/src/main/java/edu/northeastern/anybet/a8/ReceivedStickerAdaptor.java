package edu.northeastern.anybet.a8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.anybet.R;
import edu.northeastern.anybet.a8.realtimeDatabase.models.ReceivedSticker;

public class ReceivedStickerAdaptor extends RecyclerView.Adapter<ReceivedStickerViewHolder> {
    private final List<ReceivedSticker> receivedStickers;
    private final Context context;

    public ReceivedStickerAdaptor(List<ReceivedSticker> receivedStickers, Context context) {
        this.receivedStickers = receivedStickers;
        this.context = context;
    }


    @NonNull
    @Override
    public ReceivedStickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReceivedStickerViewHolder(LayoutInflater.from(context).inflate(R.layout.received_sticker_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ReceivedStickerViewHolder holder, int position) {
        holder.stickerId.setText(receivedStickers.get(position).getStickerId() + " | ");
        holder.sender.setText("sender:" + receivedStickers.get(position).getSendUser() + " | ");
        holder.time.setText(receivedStickers.get(position).getSendTime());
    }

    @Override
    public int getItemCount() {
        return receivedStickers.size();
    }
}
