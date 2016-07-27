package sistemas2014.unifebe.edu.br.infojobs;

import android.app.Application;
import android.content.res.Configuration;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.stetho.Stetho;
import com.orm.SugarContext;

import sistemas2014.unifebe.edu.br.infojobs.Model.Usuario;

/**
 * Created by mauma on 18/07/2016.
 */
public class InfoJobsApp extends Application {

    private static InfoJobsApp instance;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        Stetho.initializeWithDefaults(this);

        FacebookSdk.sdkInitialize(getApplicationContext());

        AppEventsLogger.activateApp(this);

        SugarContext.init(getApplicationContext());
        Usuario.find(Usuario.class, "email = ?", "teste");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        SugarContext.terminate();
    }
}
