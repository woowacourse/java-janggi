package view;

import domain.board.Board;
import domain.piece.Camp;

public class OutputView {
    private static final String SETUP_OPTIONS =
            """
                    ======================
                    1. 왼상차림 (상마상마)
                    2. 오른상차림 (마상마상)
                    3. 안상차림 (마상상마)
                    4. 바깥상차림 (상마마상)
                    ======================""";
    private static final String PIECE_MOVE_OR_PASS_MESSAGE = "%s 차례입니다. pass 또는 x y x y 형식으로 입력하세요.%n";
    private static final String ELEPHANT_SETUP_MESSAGE = "%s 상차림 번호를 입력하세요.%n";
    private static final String CHO_CAMP = "초나라";
    private static final String HAN_CAMP = "한나라";
    private static final String WINNER_MESSAGE = "%s의 승리입니다!";

    private final BoardRenderer boardRenderer;

    public OutputView() {
        this.boardRenderer = new BoardRenderer();
    }

    public void printSetUpOptions() {
        System.out.println(SETUP_OPTIONS);
    }

    public void printSetUpPrompt(Camp camp) {
        System.out.printf(ELEPHANT_SETUP_MESSAGE, campName(camp));
    }

    public void printBoard(Board board) {
        System.out.println(boardRenderer.render(board));
    }

    public void printTurnPrompt(Camp camp) {
        System.out.printf(PIECE_MOVE_OR_PASS_MESSAGE, campName(camp));
    }

    public void printError(String message) {
        System.out.println(message);
    }

    public void printWinner(Camp winner) {
        System.out.printf(WINNER_MESSAGE, campName(winner));
    }

    private String campName(Camp camp) {
        if (camp == Camp.CHO) {
            return CHO_CAMP;
        }
        return HAN_CAMP;
    }
}
