package view;

import domain.place.piece.Side;
import dto.GameRoomDto;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class OutputView {

    private static final String BOARD_MENU =
            "새로운 게임을 불러오시겠습니까?\n"
                    + "1. 저장된 방 찾기\n"
                    + "2. 새로운 게임";

    private static final String GAME_ROOM_INFO_MESSAGE =
            "저장된 게임방입니다.(취소는 0)";

    private static final String GAME_ROOM_INFO =
            "%d. %s %s\n";

    private static final String GAME_SAVE_NAME =
            "저장할 게임의 이름을 입력해주세요.";
    private static final String HORSE_ELEPHANT_INPUT_FORMATION =
            "%s의 상차림을 입력해주세요.(예, 상마상마,마상마상, 상마마상, 마상상마)\n";

    private static final String INPUT_PIECE_MOVE =
            "%s가 이동할 기물을 선택해주세요. (좌표 : 3,5 | 저장 : save)\n";

    private static final String SAVE_COMPLETE_MESSAGE = "게임이 저장되었습니다.";

    private static final String INPUT_POSITION_MOVE =
            "%s가 이동할 위치를 입력해주세요. (예: 3,5)\n";

    private static final String PLAYER_CHECK =
            "%s가 장군을 당했습니다.\n";

    private static final String PLAYER_SCORE =
            "%s : %.1f점 \n%s : %.1f점\n";

    private static final String PLAYER_WINNER =
            "%s 승리!";

    private OutputView() {
    }

    public static void printStartMenu() {
        System.out.println(BOARD_MENU);
    }

    public static void printSaveRoomList(List<GameRoomDto> gameRoomEntities) {
        System.out.println(GAME_ROOM_INFO_MESSAGE);
        gameRoomEntities
                .forEach(g -> System.out.printf(GAME_ROOM_INFO, g.id(), g.name(), LocalDate.from(g.createdAt())));
    }

    public static void printGameName() {
        System.out.println(GAME_SAVE_NAME);
    }

    public static void printSaveComplete() {
        System.out.println(SAVE_COMPLETE_MESSAGE);
    }

    public static void printErrorMessage(String message) {
        System.out.println(message);
    }

    public static void printHorseElephantFormation(Side side) {
        System.out.printf(HORSE_ELEPHANT_INPUT_FORMATION, side.getName());
    }

    public static void printPieceMove(Side side) {
        System.out.printf(INPUT_PIECE_MOVE, side.getName());
    }

    public static void printPositionMove(Side side) {
        System.out.printf(INPUT_POSITION_MOVE, side.getName());
    }

    public static void printCheck(Side side) {
        System.out.printf(PLAYER_CHECK, side.getName());
    }

    public static void printScore(Side side1, double score1, Side side2, double score2) {
        System.out.printf(PLAYER_SCORE, side1.getName(), score1, side2.getName(), score2);
    }

    public static void printWinner(Side side) {
        System.out.printf(PLAYER_WINNER, side.getName());
    }

    public static void printBoard(List<List<String>> boardFormats,
                                  List<List<Optional<Side>>> boardSides) {

        StringBuilder sb = new StringBuilder();
        appendHeader(sb, boardFormats);
        for (int row = 0; row < boardFormats.size(); row++) {
            appendRow(sb, row, boardFormats.get(row), boardSides.get(row));
        }
        System.out.println(sb);
    }

    private static void appendHeader(StringBuilder sb, List<List<String>> boardFormats) {
        sb.append("   ");
        int columnSize = boardFormats.get(0).size();
        for (int i = 1; i <= columnSize; i++) {
            sb.append(String.format("%2d", i)).append(" ");
        }

        sb.append("\n");
    }

    private static void appendRow(StringBuilder sb,
                                  int rowIndex,
                                  List<String> rowFormats,
                                  List<Optional<Side>> rowSides) {
        sb.append(String.format("%2d ", rowIndex + 1));

        for (int col = 0; col < rowFormats.size(); col++) {
            sb.append(colorize(rowFormats.get(col), rowSides.get(col)))
                    .append(" ");
        }

        sb.append("\n");
    }

    private static String colorize(String format, Optional<Side> side) {
        return side
                .map(s -> ColorMapper.colorize(format, s))
                .orElse(format);
    }

}
