package sistemas2014.unifebe.edu.br.infojobs.Model;

import com.orm.SugarRecord;

/**
 * Created by mauma on 20/07/2016.
 */
public class Cargo extends SugarRecord {

    private String nome;
    private AreaNegocio areaNegocio;

    public Cargo(){

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public AreaNegocio getAreaNegocio() {
        return areaNegocio;
    }

    public void setAreaNegocio(AreaNegocio areaNegocio) {
        this.areaNegocio = areaNegocio;
    }
}
