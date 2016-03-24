 package ch.hsr.edu.sinv_56082.gastroginiapp.ui.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.Fest;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.Tisch;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.ItemClickListener;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.TischeAdapter;

 /**
 * A simple {@link Fragment} subclass.
 */
public class TischeFragment extends Fragment implements ItemClickListener {

     private List<Tisch> tischeList = new ArrayList<>();
     private RecyclerView recyclerView;
     private LinearLayoutManager linearLayoutManager;

    public TischeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_tische, container, false);

        tischeList.add(new Tisch("Tisch 1"));
        tischeList.add(new Tisch("Tisch 2"));
        tischeList.add(new Tisch("Tisch 3"));
        tischeList.add(new Tisch("Tisch 4"));
        tischeList.add(new Tisch("Tisch 5"));
        tischeList.add(new Tisch("Tisch 6"));
        tischeList.add(new Tisch("Tisch 7"));
        tischeList.add(new Tisch("Tisch 8"));
        tischeList.add(new Tisch("Tisch 9"));

        TischeAdapter adapter = new TischeAdapter(this,tischeList);

        recyclerView = (RecyclerView) root.findViewById(R.id.tischeView);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        return root;
    }

     @Override
     public void onItemClicked(Fest fest) {
         //TODO: restructure to avoid refused bequest
     }

     @Override
     public void onItemClicked(Tisch tisch) {
         //TODO: prepare activity to go to onClick
         /*
         Intent intent = new Intent(getActivity(),FestBearbeiten.class);
         intent.putExtra("title",tisch.getTischDescription());
         startActivity(intent);
         */
     }
 }
