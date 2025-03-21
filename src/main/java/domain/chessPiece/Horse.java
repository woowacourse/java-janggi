package domain.chessPiece;

import domain.type.ChessPieceType;
import domain.position.ChessPosition;
import domain.type.ChessTeam;
import domain.direction.Directions;

import static domain.direction.Direction.DOWN;
import static domain.direction.Direction.LEFT;
import static domain.direction.Direction.LEFT_DOWN;
import static domain.direction.Direction.LEFT_UP;
import static domain.direction.Direction.RIGHT;
import static domain.direction.Direction.RIGHT_DOWN;
import static domain.direction.Direction.RIGHT_UP;
import static domain.direction.Direction.UP;

import java.util.List;

public class Horse extends LimitedMoveChessPiece {
    private static final List<Directions> directions = List.of(
            new Directions(List.of(UP, RIGHT_UP)),
            new Directions(List.of(UP, LEFT_UP)),
            new Directions(List.of(LEFT, LEFT_UP)),
            new Directions(List.of(LEFT, LEFT_DOWN)),
            new Directions(List.of(RIGHT, RIGHT_UP)),
            new Directions(List.of(RIGHT, RIGHT_DOWN)),
            new Directions(List.of(DOWN, LEFT_DOWN)),
            new Directions(List.of(DOWN, RIGHT_DOWN))
    );

    public Horse(ChessPosition chessPosition, final ChessTeam team) {
        super(chessPosition, team, directions);
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
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.HORSE;
    }
}
