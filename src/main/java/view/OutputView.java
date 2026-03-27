package view;

import static domain.Position.INITIAL_POSITION;
import static domain.Position.X_MAXIMUM_POSITION;
import static domain.Position.Y_MAXIMUM_POSITION;

import domain.Position;
import domain.piece.PieceInfo;
import java.util.List;
import java.util.Map;

public class OutputView {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final List<String> POSITION_NUMBERS = List.of("０", "１", "２", "３", "４", "５", "６", "７", "８", "９");

    public void printTurn(String countryName) {
        System.out.printf(LINE_SEPARATOR + "%s의 차례입니다." + LINE_SEPARATOR, countryName);
    }

    public void printBoard(Map<Position, PieceInfo> pieceInfos) {
        System.out.println();

        for (int y = Y_MAXIMUM_POSITION; y >= INITIAL_POSITION; y--) {
            System.out.print(POSITION_NUMBERS.get(y));

            for (int x = INITIAL_POSITION; x <= X_MAXIMUM_POSITION; x++) {
                PieceInfo pieceInfo = pieceInfos.get(new Position(x, y));

                System.out.print("  ");
                if (pieceInfo == null) {
                    System.out.print("十");
                    continue;
                }
                String pieceName = PieceTypeFormatter.from(pieceInfo.getPieceType(), pieceInfo.getCountry());
                System.out.print(pieceName);
            }
            System.out.println();
        }
        printXPositionNumbers();
    }

    private void printXPositionNumbers() {
        System.out.print("   ");
        System.out.println(String.join("  ", POSITION_NUMBERS.subList(0, 9)));
    }
}
