package view;

import board.Board;
import board.Position;
import java.util.Map;
import piece.Piece;
import piece.PieceType;
import piece.TeamType;

public class OutputView {
    public static final String COLOR_RESET = "\u001B[0m";
    private static final Map<TeamType, String> TEAM_FORMAT = Map.of(
            TeamType.RED, "\u001B[31m",
            TeamType.BLUE, "\u001B[34m"
    );
    private static final Map<PieceType, String> PIECE_FORMAT = Map.of(
            PieceType.GENERAL, "漢",
            PieceType.GUARD, "士",
            PieceType.HORSE, "馬",
            PieceType.ELEPHANT, "象",
            PieceType.CHARIOT, "車",
            PieceType.CANNON, "包",
            PieceType.SOLDIER, "兵"
    );


    public static void printIntroduce() {
        System.out.println("어서오세요 장기 게임입니다. :)");
    }

    public static void printBoard(final Board board, final TeamType teamType) {
        System.out.println("현재 보드 상태입니다.");
        System.out.println("현재 턴은 다음의 팀입니다. : " + teamType);

        final StringBuilder sb = new StringBuilder("ㅁ 일 이 삼 사 오 육 칠 팔 구\n");
        final Map<Position, Piece> map = board.getMap();
        for (int i = 1; i <= 10; i++) {

            sb.append(NumberFormat.findNumberName(i) + " ");
            for (int j = 1; j <= 9; j++) {
                final Position now = new Position(i, j);

                if (map.containsKey(now)) {
                    final Piece piece = map.get(now);
                    sb.append(TEAM_FORMAT.get(piece.getTeamType()));
                    sb.append(PIECE_FORMAT.get(piece.getPieceType()));
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
