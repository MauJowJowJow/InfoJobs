package sistemas2014.unifebe.edu.br.infojobs.Model;

import com.orm.SugarRecord;

/**
 * Created by mauma on 20/07/2016.
 */
public class Vaga extends SugarRecord {
    private String descricao;
    private Cargo cargo;
    private Endereco endereco;
    private Empresa empresa;
    private double salario;

    public Vaga(){

    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
}
