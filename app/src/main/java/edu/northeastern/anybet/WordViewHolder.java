package edu.northeastern.anybet;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * An implementation of the recyclerview viewholder that is created specifically with respect to the
 * item_person.xml file. The aim of this class is to provide each item in the recyclerview to the
 * adapter, another important purpose of this class is to expose the TextViews in the xml file as
 * java objects for binding the data.
 */
public class WordViewHolder extends RecyclerView.ViewHolder {

    public TextView wordTxt;
    public TextView phoneticTxt;
    public TextView partOfSpeechTxt;
    public TextView defineTxt;

    public WordViewHolder(@NonNull View itemView) {
        super(itemView);
//        this.name = itemView.findViewById(R.id.name);
//        this.age = itemView.findViewById(R.id.age);
        this.wordTxt = itemView.findViewById(R.id.wordSpellText);
        this.phoneticTxt = itemView.findViewById(R.id.wordPhoneticText);
        this.partOfSpeechTxt = itemView.findViewById(R.id.wordPartOfSpeechText);
        this.defineTxt = itemView.findViewById(R.id.defineText);

    }
}
