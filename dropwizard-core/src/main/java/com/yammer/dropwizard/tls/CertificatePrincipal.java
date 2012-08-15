package com.yammer.dropwizard.tls;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;

import java.security.Principal;
import java.security.cert.X509Certificate;

public class CertificatePrincipal implements Principal {
    private final String subjectDn;
    private final ImmutableList<X509Certificate> certificateChain;

    public CertificatePrincipal(String subjectDn, X509Certificate[] chain) {
        this.subjectDn = subjectDn;
        certificateChain = ImmutableList.copyOf(chain);
    }

    public ImmutableList<X509Certificate> getCertificateChain() {
        return certificateChain;
    }

    /**
     * @return string which should be a valid {@link sun.security.x509.X500Name}.
     */
    @Override
    public String getName() {
        return subjectDn;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .add("subjectDn", subjectDn)
                      .add("certificateChain", certificateChain)
                      .toString();
    }
}
