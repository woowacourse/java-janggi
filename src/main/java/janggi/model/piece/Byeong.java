package janggi.model.piece;

import janggi.model.Team;
import janggi.model.movement.Movement;
import janggi.model.movement.patternBasedMovement.ByeongMovement;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.PositionPath;
import java.util.List;

public class Byeong extends Piece {

    private Byeong(
            Team team,
            PieceType pieceType,
            Movement defaultMovement
    ) {
        super(team, pieceType, defaultMovement);
    }

    public Byeong(Team team) {
        this(
                team,
                PieceType.BYEONG,
                new ByeongMovement()
        );
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        if ((team == Team.CHO && isMovingSouth(from, to))
                ||(team == Team.HAN && !isMovingSouth(from, to))) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
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

    private boolean isMovingSouth(Position from, Position to) {
        return from.row().getValue() < to.row().getValue();
    }

    @Override
    public boolean canPassThrough(List<Piece> piecesOnPath) {
        return piecesOnPath.isEmpty();
    }
}
