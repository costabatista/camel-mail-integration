package br.com.paulobc.camelmail.route;

import java.io.InputStream;
import java.util.Properties;

import org.apache.camel.builder.RouteBuilder;

public class SMTPRouteBuilder extends RouteBuilder {
    private String mailAddress;
    private String smtpAddress;
    private String port;
    private String smtpEndPoint;
    private boolean ssl;
    private String password;

    public SMTPRouteBuilder() {
        Properties properties = new Properties();
        ClassLoader classLoader = this.getClass().getClassLoader();

        try (InputStream resourceStream = classLoader.getResourceAsStream("application.properties")) {
            properties.load(resourceStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setSmtpAddress(properties.getProperty("mail-smtp-address"));
        this.setMailAddress(properties.getProperty("mail-username"));
        this.setPassword(properties.getProperty("mail-password"));
        this.setPort(properties.getProperty("mail-smtp-port"));
        this.setSsl(("true".equals(properties.getProperty("mail-ssl-smtp")) ? true : false));

        StringBuilder builder = new StringBuilder();

        builder.append(this.isSsl() ? "smtps://" : "smtp://").append(this.getSmtpAddress()).append("?username=")
                .append(this.getMailAddress()).append("&password=").append(this.getPassword()).append("&delay=60000")
                .append("&debugMode=true");

        this.setSmtpEndPoint(builder.toString());

    }

    @Override
    public void configure() throws Exception {
        from("file://outbox?fileName=mail-content.txt&noop=true")
                .routeId(RouteTypeID.SMTP.getId())
                .setHeader("to", simple("paulobatistadacosta@gmail.com"))
                .setHeader("from", simple("pauloc@linuxmail.org"))
                .setHeader("subject", simple("A mail to test apache camel mail component - by Paulo Batista da Costa"))
                .to(this.getSmtpEndPoint());

    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getSmtpAddress() {
        return smtpAddress;
    }

    public void setSmtpAddress(String smtpAddress) {
        this.smtpAddress = smtpAddress;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSmtpEndPoint() {
        return smtpEndPoint;
    }

    public void setSmtpEndPoint(String smtpEndPoint) {
        this.smtpEndPoint = smtpEndPoint;
    }

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
