package sistemas2014.unifebe.edu.br.infojobs.Model;

import com.orm.SugarRecord;

/**
 * Created by mauma on 13/07/2016.
 */
public class Usuario extends SugarRecord {

    private String nome;
    private String email;
    private String senha;
    private String sobrenome;
    private Endereco endereco;
    private String telCelular;
    private String telResidencial;
    private String pathCurriculo;

    public Usuario(){}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getTelCelular() {
        return telCelular;
    }

    public void setTelCelular(String telCelular) {
        this.telCelular = telCelular;
    }

    public String getTelResidencial() {
        return telResidencial;
    }

    public void setTelResidencial(String telResidencial) {
        this.telResidencial = telResidencial;
    }

    public String getPathCurriculo() {
        return pathCurriculo;
    }

    public void setPathCurriculo(String pathCurriculo) {
        this.pathCurriculo = pathCurriculo;
    }
}
