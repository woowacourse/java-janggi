package dto.dao;

import domain.board.IntersectionState;
import domain.piece.PieceType;

public record PiecePlacement(String team, String pieceType, int y, int x) {

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
}
