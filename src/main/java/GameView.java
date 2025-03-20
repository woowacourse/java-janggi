import java.util.Map;
import java.util.Scanner;
import piece.Piece;
import piece.Position;
import piece.Team;

public class GameView {
    private static final String EMPTY_PIECE = "ㅁ";
    private static final String INVALID_POSITION = "좌표는 r,c 방식이어야합니다.";
    private static final String NOT_NUMBER = "입력된 값이 숫자가 아닙니다.";
    private static final String RED_COLOR_FORMAT = "\u001B[31m%s\u001B[0m";
    private static final String BLUE_COLOR_FORMAT = "\u001B[34m%s\u001B[0m";
    private static final String GRID_HELPER = "  영일이삼사오육칠팔\n\n";
    private final Scanner scanner;


    public GameView() {
        this.scanner = new Scanner(System.in);
    }

    /*
    1. 청 차례인지 홍 차례인지
    2. 상차림 구현안됨 알림
    3.
     */

    public void printTurn(Team team) {
        System.out.printf("%s 차례입니다.%n", team.getType());
    }


    public void printChangePieceNotImplement() {
        System.out.println("상차림 기능은 아직 구현이 안되었습니다.");
    }

    public void playerBoard(Map<Position, Piece> positionPieceMap) {
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
        String type = piece.getType();
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
        String s = scanner.nextLine();
        return inputPosition(s);
    }

    public Position inputPiecePosition() {
        System.out.printf("이동시킬 위치를 입력해주세요 (r,c) %n");
        String s = scanner.nextLine();
        return inputPosition(s);
    }

    private Position inputPosition(String s) {
        String[] position = s.split(",");
        if (position.length != 2) {
            throw new IllegalArgumentException(INVALID_POSITION);
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

    public void printError(String message) {
        System.out.printf("[ERROR] %s\n", message);
    }
}
