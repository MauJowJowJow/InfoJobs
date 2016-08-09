package sistemas2014.unifebe.edu.br.infojobs.Helpers;

import android.os.AsyncTask;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mauma on 23/07/2016.
 */
public class BuscaPermissoesFB extends AsyncTask<String, Void, Boolean> {
    private Boolean publicarPermitido;
    private String permissao;

    protected Boolean doInBackground(String... permissao) {
        publicarPermitido = false;
        this.permissao = permissao[0];

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + AccessToken.getCurrentAccessToken().getUserId() + "/permissions",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        try {

                            JSONObject jsonObj = response.getJSONObject();
                            JSONArray jsonArr = jsonObj.getJSONArray("data");

                            for(int i = 0; i< jsonArr.length(); i++){
                                JSONObject permissaoObj = jsonArr.getJSONObject(i);

                                if (permissaoObj.getString("permission").equals(getPermissao())
                                        && permissaoObj.getString("status").equals("granted")) {

                                    publicarPermitido = true;
                                    break;
                                }
                            }

                        }catch (JSONException ex){
                            ex.printStackTrace();
                        }
                    }
                }
        ).executeAndWait();

        return publicarPermitido;
    }

    private String getPermissao(){
        return permissao;
    }
}