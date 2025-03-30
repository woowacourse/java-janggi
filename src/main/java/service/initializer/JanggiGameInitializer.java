package service.initializer;

import db.MySQLConnection;
import db.dao.JanggiGameDao;
import db.dao.JanggiPieceDao;
import janggiGame.JanggiGame;
import janggiGame.arrangement.ArrangementStrategy;
import janggiGame.piece.Piece;
import janggiGame.position.Position;
import java.util.Map;

public class JanggiGameInitializer {

    private final JanggiGameDao janggiGameDao = new JanggiGameDao(MySQLConnection.getInstance());
    private final JanggiPieceDao janggiPieceDao = new JanggiPieceDao(MySQLConnection.getInstance());

    public Long getNewGameId(ArrangementStrategy hanStrategy, ArrangementStrategy choStrategy) {
        JanggiGame janggiGame = new JanggiGame();
        janggiGame.arrangePieces(hanStrategy, choStrategy);

        String currentDynasty = janggiGame.getCurrentDynasty().name();
        Long gameId = janggiGameDao.save(currentDynasty);

        Map<Position, Piece> pieces = janggiGame.getPieces();
        janggiPieceDao.saveAll(gameId, pieces);

        return gameId;
    }
}
