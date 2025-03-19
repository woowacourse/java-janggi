package domain;

import static domain.Direction.DOWN;
import static domain.Direction.LEFT;
import static domain.Direction.LEFT_DOWN;
import static domain.Direction.LEFT_UP;
import static domain.Direction.RIGHT;
import static domain.Direction.RIGHT_DOWN;
import static domain.Direction.RIGHT_UP;
import static domain.Direction.UP;

import java.util.ArrayList;
import java.util.List;

public class Horse implements ChessPiece {
    private final List<Directions> directions = List.of(
            new Directions(List.of(UP, RIGHT_UP)),
            new Directions(List.of(UP, LEFT_UP)),
            new Directions(List.of(LEFT, LEFT_UP)),
            new Directions(List.of(LEFT, LEFT_DOWN)),
            new Directions(List.of(RIGHT, RIGHT_UP)),
            new Directions(List.of(RIGHT, RIGHT_DOWN)),
            new Directions(List.of(DOWN, LEFT_DOWN)),
            new Directions(List.of(DOWN, RIGHT_DOWN))
    );

    private final ChessPosition chessPosition;
    private final ChessTeam team;

    public Horse(ChessPosition chessPosition, final ChessTeam team) {
        this.chessPosition = chessPosition;
        this.team = team;
    }

    public static List<Horse> initPieces() {
        return List.of(
                new Horse(new ChessPosition(0, 1), ChessTeam.RED),
                new Horse(new ChessPosition(0, 7), ChessTeam.RED),
                new Horse(new ChessPosition(9, 1), ChessTeam.BLUE),
                new Horse(new ChessPosition(9, 7), ChessTeam.BLUE)
        );
    }

    @Override
    public List<Path> getAvailablePaths(ChessPiecePositions positions) {
        List<Path> result = new ArrayList<>();
        for (Directions direction : directions) {
            if (direction.canApplyFrom(chessPosition)) {
                result.add(direction.getPathFrom(chessPosition));
            }
        }
        return result;
    }

    @Override
    public ChessPosition getPosition() {
        return chessPosition;
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.HORSE;
    }
}
