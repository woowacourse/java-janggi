package janggi.infra;

public class DatabaseConfig {
    private final String server;
    private final String database;
    private final String option;
    private final String username;
    private final String password;

    public DatabaseConfig(String server, String database, String option, String username, String password) {
        this.server = server;
        this.database = database;
        this.option = option;
        this.username = username;
        this.password = password;
    }

    public String getUrl() {
        return "jdbc:mysql://" + server + "/" + database + option;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
