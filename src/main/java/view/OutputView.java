package view;

import domain.board.Board;
import domain.game.Score;
import domain.game.Team;
import domain.piece.Piece;
import java.util.Map;
import java.util.Optional;

public class OutputView {
    private static final int MAX_ROW = 10;
    private static final int MAX_COLUMN = 9;
    private static final Map<Team, String> TEAM_NAMES = Map.of(
            Team.CHO, "초",
            Team.HAN, "한"
    );
    private static final String DIVIDE_AREA = "============";
    private final PieceAppearance appearance;

    public OutputView(PieceAppearance appearance) {
        this.appearance = appearance;
    }

    public void printErrorMessage(String message) {
        System.out.println("[ERROR] " + message);
    }

    public void printBoard(Board board) {
        StringBuilder sb = new StringBuilder();
        for (int row = MAX_ROW; row >= 1; row--) {
            sb.append(row).append("\t");
            appendRow(sb, board, row);
            sb.append(System.lineSeparator());
        }
        appendColumnHeader(sb);
        System.out.println(sb);
    }

    private void appendColumnHeader(StringBuilder sb) {
        sb.append(" \t");
        for (int col = 1; col <= MAX_COLUMN; col++) {
            sb.append(col).append("\t");
        }
        sb.append(System.lineSeparator());
    }

    public void printScore(Team team, Score score) {
        System.out.println(TEAM_NAMES.get(team) + "의 점수는 " + score.toString() + "점 입니다");
    }

    private void appendRow(StringBuilder sb, Board board, int row) {
        for (int col = 1; col <= MAX_COLUMN; col++) {
            Optional<Piece> piece = board.findPieceAt(row, col);
            if (piece.isPresent()) {
                sb.append(appearance.colorize(piece.get().getTeam(), piece.get().getType())).append("\t");
            } else {
                sb.append(appearance.colorizeEmpty()).append("\t");
            }
        }
    }

    public void printWinner(boolean cho) {
        Team team = validateTeam(cho);
        System.out.println(DIVIDE_AREA);
        System.out.println(TEAM_NAMES.get(team) + "나라의 승리입니다! ");
        System.out.println(DIVIDE_AREA);
    }

    private Team validateTeam(boolean cho) {
        if (cho) {
            return Team.CHO;
        }
        return Team.HAN;
    }
}
