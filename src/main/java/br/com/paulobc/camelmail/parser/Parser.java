package br.com.paulobc.camelmail.parser;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;

public class Parser {

    public String htmlToPlainText(String htmlContent) {
        InputStream inputStream = new ByteArrayInputStream(htmlContent.getBytes(StandardCharsets.UTF_8));
        ParseContext parseContext = new ParseContext();
        Metadata metadata = new Metadata();
        HtmlParser htmlParser = new HtmlParser();
        BodyContentHandler bodyContentHandler = new BodyContentHandler();

        try {
            htmlParser.parse(inputStream, bodyContentHandler, metadata, parseContext);
        } catch (IOException i) {
            i.printStackTrace();
        } catch (TikaException t) {
            t.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuilder builder = new StringBuilder();
        builder.append(bodyContentHandler.toString());

        String[] text = builder.toString().split("\\r?\\n");

        builder = new StringBuilder();

        for (String line : text) {

            if (!(line.trim().isEmpty() || line.matches("\\w.*")))
                builder.append(line.trim()).append("\n");
        }

        String plainTextContent = builder.toString().trim();
        return plainTextContent;
    }

}
