package com.mvvm.core.manager;

/**
 * Provide configurations for different environment (prod, dev staging).
 * Define base url store in res/values/config.xml for each specific environment
 * Values are loaded during app build
 */
public interface EndpointManager {

    enum Environment {dev, staging, prod}

    Environment getCurrentEnvironment();

    void setEnvironement(Environment env);

    String getAuthorizationToken();

    String getAppId();

    String getBaseUrlV2();

    String getBaseUrlV3();
}
