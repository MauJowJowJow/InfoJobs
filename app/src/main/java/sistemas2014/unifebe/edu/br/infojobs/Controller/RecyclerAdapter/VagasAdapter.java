package sistemas2014.unifebe.edu.br.infojobs.Controller.RecyclerAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sistemas2014.unifebe.edu.br.infojobs.Model.Vaga;
import sistemas2014.unifebe.edu.br.infojobs.R;

/**
 * Created by mauma on 27/07/2016.
 */
public class VagasAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HEADER_VIEW = 1;

    private List<View> mHeader = new ArrayList<>();
    private List<Vaga> mData = new ArrayList<>();

    public VagasAdapter() {
        // Pass context or other static stuff that will be needed.
    }

    //add a header to the adapter
    public void addHeader(View header) {
        if (!mHeader.contains(header)) {
            mHeader.add(header);
            //animate
            notifyItemInserted(mHeader.size() - 1);
        }
    }

    //remove a header from the adapter
    public void removeHeader(View header){
        if(mHeader.contains(header)){
            //animate
            notifyItemRemoved(mHeader.indexOf(header));
            mHeader.remove(header);
        }
    }

    public void addItem(int position, Vaga data) {
        mData.add(position, data);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public Vaga getItem(int position) {
        return mData.get(position);
    }

    public void updateList(List<Vaga> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            // This is where we'll add Header.
            return HEADER_VIEW;
        }

        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }

        // Add extra view to show the footer view
        return mData.size() + mHeader.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v;

        if (viewType == HEADER_VIEW) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.header_consulta_vagas, viewGroup, false);
            VagasHeaderViewHolder vh = new VagasHeaderViewHolder(v, this);

            return vh;
        }

        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lista_item_consulta_vagas, viewGroup, false);
        VagasViewHolder vh = new VagasViewHolder(v, this);

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        try {
            if (viewHolder instanceof VagasHeaderViewHolder) {
                VagasHeaderViewHolder vh = (VagasHeaderViewHolder) viewHolder;
                View v = mHeader.get(position);

                vh.base.removeAllViews();
                vh.base.addView(v);
            } else if (viewHolder instanceof VagasViewHolder) {
                VagasViewHolder vh = (VagasViewHolder) viewHolder;

                vh.txtNome.setText(mData.get(position).getDescricao());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
