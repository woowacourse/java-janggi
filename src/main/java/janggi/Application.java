package janggi;

import janggi.dao.JanggiJdbcDao;

public class Application {
    public static void main(String[] args) {
        final JanggiGame janggi = new JanggiGame(new JanggiJdbcDao());
        janggi.run();
    }
}
