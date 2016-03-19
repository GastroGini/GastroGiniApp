package ch.hsr.edu.sinv_56082.gastroginiapp.ui.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.BestellenActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.FestWaehlenActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.VerbindenActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View homeFragmentView =  inflater.inflate(R.layout.fragment_home, container, false);
        Button bestellenBtn = (Button) homeFragmentView.findViewById(R.id.bestellen_btn);
        Button festwaehlenBtn = (Button)homeFragmentView.findViewById(R.id.festwaehlen_btn);
        Button verbindenBtn = (Button)homeFragmentView.findViewById(R.id.verbinden_btn);

        bestellenBtn.setOnClickListener(getOnClickListener(BestellenActivity.class));
        festwaehlenBtn.setOnClickListener(getOnClickListener(FestWaehlenActivity.class));
        verbindenBtn.setOnClickListener(getOnClickListener(VerbindenActivity.class));
        return homeFragmentView;
    }

    private View.OnClickListener getOnClickListener(Class activityToStart){
        final Class activityClass = activityToStart;
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),activityClass);
                startActivity(intent);
            }
        };
        return onClickListener;
    };

}
