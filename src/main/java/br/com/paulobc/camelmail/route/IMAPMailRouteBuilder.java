package br.com.paulobc.camelmail.route;

import java.io.InputStream;
import java.util.Properties;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import br.com.paulobc.camelmail.parser.Parser;

public class IMAPMailRouteBuilder extends RouteBuilder {
    private String imapAddress;
    private String mailAddress;
    private String password;
    private String port;
    private String imapEndpoint;
    private boolean ssl;

    public IMAPMailRouteBuilder() {
        Properties properties = new Properties();
        ClassLoader classLoader = this.getClass().getClassLoader();

        try (InputStream resourceStream = classLoader.getResourceAsStream("application.properties")) {
            properties.load(resourceStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setImapAddress(properties.getProperty("gmail-imap-address"));
        this.setMailAddress(properties.getProperty("gmail-username"));
        this.setPassword(properties.getProperty("gmail-password"));
        this.setPort(properties.getProperty("gmail-imap-port"));
        this.setSsl(("true".equals(properties.getProperty("gmail-ssl")) ? true : false));

        StringBuilder builder = new StringBuilder();

        builder.append(this.isSsl() ? "imaps://" : "imap://").append(this.getImapAddress()).append("?username=")
                .append(this.getMailAddress()).append("&password=").append(this.getPassword()).append("&delete=false")
                .append("&unseen=true").append("&delay=60000").append("&closeFolder=false").append("&disconnect=false")
                .append("&debugMode=true").append("&fetchSize=35").append("&alternativeBodyHeader=plain-text")
                .append("&greedy=true");

        this.setImapEndpoint(builder.toString());

    }

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    @Override
    public void configure() throws Exception {
        from(this.getImapEndpoint()).streamCaching().process(new Processor() {

            @Override
            public void process(Exchange exchange) throws Exception {
                String messageBody = exchange.getMessage().getBody(String.class);
                
                exchange.getMessage().setBody(new Parser().htmlToPlainText(messageBody));

            }
            
        })
        .to("file://inbox").setId(RouteTypeID.IMAP.getId());

    }

    public String getImapEndpoint() {
        return imapEndpoint;
    }

    public void setImapEndpoint(String imapEndpoint) {
        this.imapEndpoint = imapEndpoint;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getImapAddress() {
        return imapAddress;
    }

    public void setImapAddress(String imapAddress) {
        this.imapAddress = imapAddress;
    }

}
