package view;

import domain.board.Intersection;
import domain.board.wing.WingPieces;
import domain.board.wing.Wings;
import domain.game.Side;
import domain.piece.Piece;
import domain.piece.PieceType;
import dto.MoveCommand;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public final class InputView {

    private static final Map<Side, String> SIDE_NAMES = Map.of(
            Side.CHO, "초(楚)",
            Side.HAN, "한(漢)"
    );
    private static final Map<String, String> RAW_WINGS_BY_NUMBER = Map.of(
            "1", "마상마상",
            "2", "마상상마",
            "3", "상마마상",
            "4", "상마상마"
    );
    private static final Map<Character, PieceType> WING_TYPES = Map.of(
            '마', PieceType.HORSE,
            '상', PieceType.ELEPHANT
    );
    private static final int WING_SIZE = 2;
    private static final String ERROR_ONLY_NUMBER = "숫자만 입력할 수 있습니다.";

    private final Scanner scanner = new Scanner(System.in);

    public int readMenuCommand() {
        System.out.println("메뉴 중 하나를 선택해 주세요(예. 1): ");
        try {
            return Integer.parseInt(readLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERROR_ONLY_NUMBER, e);
        }
    }

    public long readGameNumber() {
        System.out.println("이어서 시작할 게임의 번호를 선택해 주세요(새 게임을 시작하려면 0번): ");
        try {
            return Long.parseLong(readLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERROR_ONLY_NUMBER, e);
        }
    }

    public Wings readWings(Side side) {
        System.out.println(SIDE_NAMES.get(side) + "의 상차림을 번호로 선택해주세요 (예: 1)");
        System.out.printf("1. %s%n2. %s%n3. %s%n4. %s%n", "마상마상", "마상상마", "상마마상", "상마상마");

        String rawWingsNumber = readLine();
        if (!RAW_WINGS_BY_NUMBER.containsKey(rawWingsNumber)) {
            throw new IllegalArgumentException("선택한 상차림이 옳바르지 않습니다. 1~4 중 선택해주세요(이전 입력: " + rawWingsNumber + ")");
        }

        String rawWings = RAW_WINGS_BY_NUMBER.get(rawWingsNumber);
        System.out.println();

        return new Wings(
                side,
                createWingPieces(rawWings.substring(0, WING_SIZE), side),
                createWingPieces(rawWings.substring(WING_SIZE), side)
        );
    }

    private WingPieces createWingPieces(String wingInput, Side side) {
        List<Piece> pieces = wingInput.chars()
                .mapToObj(c -> (char) c)
                .filter(WING_TYPES::containsKey)
                .map(symbol -> Piece.of(WING_TYPES.get(symbol), side))
                .toList();

        return WingPieces.of(pieces);
    }

    public MoveCommand readMoveCommand(Side currentTurn) {
        String sideName = SIDE_NAMES.get(currentTurn);
        System.out.printf("%s 차례입니다. 이동할 기물의 좌표를 입력하세요 ('exit'를 입력하면 종료):%n", sideName);

        return MoveCommand.from(readLine());
    }

    public Intersection readDestination() {
        System.out.printf("이동 가능한 경로를 표시합니다. 도착할 좌표를 입력하세요 (예: 7,2):%n");

        return Intersection.parse(readLine());
    }

    private String readLine() {
        return scanner.nextLine()
                .trim();
    }
}
