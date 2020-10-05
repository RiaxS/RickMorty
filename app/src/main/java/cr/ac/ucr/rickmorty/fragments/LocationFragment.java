package cr.ac.ucr.rickmorty.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import cr.ac.ucr.rickmorty.adapters.LocationAdapter;
import cr.ac.ucr.rickmorty.models.Location;

public class LocationFragment extends Fragment {

    private AppCompatActivity activity;
    private ArrayList<Location> locations;
    private LocationAdapter locationAdapter;

    public LocationFragment() {
    }

    public static LocationFragment newInstance(){
        LocationFragment fragment = new LocationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locations = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }
}
