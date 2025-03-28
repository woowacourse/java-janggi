package view;

import board.Board;
import position.Position;
import piece.*;

import java.util.Map;

public class OutputView {

    public static final String COLOR_RESET = "\u001B[0m";
    private static final Map<Country, String> TEAM_FORMAT = Map.of(
            Country.CHO, "\u001B[31m",
            Country.HAN, "\u001B[34m"
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
        System.out.println("현재 턴은 " + country + "나라의 것입니다.");

        final StringBuilder sb = new StringBuilder("ㅁ 일 이 삼 사 오 육 칠 팔 구\n");
        final Map<Position, Piece> pieces = board.getPieces();
        for (int y = 1; y <= 10; y++) {

            sb.append(NumberFormat.findNumberName(y) + " ");
            for (int x = 1; x <= 9; x++) {
                final Position now = new Position(x, y);

                if (pieces.containsKey(now)) {
                    final Piece piece = pieces.get(now);
                    sb.append(TEAM_FORMAT.get(piece.getTeamType()));
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
