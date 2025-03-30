package config;

import dao.DaoConfiguration;
import dao.GameDao;
import dao.PieceDao;
import dao.ProdDaoConfiguration;
import model.JanggiGame;
import service.JanggiService;

public class JanggiConfig {

    private static final DaoConfiguration daoConfiguration = new ProdDaoConfiguration();
    private static final GameDao gameDao = new GameDao(daoConfiguration);
    private static final PieceDao pieceDao = new PieceDao(daoConfiguration);

    public static JanggiService createJanggiService() {
        return new JanggiService(
            gameDao,
            pieceDao,
            JanggiGame.initPiecesFrom(pieceDao.getAllPieces(), gameDao.getTurn()));
    }
}
