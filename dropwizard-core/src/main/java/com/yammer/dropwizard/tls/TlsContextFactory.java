package com.yammer.dropwizard.tls;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import javax.annotation.Nullable;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CRL;
import java.util.Collection;
import java.util.Set;

/**
 * If enabled and provided, client certificates are authenticated based on TrustStore configuration
 * or by wrapping the default TrustManagers.
 */
// TODO(justin): rename
// TODO(justin): Explicitly enable TLS1.1, 1.2 if supported.
public class TlsContextFactory extends SslContextFactory {

    // Using RC4 to avoid the Rizzo/Duong attack on block ciphers.
    // TODO(justin): Add TLS1.1, 1.2 ONLY ciphers to the beginning of the list.
    // TODO(justin): CipherSuite list regardless of BEAST?
    private static final ImmutableSet<String> BEAST_CIPHER_SUITES = ImmutableSet.of(
            /* Provided by BouncyCastle, at least. */
            "TLS_ECDHE_ECDSA_WITH_RC4_128_SHA", "TLS_ECDHE_RSA_WITH_RC4_128_SHA",
            "TLS_ECDH_ECDSA_WITH_RC4_128_SHA", "TLS_ECDH_RSA_WITH_RC4_128_SHA",

            /* Should be fairly widespread. */
            "TLS_RSA_WITH_RC4_128_SHA", "SSL_RSA_WITH_RC4_128_SHA");

    private final TlsManagerWrapper wrapper;

    public TlsContextFactory(TlsConfiguration config, @Nullable TlsManagerWrapper wrapper) {
        this.wrapper = (wrapper != null) ? wrapper : new TlsManagerWrapper();


        for (String path : config.getKeyStorePath().asSet()) {
            setKeyStorePath(path);
        }

        for (String password : config.getKeyStorePassword().asSet()) {
            setKeyStorePassword(password);
        }

        for (String password : config.getKeyManagerPassword().asSet()) {
            setKeyManagerPassword(password);
        }

        for (String path : config.getTrustStorePath().asSet()) {
            setTrustStore(path);
        }

        for (String password : config.getTrustStorePassword().asSet()) {
            setTrustStorePassword(password);
        }

        setIncludeCipherSuites(availableCipherSuites(BEAST_CIPHER_SUITES));

        if (config.acceptsClientCertificates()) {
            setWantClientAuth(true);
            setValidatePeerCerts(true);
        }
    }

    @Override
    protected KeyManager[] getKeyManagers(KeyStore k) throws Exception {
        KeyManager[] managers = super.getKeyManagers(k);
        for (int i = 0; i < managers.length; i++) {
            managers[1] = wrapper.wrap(managers[i]);
        }
        return managers;
    }

    @Override
    protected TrustManager[] getTrustManagers(KeyStore k, Collection<? extends CRL> c) throws Exception {
        TrustManager[] managers = super.getTrustManagers(k, c);
        for (int i = 0; i < managers.length; i++) {
            managers[1] = wrapper.wrap(managers[i]);
        }
        return managers;
    }

    /**
     * Java6's JsseJce provider has an intermittent bug when checking for EC cipher support.
     * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=7043514
     *
     * @return intersection of given and supported CipherSuites.
     */
    public static String[] availableCipherSuites(Set<String> cipherSuites) {
        String version = System.getProperty("java.version");
        if (version.startsWith("1.6.")) {
            // TODO: filter EC suites
        }

        String[] availableSuites;
        try {
            availableSuites = SSLContext.getDefault().getSupportedSSLParameters().getCipherSuites();
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError("Cannot obtain the default SSLContext: " + e.getLocalizedMessage());
        }

        Sets.SetView<String> enabled = Sets.intersection(cipherSuites, Sets.newHashSet(availableSuites));
        return enabled.toArray(new String[enabled.size()]);
    }

    /**
     * Extend to customize KeyManagers and/or TrustManagers used.
     */
    public class TlsManagerWrapper {
        protected KeyManager wrap(KeyManager manager) {
            return manager;
        }

        protected TrustManager wrap(TrustManager manager) {
            return manager;
        }
    }
}
