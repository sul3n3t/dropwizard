package com.yammer.dropwizard.tls;

import com.google.common.base.Optional;
import com.yammer.dropwizard.config.HttpConfiguration;
import org.codehaus.jackson.annotate.JsonProperty;

public class TlsConfiguration extends HttpConfiguration {
    @JsonProperty
    protected String keyStorePath = null;

    @JsonProperty
    protected String keyStorePassword = null;

    @JsonProperty
    protected String keyManagerPassword = null;

    @JsonProperty
    protected String trustStorePath = null;

    @JsonProperty
    protected String trustStorePassword = null;

    @JsonProperty
    protected boolean acceptClientCertificates = false;

    public Optional<String> getKeyStorePath() {
        return Optional.fromNullable(keyStorePath);
    }

    public Optional<String> getKeyStorePassword() {
        return Optional.fromNullable(keyStorePassword);
    }

    public Optional<String> getKeyManagerPassword() {
        return Optional.fromNullable(keyManagerPassword);
    }

    public Optional<String> getTrustStorePath() {
        return Optional.fromNullable(trustStorePath);
    }

    public Optional<String> getTrustStorePassword() {
        return Optional.fromNullable(trustStorePassword);
    }

    public boolean acceptsClientCertificates() {
        return acceptClientCertificates;
    }

    // ciphers
    // other sslContextFactory stuff
    // verify, want, etc.
}
