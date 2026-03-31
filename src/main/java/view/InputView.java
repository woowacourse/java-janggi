package view;

import domain.board.Intersection;
import domain.board.wing.WingPieces;
import domain.board.wing.Wings;
import domain.game.Side;
import domain.piece.Piece;
import domain.piece.PieceType;
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

    private final Scanner scanner = new Scanner(System.in);

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
                .map(symbol -> new Piece(WING_TYPES.get(symbol), side))
                .toList();

        return WingPieces.of(pieces);
    }

    public Intersection readStartPosition(Side currentTurn) {
        String sideName = SIDE_NAMES.get(currentTurn);
        System.out.printf("%s 차례입니다. 이동할 기물의 좌표를 입력하세요 (예: 7,2):%n", sideName);

        return parseIntersection(readLine());
    }

    public Intersection readDestination() {
        System.out.printf("이동 가능한 경로를 표시합니다. 도착할 좌표를 입력하세요 (예: 7,2):%n");

        return parseIntersection(readLine());
    }

    private String readLine() {
        return scanner.nextLine()
                .trim();
    }

    private Intersection parseIntersection(String input) {
        String[] split = input.split(",");
        int row = Integer.parseInt(split[0]);
        int file = Integer.parseInt(split[1]);

        return new Intersection(row, file);
    }
}
