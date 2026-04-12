package janggi;

import janggi.controller.JanggiController;
import janggi.db.DbConnector;
import janggi.db.dao.GameDao;
import janggi.db.dao.PieceDao;
import janggi.db.repository.JanggiRepository;

public class Application {
    public static void main(String[] args) {
        DbConnector.initDatabase();

        GameDao gameDao = new GameDao();
        PieceDao pieceDao = new PieceDao();

        JanggiRepository janggiRepository = new JanggiRepository(gameDao, pieceDao);

        JanggiController janggiController = new JanggiController(janggiRepository);
        janggiController.run();
    }
}
