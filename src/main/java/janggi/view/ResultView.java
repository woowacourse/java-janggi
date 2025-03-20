package janggi.view;

import janggi.piece.Piece;
import janggi.piece.Team;
import janggi.position.Position;
import java.util.Map;

public class ResultView {

    private static final String LINE = System.lineSeparator();
    private static final String BLANK = "ㅤ";
    private static final String HEADER = "   1    2    3    4    5    6    7    8   9%n";
    private static final String BOARD_LINE = "   |    |    |    |    |    |    |    |   |%n";

    public void printBoard(final Map<Position, Piece> pieces) {
        System.out.printf(HEADER);
        for (int y = 1; y <= 10; y++) {
            StringBuilder sb = new StringBuilder(String.format("%2d ", y));
            for (int x = 1; x <= 9; x++) {
                Position currentPosition = Position.valueOf(y, x);
                if (x != 1) {
                    sb.append(" ㅡ ");
                }
                if (!pieces.containsKey(currentPosition)) {
                    sb.append(BLANK);
                    continue;
                }
                Piece piece = pieces.get(currentPosition);
                sb.append(piece.getPieceType().getValue(piece.getTeam()));
            }
            System.out.println(sb);
            if (y != 10) {
                System.out.printf(BOARD_LINE);
            }
        }
    }

    public void printOrder(final Team team) {
        System.out.printf(LINE + "%s나라의 순서입니다." + LINE, team.getTitle());
    }

    public void printJanggiResult(final Team team) {
        System.out.printf(LINE + """
                왕이 잡혔습니다.
                %s나라의 승리입니다!""", team.getTitle());
    }
}
