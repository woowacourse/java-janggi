package dto.dao;

import domain.board.IntersectionState;
import domain.piece.PieceType;

public final class PiecePlacement {
    private final String team;
    private final String pieceType;
    private final int y;
    private final int x;

    private PiecePlacement(String team, String pieceType, int y, int x) {
        this.team = team;
        this.pieceType = pieceType;
        this.y = y;
        this.x = x;
    }

    public static PiecePlacement from(IntersectionState cell) {
        return new PiecePlacement(
                cell.getTeam().name(),
                cell.getPieceType().name(),
                cell.getPoint().y(),
                cell.getPoint().x()
        );
    }

    public static boolean isOccupied(IntersectionState cell) {
        return cell.getPieceType() != PieceType.NONE;
    }

    public String team() {
        return team;
    }

    public String pieceType() {
        return pieceType;
    }

    public int y() {
        return y;
    }

    public int x() {
        return x;
    }
}
