package sistemas2014.unifebe.edu.br.infojobs.Controller;

import android.content.Intent;
import android.net.Uri;
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

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;

import java.util.Arrays;

import sistemas2014.unifebe.edu.br.infojobs.Helpers.BuscaPermissoesFB;
import sistemas2014.unifebe.edu.br.infojobs.Model.Cargo;
import sistemas2014.unifebe.edu.br.infojobs.Model.Empresa;
import sistemas2014.unifebe.edu.br.infojobs.Model.Endereco;
import sistemas2014.unifebe.edu.br.infojobs.Model.Vaga;
import sistemas2014.unifebe.edu.br.infojobs.R;

public class DetalhesVaga extends AppCompatActivity {
    private Button btnCompartilhar;
    private Button btnEnviarCurriculo;
    private Vaga vaga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_vaga);

        if(getIntent().getExtras() != null){
            vaga = Vaga.findById(Vaga.class, getIntent().getExtras().getLong("id"));

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
            EditText txtContato = (EditText) findViewById(R.id.txtContato);
            EditText txtObservacoes = (EditText) findViewById(R.id.txtObservacoes);

            txtVaga.setText(vaga.getDescricao());
            txtEmpresa.setText(empresa.getNomeEmpresa());
            txtCargo.setText(cargo.getNome());
            txtCidade.setText(endereco.getCidade());
            txtBairro.setText(endereco.getBairro());
            txtEstado.setText(endereco.getEstado());
            txtSalario.setText(Double.toString(vaga.getSalario()));
            txtContato.setText(empresa.getEmail());
            txtObservacoes.setText(vaga.getObservacoes());
        }else{
            finish();
        }

        btnCompartilhar = (Button) findViewById(R.id.btnCompartilhar);
        btnCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compartilharVaga();
            }
        });

        btnEnviarCurriculo = (Button) findViewById(R.id.btnEnviarCurriculo);

        btnEnviarCurriculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviaCurriculo();
            }
        });
    }

    private void enviaCurriculo(){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + vaga.getEmpresa().getEmail()));
        intent.putExtra(Intent.EXTRA_EMAIL, vaga.getEmpresa().getEmail());
        intent.putExtra(Intent.EXTRA_TEXT, "Segue o curriculo em anexo.");
        intent.putExtra(Intent.EXTRA_SUBJECT, "InfoJobs - Curriculo Vaga " + vaga.getId() + " - Cargo " + vaga.getCargo().getNome());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void compartilharVaga(){
        boolean publicarPermitido = false;
        if(AccessToken.getCurrentAccessToken()==null){
            Toast.makeText(getApplicationContext(), "Nenhum usuário logado com vínculo ao Facebook!", Toast.LENGTH_LONG).show();
            return;
        }

        BuscaPermissoesFB buscaPermissoesFB = new BuscaPermissoesFB();
        try {
            publicarPermitido = buscaPermissoesFB.execute(new String[]{"publish_actions"}).get();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        if(!publicarPermitido) {
            LoginManager.getInstance().logInWithPublishPermissions(
                    this,
                    Arrays.asList("publish_actions"));
        }

        if(publicarPermitido){
            Bundle params = new Bundle();
            params.putString("message", "Procurando emprego? Venha conheçer o InfoJobs!" + System.getProperty("line.separator") +
                                        "Estamos precisando de um " +
                                         vaga.getCargo().getNome() + System.getProperty("line.separator") +
                                        "Com salário médio de " + vaga.getSalario() + System.getProperty("line.separator") +
                                        "Interessado? Entre em contato pelo e-mail: " + vaga.getEmpresa().getEmail());

            new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/me/feed",
                    params,
                    HttpMethod.POST,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            Toast.makeText(getApplicationContext(), "Publicação realizada!", Toast.LENGTH_LONG).show();
                        }
                    }
            ).executeAsync();
        }else
            Toast.makeText(getApplicationContext(), "Não foi concedida a permissão para este app publicar no facebook, " +
                    "tente novamente para liberar a publicação!", Toast.LENGTH_LONG).show();
    }
}
