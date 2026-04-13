package view;

import static domain.board.Position.INITIAL_POSITION;
import static domain.board.Position.X_MAXIMUM_POSITION;
import static domain.board.Position.Y_MAXIMUM_POSITION;

import domain.board.Board;
import domain.board.Country;
import domain.board.Position;
import domain.piece.PieceInfo;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OutputView {
    private static final List<String> POSITION_NUMBERS = List.of("０", "１", "２", "３", "４", "５", "６", "７", "８", "９");

    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String BLANK_STATE = "十";
    private static final String STATE_SEPARATOR = "  ";
    private static final String X_POSITION_START_BLANK = "   ";

    private static final String PRINT_TURN = "%s의 차례입니다.";

    public void printTurn(String countryName) {
        System.out.printf(LINE_SEPARATOR + PRINT_TURN + LINE_SEPARATOR, countryName);
    }

    public void printBoard(Board board) {
        Map<Position, PieceInfo> pieceInfos = new LinkedHashMap<>();
        board.forEachPiece(pieceInfos::put);

        System.out.println();
        for (int y = Y_MAXIMUM_POSITION; y >= INITIAL_POSITION; y--) {
            System.out.print(POSITION_NUMBERS.get(y));
            printRow(pieceInfos, y);
        }
        printXPositionNumbers();
    }

    private void printRow(Map<Position, PieceInfo> pieceInfos, int y) {
        for (int x = INITIAL_POSITION; x <= X_MAXIMUM_POSITION; x++) {
            PieceInfo pieceInfo = pieceInfos.get(new Position(x, y));

            System.out.print(STATE_SEPARATOR);
            if (pieceInfo == null) {
                System.out.print(BLANK_STATE);
                continue;
            }
            String pieceName = PieceTypeFormatter.from(pieceInfo.pieceType(), pieceInfo.country());
            System.out.print(pieceName);
        }
        System.out.println();
    }

    private void printXPositionNumbers() {
        System.out.print(X_POSITION_START_BLANK);
        System.out.println(String.join(STATE_SEPARATOR, POSITION_NUMBERS.subList(0, 9)));
    }

    public void printCurrentScores(Country country, double currentScore) {
        System.out.println(CountryFormatter.from(country) + " 점수: " + currentScore);
    }

    public void printWinner(String winCountry, String loseCountry) {
        System.out.println();
        System.out.println(winCountry + "가 " + loseCountry + " 궁을 잡았습니다.");
        System.out.println(winCountry + "가 승리했습니다!");
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
    }
}
