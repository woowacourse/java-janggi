package model;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import utils.InputParser;

public class JanggiGame {

    private final Pieces pieces;
    private boolean turnOfRedTeam = false;

    public JanggiGame() {
        Map<Position, Piece> pieces = PieceInitializer.generate();
        this.pieces = new Pieces(pieces);
    }

    public Position createPositionAndCheckTurn(String choiceDeparture) {
        Position position = createPositionFrom(choiceDeparture);
        validateTurn(position);
        return position;
    }

    public Position createPositionFrom(String choiceDeparture) {
        List<Integer> columnAndRowOfDeparture = InputParser.splitAndConvert(choiceDeparture);
        return new Position(columnAndRowOfDeparture);
    }

    public Piece findPieceBy(Position departure) {
        return pieces.findPieceBy(departure);
    }

    public void move(Position departure, Position arrival) {
        validateTurn(departure);
        pieces.move(departure, arrival);
    }

    private void validateTurn(Position departure) {
        Piece piece = pieces.findPieceBy(departure);
        Team team = piece.getTeam();
        if (team == Team.RED && !turnOfRedTeam) {
            throw new IllegalArgumentException("레드 팀 턴이 아닙니다.");
        }
        if (team == Team.GREEN && turnOfRedTeam) {
            throw new IllegalArgumentException("그린 팀 턴이 아닙니다.");
        }
        turnOfRedTeam = !turnOfRedTeam;
    }

    public String showCurrentPositionOfPieces() {
        StringBuilder sb = new StringBuilder();
        for (Column column : Column.values()) {
            for (Row row : Row.values()) {
                Optional<Piece> piece = pieces.findPieceOfNullable(new Position(column, row));
                if (piece.isEmpty()) {
                    sb.append("－");
                } else {
                    sb.append(piece.get());
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
