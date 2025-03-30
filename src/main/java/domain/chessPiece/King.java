package domain.chessPiece;

import domain.direction.Direction;
import domain.direction.Directions;
import domain.hurdlePolicy.HurdlePolicy;
import domain.hurdlePolicy.UnpassableHurdlePolicy;
import domain.position.ChessPosition;
import domain.type.ChessPieceType;
import domain.type.ChessTeam;
import java.util.List;

public class King extends LimitedMoveChessPiece{

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

    private final HurdlePolicy hurdlePolicy = new UnpassableHurdlePolicy();

    public King(final ChessPosition position, final ChessTeam team) {
        super(position, team, DIRECTIONS);
    }

    @Override
    protected boolean canMove(final ChessPosition position, final Directions directions) {
        final Direction direction = directions.getFirstDirection();
        return position.canCastleMove(direction) && position.move(direction).isInCastle();
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.KING;
    }

    @Override
    public HurdlePolicy getHurdlePolicy() {
        return hurdlePolicy;
    }

    @Override
    public ChessPiece from(final ChessPosition position) {
        return null;
    }
}
