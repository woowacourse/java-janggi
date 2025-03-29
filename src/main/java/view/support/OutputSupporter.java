package view.support;

import domain.piece.PieceType;
import domain.board.BoardLocation;
import domain.piece.Team;
import domain.piece.Piece;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class OutputSupporter {

    private static final Map<BoardLocation, Piece> EMPTY_PIECES = new HashMap<>();
    private static final Map<PieceType, String> PIECE_TYPE_FORMATTER = new EnumMap<>(PieceType.class);
    private static final Map<Team, String> TEAM_FORMATTER = new EnumMap<>(Team.class);

    static {
        PIECE_TYPE_FORMATTER.put(PieceType.KING, "왕");
        PIECE_TYPE_FORMATTER.put(PieceType.CHARIOT, "차");
        PIECE_TYPE_FORMATTER.put(PieceType.CANNON, "포");
        PIECE_TYPE_FORMATTER.put(PieceType.HORSE, "마");
        PIECE_TYPE_FORMATTER.put(PieceType.ELEPHANT, "상");
        PIECE_TYPE_FORMATTER.put(PieceType.PAWN, "폰");
        PIECE_TYPE_FORMATTER.put(PieceType.SCHOLAR, "사");
    }

    static {
        TEAM_FORMATTER.put(Team.CHO, "초");
        TEAM_FORMATTER.put(Team.HAN, "한");
    }

    public Map<BoardLocation, Piece> fillBoard(Map<BoardLocation, Piece> boardPieces) {
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 10; j++) {
                EMPTY_PIECES.put(new BoardLocation(i, j), null);
            }
        }
        EMPTY_PIECES.putAll(boardPieces);
        return EMPTY_PIECES;
    }

    public String formatPiece(Piece piece) {
        PieceType pieceType = piece.getType();
        Team team = piece.getTeam();
        return TEAM_FORMATTER.get(team) + PIECE_TYPE_FORMATTER.get(pieceType);
    }

    public String formatTurn(Team team) {
        if (team == Team.HAN) {
            return "한나라";
        }
        return "초나라";
    }
}
