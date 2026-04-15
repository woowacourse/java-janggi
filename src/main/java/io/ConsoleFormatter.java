package io;

import domain.board.Row;
import domain.game.GameResult;
import domain.game.Turn;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import java.util.Map;

public class ConsoleFormatter {
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String RESET = "\u001B[0m";
    private static final Map<Team, String> TURN_NAMES = Map.of(
            Team.CHO, "초(CHO)",
            Team.HAN, "한(HAN)"
    );
    private static final Map<Team, String> PIECE_PREFIXES = Map.of(
            Team.CHO, "초",
            Team.HAN, "한"
    );
    private static final Map<Team, String> TEAM_COLORS = Map.of(
            Team.CHO, GREEN,
            Team.HAN, RED
    );
    private static final Map<PieceType, String> PIECE_TYPE_NAMES = Map.of(
            PieceType.GENERAL, "궁",
            PieceType.GUARD, "사",
            PieceType.CHARIOT, "차",
            PieceType.CANNON, "포",
            PieceType.ELEPHANT, "상",
            PieceType.HORSE, "마",
            PieceType.SOLDIER, "졸"
    );
    private static final String RESULT_FORMAT = """
            게임 종료
            승리 팀: %s
            최종 점수
            초(CHO): %d
            한(HAN): %d""";

    public String formatTurn(Team team) {
        return TURN_NAMES.get(team);
    }

    public String formatColoredTurn(Turn turn) {
        return TEAM_COLORS.get(turn.getTeam()) + formatTurn(turn.getTeam()) + RESET;
    }

    public String formatRow(Row row) {
        return Integer.toString(row.ordinal());
    }

    public String formatPiece(Piece piece) {
        return TEAM_COLORS.get(piece.getTeam())
                + PIECE_PREFIXES.get(piece.getTeam())
                + PIECE_TYPE_NAMES.get(piece.getPieceType())
                + RESET;
    }

    public String formatGameResult(GameResult gameResult) {
        return String.format(
                RESULT_FORMAT,
                formatTurn(gameResult.winner()),
                gameResult.choScore(),
                gameResult.hanScore()
        );
    }
}
