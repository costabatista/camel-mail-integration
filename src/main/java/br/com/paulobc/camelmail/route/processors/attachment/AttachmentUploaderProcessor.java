package br.com.paulobc.camelmail.route.processors.attachment;

import java.io.File;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.attachment.AttachmentMessage;

public class AttachmentUploaderProcessor implements Processor{

    @Override
    public void process(Exchange exchange) throws Exception {
       AttachmentMessage attachmentMessage = exchange.getMessage(AttachmentMessage.class);
       
       StringBuilder builderFileName = new StringBuilder();

       builderFileName.append("outbox").append(File.separator).append("contract.txt");
        
       String fileName = builderFileName.toString();

       DataHandler handler = new DataHandler(new FileDataSource(fileName)); 
       attachmentMessage.addAttachment("contract.txt", handler); 

    }

    
}
