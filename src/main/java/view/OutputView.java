package view;

import dto.BoardDto;
import java.util.List;
import message.OutputMessage;

public class OutputView {

    public static void printBoard(BoardDto boardDTO) {
        List<List<String>> rows = boardDTO.convertRows();

        for (int row = 0; row < rows.size(); row++) {
            System.out.printf("%d ", row);
            for (String cell : rows.get(row)) {
                System.out.printf("[%s]", cell);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printCurrentPlayerTurn(String playerTurn) {
        System.out.printf(OutputMessage.PLAYER_TURN_SIGN.getMessage(), playerTurn);
        System.out.println();
    }

    public static void printGameResult(String gameResult) {
        System.out.println(gameResult);
    }
}
