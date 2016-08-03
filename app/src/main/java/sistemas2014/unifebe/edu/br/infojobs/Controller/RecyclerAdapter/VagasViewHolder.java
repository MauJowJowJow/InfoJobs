package sistemas2014.unifebe.edu.br.infojobs.Controller.RecyclerAdapter;

import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import sistemas2014.unifebe.edu.br.infojobs.Controller.DetalhesVaga;
import sistemas2014.unifebe.edu.br.infojobs.Model.Vaga;
import sistemas2014.unifebe.edu.br.infojobs.R;

/**
 * Created by mauma on 27/07/2016.
 */
public class VagasViewHolder extends RecyclerView.ViewHolder {
    private VagasAdapter adapter;
    public TextView txtNome;

    public VagasViewHolder(View itemView, final VagasAdapter adapter) {
        super(itemView);

        this.adapter = adapter;
        txtNome = (TextView) itemView.findViewById(R.id.txtNome);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if(getAdapterPosition() != 0){
                     Vaga vaga = adapter.getItem(getAdapterPosition()-1);

                     Intent i = new Intent(view.getContext(), DetalhesVaga.class);
                     i.putExtra("id", vaga.getId());

                     view.getContext().startActivity(i);
                 }
            }
        });
    }
}
