package view.support;

import static domain.piece.PieceType.CANNON;
import static domain.piece.PieceType.CHARIOT;
import static domain.piece.PieceType.ELEPHANT;
import static domain.piece.PieceType.HORSE;
import static domain.piece.PieceType.KING;
import static domain.piece.PieceType.PAWN;
import static domain.piece.PieceType.SCHOLAR;

import domain.BoardLocation;
import domain.Team;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class OutputSupporter {

    private static final Map<BoardLocation, Piece> EMPTY_PIECES = new HashMap<>();
    private static final Map<PieceType, String> PIECE_TYPE_FORMATTER = new EnumMap<>(PieceType.class);
    private static final Map<Team, String> TEAM_FORMATTER = new EnumMap<>(Team.class);

    static {
        PIECE_TYPE_FORMATTER.put(KING, "왕");
        PIECE_TYPE_FORMATTER.put(CHARIOT, "차");
        PIECE_TYPE_FORMATTER.put(CANNON, "포");
        PIECE_TYPE_FORMATTER.put(HORSE, "마");
        PIECE_TYPE_FORMATTER.put(ELEPHANT, "상");
        PIECE_TYPE_FORMATTER.put(PAWN, "폰");
        PIECE_TYPE_FORMATTER.put(SCHOLAR, "사");
    }

    static {
        TEAM_FORMATTER.put(Team.CHO, "楚");
        TEAM_FORMATTER.put(Team.HAN, "漢");
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
