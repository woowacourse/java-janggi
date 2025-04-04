package domain.chesspiece;

import domain.direction.Direction;
import domain.direction.Directions;
import domain.hurdlePolicy.HurdlePolicy;
import domain.hurdlePolicy.UnpassableHurdlePolicy;
import domain.position.ChessPosition;
import domain.type.ChessPieceType;
import domain.type.ChessTeam;
import java.util.List;

public class King extends LimitedMoveChessPiece {

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

    public King(final ChessTeam team, final ChessPosition position) {
        super(position, team, DIRECTIONS, new UnpassableHurdlePolicy());
    }

    public static List<King> initPieces() {
        return List.of(
                new King(ChessTeam.RED, new ChessPosition(1, 4)),
                new King(ChessTeam.BLUE, new ChessPosition(8, 4))
        );
    }

    @Override
    protected boolean canMoveInCastle(final ChessPosition position, final Directions directions) {
        final Direction direction = directions.getFirstDirection();
        return position.canCastleMove(direction) && position.move(direction).isInCastle();
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.KING;
    }

    @Override
    public ChessPiece from(final ChessPosition position) {
        return new King(getTeam(), position);
    }
}
