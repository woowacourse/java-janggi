package view;

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

    private final Scanner scanner = new Scanner(System.in);

    public ChoWings readChoWings() {
        System.out.println("초(楚)의 상차림을 입력해주세요 (예: 마상마상, 마상상마, 상마마상, 상마상마):");
        String input = scanner.nextLine().trim();
        System.out.println();

        return new ChoWings(
                createWingPieces(input.substring(0, 2), Side.CHO),
                createWingPieces(input.substring(2, 4), Side.CHO)
        );
    }

    public HanWings readHanWings() {
        System.out.println("한(漢)의 상차림을 입력해주세요 (예: 마상마상, 마상상마, 상마마상, 상마상마):");
        String input = scanner.nextLine().trim();
        System.out.println();

        return new HanWings(
                createWingPieces(input.substring(0, 2), Side.HAN),
                createWingPieces(input.substring(2, 4), Side.HAN)
        );
    }

    private List<Piece> createWingPieces(String wingInput, Side side) {
        return wingInput.chars()
                .mapToObj(c -> (char) c)
                .filter(WING_TYPES::containsKey) // '마'나 '상'이 아닌 문자는 걸러냄
                .map(symbol -> new Piece(WING_TYPES.get(symbol), side))
                .toList(); // Java 16 미만이라면 .collect(Collectors.toList())
    }
}
