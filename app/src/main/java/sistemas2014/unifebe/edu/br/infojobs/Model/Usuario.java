package sistemas2014.unifebe.edu.br.infojobs.Model;

import android.content.Context;

import com.orm.SugarRecord;

/**
 * Created by mauma on 13/07/2016.
 */
public class Usuario extends SugarRecord {

    private String nome;
    private String login;
    private String senha;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
