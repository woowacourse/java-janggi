package domain.chessPiece;

import domain.type.ChessPieceType;
import domain.position.ChessPosition;
import domain.type.ChessTeam;
import domain.direction.Directions;

import static domain.direction.Direction.DOWN;
import static domain.direction.Direction.LEFT;
import static domain.direction.Direction.RIGHT;
import static domain.direction.Direction.UP;

import java.util.List;
import java.util.Map;

public class Pawn extends LimitedMoveChessPiece {

    private static final Map<ChessTeam, List<Directions>> DIRECTIONS = Map.of(
            ChessTeam.RED, List.of(new Directions(List.of(LEFT)), new Directions(List.of(RIGHT)), new Directions(List.of(DOWN))),
            ChessTeam.BLUE, List.of(new Directions(List.of(LEFT)), new Directions(List.of(RIGHT)), new Directions(List.of(UP))));

    public Pawn(ChessPosition position, final ChessTeam chessTeam) {
        super(position, chessTeam, DIRECTIONS.get(chessTeam));
    }

    public static List<Pawn> initPieces() {
        return List.of(
                new Pawn(new ChessPosition(3, 0), ChessTeam.RED),
                new Pawn(new ChessPosition(3, 2), ChessTeam.RED),
                new Pawn(new ChessPosition(6, 0), ChessTeam.BLUE),
                new Pawn(new ChessPosition(6, 2), ChessTeam.BLUE)
        );
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.PAWN;
    }
}
