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
                    new Directions(List.of(Direction.DOWN)),
                    new Directions(List.of(Direction.LEFT_DOWN)),
                    new Directions(List.of(Direction.RIGHT_DOWN))
            ),
            ChessTeam.BLUE, List.of(
                    new Directions(List.of(Direction.LEFT)),
                    new Directions(List.of(Direction.RIGHT)),
                    new Directions(List.of(Direction.UP)),
                    new Directions(List.of(Direction.LEFT_UP)),
                    new Directions(List.of(Direction.RIGHT_UP))
            )
    );
    private final HurdlePolicy hurdlePolicy = new UnpassableHurdlePolicy();

    public Pawn(ChessTeam chessTeam) {
        super(chessTeam, DIRECTIONS.get(chessTeam));
    }

    public Pawn(final ChessPosition position, final ChessTeam team) {
        super(position, team, DIRECTIONS.get(team));
    }

    public static List<ChessPiece> initPieces() {
        return List.of(
                new Pawn(new ChessPosition(3, 0), ChessTeam.RED),
                new Pawn(new ChessPosition(3, 2), ChessTeam.RED),
                new Pawn(new ChessPosition(3, 4), ChessTeam.RED),
                new Pawn(new ChessPosition(3, 6), ChessTeam.RED),
                new Pawn(new ChessPosition(3, 8), ChessTeam.RED),
                new Pawn(new ChessPosition(6, 0), ChessTeam.BLUE),
                new Pawn(new ChessPosition(6, 2), ChessTeam.BLUE),
                new Pawn(new ChessPosition(6, 4), ChessTeam.BLUE),
                new Pawn(new ChessPosition(6, 6), ChessTeam.BLUE),
                new Pawn(new ChessPosition(6, 8), ChessTeam.BLUE)
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

    @Override
    protected boolean canMove(final ChessPosition position, final Directions directions) {
        return position.canCastleMove(directions.getFirstDirection());
    }

    @Override
    public ChessPiece from(final ChessPosition position) {
        return new Pawn(position, getTeam());
    }
}
