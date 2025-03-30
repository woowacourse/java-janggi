package service.loader;

import db.MySQLConnection;
import db.dao.JanggiGameDao;
import db.dao.JanggiPieceDao;
import janggiGame.JanggiGame;
import janggiGame.piece.Piece;
import janggiGame.position.Position;
import janggiGame.state.Running.ChoTurn;
import janggiGame.state.Running.HanTurn;
import janggiGame.state.State;
import java.util.Map;

public class GameLoader {
    private static final JanggiGameDao gameDao = new JanggiGameDao(MySQLConnection.getInstance());
    private static final JanggiPieceDao pieceDao = new JanggiPieceDao(MySQLConnection.getInstance());

    public static JanggiGame loadGame(Long gameId) {
        JanggiGameDao.GameEntity entity = gameDao.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 ID의 게임이 존재하지 않습니다: " + gameId));

        Map<Position, Piece> pieces = pieceDao.findByGameId(gameId);
        State state;

        switch (entity.currentDynasty()) {
            case "HAN" -> state = new HanTurn(pieces, entity.wasLastPassed());
            case "CHO" -> state = new ChoTurn(pieces, entity.wasLastPassed());
            default -> throw new IllegalArgumentException("[ERROR] 잘못된 턴 정보입니다: " + entity.currentDynasty());
        }

        JanggiGame game = new JanggiGame();
        game.restoreGameWith(state);
        return game;
    }
}
