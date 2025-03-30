package service;

import dao.GameDao;
import dao.PieceDao;
import java.util.Map;
import model.JanggiGame;
import model.Team;
import model.piece.Piece;
import model.position.Position;
import model.position.Score;

public class JanggiService {

    private final GameDao gameDao;
    private final PieceDao pieceDao;
    private final JanggiGame janggiGame;

    public JanggiService(GameDao gameDao, PieceDao pieceDao, JanggiGame janggiGame) {
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
        this.janggiGame = janggiGame;
        init();
    }

    private void init() {
        Map<Position, Piece> pieces = janggiGame.getPieces();
        Team currentTurn = janggiGame.getCurrentTurn();
        pieceDao.addPieces(pieces);
        gameDao.addTurn(currentTurn);
    }

    public void move(Position departure, Position arrival) {
        janggiGame.move(departure, arrival);
        pieceDao.deletePiece(arrival);
        pieceDao.updatePiece(departure, arrival);
        gameDao.updateTurn(janggiGame.getCurrentTurn());
    }

    public void removeGameInfo() {
        pieceDao.deletePieces();
        gameDao.deleteTurn();
    }

    public boolean isEnd() {
        return janggiGame.isEnd();
    }

    public Position createPosition(String choiceDeparture) {
        return janggiGame.createPosition(choiceDeparture);
    }

    public Piece findPieceBy(Position departure) {
        return janggiGame.findPieceBy(departure);
    }

    public Score showGameResult() {
        return janggiGame.showGameResult();
    }

    public Map<Position, Piece> getPieces() {
        return janggiGame.getPieces();
    }

    public Team getCurrentTurn() {
        return janggiGame.getCurrentTurn();
    }
}
