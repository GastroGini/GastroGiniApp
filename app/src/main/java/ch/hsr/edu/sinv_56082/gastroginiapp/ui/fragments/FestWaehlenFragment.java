package ch.hsr.edu.sinv_56082.gastroginiapp.ui.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.ApplicationObject;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.Fest;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.Tisch;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.FestBearbeiten;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.FesteAdapter;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.ItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class FestWaehlenFragment extends Fragment implements ItemClickListener {
    private List<Fest> festeList = ApplicationObject.getFesteList();
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    final FesteAdapter adapter = new FesteAdapter(this,festeList);;

    public FestWaehlenFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_fest_waehlen, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.view);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        Button auswahlModus_btn = (Button) root.findViewById(R.id.auswahlModus_btn);
        Button copy_btn = (Button) root.findViewById(R.id.copy_btn);
        Button delete_btn = (Button) root.findViewById(R.id.delete_btn);
        auswahlModus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setCheckBox(!adapter.getEditMode());
                adapter.clearSelections();
                adapter.notifyDataSetChanged();
            }
        });
        copy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Boolean> selectedPositions = adapter.getSelectedPositions();
                for(int i = 0;i < festeList.size();i++){
                    if(selectedPositions.get(i)){
                        Fest festToCopy = festeList.get(i);
                        festeList.add(new Fest(festToCopy.getTitle()));
                        selectedPositions.add(false);
                        //TODO: Somethings wrong with the checkbox mechanism, make it simpler and correct
                    }
                }
                adapter.setCheckBox(!adapter.getEditMode());
                adapter.clearSelections();
                adapter.notifyDataSetChanged();
            }
        });
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Boolean> selectedPositions = adapter.getSelectedPositions();
                for(int i = 0;i < festeList.size(); i++){
                    if(selectedPositions.get(i)){
                        festeList.remove(i);
                        selectedPositions.remove(i);
                    }
                }
                adapter.setCheckBox(!adapter.getEditMode());
                adapter.clearSelections();
                adapter.notifyDataSetChanged();
            }
        });

        return root;
    }

    public void refreshList(){
        adapter.refreshList();
    }

    @Override
    public void onItemClicked(Fest fest, int position) {
        Intent intent = new Intent(getActivity(),FestBearbeiten.class);
        intent.putExtra("title",fest.getTitle());
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public void onItemClicked(Tisch tisch) {
        //TODO: restructure to avoid refused bequest
    }
}
