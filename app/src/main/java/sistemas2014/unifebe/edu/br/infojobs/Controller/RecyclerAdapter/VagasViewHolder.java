package sistemas2014.unifebe.edu.br.infojobs.Controller.RecyclerAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import sistemas2014.unifebe.edu.br.infojobs.R;

/**
 * Created by mauma on 27/07/2016.
 */
public class VagasViewHolder extends RecyclerView.ViewHolder {

    public TextView txtNome;

    public VagasViewHolder(View itemView) {
        super(itemView);
        txtNome = (TextView) itemView.findViewById(R.id.txtNome);
    }
}
