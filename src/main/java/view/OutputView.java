package view;

import java.util.Map;
import participant.Score;
import pieces.Side;

public class OutputView {

    private static final Map<Side, String> SIDE_SYMBOL = Map.of(
        Side.CHO, "초",
        Side.HAN, "한"
    );

    public void askSangSetup(Side side) {
        System.out.println(SIDE_SYMBOL.get(side) + "나라의 상차림을 선택해주세요.");
        System.out.println(SangSetupInput.convertDisplayFormat());
    }

    public void printBoard(String board) {
        System.out.println(board);
    }

    public static void printErrorMessage(String message) {
        System.out.println("[ERROR] " + message);
    }

    public void printTurnSide(Side side) {
        System.out.println(SIDE_SYMBOL.get(side) + "나라의 차례 입니다.");
    }

    public void askDeparture() {
        System.out.println("이동하고 싶은 기물의 좌표를 입력해주세요. (형식: row, column)");
    }

    public void askDestination() {
        System.out.println("기물의 도착지 좌표를 입력해주세요. (형식: row, column)");
    }

    public void printGameIsOver(Side winner) {
        System.out.println(SIDE_SYMBOL.get(winner) + "나라의 승리입니다.");
        System.out.println("게임이 종료되었습니다.");
    }

    public void printScore(Side side, Score score) {
        System.out.println(SIDE_SYMBOL.get(side) + "나라 점수: " + score.value());
    }
}
