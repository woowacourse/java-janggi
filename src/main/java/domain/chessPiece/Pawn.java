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

    public Pawn(ChessTeam chessTeam) {
        super(chessTeam, DIRECTIONS.get(chessTeam));
    }

    public static Map<ChessPosition, ChessPiece> initPieces() {
        return Map.of(
                new ChessPosition(3, 0), new Pawn(ChessTeam.RED),
                new ChessPosition(3, 2), new Pawn(ChessTeam.RED),
                new ChessPosition(6, 0), new Pawn(ChessTeam.BLUE),
                new ChessPosition(6, 2), new Pawn(ChessTeam.BLUE)
        );
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.PAWN;
    }
}
