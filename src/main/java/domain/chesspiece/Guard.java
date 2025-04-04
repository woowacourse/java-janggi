package domain.chesspiece;

import domain.direction.Direction;
import domain.direction.Directions;
import domain.hurdlePolicy.HurdlePolicy;
import domain.hurdlePolicy.UnpassableHurdlePolicy;
import domain.position.ChessPosition;
import domain.type.ChessPieceType;
import domain.type.ChessTeam;
import java.util.List;

public class Guard extends LimitedMoveChessPiece {

    private static final List<Directions> DIRECTIONS = List.of(
            new Directions(List.of(Direction.UP)),
            new Directions(List.of(Direction.DOWN)),
            new Directions(List.of(Direction.LEFT)),
            new Directions(List.of(Direction.RIGHT)),
            new Directions(List.of(Direction.LEFT_UP)),
            new Directions(List.of(Direction.LEFT_DOWN)),
            new Directions(List.of(Direction.RIGHT_UP)),
            new Directions(List.of(Direction.RIGHT_DOWN))
    );

    public Guard(final ChessTeam team, final ChessPosition position) {
        super(position, team, DIRECTIONS, new UnpassableHurdlePolicy());
    }

    public static List<Guard> initPieces() {
        return List.of(
                new Guard(ChessTeam.RED, new ChessPosition(0,3)),
                new Guard(ChessTeam.RED, new ChessPosition(0,5)),
                new Guard(ChessTeam.BLUE, new ChessPosition(9,3)),
                new Guard(ChessTeam.BLUE, new ChessPosition(9,5))
        );
    }

    @Override
    protected boolean canMoveInCastle(final ChessPosition position, final Directions directions) {
        final Direction direction = directions.getFirstDirection();
        return position.canCastleMove(direction) && position.move(direction).isInCastle();
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.GUARD;
    }

    @Override
    public ChessPiece from(final ChessPosition position) {
        return new Guard(getTeam(), position);
    }
}
