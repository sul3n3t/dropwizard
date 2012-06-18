package com.yammer.dropwizard.jetty;

import com.sun.security.auth.UserPrincipal;
import org.eclipse.jetty.security.DefaultIdentityService;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.server.UserIdentity;

import javax.security.auth.Subject;
import java.security.Principal;

public class CertificateLoginService implements LoginService {
    private IdentityService identityService = new DefaultIdentityService();

    /**
     * Given a username and the signature from the client cert, validate that the user is
     * authorized.
     */
    @Override
    public UserIdentity login(final String username, final Object credentials) {
        final Principal userPrincipal = new UserPrincipal(username);
        final Subject subject = new Subject();
        subject.getPrincipals().add(userPrincipal);
        subject.getPrivateCredentials().add(credentials);
        subject.setReadOnly();

        return identityService.newUserIdentity(subject, userPrincipal, new String[] {"user"});
    }

    @Override
    public String getName() {
        return "client-cert";
    }

    @Override
    public boolean validate(UserIdentity user) {
        return true;
    }

    @Override
    public IdentityService getIdentityService() {
        return identityService;
    }

    @Override
    public void setIdentityService(IdentityService service) {
        this.identityService = service;
    }

    @Override
    public void logout(UserIdentity user) { /* Don't need to do anything. */ }
}

