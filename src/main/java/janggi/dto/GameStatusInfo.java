package janggi.dto;

import janggi.domain.piece.Piece;
import janggi.domain.status.Team;
import java.util.ArrayList;
import java.util.List;

public record GameStatusInfo(
        List<List<PieceInfo>> pieces
) {
    public static GameStatusInfo from(List<List<Piece>> board) {
        return new GameStatusInfo(
                board.stream()
                        .map(GameStatusInfo::toPieceInfos)
                        .toList()
                        .reversed()
        );
    }

    private static List<PieceInfo> toPieceInfos(List<Piece> piecesAtY) {
        List<PieceInfo> pieceInfos = new ArrayList<>();

        for (Piece piece : piecesAtY) {
            pieceInfos.add(createPieceInfo(piece));
        }
        return pieceInfos;
    }

    private static PieceInfo createPieceInfo(Piece piece) {
        if (piece == null) {
            return new PieceInfo("+", null);
        }
        return new PieceInfo(piece.getType().getName(), extractTeam(piece));
    }

    private static Team extractTeam(Piece piece) {
        if (piece.isSameTeam(Team.HAN)) {
            return Team.HAN;
        }
        return Team.CHO;
    }
}
