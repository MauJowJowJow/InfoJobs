package sistemas2014.unifebe.edu.br.infojobs.Controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import sistemas2014.unifebe.edu.br.infojobs.Model.Vaga;
import sistemas2014.unifebe.edu.br.infojobs.R;

public class DetalhesVaga extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_vaga);

        if(getIntent().getExtras() != null){
            Vaga vaga = Vaga.findById(Vaga.class, getIntent().getExtras().getLong("id"));

            Toast.makeText(getApplicationContext(), vaga.getDescricao(), Toast.LENGTH_LONG).show();
        }
    }

}
