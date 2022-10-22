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
public class DefinitionViewHolder extends RecyclerView.ViewHolder {


    public TextView definitionTxt;

    public DefinitionViewHolder(@NonNull View itemView) {
        super(itemView);
        this.definitionTxt = itemView.findViewById(R.id.definitionText);
//        this.age = itemView.findViewById(R.id.age);
    }
}
