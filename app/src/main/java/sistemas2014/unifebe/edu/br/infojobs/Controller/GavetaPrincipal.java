package sistemas2014.unifebe.edu.br.infojobs.Controller;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sistemas2014.unifebe.edu.br.infojobs.Model.Usuario;
import sistemas2014.unifebe.edu.br.infojobs.Model.UsuarioLogado;
import sistemas2014.unifebe.edu.br.infojobs.R;

public class GavetaPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private Usuario usuario;
    private TextView txtUsuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gaveta_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View nav_heaver_gaveta = navigationView.getHeaderView(0);

        txtUsuarioLogado = (TextView) nav_heaver_gaveta.findViewById(R.id.txtUsuarioLogado);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.gaveta_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment=null;
        Intent i=null;
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        switch (id){
            case R.id.nav_vagas:
                fragment = new ConsultaVagas();
                break;
            case R.id.nav_login:
                i = new Intent(getApplicationContext(), LoginActivity.class);
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_about:
                break;
        }

        if(fragment != null) {
            transaction.replace(R.id.flContent, fragment);
            transaction.commit();

            item.setChecked(true);
            setTitle(item.getTitle());
        }else if(i != null){
            startActivity(i);
        }

        return true;
    }

    @Override
    public void onResume(){
        super.onResume();

        checaUsuarioLogado();
    }

    private void checaUsuarioLogado(){
        Iterator<UsuarioLogado> usuarioLogado = UsuarioLogado.findAll(UsuarioLogado.class);
        usuario = null;
        while(usuarioLogado.hasNext()){
            usuario = usuarioLogado.next().getUsuario();
            setaUsuarioLogado();
            return;
        }
    }

    private void setaUsuarioLogado(){
        if(usuario != null) {
            txtUsuarioLogado.setText(usuario.getNome() + usuario.getSobrenome());
        }else{
            txtUsuarioLogado.setText("Nenhum usuário logado");
        }
    }
}
