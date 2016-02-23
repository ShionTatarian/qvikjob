package fi.qvik.job;

import android.app.Application;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Tommy on 23/02/16.
 */
public class BaseApplication extends Application {

    private final String TAG = "BaseApplication";
    private final int REALM_VERSION = 1;

    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .name("qvik_job_db.realm")
                .schemaVersion(REALM_VERSION)
                .deleteRealmIfMigrationNeeded()
                .build();
        // Use the config
        Realm.setDefaultConfiguration(config);
        Realm realm = Realm.getDefaultInstance();
        Log.d(TAG, "Realm initiated: " + realm.getVersion());
        realm.close();
    }



}
