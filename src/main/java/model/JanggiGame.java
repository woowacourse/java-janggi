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

    private static final PieceDao pieceDao = new PieceDao();
    private static final GameDao gameDao = new GameDao();
    private final Pieces pieces;
    private Team turn;

    private JanggiGame(Map<Position, Piece> pieces, Team turn) {
        this.pieces = new Pieces(pieces);
        this.turn = turn;
    }

    public static JanggiGame initPiecesFrom() {
        Map<Position, Piece> pieces = initPieces();
        Team turn = initTeam();
        return new JanggiGame(pieces, turn);
    }

    private static Map<Position, Piece> initPieces() {
        Map<Position, Piece> allPieces = pieceDao.getAllPieces();
        if (allPieces.isEmpty()) {
            Map<Position, Piece> generatePieces = PieceInitializer.generate();
            pieceDao.addPieces(generatePieces);
            return generatePieces;
        }
        return allPieces;
    }

    private static Team initTeam() {
        if (gameDao.getTurn() == null) {
            Team turn = Team.GREEN;
            gameDao.addTurn(turn);
            return turn;
        }
        return gameDao.getTurn();
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
