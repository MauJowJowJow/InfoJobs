package sistemas2014.unifebe.edu.br.infojobs.Model;

import com.orm.SugarRecord;

/**
 * Created by mauma on 20/07/2016.
 */
public class Vaga extends SugarRecord {
    private String descricao;
    private Cargo cargo;
    private Empresa empresa;
    private double salario;
}
