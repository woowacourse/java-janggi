package model;

import dao.GameDao;
import dao.PieceDao;
import java.util.List;
import java.util.Map;
import model.piece.Piece;
import model.position.Position;
import model.position.Score;
import utils.InputParser;

public class JanggiGame {

    private final Pieces pieces;
    private Team turn;
    private final PieceDao pieceDao;
    private final GameDao gameDao;

    public JanggiGame() {
        Map<Position, Piece> pieces;
        pieceDao = new PieceDao();
        gameDao = new GameDao();
        Map<Position, Piece> allPieces = pieceDao.getAllPieces();
        if (allPieces.isEmpty()) {
            pieces = PieceInitializer.generate();
            this.pieces = new Pieces(pieces);
            pieceDao.addPieces(pieces);
        } else {
            this.pieces = new Pieces(allPieces);
        }
        if (gameDao.getTurn() == null) {
            turn = Team.GREEN;
            gameDao.addTurn(turn);
        } else {
            turn = gameDao.getTurn();
        }
    }

    public Map<Position, Piece> getPieces() {
        return pieces.getPieces();
    }

    public boolean isEnd() {
        return !pieces.isGeneralAlive();
    }

    public Position createPositionAndCheckTurn(String choiceDeparture) {
        Position position = createPositionFrom(choiceDeparture);
        validateTurnAndChange(position);
        return position;
    }

    public Position createPositionFrom(String choiceDeparture) {
        List<Integer> columnAndRowOfDeparture = InputParser.splitAndConvert(choiceDeparture);
        int column = columnAndRowOfDeparture.get(0);
        int row = columnAndRowOfDeparture.get(1);
        return new Position(column, row);
    }

    public Piece findPieceBy(Position departure) {
        return pieces.findPieceBy(departure);
    }

    public void move(Position departure, Position arrival) {
        pieces.move(departure, arrival);
        pieceDao.deletePiece(arrival);
        pieceDao.updatePiece(departure, arrival);
    }

    private void validateTurnAndChange(Position departure) {
        Piece piece = pieces.findPieceBy(departure);
        piece.checkOfTurn(turn);
        turn = turn.change();
        gameDao.updateTurn(turn);
    }

    public Team getCurrentTurn() {
        return this.turn;
    }

    public Score showGameResult() {
        Map<Position, Piece> pieces = this.pieces.getPieces();
        return Score.calculateScoreFrom(pieces);
    }

    public void removeGameInfo() {
        pieceDao.deleteAllPieces();
        gameDao.deleteTurn();
    }
}
