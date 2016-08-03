package sistemas2014.unifebe.edu.br.infojobs.Controller.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.orm.SugarDb;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import sistemas2014.unifebe.edu.br.infojobs.Model.AreaNegocio;
import sistemas2014.unifebe.edu.br.infojobs.Model.Vaga;
import sistemas2014.unifebe.edu.br.infojobs.R;

/**
 * Created by mauma on 27/07/2016.
 */
public class VagasHeaderViewHolder extends RecyclerView.ViewHolder {
    private VagasAdapter adapter;
    private Button btnFiltrar;
    private EditText txtCidade;
    private EditText txtCargo;
    private Spinner spiAreaNegocio;
    private RangeSeekBar<Integer> seekFaixaValor;
    private ImageButton btnLocation;
    private List<AreaNegocio> areasNegocio;

    private LocationManager locationManager = (LocationManager) itemView.getContext().getSystemService(Context.LOCATION_SERVICE);
    protected LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            try {
                locationManager.removeUpdates(locationListener);
            }catch (SecurityException ex){
                ex.printStackTrace();
            }
        }

        @Override public void onProviderDisabled(String provider) {}
        @Override public void onProviderEnabled(String provider) {}
        @Override public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    public VagasHeaderViewHolder(final View itemView, VagasAdapter adapter) {
        super(itemView);

        this.adapter = adapter;

        txtCargo = (EditText) itemView.findViewById(R.id.txtCargo);
        txtCidade = (EditText) itemView.findViewById(R.id.txtCidade);
        spiAreaNegocio = (Spinner) itemView.findViewById(R.id.spiAreaNegocio);
        seekFaixaValor = (RangeSeekBar<Integer>) itemView.findViewById(R.id.seekFaixaValor);

        spiAreaNegocio.setAdapter(buscaAreaNegocio());

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
        String where="";
        List<String> whereArgs = new ArrayList<>();
        String[] whereArgsArray;
        ArrayList<Vaga> vagas = new ArrayList<>();
        int valorMin, valorMax;

        SugarDb db = new SugarDb(itemView.getContext());
        SQLiteDatabase sqLiteDatabase = db.getDB();

        if(!txtCidade.getText().toString().isEmpty()){
            where += "lower(endereco.cidade) like ?";
            whereArgs.add("%" + txtCidade.getText().toString().toLowerCase() + "%");
        }

        if(!txtCargo.getText().toString().isEmpty()){
            if(!where.isEmpty()){
                where += " and ";
            }
            where += "lower(cargo.nome) like ?";
            whereArgs.add("%" + txtCargo.getText().toString().toLowerCase() + "%");
        }

        if(spiAreaNegocio.getSelectedItem() != null) {
            AreaNegocio areaNegocio = areasNegocio.get((int) spiAreaNegocio.getSelectedItemId());

            if(areaNegocio != null) {
                if (areaNegocio.getId() != null) {
                    if (!where.isEmpty()) {
                        where += " and ";
                    }
                    where += "area_negocio = ?";
                    whereArgs.add(areaNegocio.getId().toString());
                }
            }
        }

        valorMin = seekFaixaValor.getSelectedMinValue() * 1000;
        valorMax = seekFaixaValor.getSelectedMaxValue() * 1000;

        if (!where.isEmpty()) {
            where += " and ";
        }
        where += "salario >= ? and salario <= ?";
        whereArgs.add(String.valueOf(valorMin));
        whereArgs.add(String.valueOf(valorMax));

        if(whereArgs.size() > 0) {
            whereArgsArray = whereArgs.toArray(new String[whereArgs.size()]);
        }else{
            whereArgsArray = null;
        }
        Cursor cursor = sqLiteDatabase.query("vaga" +
                " LEFT JOIN endereco on vaga.endereco = endereco.id" +
                " LEFT JOIN cargo on vaga.cargo = cargo.id",
                new String [] {"vaga.id"},
                where,
                whereArgsArray,
                null,
                null,
                null,
                null
                );

        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                Long id = cursor.getLong(0);

                vagas.add(Vaga.findById(Vaga.class, id));
            } while (cursor.moveToNext());
        }

        adapter.updateList(vagas);
    }

    private String getCidadeAtual(Context context){
        String cidade="";
        Location location = null;
        float bestAccuracy = Float.MAX_VALUE;

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
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);

        // check if result is within limits (time and accuracy)
        if (locationListener != null) {
            String provider = locationManager.getBestProvider(criteria, true);
            if (provider != null) {

                // if there's no result - request location update
                try {
                    locationManager.requestLocationUpdates(provider, 0, 0, locationListener, context.getMainLooper());
                } catch (SecurityException ex) {
                    ex.printStackTrace();
                }

                // if GPS Provider is available than register it for location update,
                // but only for 30 second (usually it's enough time to get fix with clear sky)
                // remove GPS listener if it takes longer than 30 seconds to get GPS fix
                if (provider.equalsIgnoreCase(LocationManager.GPS_PROVIDER)) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                locationManager.removeUpdates(locationListener);
                            } catch (SecurityException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }, 30000L);
                }
            }
        }

        List<String> matchingProviders = locationManager.getAllProviders();
        for (String provider: matchingProviders) {
            try {
                location = locationManager.getLastKnownLocation(provider);
            }catch (SecurityException ex){
                ex.printStackTrace();
            }

            if (location != null) {
                float accuracy = location.getAccuracy();
                if ((accuracy < bestAccuracy)) {
                    bestAccuracy = accuracy;
                }
            }
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

    private ArrayAdapter<String> buscaAreaNegocio(){
        ArrayAdapter<String> areasNegocioAdapter = new ArrayAdapter<String>(itemView.getContext(), android.R.layout.simple_dropdown_item_1line);
        Iterator<AreaNegocio> areasNegocioBD = AreaNegocio.findAll(AreaNegocio.class);
        areasNegocio = new ArrayList<>();

        areasNegocio.add(new AreaNegocio() {});
        areasNegocioAdapter.add("Todas as áreas de negócio");
        while (areasNegocioBD.hasNext()){
            AreaNegocio areaNegocio = areasNegocioBD.next();

            areasNegocio.add(areaNegocio);
            areasNegocioAdapter.add(areaNegocio.getDescricao());
        }

        return areasNegocioAdapter;
    }
}
