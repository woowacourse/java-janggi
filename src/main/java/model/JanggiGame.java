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
    private final GameDao gameDao;
    private final PieceDao pieceDao;

    private JanggiGame(Map<Position, Piece> pieces, Team turn, GameDao gameDao, PieceDao pieceDao) {
        this.pieces = new Pieces(pieces);
        this.turn = turn;
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
    }

    public static JanggiGame initPiecesFrom(GameDao gameDao, PieceDao pieceDao) {
        Map<Position, Piece> pieces = initPieces(pieceDao);
        Team turn = initTeam(gameDao);
        return new JanggiGame(pieces, turn, gameDao, pieceDao);
    }

    private static Map<Position, Piece> initPieces(PieceDao pieceDao) {
        Map<Position, Piece> allPieces = pieceDao.getAllPieces();
        if (allPieces.isEmpty()) {
            Map<Position, Piece> generatePieces = PieceInitializer.generate();
            pieceDao.addPieces(generatePieces);
            return generatePieces;
        }
        return allPieces;
    }

    private static Team initTeam(GameDao gameDao) {
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
        pieceDao.deletePieces();
        gameDao.deleteTurn();
    }
}
