package com.yammer.dropwizard.auth.clientcert;

import com.google.common.base.Optional;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.Authenticator;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

class ClientCertAuthInjectable<T> extends AbstractHttpContextInjectable<T>  {
    private final Authenticator<Principal, T> authenticator;
    private final boolean required;

    public ClientCertAuthInjectable(Authenticator<Principal, T> authenticator, boolean required) {
        this.authenticator = authenticator;
        this.required = required;
    }

    public Authenticator<Principal, T> getAuthenticator() {
        return authenticator;
    }

    public boolean isRequired() {
        return required;
    }

    @Override
    public T getValue(HttpContext c) {
        if (SecurityContext.CLIENT_CERT_AUTH.equals(c.getRequest().getAuthenticationScheme())) {
            Principal principal = c.getRequest().getUserPrincipal();
            try {
                Optional<T> result = authenticator.authenticate(principal);
                if (result.isPresent()) {
                    return result.get();
                }
            } catch (AuthenticationException e) {
                ClientCertAuthProvider.LOG.warn(e, "Error authenticating credentials");
                throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
            }
        }

        if (required) {
            throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Credentials are required to access this resource.")
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .build());
        }

        return null;
    }
}
