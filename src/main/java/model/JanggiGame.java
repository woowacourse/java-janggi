package model;

import java.util.List;
import java.util.Map;
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
        return new Position(columnAndRowOfDeparture);
    }

    public Piece findPieceBy(Position departure) {
        return pieces.findPieceBy(departure);
    }

    public void move(Position departure, Position arrival) {
        pieces.move(departure, arrival);
    }

    private void validateTurnAndChange(Position departure) {
        Piece piece = pieces.findPieceBy(departure);
        Team team = piece.getTeam();
        if (team.isMyTurn(turn)) {
            throw new IllegalArgumentException("본인 팀의 턴이 아닙니다.");
        }
        turn = turn.change();
    }

    public Team getCurrentTurn() {
        return this.turn;
    }
}
