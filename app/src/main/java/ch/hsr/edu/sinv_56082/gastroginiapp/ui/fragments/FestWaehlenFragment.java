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
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.FestBearbeiten;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.FesteAdapter;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.ItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class FestWaehlenFragment extends Fragment implements ItemClickListener {
    private List<Fest> festeList = new ArrayList<>();
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    public FestWaehlenFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_fest_waehlen, container, false);

        festeList.add(new Fest("Fest 1"));
        festeList.add(new Fest("Fest 2"));
        festeList.add(new Fest("Fest 3"));
        festeList.add(new Fest("Fest 4"));
        festeList.add(new Fest("Fest 5"));

        FesteAdapter adapter = new FesteAdapter(this,festeList);

        recyclerView = (RecyclerView) root.findViewById(R.id.view);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        return root;
    }

    @Override
    public void onItemClicked(Fest fest) {
        Intent intent = new Intent(getActivity(),FestBearbeiten.class);
        intent.putExtra("title",fest.getTitle());
        startActivity(intent);
    }

    @Override
    public void onItemClicked(Tisch tisch) {
        //TODO: restructure to avoid refused bequest
    }
}
