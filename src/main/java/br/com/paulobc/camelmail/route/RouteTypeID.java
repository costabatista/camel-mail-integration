package br.com.paulobc.camelmail.route;



public enum RouteTypeID {
    SMTP("smtp"), IMAP("imap");

    private String id;
    
    RouteTypeID(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}