package janggi.view;

import janggi.Team.Team;
import janggi.piece.Piece;
import janggi.position.Position;
import java.util.Map;

public class ResultView {

    private static final String LINE = System.lineSeparator();
    private static final String BLANK = "ㅤ";
    private static final String HEADER = "   1    2    3    4    5    6    7    8   9%n";
    private static final String BOARD_LINE = "   |    |    |    |    |    |    |    |   |%n";
    private static final String BLUE_CODE = "\u001B[34m";
    private static final String RED_CODE = "\u001B[31m";
    private static final String EXIT_CODE = "\u001B[0m";

    public void printSetting() {
        System.out.println("""
                마와 상을 배치할 수 있는 경우의 수는 다음과 같습니다.
                1. 상 마 상 마
                2. 마 상 마 상
                3. 상 마 마 상
                4. 마 상 상 마
                """);
    }

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
                Team team = piece.getTeam();
                sb.append(convertColor(team, PieceTitle.getTitleFromTypeAndTeam(piece.getPieceType(), team)));
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
                궁이 잡혔습니다.
                %s나라의 승리입니다!""", team.getTitle());
    }

    private String convertColor(Team team, String input) {
        if (team == Team.HAN) {
            return RED_CODE + input + EXIT_CODE;
        }
        return BLUE_CODE + input + EXIT_CODE;
    }
}
