package janggi.view;

import janggi.piece.Piece;
import janggi.setting.CampType;
import java.util.ArrayList;
import java.util.List;

public class OutputView {

    private static final char SPACE = '\u3000';
    private static final char SQUARE = '\u53E3';
    private static final List<Character> unicodes = List.of(
            '\uFF10', '\uFF11', '\uFF12', '\uFF13', '\uFF14',
            '\uFF15', '\uFF16', '\uFF17', '\uFF18', '\uFF19');

    public void writeStartMessage() {
        System.out.println("장기 게임을 시작하겠습니다!");
    }

    public void writeJanggiBoard(final List<Piece> choPieces, final List<Piece> hanPieces) {
        writeBoardYHeader();

        List<Piece> allPieces = new ArrayList<>();
        allPieces.addAll(choPieces);
        allPieces.addAll(hanPieces);

        for (int y = 0; y < 10; y++) {
            int yPosition = y;
            List<Piece> pieces = allPieces.stream()
                    .filter(piece -> piece.getPosition().y() == yPosition)
                    .toList();
            writeOneLineInBoard(y, pieces);
        }

        System.out.println();
    }

    public void writeTurn(CampType campType) {
        String turnContent = String.format("%s의 턴입니다.", campType.getName());
        System.out.println(turnContent);
    }

    private void writeOneLineInBoard(int y, List<Piece> pieces) {
        System.out.print(unicodes.get(y));
        for (int x = 0; x < 9; x++) {
            int xPosition = x;
            pieces.stream()
                    .filter(piece -> piece.getPosition().x() == xPosition)
                    .findFirst()
                    .ifPresentOrElse(
                            piece -> System.out.print(piece.getPieceType().getName()),
                            () -> System.out.print(SQUARE)
                    );
        }
        System.out.println();
    }

    private static void writeBoardYHeader() {
        System.out.print(SPACE);
        for (int i = 0; i < 9; i++) {
            System.out.print(unicodes.get(i));
        }
        System.out.println();
    }
}
