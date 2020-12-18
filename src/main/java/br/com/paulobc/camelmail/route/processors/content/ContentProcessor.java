package br.com.paulobc.camelmail.route.processors.content;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import br.com.paulobc.camelmail.format.Mail;
import br.com.paulobc.camelmail.parser.Parser;

public class ContentProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String messageBody = exchange.getMessage().getBody(String.class);
        String messageFrom = (String) exchange.getMessage().getHeader("from", String.class);
        String messageSubject = (String) exchange.getMessage().getHeader("subject", String.class);
        String messageDate = (String) exchange.getMessage().getHeader("date", String.class);
        
        Mail mail = new Mail();

        mail.setContent(new Parser().htmlToPlainText(messageBody).trim());
        mail.setFrom(messageFrom.trim());
        mail.setSubject(messageSubject.trim());
        mail.setDate(messageDate.trim());


        String formattedMessage = new Parser().toJson(mail, mail.getClass());
        

        exchange.getMessage().setBody(formattedMessage.toString());

    }

}
