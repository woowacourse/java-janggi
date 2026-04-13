package domain.piece;

import domain.board.Board;
import domain.player.Team;

import java.util.List;

public class Piece {

    private static final String CANNOT_MOVE = "이동 가능한 위치가 없습니다.";

    private final PieceStatus pieceStatus;
    private final Team team;

    public static Piece of(final PieceStatus pieceStatus, final Team team) {
        return new Piece(pieceStatus, team);
    }

    private Piece(final PieceStatus pieceStatus, final Team team) {
        this.pieceStatus = pieceStatus;
        this.team = team;
    }


    public List<Position> calculateMovablePositions(final Position from, final Board board) {
        final List<Position> movablePositions = pieceStatus.moveStrategyType().getMoveStrategy().calculateMovablePositions(from, board);

        if (movablePositions.isEmpty()) {
            throw new IllegalArgumentException(CANNOT_MOVE);
        }

        return movablePositions;
    }

    public boolean isSameTeam(final Team team) {
        return this.team == team;
    }

    public boolean isSameTeam(final Piece other) {
        return this.team == other.team;
    }

    public boolean isSameType(PieceType pieceType) {
        return pieceStatus.pieceType() == pieceType;
    }


    public Team opponentTeam() {
        return this.team.opponent();
    }

    public PieceType getPieceType() {
        return pieceStatus.pieceType();
    }

    public Team getTeam() {
        return team;
    }

    public int getScore() {
        return pieceStatus.pieceType().getScore();
    }
}
