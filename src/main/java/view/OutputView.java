package view;

import static domain.Position.INITIAL_POSITION;
import static domain.Position.X_MAXIMUM_POSITION;
import static domain.Position.Y_MAXIMUM_POSITION;

import domain.Position;
import domain.piece.PieceInfo;
import java.util.List;
import java.util.Map;

public class OutputView {
    private static final List<String> POSITION_NUMBERS = List.of("０", "１", "２", "３", "４", "５", "６", "７", "８", "９");

    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String STATE_SEPARATOR = "  ";
    private static final String X_POSITION_START_BLANK = "   ";

    private static final String PRINT_TURN = "%s의 차례입니다.";

    public void printTurn(String countryName) {
        System.out.printf(LINE_SEPARATOR + PRINT_TURN + LINE_SEPARATOR, countryName);
    }

    public void printBoard(Map<Position, PieceInfo> pieceInfos) {
        System.out.println();

        for (int y = Y_MAXIMUM_POSITION; y >= INITIAL_POSITION; y--) {
            System.out.print(POSITION_NUMBERS.get(y));
            printRow(pieceInfos, y);
        }
        printXPositionNumbers();
    }

    private void printRow(Map<Position, PieceInfo> pieceInfos, int y) {
        for (int x = INITIAL_POSITION; x <= X_MAXIMUM_POSITION; x++) {
            Position position = new Position(x, y);
            PieceInfo pieceInfo = pieceInfos.get(position);
            printState(pieceInfo, position);
        }
        System.out.println();
    }

    private void printState(PieceInfo pieceInfo, Position position) {
        System.out.print(STATE_SEPARATOR);
        if (pieceInfo == null) {
            System.out.print(EmptyStateFormatter.getEmptyState(position));
            return;
        }
        String pieceName = PieceTypeFormatter.from(pieceInfo.pieceType(), pieceInfo.country());
        System.out.print(pieceName);
    }

    private void printXPositionNumbers() {
        System.out.print(X_POSITION_START_BLANK);
        System.out.println(String.join(STATE_SEPARATOR, POSITION_NUMBERS.subList(0, 9)));
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
    }
}
