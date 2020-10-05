package cr.ac.ucr.rickmorty.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cr.ac.ucr.rickmorty.models.Location;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> implements ItemClickListener {

    private Context context;
    private ArrayList<Location> locations;

    public LocationAdapter(Context context, ArrayList<Location> locations){
        this.context=context;
        this.locations=locations;
    }

    public LocationAdapter(Context context) {
        this.context = context;
        this.locations= new ArrayList<>();
    }

    @NonNull
    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate()

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {return 0;}

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ViewHolder(@NonNull View view){
            super(view);

        }

            @Override
            public void onClick(View view) {

            }
        }



    @Override
    public void onClick(View view, int position) {

    }
}
