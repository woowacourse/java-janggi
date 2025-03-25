package janggi.view;

import janggi.board.Board;
import janggi.coordinate.Position;
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
        final Map<Position, Piece> map = board.getJanggiBoard();
        for (int i = Position.POSITION_RANGE_X_MIN; i <= Position.POSITION_RANGE_X_MAX; i++) {
            appendRow(sb, map, i);
        }
        System.out.println(sb);
    }

    private static void appendRow(final StringBuilder sb, final Map<Position, Piece> map, final int i) {
        sb.append(NumberFormat.findNumberName(i) + " ");
        for (int j = Position.POSITION_RANGE_Y_MIN; j <= Position.POSITION_RANGE_Y_MAX; j++) {
            final Position now = new Position(i, j);
            appendPiece(sb, map, now);
        }
        sb.append(System.lineSeparator());
    }

    private static void appendPiece(final StringBuilder sb, final Map<Position, Piece> map, final Position now) {
        if (map.containsKey(now)) {
            final Piece piece = map.get(now);
            sb.append(TEAM_FORMAT.get(piece.getTeamType()));
            sb.append(PIECE_FORMAT.get(piece.getClass()));
            sb.append(COLOR_RESET + " ");
            return;
        }
        sb.append("　 ");
    }

    private static void appendBoardHeader(final StringBuilder sb) {
        final NumberFormat[] values = NumberFormat.values();

        sb.append("ㅁ ");
        for (int i = Position.POSITION_RANGE_Y_MIN - 1; i < Position.POSITION_RANGE_Y_MAX; i++) {
            sb.append(values[i].name()).append(" ");
        }
        sb.append(System.lineSeparator());
    }
}
