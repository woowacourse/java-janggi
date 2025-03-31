import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import piece.Piece;
import piece.initiate.TableSetting;
import piece.player.Team;
import piece.position.JanggiPosition;

public class GameView {

    private static final String EMPTY_PIECE = "ㅁ";
    private static final String INVALID_POSITION_INPUT = "좌표는 r,c 방식이어야합니다.";
    private static final String NOT_NUMBER = "입력된 값이 숫자가 아닙니다.";
    private static final String RED_COLOR_FORMAT = "\u001B[31m%s\u001B[0m";
    private static final String BLUE_COLOR_FORMAT = "\u001B[34m%s\u001B[0m";
    private static final String GRID_HELPER = "  ０１２３４５６７８";
    private static final String WINNER_FORMAT = "%s 이 승리하였습니다.%n";
    private static final String TABLE_SETTING_FORMATTER = "%s 팀 의 상차림을 선택해주세요 0:마상마상 1:마상상마, 2:상마마상 3:상마상마";
    private static final String PLAYER_SCORE_FORMAT = "%s팀의 점수 %d";
    private static final Map<Team, String> teamStringMapper = Map.of(Team.BLUE, "청", Team.RED, "홍");

    private final Map<Integer, TableSetting> tableSettingMapper = Map.of(0,
            TableSetting.MA_SANG_MA_SANG,
            1, TableSetting.MA_SANG_SANG_MA, 2, TableSetting.SANG_MA_MA_SANG,
            3, TableSetting.SANG_MA_SANG_MA);

    private final Scanner scanner;

    public GameView() {
        this.scanner = new Scanner(System.in);
    }

    public void printPlayer(Team team) {
        System.out.printf("%s 차례입니다.%n", teamStringMapper.get(team));
    }

    public void printJanggiBoard(Map<JanggiPosition, Piece> positionPieceMap) {
        StringBuilder stringBuilder = new StringBuilder(GRID_HELPER).append(System.lineSeparator());
        for (int i = 9; i >= 0; i--) {
            stringBuilder.append(i).append(" ");
            for (int j = 0; j < 9; j++) {
                String type = pieceType(positionPieceMap, i, j);
                stringBuilder.append(type);
            }
            stringBuilder.append(System.lineSeparator());
        }
        System.out.println(stringBuilder.toString());
    }

    private static String pieceType(Map<JanggiPosition, Piece> positionPieceMap, int i, int j) {
        JanggiPosition position = new JanggiPosition(i, j);
        if (positionPieceMap.get(position) == null) {
            return EMPTY_PIECE;
        }
        Piece piece = positionPieceMap.get(position);
        String type = piece.type().getType();
        if (piece.isSameTeam(Team.BLUE)) {
            return String.format(BLUE_COLOR_FORMAT, type);
        }
        if (piece.isSameTeam(Team.RED)) {
            return String.format(RED_COLOR_FORMAT, type);
        }
        return type;
    }

    public JanggiPosition inputSelectPiece() {
        System.out.printf("이동할 기물 위치를 입력해주세요 (r,c) %n");
        String input = scanner.nextLine();
        return inputPosition(input);
    }

    public JanggiPosition inputPiecePosition() {
        System.out.printf("이동시킬 위치를 입력해주세요 (r,c) %n");
        String input = scanner.nextLine();
        return inputPosition(input);
    }

    private JanggiPosition inputPosition(String s) {
        String[] position = s.split(",");
        if (position.length != 2) {
            throw new IllegalArgumentException(INVALID_POSITION_INPUT);
        }
        return inputPosition(position);
    }

    private static JanggiPosition inputPosition(String[] position) {
        try {
            int r = Integer.parseInt(position[0]);
            int c = Integer.parseInt(position[1]);
            return new JanggiPosition(r, c);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(NOT_NUMBER);
        }
    }

    public TableSetting inputTableSetting(Team team) {
        Optional<TableSetting> tableSetting = Optional.empty();
        while (tableSetting.isEmpty()) {
            System.out.printf(TABLE_SETTING_FORMATTER, teamStringMapper.get(team));
            String input = scanner.nextLine();
            tableSetting = inputTableSetting(input);
        }
        return tableSetting.get();
    }

    private Optional<TableSetting> inputTableSetting(String input) {
        try {
            int selectTableSetting = Integer.parseInt(input);
            return tableSettingMapping(selectTableSetting);
        } catch (NumberFormatException e) {
            printError(NOT_NUMBER);
            return Optional.empty();
        } catch (IllegalArgumentException e) {
            printError(e.getMessage());
            return Optional.empty();
        }
    }

    private Optional<TableSetting> tableSettingMapping(int selectTableSetting) {
        TableSetting tableSetting = tableSettingMapper.get(selectTableSetting);
        if (tableSetting == null) {
            throw new IllegalArgumentException("유효하지 않은 상차림입니다.");
        }
        return Optional.of(tableSetting);
    }

    public void printError(String message) {
        System.out.printf("[ERROR] %s%n", message);
    }

    public void printWinner(Team winner) {
        System.out.printf(WINNER_FORMAT, teamStringMapper.get(winner));
    }

    public void printPlayerScore(Team team, int score) {
        System.out.printf(PLAYER_SCORE_FORMAT, teamStringMapper.get(team), score);
    }

    public void printTurn(int turn) {
        System.out.printf("%d 번째 수입니다 ", turn);
    }

    public void printCanNotApplySave(String errorMessage) {
        System.out.printf("에러 메시지 : %s 장기 저장 기능이 동작하지 않습니다. 여전히 게임은 진행하실 수 있습니다!", errorMessage);
    }

    public void printStartFromUserInput() {
        System.out.println("게임을 다시 처음부터 시작합니다.");
    }
}
