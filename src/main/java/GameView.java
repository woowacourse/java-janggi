import java.util.Map;
import java.util.Scanner;
import piece.Piece;
import piece.Team;
import piece.initiate.TableSetting;
import piece.position.Position;

public class GameView {

    private static final String EMPTY_PIECE = "ㅁ";
    private static final String INVALID_POSITION_INPUT = "좌표는 r,c 방식이어야합니다.";
    private static final String NOT_NUMBER = "입력된 값이 숫자가 아닙니다.";
    private static final String RED_COLOR_FORMAT = "\u001B[31m%s\u001B[0m";
    private static final String BLUE_COLOR_FORMAT = "\u001B[34m%s\u001B[0m";
    private static final String GRID_HELPER = "  영일이삼사오육칠팔\n\n";
    private static final String WINNER_FORMAT = "%s 이 승리하였습니다.";
    private static final String TABLE_SETTING_FORMATTER = "%s 팀 의 상차림을 선택해주세요 0:마상마상 1:마상상마, 2:상마마상 3:상마상마";

    private final Map<Integer, TableSetting> tableSettingMapper = Map.of(0,
            TableSetting.MA_SANG_MA_SANG,
            1, TableSetting.MA_SANG_SANG_MA, 2, TableSetting.SANG_MA_MA_SANG,
            3, TableSetting.SANG_MA_SANG_MA);

    private final Scanner scanner;

    public GameView() {
        this.scanner = new Scanner(System.in);
    }

    public void printTurn(Team team) {
        System.out.printf("%s 차례입니다.%n", team.getType());
    }

    public void printJanggiBoard(Map<Position, Piece> positionPieceMap) {
        StringBuilder stringBuilder = new StringBuilder(GRID_HELPER);
        for (int i = 9; i >= 0; i--) {
            stringBuilder.append(i).append(" ");
            for (int j = 0; j < 9; j++) {
                String type = pieceType(positionPieceMap, i, j);
                stringBuilder.append(type);
            }
            stringBuilder.append("\n");
        }
        System.out.println(stringBuilder.toString());
    }

    private static String pieceType(Map<Position, Piece> positionPieceMap, int i, int j) {
        Position position = new Position(i, j);
        if (positionPieceMap.get(position) == null) {
            return EMPTY_PIECE;
        }
        Piece piece = positionPieceMap.get(position);
        String type = piece.getType().getType();
        if (piece.isSameTeam(Team.BLUE)) {
            return String.format(BLUE_COLOR_FORMAT, type);
        }
        if (piece.isSameTeam(Team.RED)) {
            return String.format(RED_COLOR_FORMAT, type);
        }
        return type;
    }

    public Position inputSelectPiece() {
        System.out.printf("이동할 기물 위치를 입력해주세요 (r,c) %n");
        String input = scanner.nextLine();
        return inputPosition(input);
    }

    public Position inputPiecePosition() {
        System.out.printf("이동시킬 위치를 입력해주세요 (r,c) %n");
        String input = scanner.nextLine();
        return inputPosition(input);
    }

    private Position inputPosition(String s) {
        String[] position = s.split(",");
        if (position.length != 2) {
            throw new IllegalArgumentException(INVALID_POSITION_INPUT);
        }
        return inputPosition(position);
    }

    private static Position inputPosition(String[] position) {
        try {
            int r = Integer.parseInt(position[0]);
            int c = Integer.parseInt(position[1]);
            return new Position(r, c);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(NOT_NUMBER);
        }
    }

    public TableSetting inputTableSetting(Team team) {
        System.out.printf(TABLE_SETTING_FORMATTER, team.getType());
        String input = scanner.nextLine();
        try {
            int selectTableSetting = Integer.parseInt(input);
            return tableSettingMapper.get(selectTableSetting);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(NOT_NUMBER);
        }
    }

    public void printError(String message) {
        System.out.printf("[ERROR] %s\n", message);
    }

    public void printWinner(Team winner) {
        System.out.printf(WINNER_FORMAT, winner.getType());
    }
}
