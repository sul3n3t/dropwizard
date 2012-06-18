package com.yammer.dropwizard.auth.clientcert.tests;

import com.sun.jersey.core.spi.component.ComponentScope;
import com.yammer.dropwizard.auth.Authenticator;
import com.yammer.dropwizard.auth.clientcert.ClientCertAuthProvider;
import java.security.Principal;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class ClientCertAuthProviderTest {
    private final Authenticator<Principal, String> authenticator = mock(Authenticator.class);
    private final ClientCertAuthProvider<String> provider = new ClientCertAuthProvider<String>(authenticator);

    @Test
    public void isPerRequest() {
        assertThat(provider.getScope(),
                   is(ComponentScope.PerRequest));
    }
}
