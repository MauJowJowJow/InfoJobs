package sistemas2014.unifebe.edu.br.infojobs.Controller;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.List;

import sistemas2014.unifebe.edu.br.infojobs.Model.Endereco;
import sistemas2014.unifebe.edu.br.infojobs.Model.Usuario;
import sistemas2014.unifebe.edu.br.infojobs.Model.UsuarioLogado;
import sistemas2014.unifebe.edu.br.infojobs.R;

public class CadastroUsuario extends AppCompatActivity {
    private static final int READ_REQUEST_CODE = 42;

    private Uri uriCurriculo = null;

    private String errValidacao;
    private Usuario usuario;
    private EditText txtEmail;
    private EditText txtSenha;
    private EditText txtConfSenha;
    private EditText txtNome;
    private EditText txtSobrenome;
    private EditText txtCidade;
    private EditText txtBairro;
    private EditText txtEndereco;
    private EditText txtEstado;
    private EditText txtCEP;
    private EditText txtTelCelular;
    private EditText txtTelResidencial;
    private ImageButton btnSelArquivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
        txtConfSenha = (EditText) findViewById(R.id.txtConfSenha);
        txtNome = (EditText) findViewById(R.id.txtNome);
        txtSobrenome = (EditText) findViewById(R.id.txtSobrenome);
        txtCidade = (EditText) findViewById(R.id.txtCidade);
        txtBairro = (EditText) findViewById(R.id.txtBairro);
        txtEndereco = (EditText) findViewById(R.id.txtEndereco);
        txtCEP = (EditText) findViewById(R.id.txtCEP);
        txtEstado = (EditText) findViewById(R.id.txtEstado);
        txtTelCelular = (EditText) findViewById(R.id.txtTelCelular);
        txtTelResidencial = (EditText) findViewById(R.id.txtTelResidencial);

        if (getIntent().getExtras() != null) {
            Long id = getIntent().getExtras().getLong("id");
            usuario = Usuario.findById(Usuario.class, id);

            txtEmail.setText(usuario.getEmail());
            txtSenha.setText(usuario.getSenha());
            txtConfSenha.setText(usuario.getSenha());
            txtNome.setText(usuario.getNome());
            txtSobrenome.setText(usuario.getSobrenome());
            txtTelCelular.setText(usuario.getTelCelular());
            txtTelResidencial.setText(usuario.getTelResidencial());

            Endereco endereco = usuario.getEndereco();
            if (endereco != null) {
                if(endereco.getCidade() != null) txtCidade.setText(endereco.getCidade());
                if(endereco.getLogradouro() != null) txtEndereco.setText(endereco.getLogradouro());
                if(endereco.getCEP() != null) txtCEP.setText(endereco.getCEP());
                if(endereco.getEstado() != null) txtEstado.setText(endereco.getEstado());
                if(endereco.getBairro() != null) txtBairro.setText(endereco.getBairro());
            }

            txtEmail.setEnabled(false);
            txtSenha.setEnabled(false);
            txtConfSenha.setEnabled(false);
        }

        btnSelArquivo = (ImageButton) findViewById(R.id.btnSelArquivo);
        btnSelArquivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escolheCurriculo();
            }
        });

        Button txtSalvar = (Button) findViewById(R.id.btnSalvar);
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
                    usuario.setTelCelular(txtTelCelular.getText().toString());
                    usuario.setTelResidencial(txtTelResidencial.getText().toString());

                    endereco.setCidade(txtCidade.getText().toString());
                    endereco.setBairro(txtBairro.getText().toString());
                    endereco.setLogradouro(txtEndereco.getText().toString());
                    endereco.setCEP(txtCEP.getText().toString());

                    endereco.save();
                    usuario.setEndereco(endereco);
                    salvaCurriculo();

                    usuario.save();

                    limpaTela();
                    Snackbar.make(view, "Usuário salvo com sucesso!", Snackbar.LENGTH_LONG).show();
                    finish();
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

        if(!txtSenha.getText().toString().equals(txtConfSenha.getText().toString())){
            errValidacao  = "Senhas de confirmação não conferem!";
            return false;
        }

        return true;
    }

    private void limpaTela(){
        txtEmail.setText("");
        txtSenha.setText("");
        txtConfSenha.setText("");
        txtNome.setText("");
        txtSobrenome.setText("");
        txtCidade.setText("");
        txtBairro.setText("");
        txtEndereco.setText("");
        txtCEP.setText("");
        txtTelCelular.setText("");
        txtTelResidencial.setText("");
    }

    private void escolheCurriculo(){
        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".

        intent.setType("*/*");
        String[] mimetypes = {"application/pdf", "application/msword"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().

            if (resultData != null) {
                uriCurriculo = resultData.getData();
            }
        }
    }

    private void salvaCurriculo(){
        if(uriCurriculo == null || usuario.getId() == null){
            return;
        }

        File file = new File(uriCurriculo.getPath());

        Usuario usuarioLogado = UsuarioLogado.findAll(UsuarioLogado.class).next().getUsuario();
        File diretorio = getApplicationContext().getExternalFilesDir(null);
        diretorio.mkdirs();

        File newFile = new File(diretorio, "curr" + usuario.getId() + file.getName());

        try {
            InputStream in = getContentResolver().openInputStream(uriCurriculo);
            OutputStream out = new FileOutputStream(newFile);

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        /*
        FileChannel outputChannel = null;
        FileChannel inputChannel = null;

        try {
            outputChannel = new FileOutputStream(newFile).getChannel();
            inputChannel = new FileInputStream(uriCurriculo).getChannel();
            inputChannel.transferTo(0, inputChannel.size(), outputChannel);

            if (inputChannel != null) inputChannel.close();
            if (outputChannel != null) outputChannel.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }*/

        usuario.setPathCurriculo(newFile.getPath());
    }
}
