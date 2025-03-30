package model;

import java.util.List;
import java.util.Map;
import model.piece.Piece;
import model.piece.PieceInitializer;
import model.piece.Team;
import model.position.Position;
import model.piece.Score;
import utils.InputParser;

public class JanggiGame {

    private final Pieces pieces;
    private Team turn;

    private JanggiGame(Map<Position, Piece> pieces, Team turn) {
        this.pieces = new Pieces(pieces);
        this.turn = turn;
    }

    public static JanggiGame initPiecesFrom(Map<Position, Piece> currentPieces, Team currentTeam) {
        Map<Position, Piece> pieces = initPieces(currentPieces);
        Team turn = initTeam(currentTeam);
        return new JanggiGame(pieces, turn);
    }

    private static Map<Position, Piece> initPieces(Map<Position, Piece> currentPieces) {
        if (currentPieces.isEmpty()) {
            return PieceInitializer.generate();
        }
        return currentPieces;
    }

    private static Team initTeam(Team currentTurn) {
        if (currentTurn == null) {
            return Team.GREEN;
        }
        return currentTurn;
    }

    public Map<Position, Piece> getPieces() {
        return pieces.getPieces();
    }

    public boolean isEnd() {
        return !pieces.isGeneralAlive();
    }

    public Position createPosition(String choiceDeparture) {
        return createPositionFrom(choiceDeparture);
    }

    private Position createPositionFrom(String choiceDeparture) {
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
        this.turn = turn.change();
    }

    public Team getCurrentTurn() {
        return this.turn;
    }

    public Score showGameResult() {
        Map<Position, Piece> pieces = this.pieces.getPieces();
        return Score.calculateScoreFrom(pieces);
    }
}
