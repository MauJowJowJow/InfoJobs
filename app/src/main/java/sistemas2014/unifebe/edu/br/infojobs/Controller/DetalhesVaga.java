package sistemas2014.unifebe.edu.br.infojobs.Controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import sistemas2014.unifebe.edu.br.infojobs.Model.Cargo;
import sistemas2014.unifebe.edu.br.infojobs.Model.Empresa;
import sistemas2014.unifebe.edu.br.infojobs.Model.Endereco;
import sistemas2014.unifebe.edu.br.infojobs.Model.Vaga;
import sistemas2014.unifebe.edu.br.infojobs.R;

public class DetalhesVaga extends AppCompatActivity {
    private Button btnCompartilhar;
    private Button btnEnviarCurriculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_vaga);

        if(getIntent().getExtras() != null){
            Vaga vaga = Vaga.findById(Vaga.class, getIntent().getExtras().getLong("id"));
            Cargo cargo = vaga.getCargo();
            Endereco endereco = vaga.getEndereco();
            Empresa empresa = vaga.getEmpresa();

            EditText txtVaga = (EditText) findViewById(R.id.txtVaga);
            EditText txtEmpresa = (EditText) findViewById(R.id.txtEmpresa);
            EditText txtCargo = (EditText) findViewById(R.id.txtCargo);
            EditText txtCidade = (EditText) findViewById(R.id.txtCidade);
            EditText txtBairro = (EditText) findViewById(R.id.txtBairro);
            EditText txtEstado = (EditText) findViewById(R.id.txtEstado);
            EditText txtSalario = (EditText) findViewById(R.id.txtSalario);
            EditText txtObservacoes = (EditText) findViewById(R.id.txtObservacoes);

            txtVaga.setText(vaga.getDescricao());
            txtEmpresa.setText(empresa.getNomeEmpresa());
            txtCargo.setText(cargo.getNome());
            txtCidade.setText(endereco.getCidade());
            txtBairro.setText(endereco.getBairro());
            txtEstado.setText(endereco.getEstado());
            txtSalario.setText(Double.toString(vaga.getSalario()));
            txtObservacoes.setText(vaga.getObservacoes());
        }else{
            finish();
        }

        btnCompartilhar = (Button) findViewById(R.id.btnCompartilhar);
        btnCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnEnviarCurriculo = (Button) findViewById(R.id.btnEnviarCurriculo);

        btnEnviarCurriculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
