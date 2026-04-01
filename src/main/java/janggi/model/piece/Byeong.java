package janggi.model.piece;

import janggi.model.Team;
import janggi.model.movement.ByeongMovement;
import janggi.model.movement.Movement;
import janggi.model.position.Position;
import janggi.model.position.PositionPath;
import java.util.List;

public class Byeong extends Piece {

    private final Movement movement;

    private Byeong(
            Team team,
            PieceType pieceType,
            Movement movement
    ) {
        super(team, pieceType);
        this.movement = movement;
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

        return movement.move(from, to);
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
