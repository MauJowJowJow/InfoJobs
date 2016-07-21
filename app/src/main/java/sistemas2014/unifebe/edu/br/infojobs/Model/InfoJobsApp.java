package sistemas2014.unifebe.edu.br.infojobs.Model;

import android.app.Application;
import android.content.res.Configuration;

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
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
