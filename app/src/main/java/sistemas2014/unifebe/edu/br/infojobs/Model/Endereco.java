package sistemas2014.unifebe.edu.br.infojobs.Model;

import com.orm.SugarRecord;

/**
 * Created by mauma on 24/07/2016.
 */
public class Endereco extends SugarRecord {
    private String logradouro;
    private String cidade;

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    private String estado;
}
