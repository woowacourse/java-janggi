package janggi.view;

import janggi.domain.Position;
import janggi.domain.piece.PieceType;
import janggi.domain.team.TeamType;
import janggi.dto.BoardDto;
import janggi.dto.PieceDto;
import java.util.List;
import java.util.Map;

public final class OutputView {

    private static final String ERROR_PREFIX = "[ERROR]: ";
    private static final String EMPTY_SPACE = "＊";
    private static final String DELIMITER = "  ";
    private static final String[] INDEX_LABELS = {"１", "２", "３", "４", "５", "６", "７", "８", "９", "10"};
    private static final Map<TeamType, Map<PieceType, String>> CHINESE_MAP;
    private static final List<String> ELEPHANT_FORMATION_DESCRIPTIONS = List.of(
            "1. 안상 차림",
            "2. 바깥상 차림",
            "3. 왼상 차림",
            "4. 오른상 차림"
    );

    static {
        CHINESE_MAP = Map.of(
                TeamType.RED, Map.of(
                        PieceType.GENERAL, "漢",
                        PieceType.GUARD, "士",
                        PieceType.CHARIOT, "車",
                        PieceType.CANNON, "包",
                        PieceType.HORSE, "馬",
                        PieceType.ELEPHANT, "象",
                        PieceType.SOLDIER, "兵"
                ),
                TeamType.BLUE, Map.of(
                        PieceType.GENERAL, "楚",
                        PieceType.GUARD, "士",
                        PieceType.CHARIOT, "車",
                        PieceType.CANNON, "包",
                        PieceType.HORSE, "馬",
                        PieceType.ELEPHANT, "象",
                        PieceType.SOLDIER, "卒"
                ));
    }

    private OutputView() {
    }

    public static void printStartJanggi() {
        System.out.println("장기 게임입니다. 메뉴 번호를 입력해주세요.");
        System.out.println("1. 새로운 게임 시작");
        System.out.println("2. 이전 게임 시작");
        System.out.println("3. 종료");
    }

    public static void printSetupGuide(TeamType teamType) {
        System.out.println(teamType.getName() + "의 차림법을 입력해주세요.");
        for (final String description : ELEPHANT_FORMATION_DESCRIPTIONS) {
            System.out.println(description);
        }
    }

    public static void printErrorMessage(String message) {
        System.out.println(ERROR_PREFIX + message);
    }

    public static void printBoard(final BoardDto boardDto, String currentTeamType) {
        System.out.println(currentTeamType + "의 차례입니다.");
        printBoardWithMovable(boardDto, List.of());
    }

    public static void printBoardWithMovable(final BoardDto boardDto, final List<Position> movablePositions) {
        System.out.println(generateColumnIndex());
        for (int row = Position.MINIMUM_ROW; row <= Position.MAXIMUM_ROW; row++) {
            System.out.println(composeRowStatus(row, boardDto.pieceMap(), movablePositions));
        }
    }

    public static void printNewGameStartNotice() {
        System.out.println("불러올 수 있는 게임이 없어 새 게임을 시작합니다.");
    }

    public static void printInputFromPosition() {
        System.out.println("움직이고 싶은 기물의 위치를 n,n 형태로 입력해주세요.");
    }

    public static void printInputToPosition() {
        System.out.println("이동하고 싶은 위치를 n,n 형태로 입력해주세요.");
    }

    public static void printGameOverMessage(String teamType) {
        System.out.println(teamType + "의 승리로 게임이 종료되었습니다.");
    }

    private static String generateColumnIndex() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DELIMITER).append(DELIMITER);
        for (int column = Position.MINIMUM_COLUMN; column <= Position.MAXIMUM_COLUMN; column++) {
            stringBuilder.append(DELIMITER).append(INDEX_LABELS[column - 1]);
        }
        return stringBuilder.toString();
    }

    private static String composeRowStatus(final int row, final Map<Position, PieceDto> pieceMap,
                                           final List<Position> movablePositions) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DELIMITER).append(INDEX_LABELS[row - 1]);
        for (int column = Position.MINIMUM_COLUMN; column <= Position.MAXIMUM_COLUMN; column++) {
            Position position = Position.valueOf(row, column);
            stringBuilder.append(DELIMITER).append(getPositionDisplay(position, pieceMap, movablePositions));
        }
        return stringBuilder.toString();
    }

    private static String getPositionDisplay(final Position position, final Map<Position, PieceDto> pieceMap,
                                             final List<Position> movablePositions) {
        String cellText = toCellText(position, pieceMap);
        if (movablePositions.contains(position)) {
            return ConsoleColor.getGreenBackground(cellText);
        }
        return cellText;
    }

    private static String toCellText(final Position position, final Map<Position, PieceDto> pieceMap) {
        PieceDto pieceDto = pieceMap.get(position);
        if (pieceDto == null) {
            return EMPTY_SPACE;
        }
        return applyTeamColor(pieceDto);
    }

    private static String applyTeamColor(final PieceDto pieceDto) {
        String chinese = CHINESE_MAP.get(pieceDto.teamType()).get(pieceDto.pieceType());
        if (pieceDto.teamType() == TeamType.RED) {
            return ConsoleColor.red(chinese);
        }
        return ConsoleColor.blue(chinese);
    }
}
