package edu.northeastern.anybet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * This is a recyclerview adapter class, the purpose of this class is to act as a bridge between the
 * collection (arraylist) and the view (recyclerview). This class provides 3 methods that are
 * utilised for binding the data to the view. The explanation of each method is provided in comments
 * within the methods.
 */
public class DefinitionAdapter extends RecyclerView.Adapter<DefinitionViewHolder> {

    private final List<Definition> definitions;
    private final Context context;

    /**
     * Creates a DefinitionAdapter with the provided arraylist of Definition objects.
     *
     * @param definitions    arraylist of definition object.
     * @param context   context of the activity used for inflating layout of the viewholder.
     */
    public DefinitionAdapter(List<Definition> definitions, Context context) {
        this.definitions = definitions;
        this.context = context;
    }

    @NonNull
    @Override
    public DefinitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create an instance of the viewholder by passing it the layout inflated as view and no root.
        return new DefinitionViewHolder(LayoutInflater.from(context).inflate(R.layout.word_definition_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull DefinitionViewHolder holder, int position) {
        // sets the name of the person to the name textview of the viewholder.
        holder.definitionTxt.setText(definitions.get(position).getDefinition());
        // sets the age of the person to the age textview of the viewholder.
//        holder.age.setText(String.valueOf(people.get(position).getAge()));

        // set a click event on the whole itemView (every element of the recyclerview).
//        holder.itemView.setOnClickListener(view -> {
//            Toast.makeText(context, people.get(position).getName(), Toast.LENGTH_SHORT).show();
//        });
    }

    @Override
    public int getItemCount() {
        // Returns the size of the recyclerview that is the list of the arraylist.
        return definitions.size();
    }
}
