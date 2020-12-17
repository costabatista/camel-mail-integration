package br.com.paulobc.camelmail.route.processors.attachment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

import javax.activation.DataHandler;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.attachment.AttachmentMessage;

public class AttachmentDownloaderProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        try {
            AttachmentMessage attachmentMessage = exchange.getMessage(AttachmentMessage.class);
            Map<String, DataHandler> attachments = attachmentMessage.getAttachments();
            StringBuilder builder = new StringBuilder();
            
            if (attachments.size() > 0) {
                for (String name : attachments.keySet()) {
                    DataHandler dataHandler = attachments.get(name);

                    builder.append("inbox").append(File.separator).append("attachments").append(File.separator)
                            .append(exchange.getExchangeId()).append("_").append(dataHandler.getName());

                    String fileName = builder.toString();

                    byte[] data = exchange.getContext().getTypeConverter().convertTo(byte[].class, exchange,
                            dataHandler.getInputStream());

                    FileOutputStream out = new FileOutputStream(fileName);
                    out.write(data);
                    out.flush();
                    out.close();
                }
            }
        } catch (NullPointerException n) {
            n.printStackTrace();
        }
    }

}
