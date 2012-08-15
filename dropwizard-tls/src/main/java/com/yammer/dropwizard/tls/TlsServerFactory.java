package com.yammer.dropwizard.tls;

import com.yammer.dropwizard.config.Environment;

public class TlsServerFactory {
    public TlsServerFactory(Environment environment) {
        environment.addProvider(ClientCertificateFilterFactory.class);
    }
}
