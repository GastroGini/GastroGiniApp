package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order;


import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;

/**
 * Created by Phil on 09.04.2016.
 */
public interface NewOrderClickListener {
    void onClick(Product product, int identifier);
}
