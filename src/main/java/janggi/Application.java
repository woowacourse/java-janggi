package janggi;

import janggi.repository.JdbcRepository;

public class Application {
    public static void main(String[] args) {
        final JanggiGame janggi = new JanggiGame(new JdbcRepository());
        janggi.run();
    }
}
