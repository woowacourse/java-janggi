package piece;

import board.Board;
import coordinate.Coordinate;
import java.util.stream.Collectors;
import team.Team;

public class Piece {

    private final Team team;
    private final PieceType pieceType;

    public Piece(Team team, PieceType pieceType) {
        this.team = team;
        this.pieceType = pieceType;
    }

    public boolean canMove(Board board, Coordinate departure, Coordinate arrival) {
        if (!validateObstacle(board, departure, arrival)) {
            return false;
        }
        if (!validateMovable(departure, arrival)) {
            return false;
        }
        return true;
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

    public boolean isSameTeam(Piece piece) {
        return this.team.equals(piece.team);
    }

    public String colorName() {
        return this.team.applyColor(this.pieceType.name());
    }
}
