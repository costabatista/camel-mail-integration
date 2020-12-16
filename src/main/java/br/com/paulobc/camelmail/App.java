package br.com.paulobc.camelmail;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.jsse.KeyStoreParameters;
import org.apache.camel.support.jsse.SSLContextParameters;
import org.apache.camel.support.jsse.TrustManagersParameters;

import br.com.paulobc.camelmail.route.IMAPMailRouteBuilder;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        KeyStoreParameters trust_ksp = new KeyStoreParameters();
        trust_ksp.setResource("/cacerts.jks");
        trust_ksp.setPassword("changeit");
        TrustManagersParameters trustp = new TrustManagersParameters();
        trustp.setKeyStore(trust_ksp);

        SSLContextParameters scp = new SSLContextParameters();
        scp.setTrustManagers(trustp);

        CamelContext context = new DefaultCamelContext();

        context.setSSLContextParameters(scp);
        try {
            context.addRoutes(new IMAPMailRouteBuilder());
            while (true) {
                context.start();
                Thread.sleep(60000 * 2);
                context.stop();
                context.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        

    }
}
