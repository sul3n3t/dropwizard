package com.yammer.dropwizard.tls;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.security.cert.X509Certificate;

/**
 * #getUserPrincipal() returns a {@link CertificatePrincipal} to expose the client's certificate
 * chain. This can be useful for more granular authorization, for example on a resource.
 */
public class CertificateSecurityContext implements SecurityContext {
    private final CertificatePrincipal principal;

    public CertificateSecurityContext(String subjectDn, X509Certificate[] chain) {
        this.principal = new CertificatePrincipal(subjectDn, chain);
    }

    /**
     * After ensuring your {@link javax.ws.rs.core.SecurityContext} is an instance of CertificateSecurityContext,
     * cast the {@link java.security.Principal} to a {@link CertificatePrincipal}.
     *
     * @return CertificatePrincipal principal type which includes the client's certificate chain.
     */
    @Override
    public Principal getUserPrincipal() {
        return principal;
    }

    @Override
    public boolean isUserInRole(String role) {
        return false;
    }

    @Override
    public boolean isSecure() {
        return true;
    }

    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.CLIENT_CERT_AUTH;
    }
}
