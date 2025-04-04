package view;

import domain.board.Board;
import domain.piece.*;
import domain.position.Position;

import java.util.Map;

public class OutputView {

    public static final String COLOR_RESET = "\u001B[0m";
    private static final Map<Country, String> COUNTRY_COLOR_FORMAT = Map.of(
            Country.CHO, "\u001B[31m",
            Country.HAN, "\u001B[34m"
    );
    private static final Map<String, String> COLOR_NAME_FORMAT = Map.of(
            "\u001B[31m", "빨간색",
            "\u001B[34m", "파란색"
    );
    private static final Map<String, String> COLOR_FORMAT_BY_NAME = Map.of(
            "빨간색", "\u001B[31m",
            "파란색", "\u001B[34m"
    );
    private static final Map<Country, String> COUNTRY_NAME_FORMAT = Map.of(
            Country.CHO, "초",
            Country.HAN, "한"
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
        System.out.println("----------------------게임 시작-----------------------");
        System.out.println("어서오세요 장기 게임입니다. :)");
        System.out.println("게임을 종료하고 싶을 때는 언제든 exit를 입력해주세요.");
        System.out.println();
    }

    public static void printBoard(final Board board, final Country country) {
        System.out.println();
        System.out.println();
        System.out.println("-----------------------점수판------------------------");
        String scoreFormat = "%s나라: %d";
        Map<Country, Integer> scoreByCountry = board.getScoreByCountry();
        for (Country countryValue : scoreByCountry.keySet()) {
            System.out.println(String.format(scoreFormat, COUNTRY_NAME_FORMAT.get(countryValue), scoreByCountry.get(countryValue)));
        }
        System.out.println();
        System.out.println("-----------------------보드판------------------------");
        System.out.println("현재 보드 상태입니다.");
        System.out.println();
        printBoard(board);
        System.out.println("현재 턴은 " + COUNTRY_NAME_FORMAT.get(country) + "나라입니다. " +
                        getColorNameWithColor(country) +
                " 기물을 선택해주세요.");
    }

    private static String getColorNameWithColor(Country country) {
        return COLOR_FORMAT_BY_NAME.get(COLOR_NAME_FORMAT.get(COUNTRY_COLOR_FORMAT.get(country))) + COLOR_NAME_FORMAT.get(COUNTRY_COLOR_FORMAT.get(country)) + COLOR_RESET;
    }

    private static void printBoard(Board board) {
        final StringBuilder sb = new StringBuilder("ㅁ 일 이 삼 사 오 육 칠 팔 구\n");
        final Map<Position, Piece> pieces = board.getPieceMap();
        for (int y = 1; y <= 10; y++) {

            sb.append(NumberFormat.findNumberName(y) + " ");
            for (int x = 1; x <= 9; x++) {
                final Position now = new Position(x, y);

                if (pieces.containsKey(now)) {
                    final Piece piece = pieces.get(now);
                    sb.append(COUNTRY_COLOR_FORMAT.get(piece.getCountry()));
                    sb.append(PIECE_FORMAT.get(piece.getClass()));
                    sb.append(COLOR_RESET + " ");
                    continue;
                }
                sb.append("　 ");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }
}
