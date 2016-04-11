package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductList;

/**
 * Created by Dogan on 11.04.16.
 */

public interface MenuProductListClickListener {
    void onClick(ProductList identifier);
    void editItem(ProductList identifier);
    void deleteItem(ProductList identifier);
}
