package janggi.view;

import janggi.piece.Piece;
import janggi.setting.CampType;
import janggi.value.Position;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OutputView {

    private static final char SPACE = '\u3000';
    private static final char SQUARE = '\u53E3';
    private static final List<Character> unicodes = List.of('\uFF10', '\uFF11', '\uFF12', '\uFF13', '\uFF14', '\uFF15',
            '\uFF16', '\uFF17', '\uFF18', '\uFF19');

    public void writeStartMessage() {
        System.out.println("장기 게임을 시작하겠습니다!");
    }

    public void writeChoStart() {
        System.out.println("초나라 먼저 시작");
        System.out.println();
    }

    public void writeJanggiBoard(final List<Piece> choPieces, final List<Piece> hanPieces) {
        List<Piece> allPieces = new ArrayList<>();
        allPieces.addAll(choPieces);
        allPieces.addAll(hanPieces);

        writeHeader();

        for (int i = 0; i < 10; i++) {
            writeOneLineInBoard(i, allPieces);
        }

        System.out.println();
    }

    public void writeTurn(CampType campType) {
        String turnContent = String.format("%s의 턴입니다.", campType.getName());
        System.out.println(turnContent);
        System.out.println();
    }

    private void writeOneLineInBoard(int i, List<Piece> allPieces) {
        List<Piece> pieces = allPieces.stream()
                .filter(piece -> piece.getPosition().equals(new Position(piece.getPosition().getX(), i)))
                .sorted(Comparator.comparing(Piece::getPosition))
                .toList();

        System.out.print(unicodes.get(i));
        for (int x = 0; x < 9; x++) {
            int xPosition = x;
            pieces.stream()
                    .filter(piece -> piece.getPosition().getX() == xPosition)
                    .findFirst()
                    .ifPresentOrElse(
                            piece -> System.out.print(piece.getPieceType().getName()),
                            () -> System.out.print(SQUARE)
                    );
        }
        System.out.println();
    }

    private static void writeHeader() {
        System.out.print(SPACE);
        for (int i = 0; i < 9; i++) {
            System.out.print(unicodes.get(i));
        }
        System.out.println();
    }

}
