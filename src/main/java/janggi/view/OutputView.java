package janggi.view;

import janggi.board.Board;
import janggi.board.JanggiScore;
import janggi.coordinate.JanggiPosition;
import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.Country;
import janggi.piece.Elephant;
import janggi.piece.General;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import java.util.Map;

public class OutputView {

    public static final String COLOR_RESET = "\u001B[0m";
    private static final Map<Country, String> TEAM_FORMAT = Map.of(
            Country.HAN, "\u001B[31m",
            Country.CHO, "\u001B[34m"
    );
    private static final Map<Class<? extends Piece>, String> PIECE_FORMAT = Map.of(
            General.class, "漢",
            Guard.class, "士",
            Horse.class, "馬",
            Elephant.class, "象",
            Chariot.class, "車",
            Cannon.class, "包",
            Soldier.class, "兵"
    );


    public static void printIntroduce() {
        System.out.println("어서오세요 장기 게임입니다. :)");
    }

    public static void printBoard(final Board board, final Country country) {
        System.out.println("현재 보드 상태입니다.");
        System.out.println("현재 턴은 다음의 팀입니다. : " + country);

        final StringBuilder sb = new StringBuilder();
        appendBoardHeader(sb);
        final Map<JanggiPosition, Piece> map = board.getJanggiBoard();
        for (int i = JanggiPosition.POSITION_RANGE_X_MIN; i <= JanggiPosition.POSITION_RANGE_X_MAX; i++) {
            appendRow(sb, map, i);
        }
        System.out.println(sb);
    }

    private static void appendRow(final StringBuilder sb, final Map<JanggiPosition, Piece> map, final int i) {
        sb.append(NumberFormat.findNumberName(i) + " ");
        for (int j = JanggiPosition.POSITION_RANGE_Y_MIN; j <= JanggiPosition.POSITION_RANGE_Y_MAX; j++) {
            final JanggiPosition now = new JanggiPosition(i, j);
            appendPiece(sb, map, now);
        }
        sb.append(System.lineSeparator());
    }

    private static void appendPiece(final StringBuilder sb, final Map<JanggiPosition, Piece> map,
                                    final JanggiPosition now) {
        if (map.containsKey(now)) {
            final Piece piece = map.get(now);
            sb.append(TEAM_FORMAT.get(piece.getCountry()));
            sb.append(PIECE_FORMAT.get(piece.getClass()));
            sb.append(COLOR_RESET + " ");
            return;
        }
        sb.append("　 ");
    }

    private static void appendBoardHeader(final StringBuilder sb) {
        final NumberFormat[] values = NumberFormat.values();

        sb.append("ㅁ ");
        for (int i = JanggiPosition.POSITION_RANGE_Y_MIN - 1; i < JanggiPosition.POSITION_RANGE_Y_MAX; i++) {
            sb.append(values[i].name()).append(" ");
        }
        sb.append(System.lineSeparator());
    }

    public static void printJanggiWinner(final Board board) {
        final Country winnerCountry = board.findWinnerCountry();

        System.out.println("축하합니다.");
        System.out.print("우승한 국가는 ");

        final JanggiScore janggiScore = board.calculateScore(winnerCountry);
        if (winnerCountry == Country.HAN) {
            System.out.println("한나라 입니다!");
            System.out.println("점수 : " + janggiScore.value());
            return;
        }

        System.out.println("초나라 입니다!");
        System.out.println("점수 : " + janggiScore.value());
    }
}
