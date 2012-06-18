package com.yammer.dropwizard.auth.clientcert;

import com.google.common.base.Optional;
import com.sun.security.auth.UserPrincipal;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.Authenticator;
import java.security.KeyStore;
import java.security.Principal;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

/**
 * This is more of an example
 */
public class ClientCertAuthenticator implements Authenticator<Principal, UserPrincipal>, X509TrustManager {
  private final KeyStore trustStore;

  public ClientCertAuthenticator(KeyStore trustStore) {
    this.trustStore = trustStore;
  }

  @Override public Optional<UserPrincipal> authenticate(Principal credentials)
      throws AuthenticationException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override public void checkClientTrusted(X509Certificate[] x509Certificates, String s)
      throws CertificateException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override public void checkServerTrusted(X509Certificate[] x509Certificates, String s)
      throws CertificateException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override public X509Certificate[] getAcceptedIssuers() {
    return new X509Certificate[0];  //To change body of implemented methods use File | Settings | File Templates.
  }
}
