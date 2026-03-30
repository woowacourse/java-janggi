package view;

import domain.board.Intersection;
import domain.board.wing.ChoWings;
import domain.board.wing.HanWings;
import domain.game.Side;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public final class InputView {

    private static final Map<Character, PieceType> WING_TYPES = Map.of(
            '마', PieceType.HORSE,
            '상', PieceType.ELEPHANT
    );

    private static final Map<Side, String> SIDE_NAMES = Map.of(
            Side.CHO, "초(초록색)",
            Side.HAN, "한(빨간색)"
    );

    private final Scanner scanner = new Scanner(System.in);

    public ChoWings readChoWings() {
        System.out.println("초(楚)의 상차림을 입력해주세요 (예: 마상마상, 마상상마, 상마마상, 상마상마):");
        String input = readLine();
        System.out.println();

        return new ChoWings(
                createWingPieces(input.substring(0, 2), Side.CHO),
                createWingPieces(input.substring(2, 4), Side.CHO)
        );
    }

    public HanWings readHanWings() {
        System.out.println("한(漢)의 상차림을 입력해주세요 (예: 마상마상, 마상상마, 상마마상, 상마상마):");
        String input = readLine();
        System.out.println();

        return new HanWings(
                createWingPieces(input.substring(0, 2), Side.HAN),
                createWingPieces(input.substring(2, 4), Side.HAN)
        );
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

    private List<Piece> createWingPieces(String wingInput, Side side) {
        return wingInput.chars()
                .mapToObj(c -> (char) c)
                .filter(WING_TYPES::containsKey)
                .map(symbol -> new Piece(WING_TYPES.get(symbol), side))
                .toList();
    }
}
