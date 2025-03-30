package janggi.dao;

public class DaoSettings {

    public final String server;
    public final String database;
    public final String username;
    public final String password;
    public final String option;

    public DaoSettings(String server, String database, String username, String password) {
        this(server, database, username, password, "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
    }

    public DaoSettings(String server, String database, String username, String password, String option) {
        this.server = server;
        this.database = database;
        this.username = username;
        this.password = password;
        this.option = option;
    }
}
