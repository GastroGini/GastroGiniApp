package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.Produkt;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.Tisch;

public class ProdukteAdapter extends RecyclerView.Adapter<ProdukteViewHolder> {
    private List<Produkt> produkteList;
    private ItemClickListener mListener;

    public ProdukteAdapter(ItemClickListener mListener, List<Produkt> produkteList){
        this.mListener = mListener;
        this.produkteList = produkteList;
    }

    @Override
    public ProdukteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View columnViewBestellenTische = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.column_row_bestellen_tische, parent, false);
        TextView anzahlTextView = (TextView) columnViewBestellenTische.findViewById(R.id.anzahlText);
        TextView produktTextView = (TextView) columnViewBestellenTische.findViewById(R.id.produktText);
        TextView groesseTextView = (TextView) columnViewBestellenTische.findViewById(R.id.groesseText);
        TextView preisTextView = (TextView) columnViewBestellenTische.findViewById(R.id.preisText);

        ProdukteViewHolder pvh = new ProdukteViewHolder(
                columnViewBestellenTische,anzahlTextView, produktTextView,groesseTextView,preisTextView);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ProdukteViewHolder holder, int position) {
        final int pos = position;
        holder.getAnzahlTextView().setText(produkteList.get(position).getAnzahl()+"");
        holder.getProduktTextView().setText(produkteList.get(position).getBeschreibung());
        holder.getGroesseTextView().setText(produkteList.get(position).getGroesse());
        holder.getPreisTextView().setText(produkteList.get(position).getPreis());

/*        TODO: check if onClick required on seperate positions.
            holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.onItemClicked(tischeList.get(pos));
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return produkteList.size();
    }
}
