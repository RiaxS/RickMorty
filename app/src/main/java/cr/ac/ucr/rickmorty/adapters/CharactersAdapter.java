package cr.ac.ucr.rickmorty.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import cr.ac.ucr.rickmorty.CharacterActivity;
import cr.ac.ucr.rickmorty.R;

import cr.ac.ucr.rickmorty.models.Character;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.ViewHolder> implements ItemClickListener{

    private  Context context;
    private  ArrayList<Character> characters;

    public CharactersAdapter(Context context, ArrayList<Character> characters){

        this.context = context;
        this.characters = characters;
    }

    public CharactersAdapter(Context context){
        this.context = context;
        this.characters = new ArrayList<>();
    }



    @NonNull
    @Override
    public CharactersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_character, parent, false);
        return new ViewHolder(view, (ItemClickListener) this);///******
    }

    @Override
    public void onBindViewHolder(@NonNull CharactersAdapter.ViewHolder holder, int position) {
        Character character = characters.get(position);

        holder.tvName.setText(character.getName());
        holder.tvStatus.setText(character.getStatus());
        holder.tvSpecies.setText(character.getSpecies());
        holder.tvLocation.setText(character.getLocation().getName());

        Glide.with(context)
                .load(character.getImage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivCharacter);

    }

    public void addCharacters(ArrayList<Character> characters){
        this.characters.addAll(characters);
        notifyDataSetChanged();
    }

    public void onClick(View view, int position){

        Intent intent = new Intent(context, CharacterActivity.class);

        Character character = characters.get(position);
        intent.putExtra(context.getString(R.string.character_id),character.getId());
        intent.putExtra(context.getString(R.string.character_name),character.getName());


        context.startActivity(intent);

    }
    @Override
    public int getItemCount() {
        return characters != null ? characters.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private CardView cvCharacterCard;

        private ItemClickListener listener;

        private final ImageView ivCharacter;
        private final TextView tvName;
        private final TextView tvStatus;
        private final TextView tvSpecies;
        private final TextView tvLocation;



        public ViewHolder(@NonNull View view, ItemClickListener listener){
            super(view);

            //ctrl alt f
            this.listener = listener;
            cvCharacterCard = view.findViewById(R.id.cv_character_card);
            ivCharacter = view.findViewById(R.id.iv_character);
            tvName = view.findViewById(R.id.tv_name);
            tvStatus = view.findViewById(R.id.tv_status);
            tvSpecies = view.findViewById(R.id.tv_species);
            tvLocation = view.findViewById(R.id.tv_location);

            cvCharacterCard.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getLayoutPosition());

        }
    }
}

interface ItemClickListener{
    void onClick(View view, int position); //la vista del elemento y la posiscion, a lo que se le dio click
}
