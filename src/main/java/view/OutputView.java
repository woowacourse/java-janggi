package view;

import domain.board.Board;
import domain.piece.Camp;
import domain.game.TurnResult;

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
    public static final String GAME_WINNER_MESSAGE = "게임이 종료되었습니다. 승자는 %s 입니다!";
    public static final String EACH_CAMP_SCORE = "초나라 점수: %d, 한나라 점수: %d%n";
    private static final String RESUME_MESSAGE = "진행중인 게임이 있습니다. 이어서 하시겠습니까? (y/n)%n";
    private static final String CHECK_MESSAGE = "%s 장군입니다.%n";

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

    private String campName(Camp camp) {
        if (camp == Camp.CHO) {
            return CHO_CAMP;
        }

        return HAN_CAMP;
    }

    public void printWinner(Camp camp) {
        System.out.printf(GAME_WINNER_MESSAGE, campName(camp));
    }

    public void printScore(int choScore, int hanScore) {
        System.out.printf(EACH_CAMP_SCORE, choScore, hanScore);
    }

    public void printResumePrompt() {
        System.out.printf(RESUME_MESSAGE);
    }

    public void printTurnResult(TurnResult turnResult) {
        turnResult.checkedCamp()
                .ifPresent(this::printCheck);
    }

    private void printCheck(Camp camp) {
        System.out.printf(CHECK_MESSAGE, campName(camp));
    }
}
