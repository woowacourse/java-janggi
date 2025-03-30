package service;

import db.MySQLConnection;
import db.dao.JanggiGameDao;
import db.dao.JanggiPieceDao;
import janggiGame.JanggiGame;
import janggiGame.piece.Piece;
import janggiGame.position.Position;
import service.loader.GameLoader;

public class JanggiGameService {
    private final Long gameId;
    private final JanggiGame game;
    private final JanggiGameDao gameDao = new JanggiGameDao(MySQLConnection.getInstance());
    private final JanggiPieceDao pieceDao = new JanggiPieceDao(MySQLConnection.getInstance());

    public JanggiGameService(Long gameId) {
        this.gameId = gameId;
        this.game = GameLoader.loadGame(gameId);
    }

    public void takeTurn(Position origin, Position destination) {
        Piece captured = game.getPieces().get(destination);
        game.takeTurn(origin, destination);

        if (checkFinished()) {
            gameDao.markAsFinished(gameId);
            return;
        }

        if (captured != null) {
            pieceDao.deletePieceAt(gameId, destination);
        }

        pieceDao.updatePiecePosition(gameId, origin, destination);
        gameDao.updateGame(gameId, game.getCurrentDynasty().name(), game.wasLastTurnPassed());
    }

    public void skipTurn() {
        game.skipTurn();

        if (checkFinished()) {
            gameDao.markAsFinished(gameId);
            return;
        }

        gameDao.updateGame(gameId, game.getCurrentDynasty().name(), game.wasLastTurnPassed());
    }

    public void undoTurn() {
        game.undoTurn();
        gameDao.updateGame(gameId, game.getCurrentDynasty().name(), game.wasLastTurnPassed());
    }

    public boolean isFinished() {
        return game.isFinished();
    }

    public JanggiGame getGame() {
        return game;
    }

    private boolean checkFinished() {
        return game.isFinished();
    }
}