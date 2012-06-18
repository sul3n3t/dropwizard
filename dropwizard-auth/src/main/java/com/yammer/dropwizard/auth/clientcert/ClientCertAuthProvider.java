package com.yammer.dropwizard.auth.clientcert;

import com.sun.jersey.api.model.Parameter;
import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;
import com.yammer.dropwizard.auth.Auth;
import com.yammer.dropwizard.auth.Authenticator;
import com.yammer.dropwizard.logging.Log;

import java.security.Principal;

/**
 * A Jersey provider for Client Certificate authentication.
 *
 * @param <T>    the principal type.
 */
public class ClientCertAuthProvider<T> implements InjectableProvider<Auth, Parameter> {
    static final Log LOG = Log.forClass(ClientCertAuthProvider.class);

    private final Authenticator<Principal, T> authenticator;

    /**
     * Creates a new {@link ClientCertAuthProvider} with the given {@link Authenticator}.
     *
     * @param authenticator    the authenticator which will take the {@link Principal} and convert
     *                         it to instances of {@code T}.
     */
    public ClientCertAuthProvider(Authenticator<Principal, T> authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public ComponentScope getScope() {
        return ComponentScope.PerRequest;
    }

    @Override
    public Injectable getInjectable(ComponentContext c, Auth a, Parameter p) {
        return new ClientCertAuthInjectable<T>(authenticator, a.required());
    }
}
