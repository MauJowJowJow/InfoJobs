package sistemas2014.unifebe.edu.br.infojobs.Controller;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import sistemas2014.unifebe.edu.br.infojobs.Model.Endereco;
import sistemas2014.unifebe.edu.br.infojobs.Model.Usuario;
import sistemas2014.unifebe.edu.br.infojobs.R;

public class CadastroUsuario extends AppCompatActivity {
    private String errValidacao;
    private Usuario usuario;
    private EditText txtEmail;
    private EditText txtSenha;
    private EditText txtNome;
    private EditText txtSobrenome;
    private EditText txtCidade;
    private EditText txtBairro;
    private EditText txtEndereco;
    private EditText txtCEP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
        txtNome = (EditText) findViewById(R.id.txtNome);
        txtSobrenome = (EditText) findViewById(R.id.txtSobrenome);
        txtCidade = (EditText) findViewById(R.id.txtCidade);
        txtBairro = (EditText) findViewById(R.id.txtBairro);
        txtEndereco = (EditText) findViewById(R.id.txtEndereco);
        txtCEP = (EditText) findViewById(R.id.txtCEP);

        if (getIntent().getExtras() != null){
            Long id = getIntent().getExtras().getLong("id");
            usuario = Usuario.findById(Usuario.class, id);

            txtEmail.setText(usuario.getEmail());
            txtSenha.setText(usuario.getSenha());
            txtNome.setText(usuario.getNome());
        }

        Button txtSalvar = (Button) findViewById(R.id.txtSalvar);
        txtSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!valida()){
                    Snackbar.make(view, errValidacao, Snackbar.LENGTH_LONG).show();
                    return;
                }

                if(usuario == null) {
                    List<Usuario> list = Usuario.find(Usuario.class, "email = ?", txtEmail.getText().toString());
                    if (list.size() > 0) {
                        usuario = list.get(0);
                        Snackbar.make(view, "Já existe um usuário com este e-mail cadastrado: " + usuario.getNome(), Snackbar.LENGTH_LONG).show();
                        usuario = null;
                    }else{
                        usuario = new Usuario();
                    }
                }

                if(usuario != null){
                    Endereco endereco;
                    if(usuario.getEndereco() == null){
                        endereco = new Endereco();
                    } else{
                        endereco = usuario.getEndereco();
                    }

                    usuario.setEmail(txtEmail.getText().toString());
                    usuario.setSenha(txtSenha.getText().toString());
                    usuario.setNome(txtNome.getText().toString());
                    usuario.setSobrenome(txtSobrenome.getText().toString());

                    endereco.setCidade(txtCidade.getText().toString());
                    endereco.setBairro(txtBairro.getText().toString());
                    endereco.setLogradouro(txtEndereco.getText().toString());
                    endereco.setCEP(txtCEP.getText().toString());

                    endereco.save();
                    usuario.setEndereco(endereco);
                    usuario.save();

                    limpaTela();
                    Snackbar.make(view, "Usuário salvo com sucesso!", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean valida(){
        // validação dos campos
        if(txtEmail.getText().length() == 0){
            errValidacao  = "E-mail inválido!";
            return false;
        }

        if(txtSenha.getText().length() == 0){
            errValidacao  = "Senha inválida!";
            return false;
        }

        return true;
    }

    private void limpaTela(){
        txtEmail.setText("");
        txtSenha.setText("");
        txtNome.setText("");
        txtSobrenome.setText("");
        txtCidade.setText("");
        txtBairro.setText("");
        txtEndereco.setText("");
        txtCEP.setText("");
    }
}
