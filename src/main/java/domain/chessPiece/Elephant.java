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

public class Elephant extends LimitedMoveChessPiece {
    private static final List<Directions> directions = List.of(
            new Directions(List.of(UP, RIGHT_UP, RIGHT_UP)),
            new Directions(List.of(UP, LEFT_UP, LEFT_UP)),
            new Directions(List.of(LEFT, LEFT_UP, LEFT_UP)),
            new Directions(List.of(LEFT, LEFT_DOWN, LEFT_DOWN)),
            new Directions(List.of(RIGHT, RIGHT_UP, RIGHT_UP)),
            new Directions(List.of(RIGHT, RIGHT_DOWN, RIGHT_DOWN)),
            new Directions(List.of(DOWN, LEFT_DOWN, LEFT_DOWN)),
            new Directions(List.of(DOWN, RIGHT_DOWN, RIGHT_DOWN))
    );

    public Elephant(ChessPosition position, final ChessTeam team) {
        super(position, team, directions);
    }

    public static List<Elephant> initPieces() {
        return List.of(
                new Elephant(new ChessPosition(0, 2), ChessTeam.RED),
                new Elephant(new ChessPosition(0, 6), ChessTeam.RED),
                new Elephant(new ChessPosition(9, 2), ChessTeam.BLUE),
                new Elephant(new ChessPosition(9, 6), ChessTeam.BLUE)
        );
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.ELEPHANT;
    }
}
