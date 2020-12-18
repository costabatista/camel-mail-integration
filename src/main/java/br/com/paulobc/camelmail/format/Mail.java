package br.com.paulobc.camelmail.format;

public class Mail {
    private String from;
    private String subject;
    private String content;
    private String date;

    public String getFrom() {
        return from;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("{\nfrom: \"").append(this.getFrom()).append("\",\nsubject: \"").append(this.getSubject())
                .append("\",").append("\ndate: \"").append(this.getDate()).append("\",").append("\ncontent: \"").append(this.getContent()).append("\"\n}");

        return builder.toString();
    }

}
