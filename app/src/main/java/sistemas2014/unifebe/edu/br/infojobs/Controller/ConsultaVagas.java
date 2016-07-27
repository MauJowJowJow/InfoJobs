package sistemas2014.unifebe.edu.br.infojobs.Controller;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import sistemas2014.unifebe.edu.br.infojobs.Model.Endereco;
import sistemas2014.unifebe.edu.br.infojobs.Model.Vaga;
import sistemas2014.unifebe.edu.br.infojobs.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConsultaVagas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConsultaVagas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsultaVagas extends Fragment {

    private View inflatedView;
    private ArrayList<Vaga> vagas;
    private OnFragmentInteractionListener mListener;

    public ConsultaVagas() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ConsultaVagas newInstance() {
        ConsultaVagas fragment = new ConsultaVagas();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.inflatedView = inflater.inflate(R.layout.fragment_consulta_vagas, container, false);

        ImageButton BtnLocation = (ImageButton) inflatedView.findViewById(R.id.btnLocation);

        BtnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText txtCidade = (EditText) inflatedView.findViewById(R.id.txtCidade);
                txtCidade.setText(getCidadeAtual(inflatedView.getContext()));
            }
        });

        ListView listVagas = (ListView) inflatedView.findViewById(R.id.listVagas);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (inflatedView.getContext(),android.R.layout.simple_list_item_1,
                        getVagas());
        listVagas.setAdapter(dataAdapter);

        listVagas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Snackbar.make(view, "Vaga selecionada: " + vagas.get(i).getDescricao(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return inflatedView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
            startActivity(onGPS);
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

    private List<String> getVagas(){
        vagas = new ArrayList<>();
        List<String> vagasDescricao = new ArrayList<>();

        for(int i = 0; i < 25; i++){
            Vaga vaga = new Vaga();
            Endereco endereco = new Endereco();
            endereco.setCidade("Brusque");

            vaga.setDescricao("Vaga " + i);
            vaga.setEndereco(endereco);

            vagas.add(vaga);
            vagasDescricao.add((vaga.getDescricao()));
        }

        return vagasDescricao;
    }
}
