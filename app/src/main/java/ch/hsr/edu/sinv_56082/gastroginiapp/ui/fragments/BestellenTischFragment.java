package ch.hsr.edu.sinv_56082.gastroginiapp.ui.fragments;


import android.app.Fragment;
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
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.Produkt;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.Tisch;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.ItemClickListener;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.ProdukteAdapter;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.TischeAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class BestellenTischFragment extends Fragment implements ItemClickListener {

    private List<Produkt> produktList = new ArrayList<>();
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;


    public BestellenTischFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_bestellen_tisch, container, false);

        produktList.add(new Produkt(3,"Coca Cola","1.5l","6.60.-"));
        produktList.add(new Produkt(3,"Coca Cola","1.5l","6.60.-"));
        produktList.add(new Produkt(3,"Coca Cola","1.5l","6.60.-"));
        produktList.add(new Produkt(3,"Coca Cola","1.5l","6.60.-"));
        produktList.add(new Produkt(3,"Coca Cola","1.5l","6.60.-"));
        produktList.add(new Produkt(3,"Coca Cola","1.5l","6.60.-"));
        produktList.add(new Produkt(3,"Coca Cola","1.5l","6.60.-"));

        ProdukteAdapter adapter = new ProdukteAdapter(this,produktList);

        recyclerView = (RecyclerView) root.findViewById(R.id.bestellenTischeView);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        return root;
    }

    @Override
    public void onItemClicked(Fest fest, int position) {
        //TODO: remove refused bequest
    }

    @Override
    public void onItemClicked(Tisch tisch) {

    }
}
