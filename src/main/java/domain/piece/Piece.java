package domain.piece;

import domain.board.Board;
import domain.player.Team;
import java.util.List;

public class Piece {

    private static final String CANNOT_MOVE = "이동 가능한 위치가 없습니다.";

    private final Team team;
    private final PieceType pieceType;

    public static Piece of(final PieceType pieceType, final Team team) {
        return new Piece(team, pieceType);
    }

    private Piece(final Team team, final PieceType pieceType) {
        this.team = team;
        this.pieceType = pieceType;
    }


    public List<Position> calculateMovablePositions(Position from, Board board) {
        List<Position> movablePositions = pieceType.getMoveStrategy().calculateMovablePositions(from, board);

        if (movablePositions.isEmpty()) {
            throw new IllegalArgumentException(CANNOT_MOVE);
        }

        return movablePositions;
    }

    public boolean isSameTeam(Team team) {
        return this.team == team;
    }

    public boolean isSameTeam(Piece other) {
        return this.team == other.team;
    }

    public boolean isCannon() {
        return pieceType == PieceType.CANNON;
    }


    public Team opponentTeam() {
        return this.team.opponent();
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Team getTeam() {
        return team;
    }
}
