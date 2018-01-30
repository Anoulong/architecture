package com.prototype.architecture.mvvm.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.mvvm.core.manager.EndpointManager;
import com.prototype.architecture.mvvm.R;

/**
 * Provide configurations for different environment (prod, dev staging).
 * Define base url store in res/values/config.xml for each specific environment
 * Values are loaded during app build
 */
public class EndpointManagerImpl implements EndpointManager{

    private String baseUrlV2 = "undefined";
    private String baseUrlV3 = "undefined";
    public static final String TAG = EndpointManagerImpl.class.getSimpleName();

    public static String PREF_ENV = "env";

    private Environment currentEnvironment;
    private Context context;

    public EndpointManagerImpl(Context context) {
        this.context = context;
        Environment savedEnvironment = getSavedEnvironement();
        if (savedEnvironment == null) {
            // Using env from build
            String rawEnv = context.getString(R.string.config_environment);
            setEnvironement(Environment.valueOf(rawEnv));
        } else {
            currentEnvironment = savedEnvironment;
        }

        switch (currentEnvironment) {
            case prod:
                baseUrlV2 = context.getString(R.string.prod_base_url_v2);
                baseUrlV3 = context.getString(R.string.prod_base_url_v3);
                break;
            case staging:
                baseUrlV2 = context.getString(R.string.staging_base_url_v2);
                baseUrlV3 = context.getString(R.string.staging_base_url_v3);
                break;
            case dev:
            default:
                baseUrlV2 = context.getString(R.string.dev_base_url_v2);
                baseUrlV3 = context.getString(R.string.dev_base_url_v3);
                break;
        }
        Log.i(TAG,"Using environment: " + getCurrentEnvironment());
    }

    public String getBaseUrlV2() {
        return baseUrlV2;
    }

    public String getBaseUrlV3() {
        return baseUrlV3;
    }

    public Environment getCurrentEnvironment() {
        return currentEnvironment;
    }

    private Environment getSavedEnvironement() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_ENV, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(context.getString(R.string.pref_key_env))) {
            Log.i(TAG,"Environment found on disk");
            return Environment.valueOf(sharedPreferences.getString(context.getString(R.string.pref_key_env), null));
        }
        Log.i(TAG,"Environment not found on disk, using build default");
        return null;
    }

    @Override
    public void setEnvironement(EndpointManager.Environment env) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_ENV, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(context.getString(R.string.pref_key_env), env.toString()).apply();
        currentEnvironment = env;
    }

    @Override
    public String getAuthorizationToken() {
        return context.getString(R.string.authorization_value);
    }

    public String getAppId() {
        return context.getString(R.string.app_id);
    }
}
