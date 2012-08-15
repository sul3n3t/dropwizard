package com.yammer.dropwizard.tls;

import com.sun.jersey.api.model.AbstractMethod;
import com.sun.jersey.spi.container.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;

/**
 * If a client uses a certificate to authenticate itself, this class provides a resource filter to
 * pass the client's certificate chain on for a resource. This is best used in a injection provider.
 *
 * NOTE: ClientCertificateFilter will NOT authenticate the client's certificate chain, just pass it
 * along.
 *
 * HttpContext c;
 * Principal p = c.getRequest().getUserPrincipal();
 * if (p instanceof CertificatePrincipal) {
 *     ImmutableList<X509Certificate> certs = ((CertificatePrincipal) p).getCertificateChain();
 * }
 */
public class ClientCertificateFilterFactory implements ResourceFilterFactory {
    @Context HttpServletRequest servletRequest;
    private boolean enable;

    public ClientCertificateFilterFactory(boolean enable) {
        this.enable = enable;
    }

    @Override
    public List<ResourceFilter> create(AbstractMethod am) {
        if (enable) {
            return Collections.<ResourceFilter>singletonList(new ClientCertificateFilter());
        }
        return Collections.emptyList();
    }

    public class ClientCertificateFilter implements ContainerRequestFilter, ResourceFilter {

        @Override
        public ContainerRequestFilter getRequestFilter() {
            return this;
        }

        @Override
        public ContainerResponseFilter getResponseFilter() {
            return null;
        }

        @Override
        public ContainerRequest filter(ContainerRequest request) {
            X509Certificate[] chain = (X509Certificate[]) servletRequest.getAttribute(
                    "javax.servlet.request.X509Certificate");
            if (chain != null && chain.length > 0) {
                String subjectDn = chain[0].getSubjectDN().getName();
                CertificateSecurityContext securityContext =
                        new CertificateSecurityContext(subjectDn, chain);
                request.setSecurityContext(securityContext);
            }

            return request;
        }
    }
}
