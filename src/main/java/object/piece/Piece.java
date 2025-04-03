package object.piece;

import java.util.stream.Collectors;
import object.board.Board;
import object.coordinate.Coordinate;
import object.team.Country;

public class Piece {

    private final Country country;
    private final PieceType pieceType;

    public Piece(Country country, PieceType pieceType) {
        this.country = country;
        this.pieceType = pieceType;
    }

    public boolean canMove(Board board, Coordinate departure, Coordinate arrival) {
        return validateObstacle(board, departure, arrival) && validateMovable(departure, arrival);
    }

    private boolean validateObstacle(Board board, Coordinate departure, Coordinate arrival) {
        return pieceType.getObstacleValidators().stream()
                .allMatch(pathValidator -> pathValidator.validate(board, departure, arrival));
    }

    private boolean validateMovable(Coordinate departure, Coordinate arrival) {
        return pieceType.getMovableValidators().stream()
                .flatMap(pathGenerator -> pathGenerator.generate(departure).stream())
                .collect(Collectors.toSet())
                .contains(arrival);
    }

    public boolean isPo() {
        return pieceType.equals(PieceType.포);
    }

    public boolean isGoong() {
        return pieceType.equals(PieceType.궁);
    }

    public int getScore() {
        return this.pieceType.getScore();
    }

    public Country getCountry() {
        return country;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public String colorName() {
        return this.country.applyColor(this.pieceType.name());
    }
}
