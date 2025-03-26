package model;

import java.util.List;
import java.util.Map;
import model.piece.Piece;
import model.position.Position;
import utils.InputParser;

public class JanggiGame {

    private final Pieces pieces;
    private Team turn;

    public JanggiGame() {
        Map<Position, Piece> pieces = PieceInitializer.generate();
        this.pieces = new Pieces(pieces);
        turn = Team.GREEN;
    }

    public Map<Position, Piece> getPieces() {
        return pieces.getPieces();
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
    }

    private void validateTurnAndChange(Position departure) {
        Piece piece = pieces.findPieceBy(departure);
        piece.checkOfTurn(turn);
        turn = turn.change();
    }

    public Team getCurrentTurn() {
        return this.turn;
    }
}
