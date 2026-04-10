package view;

import domain.board.Board;
import domain.piece.Camp;

import java.util.List;

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
    private static final String SCORE_MESSAGE = "기물 부족으로 게임이 종료되었습니다. (점수 - 초: %.1f, 한: %.1f)%n";
    private static final String MENU_MESSAGE = """
            > 장기 게임을 시작합니다.
            > 1. 새로 하기
            > 2. 이어 하기
            """;
    private static final String LOAD_MENU_MESSAGE = "> 불러올 게임 번호를 입력해 주세요.";
    private static final String LOADABLE_GAMEID_MESSAGE = "불러올 수 있는 게임 번호 목록: ";
    private static final String NEW_GAME_START_MESSAGE = "새 게임 시작 (게임 번호: %d)%n";
    private static final String LOAD_PREVIOUS_GAME_MESSAGE = "%d 번 게임을 불러왔습니다.%n";

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

    public void printScore(double choScore, double hanScore) {
        System.out.printf(SCORE_MESSAGE, choScore, hanScore);
    }

    public void printMainMenu() {
        System.out.println(MENU_MESSAGE);
    }

    public void printLoadGameMenu(List<Integer> activeGames) {
        System.out.println(LOADABLE_GAMEID_MESSAGE + activeGames);
        System.out.println(LOAD_MENU_MESSAGE);
    }

    public void printNewGameStart(int gameId) {
        System.out.printf(NEW_GAME_START_MESSAGE, gameId);
    }

    public void printLoadPreviousGame(int gameId) {
        System.out.printf(LOAD_PREVIOUS_GAME_MESSAGE, gameId);
    }

    private String campName(Camp camp) {
        if (camp == Camp.CHO) {
            return CHO_CAMP;
        }
        return HAN_CAMP;
    }
}
