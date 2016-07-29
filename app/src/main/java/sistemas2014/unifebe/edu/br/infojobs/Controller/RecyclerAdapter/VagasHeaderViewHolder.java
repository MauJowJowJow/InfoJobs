package sistemas2014.unifebe.edu.br.infojobs.Controller.RecyclerAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import sistemas2014.unifebe.edu.br.infojobs.Model.Endereco;
import sistemas2014.unifebe.edu.br.infojobs.Model.Vaga;
import sistemas2014.unifebe.edu.br.infojobs.R;

/**
 * Created by mauma on 27/07/2016.
 */
public class VagasHeaderViewHolder extends RecyclerView.ViewHolder {
    private VagasAdapter adapter;
    private Button btnFiltrar;
    private EditText txtCidade;
    private ImageButton btnLocation;

    public VagasHeaderViewHolder(final View itemView, VagasAdapter adapter) {
        super(itemView);

        this.adapter = adapter;

        txtCidade = (EditText) itemView.findViewById(R.id.txtCidade);
        btnFiltrar = (Button) itemView.findViewById(R.id.btnFiltrar);

        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filtraVagas();
            }
        });

        btnLocation = (ImageButton) itemView.findViewById(R.id.btnLocation);

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtCidade.setText(getCidadeAtual(itemView.getContext()));
            }
        });
    }

    private void filtraVagas(){
        ArrayList<Vaga> vagas = new ArrayList<>();
        Iterator<Vaga> vagasAtuais = Vaga.findAll(Vaga.class);

        while(vagasAtuais.hasNext()){
            Vaga vaga = vagasAtuais.next();

            if(!vaga.getEndereco().getCidade().toLowerCase().contains(txtCidade.getText().toString().toLowerCase())){
                vagas.add(vaga);
            }
        }

        adapter.updateList(vagas);
    }

    private String getCidadeAtual(Context context){
        String cidade="";

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        boolean on = false;
        try {
            on = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        if(!on) {
            Intent onGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            itemView.getContext().startActivity(onGPS);
        }

        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);
        Location location = null;
        try{
            location = locationManager.getLastKnownLocation(provider);
        }catch (SecurityException ex){
            ex.printStackTrace();
        }

        if (location != null) {
            Geocoder geoCoder = new Geocoder(context, Locale.getDefault());

            List<Address> list=null;
            try {
                list = geoCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            }catch (IOException ex){
                ex.printStackTrace();
            }

            if (list != null) {
                if(list.size() > 0){
                    Address address = list.get(0);

                    cidade = address.getLocality();
                }
            }

        }

        return cidade;
    }

}
