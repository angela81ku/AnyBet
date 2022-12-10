package edu.northeastern.anybet.finalProject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.anybet.R;
import edu.northeastern.anybet.finalProject.realtimeDatabase.models.Bet;


public class BetAdaptor extends RecyclerView.Adapter<BetViewHolder> {
    private List<Bet> bets;
    private Context context;
    private BetClickListener listener;

    public BetAdaptor(List<Bet> bets, Context context) {
        this.bets = bets;
        this.context = context;
    }

    public void changeData(List<Bet> bets) {
        this.bets = bets;
    }

    @NonNull
    @Override
    public BetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_bet_item_homepage, null);// todo: a little different from LinkAdaptor
        return new BetViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull BetViewHolder holder, int position) {
        holder.betTitle.setText(bets.get(position).getTitle());
        holder.betAmount.setText(bets.get(position).getBetPrice());
        holder.betUser1.setText(bets.get(position).getParticipant1());
        holder.betUser2.setText(bets.get(position).getParticipant2());
        holder.endTime.setText(bets.get(position).getBetEndTime());
    }

    @Override
    public int getItemCount() {
        return bets.size();
    }

    public void setOnItemClickListener(BetClickListener betClickListener) {
        this.listener = betClickListener;
    }
}
