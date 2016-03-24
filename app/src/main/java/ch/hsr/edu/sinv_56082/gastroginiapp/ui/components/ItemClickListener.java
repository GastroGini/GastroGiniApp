package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.Fest;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.Tisch;

public interface ItemClickListener {
    void onItemClicked(Fest fest);
    void onItemClicked(Tisch tisch);
}
