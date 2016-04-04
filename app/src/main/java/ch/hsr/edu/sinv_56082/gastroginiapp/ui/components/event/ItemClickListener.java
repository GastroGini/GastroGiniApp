package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.event;

import android.view.View;

import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.Event;

public interface ItemClickListener{
    void onClick(String title, int position, int identifier);
}
