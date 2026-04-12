package janggi.model.piece;

import janggi.model.Team;
import janggi.model.movement.MovementSelector;
import janggi.model.movement.palace.PalaceAdjacentMovement;
import janggi.model.movement.patternBasedMovement.DefaultByeongMovement;
import janggi.model.palace.PalaceFactory;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.PositionPath;
import java.util.List;

public class Byeong extends Piece {

    private final MovementSelector movementSelector;

    private Byeong(
            Team team,
            PieceType pieceType,
            MovementSelector movementSelector
    ) {
        super(team, pieceType);
        this.movementSelector = movementSelector;
    }

    public Byeong(Team team) {
        this(team, PieceType.BYEONG,
                new MovementSelector(
                        new DefaultByeongMovement(),
                        new PalaceAdjacentMovement(
                                new PalaceFactory().create()
                        )
                )
        );
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        if (team.isMovingBackward(from, to)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }

        return movementSelector.select(from, to)
                .move(from, to);
    }

    @Override
    public boolean canPassThrough(
            List<Piece> piecesOnPath,
            Piece pieceAtTo
    ) {
        return piecesOnPath.isEmpty() && !this.isSameTeam(pieceAtTo);
    }

    @Override
    public boolean canPassThrough(List<Piece> piecesOnPath) {
        return piecesOnPath.isEmpty();
    }
}
