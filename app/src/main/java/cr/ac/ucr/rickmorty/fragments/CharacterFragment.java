package cr.ac.ucr.rickmorty.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;

import cr.ac.ucr.rickmorty.R;
import cr.ac.ucr.rickmorty.adapters.CharactersAdapter;
import cr.ac.ucr.rickmorty.api.CharacterService;
import cr.ac.ucr.rickmorty.api.RetrofitBuilder;
import cr.ac.ucr.rickmorty.models.Character;
import cr.ac.ucr.rickmorty.models.CharacterResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CharacterFragment extends Fragment {


    private final String TAG = "CharacterFragment";
    private AppCompatActivity activity;
    private ArrayList<Character> characters;
    private CharactersAdapter charactersAdapter;

    boolean canLoad = true;
    int limit = 0;
    int page = 0;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar pbLoading;
    private RecyclerView rvCharacters;

    public CharacterFragment() {
        // Required empty public constructor
    }


    public static CharacterFragment newInstance() {
        CharacterFragment fragment = new CharacterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //TODO inicializamos variables que no dependen de la vista(layout)

        characters = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_character, container, false);

        pbLoading = view.findViewById(R.id.pb_loading);

        rvCharacters = view.findViewById(R.id.rv_characters);

        charactersAdapter = new CharactersAdapter(activity);
        rvCharacters.setAdapter(charactersAdapter);
        rvCharacters.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(activity);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 3);
        rvCharacters.setLayoutManager(linearLayoutManager);

        charactersAdapter.addCharacters(characters);

        setupRVScrollListener(rvCharacters, linearLayoutManager);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getCharactersInfo(page);
    }

    private void getCharactersInfo(int page) {
        canLoad = false;

        CharacterService characterService = RetrofitBuilder.createService(CharacterService.class);
        Call<CharacterResponse> response = characterService.getCharacters(page);
        response.enqueue(new Callback<CharacterResponse>() {
            @Override
            public void onResponse(@NonNull Call<CharacterResponse> call, @NonNull Response<CharacterResponse> response) {
                if(response.isSuccessful()){
                    CharacterResponse characterResponse = response.body();
                    ArrayList<Character> characters = characterResponse.getResults();

                    charactersAdapter.addCharacters(characters);
                    showCharacter(true);

                } else {
                    Log.e(TAG, "onError" + response.errorBody());
                }
                canLoad=true;
            }

            @Override
            public void onFailure(@NonNull Call<CharacterResponse> call, @NonNull Throwable t) {
                canLoad = true;
                throw new RuntimeException(t);

            }
        });
        //TODO cargar los datos del restapi
    }

    private void setupRVScrollListener(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0){
                //Log.i(TAG, "onScrolled: "+ dy);
                int totalItems = linearLayoutManager.getItemCount();
                int pastItems = linearLayoutManager.findFirstVisibleItemPosition();
                int visibleItems = linearLayoutManager.getChildCount();

                if(canLoad){
                    if((pastItems + visibleItems)>= totalItems){
                        page++;
                        pbLoading.setVisibility(View.VISIBLE);
                        getCharactersInfo(page);
                    }
                }

                }
            }
        });
    }

    private void showCharacter(boolean setVisible){
        rvCharacters.setVisibility(setVisible ? View.VISIBLE : View.GONE);
        pbLoading.setVisibility(!setVisible ? View.VISIBLE : View.GONE);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        activity = (AppCompatActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }
}