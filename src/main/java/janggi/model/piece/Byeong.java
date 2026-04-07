package janggi.model.piece;

import janggi.model.Team;
import janggi.model.movement.Movement;
import janggi.model.movement.palace.PalaceAdjacentMovement;
import janggi.model.movement.palace.PalaceMovement;
import janggi.model.movement.patternBasedMovement.DefaultByeongMovement;
import janggi.model.palace.Palaces;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.PositionPath;
import java.util.List;

public class Byeong extends Piece {

    private final PalaceMovement palaceMovement;

    private Byeong(
            Team team,
            PieceType pieceType,
            Movement defaultMovement,
            PalaceMovement palaceMovement
    ) {
        super(team, pieceType, defaultMovement);
        this.palaceMovement = palaceMovement;
    }

    public Byeong(Team team, Palaces palaces) {
        this(
                team,
                PieceType.BYEONG,
                new DefaultByeongMovement(),
                new PalaceAdjacentMovement(palaces)
        );
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        if (team.isMovingBackward(from, to)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }

        if (palaceMovement.supports(from, to)) {
            return palaceMovement.move(from, to);
        }

        return defaultMovement.move(from, to);
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
