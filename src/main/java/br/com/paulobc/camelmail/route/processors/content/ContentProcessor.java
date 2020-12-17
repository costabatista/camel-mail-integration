package br.com.paulobc.camelmail.route.processors.content;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import br.com.paulobc.camelmail.parser.Parser;

public class ContentProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String messageBody = exchange.getMessage().getBody(String.class);

        exchange.getMessage().setBody(new Parser().htmlToPlainText(messageBody));

    }

}
