package janggi.view;

import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.domain.team.TeamType;
import java.util.Map;

public class ResultView {

    private static final String LINE = System.lineSeparator();
    private static final String BLANK = "ㅤ";
    private static final String HEADER = "   1    2    3    4    5    6    7    8   9%n";
    private static final String BOARD_LINE = "   |    |    |    |    |    |    |    |   |%n";
    private static final String BOARD_LINE_HEAD_GUNG_CASTLE = "   |    |    |    | \\  |  / |    |    |   |%n";
    private static final String BOARD_LINE_TAIL_GUNG_CASTLE = "   |    |    |    | /  |  \\ |    |    |   |%n";
    private static final String BLUE_CODE = "\u001B[34m";
    private static final String RED_CODE = "\u001B[31m";
    private static final String EXIT_CODE = "\u001B[0m";

    public void printLoadingDoneMessage() {
        System.out.println("진행 중인 게임 정보를 불러왔습니다.");
    }

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
                TeamType teamType = piece.getTeamType();
                sb.append(convertColor(teamType, PieceTitle.getTitleFromTypeAndTeam(piece.getPieceType(), teamType)));
            }
            System.out.println(sb);
            if (y == 1 || y == 8) {
                System.out.printf(BOARD_LINE_HEAD_GUNG_CASTLE);
                continue;
            }
            if (y == 2 || y == 9) {
                System.out.printf(BOARD_LINE_TAIL_GUNG_CASTLE);
                continue;
            }
            if (y == 10) {
                System.out.print(LINE);
                continue;
            }
            System.out.printf(BOARD_LINE);
        }
    }

    public void printOrder(final TeamType teamType) {
        System.out.printf(LINE + "%s나라의 순서입니다." + LINE, teamType.getTitle());
    }

    public void printScoreBoard(final Map<TeamType, Double> scores) {
        scores.forEach((key, value) -> {
            String colorfulScore = convertColor(key, String.format("%.1f", value));
            System.out.printf("%s나라 : %s점%s", key.getTitle(), colorfulScore, LINE);
        });
    }

    public void printCatchingGungMessage() {
        System.out.println("궁이 잡혔습니다.");
    }

    public void printJanggiResult(final TeamType teamType) {
        System.out.printf(LINE + "%s나라의 승리입니다!", teamType.getTitle());
    }

    private String convertColor(TeamType teamType, String input) {
        if (teamType == TeamType.HAN) {
            return RED_CODE + input + EXIT_CODE;
        }
        return BLUE_CODE + input + EXIT_CODE;
    }
}
