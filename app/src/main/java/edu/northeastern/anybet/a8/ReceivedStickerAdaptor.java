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
        holder.sender.setText("sender:" + receivedStickers.get(position).getSendUser() + " | ");
        holder.time.setText(receivedStickers.get(position).getSendTime());
        String id = receivedStickers.get(position).getStickerId();
        if (id.equals("sticker1")) {
            holder.img.setImageResource(R.drawable.sticker1);
        } else if (id.equals("sticker2")) {
            holder.img.setImageResource(R.drawable.sticker2);
        } else if (id.equals("sticker3")) {
            holder.img.setImageResource(R.drawable.sticker3);
        } else {
            holder.img.setImageResource(R.drawable.ic_launcher_background);
        }
    }

    @Override
    public int getItemCount() {
        return receivedStickers.size();
    }
}
