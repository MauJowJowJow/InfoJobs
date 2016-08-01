package sistemas2014.unifebe.edu.br.infojobs.Controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.Iterator;

import sistemas2014.unifebe.edu.br.infojobs.Controller.RecyclerAdapter.VagasAdapter;
import sistemas2014.unifebe.edu.br.infojobs.Model.Cargo;
import sistemas2014.unifebe.edu.br.infojobs.Model.Empresa;
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
    private View header;
    private View inflatedView;
    private EditText txtCidade;
    private Button btnFiltrar;
    private RangeSeekBar<Integer> seekFaixaValor;

    private VagasAdapter vagasAdapter;
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

        RecyclerView recyclerView = (RecyclerView) inflatedView.findViewById(R.id.listVagas);
        header = inflater.inflate(R.layout.header_consulta_vagas, null);

        recyclerView.setLayoutManager(new LinearLayoutManager(inflatedView.getContext()));
        recyclerView.setAdapter(getVagas(header));

        seekFaixaValor = (RangeSeekBar<Integer>) inflatedView.findViewById(R.id.seekFaixaValor);

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

    private VagasAdapter getVagas(View header){
        vagas = new ArrayList<>();
        vagasAdapter = new VagasAdapter();
        vagasAdapter.addHeader(header);

        Iterator<Vaga> vagasAtuais = Vaga.findAll(Vaga.class);
        if(vagasAtuais.hasNext()) {
            while (vagasAtuais.hasNext()) {
                Vaga vaga = vagasAtuais.next();

                vagas.add(vaga);
            }
        }else {

            for (int i = 1; i < 15; i++) {
                Vaga vaga = new Vaga();
                Cargo cargo = new Cargo();
                Empresa empresa = new Empresa();

                Endereco endereco = new Endereco();
                if (i < 7) {
                    endereco.setCidade("Brusque");
                    endereco.setBairro("Centro");
                } else {
                    endereco.setCidade("Blumenau");
                    endereco.setBairro("Bom Retiro");

                }
                endereco.setEstado("SC");
                endereco.save();

                vaga.setDescricao("Vaga " + i);

                vaga.setObservacoes("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");

                if (i < 5) {
                    vaga.setSalario(1500.00);
                } else if(i > 5 && i < 10){
                    vaga.setSalario(5900.00);
                }else{
                    vaga.setSalario(900.00);
                }

                if (i < 5) {
                    cargo.setNome("Programador");
                } else if(i > 5 && i < 10){
                    cargo.setNome("DBA");
                }else{
                    cargo.setNome("Analista");
                }
                cargo.save();

                if (i < 5) {
                    empresa.setNomeEmpresa("TecMicro");
                } else if(i > 5 && i < 10){
                    empresa.setNomeEmpresa("T-Systems");
                }else{
                    empresa.setNomeEmpresa("Hiper");
                }
                empresa.save();

                vaga.setEmpresa(empresa);
                vaga.setCargo(cargo);
                vaga.setEndereco(endereco);

                vagas.add(vaga);
                vaga.save();
            }
        }

        vagasAdapter.updateList(vagas);
        return vagasAdapter;
    }


}
