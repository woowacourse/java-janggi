package view;

import static domain.Position.INITIAL_POSITION;
import static domain.Position.X_MAXIMUM_POSITION;
import static domain.Position.Y_MAXIMUM_POSITION;

import domain.Position;
import domain.country.CountryType;
import domain.piece.PieceInfo;
import domain.piece.PieceInfos;
import java.util.List;

public class OutputView {
    private static final List<String> POSITION_NUMBERS = List.of("０", "１", "２", "３", "４", "５", "６", "７", "８", "９");

    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String STATE_SEPARATOR = "  ";
    private static final String X_POSITION_START_BLANK = "   ";

    private static final String PRINT_BOARD_ID = "보드 ID";
    private static final String PRINT_TURN = "%s의 차례입니다.";
    private static final String PRINT_SCORE = "%s: %.1f점";
    private static final String PRINT_END_WITH_CATCH_GENERAL = "%s가 %s의 궁을 잡아서 게임을 종료합니다.";
    private static final String PRINT_END_WITH_BOARD_REPEAT = "동일 포지션이 3번 반복되어 게임을 종료합니다.";
    private static final String PRINT_WINNER = "%s가 게임을 승리했습니다.";

    public void printBoardId(List<Integer> boardIds) {
        System.out.println(LINE_SEPARATOR + PRINT_BOARD_ID);
        for (int id : boardIds) {
            System.out.println(id);
        }
    }

    public void printBoard(PieceInfos pieceInfos, CountryType turn, double choScore, double hanScore) {
        System.out.printf(LINE_SEPARATOR + PRINT_TURN + LINE_SEPARATOR, CountryFormatter.from(turn));
        printScore(choScore, hanScore);

        for (int y = Y_MAXIMUM_POSITION; y >= INITIAL_POSITION; y--) {
            System.out.print(POSITION_NUMBERS.get(y));
            printRow(pieceInfos, y);
        }
        printXPositionNumbers();
    }

    public void printScore(double choScore, double hanScore) {
        System.out.printf(LINE_SEPARATOR + PRINT_SCORE + LINE_SEPARATOR, CountryFormatter.from(CountryType.CHO),
                choScore);
        System.out.printf(PRINT_SCORE + LINE_SEPARATOR, CountryFormatter.from(CountryType.HAN), hanScore);
    }

    private void printRow(PieceInfos pieceInfos, int y) {
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
        String pieceName = PieceTypeFormatter.from(pieceInfo.pieceType(), pieceInfo.countryType());
        System.out.print(pieceName);
    }

    private void printXPositionNumbers() {
        System.out.print(X_POSITION_START_BLANK);
        System.out.println(String.join(STATE_SEPARATOR, POSITION_NUMBERS.subList(0, 9)));
    }

    public void printEndWithCatchGeneral(CountryType winnerCountryType) {
        String winner = CountryFormatter.from(winnerCountryType);
        String loser = CountryFormatter.from(winnerCountryType.anotherCountryType());
        System.out.printf(LINE_SEPARATOR + PRINT_END_WITH_CATCH_GENERAL + LINE_SEPARATOR, winner, loser);
        printWinner(winnerCountryType);
    }

    public void printEndWithBoardRepeat(double choScore, double hanScore) {
        System.out.println(LINE_SEPARATOR + PRINT_END_WITH_BOARD_REPEAT);
        printScore(choScore, hanScore);
        if (hanScore > choScore) {
            printWinner(CountryType.HAN);
            return;
        }
        printWinner(CountryType.CHO);
    }

    private void printWinner(CountryType winnerCountryType) {
        System.out.printf(LINE_SEPARATOR + PRINT_WINNER + LINE_SEPARATOR, CountryFormatter.from(winnerCountryType));
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
    }
}
