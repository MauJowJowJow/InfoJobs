package sistemas2014.unifebe.edu.br.infojobs.Model;

import com.orm.SugarRecord;

/**
 * Created by mauma on 26/07/2016.
 */
public class UsuarioLogado extends SugarRecord{
    private Usuario usuario;

    public  UsuarioLogado(){

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
