package domain.chessPiece;

import domain.direction.Direction;
import domain.direction.Directions;
import domain.hurdlePolicy.HurdlePolicy;
import domain.hurdlePolicy.UnpassableHurdlePolicy;
import domain.position.ChessPosition;
import domain.type.ChessPieceType;
import domain.type.ChessTeam;

import java.util.List;
import java.util.Map;

public class Pawn extends LimitedMoveChessPiece {

    private static final Map<ChessTeam, List<Directions>> DIRECTIONS = Map.of(
            ChessTeam.RED, List.of(
                    new Directions(List.of(Direction.LEFT)),
                    new Directions(List.of(Direction.RIGHT)),
                    new Directions(List.of(Direction.DOWN))
            ),
            ChessTeam.BLUE, List.of(
                    new Directions(List.of(Direction.LEFT)),
                    new Directions(List.of(Direction.RIGHT)),
                    new Directions(List.of(Direction.UP))
            )
    );
    private final HurdlePolicy hurdlePolicy = new UnpassableHurdlePolicy();

    public Pawn(ChessTeam chessTeam) {
        super(chessTeam, DIRECTIONS.get(chessTeam));
    }

    public static Map<ChessPosition, ChessPiece> initPieces() {
        return Map.of(
                new ChessPosition(3, 0), new Pawn(ChessTeam.RED),
                new ChessPosition(3, 2), new Pawn(ChessTeam.RED),
                new ChessPosition(3, 4), new Pawn(ChessTeam.RED),
                new ChessPosition(3, 6), new Pawn(ChessTeam.RED),
                new ChessPosition(3, 8), new Pawn(ChessTeam.RED),
                new ChessPosition(6, 0), new Pawn(ChessTeam.BLUE),
                new ChessPosition(6, 2), new Pawn(ChessTeam.BLUE),
                new ChessPosition(6, 4), new Pawn(ChessTeam.BLUE),
                new ChessPosition(6, 6), new Pawn(ChessTeam.BLUE),
                new ChessPosition(6, 8), new Pawn(ChessTeam.BLUE)
        );
    }

    @Override
    public HurdlePolicy getHurdlePolicy() {
        return hurdlePolicy;
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.PAWN;
    }
}
