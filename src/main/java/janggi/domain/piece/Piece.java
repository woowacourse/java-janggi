package janggi.domain.piece;

import janggi.domain.board.Board;
import janggi.domain.board.Palace;
import janggi.domain.common.Position;
import janggi.domain.common.Team;
import java.util.List;
import java.util.Objects;

public class Piece {

    private final Team team;
    private final PieceType pieceType;

    public Piece(Team team, PieceType pieceType) {
        this.team = team;
        this.pieceType = pieceType;
    }

    public String getTeamName() {
        return team.getName();
    }

    public String getPieceTypeName() {
        return pieceType.getName();
    }

    public boolean isCho() {
        return team == Team.CHO;
    }

    public boolean isCha() {
        return pieceType == PieceType.CHA;
    }

    public boolean isPo() {
        return pieceType == PieceType.PO;
    }

    public boolean isSameTeam(Piece other) {
        return team == other.team;
    }

    public List<Position> findMovablePositions(Board board, Position position) {
        return pieceType.findMovablePositions(board, position, team);
    }

    public Palace selectPalace() {
        return team.selectPalace();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Piece piece)) {
            return false;
        }
        return team == piece.team && pieceType == piece.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, pieceType);
    }
}
