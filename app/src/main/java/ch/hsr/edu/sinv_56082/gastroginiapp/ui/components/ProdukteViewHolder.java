package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ProdukteViewHolder extends RecyclerView.ViewHolder {
    private View produktView;
    private TextView anzahlTextView;
    private TextView produktTextView;
    private TextView groesseTextView;
    private TextView preisTextView;
    public ProdukteViewHolder(View produktView, TextView anzahlTextView , TextView produktTextView,
                              TextView groesseTextView, TextView preisTextView) {
        super(produktView);
        this.produktView = produktView;
        this.anzahlTextView = anzahlTextView;
        this.produktTextView = produktTextView;
        this.groesseTextView = groesseTextView;
        this.preisTextView = preisTextView;
    }

    public TextView getProduktTextView(){
        return produktTextView;
    }
    public TextView getAnzahlTextView(){
        return anzahlTextView;
    }
    public TextView getGroesseTextView(){
        return groesseTextView;
    }

    public TextView getPreisTextView(){
        return preisTextView;
    }
    public View getView(){return produktView;};
}
