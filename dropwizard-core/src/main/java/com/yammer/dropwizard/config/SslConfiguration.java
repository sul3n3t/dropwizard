package com.yammer.dropwizard.config;

import com.google.common.base.Optional;
import org.codehaus.jackson.annotate.JsonProperty;

public class SslConfiguration {
    @JsonProperty
    protected String keyStorePath = null;

    @JsonProperty
    protected String keyStorePassword = null;

    @JsonProperty
    protected String keyManagerPassword = null;

    @JsonProperty
    protected boolean acceptsClientCertificates = false;

    @JsonProperty
    protected String trustStorePath = null;

    @JsonProperty
    protected String trustStorePassword = null;

    public Optional<String> getKeyStorePath() {
        return Optional.fromNullable(keyStorePath);
    }

    public Optional<String> getKeyStorePassword() {
        return Optional.fromNullable(keyStorePassword);
    }

    public Optional<String> getKeyManagerPassword() {
        return Optional.fromNullable(keyManagerPassword);
    }

    public boolean isAcceptsClientCertificates() {
        return acceptsClientCertificates;
    }

    public Optional<String> getTrustStorePath() {
        return Optional.fromNullable(trustStorePath);
    }

    public Optional<String> getTrustStorePassword() {
        return Optional.fromNullable(trustStorePassword);
    }
}
