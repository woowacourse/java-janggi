package janggi;

import janggi.dao.GameJdbcDao;
import janggi.dao.MoveJdbcDao;

public class Application {
    public static void main(String[] args) {
        final JanggiGame janggi = new JanggiGame(new GameJdbcDao(), new MoveJdbcDao());
        janggi.run();
    }
}
