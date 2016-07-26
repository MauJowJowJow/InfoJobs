package sistemas2014.unifebe.edu.br.infojobs.Helpers;

import android.os.AsyncTask;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONObject;

/**
 * Created by mauma on 25/07/2016.
 */
public class FBGetUsuario extends AsyncTask<String, Void, String> {
    private String usuario = "";

    protected String doInBackground(String... voids) {
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        JSONObject jsonObj = response.getJSONObject();
                        try {
                            usuario = jsonObj.getString("name");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
        ).executeAndWait();

        return usuario;
    }

    private String getUsuario() {
        return usuario;
    }
}
