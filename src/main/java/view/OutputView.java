package view;

import core.GameStatus;
import core.GameSummary;
import java.util.List;
import java.util.Map;
import participant.Score;
import pieces.Side;

public class OutputView {

    private static final Map<Side, String> SIDE_SYMBOL = Map.of(
        Side.CHO, "초",
        Side.HAN, "한"
    );

    private static final Map<GameStatus, String> GAME_STATUS_LABELS = Map.of(
        GameStatus.PLAYING, "진행 중",
        GameStatus.CHO_WIN_BY_GUNG, "초나라승리(장군)",
        GameStatus.HAN_WIN_BY_GUNG, "한나라승리(장군)",
        GameStatus.CHO_WIN_BY_SCORE, "초나라승리(점수)",
        GameStatus.HAN_WIN_BY_SCORE, "한나라승리(점수)"
    );

    public void askSangSetup(final Side side) {
        System.out.println(SIDE_SYMBOL.get(side) + "나라의 상차림을 선택해주세요.");
        System.out.println(SangSetupInput.convertDisplayFormat());
    }

    public void printBoard(final String board) {
        System.out.println(board);
    }

    public static void printErrorMessage(final String message) {
        System.out.println("[ERROR] " + message);
    }

    public void printTurnSide(final Side side) {
        System.out.println(SIDE_SYMBOL.get(side) + "나라의 차례 입니다.");
    }

    public void askEndByScore() {
        System.out.println("점수로 게임을 종료하시겠습니까? (y/n)");
    }

    public void askConfirmEndByScore(final Side side) {
        System.out.println(SIDE_SYMBOL.get(side) + "나라도 동의하십니까? (y/n)");
    }

    public void askDeparture() {
        System.out.println("이동하고 싶은 기물의 좌표를 입력해주세요. (형식: row, column)");
    }

    public void askDestination() {
        System.out.println("기물의 도착지 좌표를 입력해주세요. (형식: row, column)");
    }

    public void printGameResult(final GameStatus status) {
        System.out.println(GAME_STATUS_LABELS.get(status));
        System.out.println("게임이 종료되었습니다.");
    }

    public void printScore(final Side side, final Score score) {
        System.out.println(SIDE_SYMBOL.get(side) + "나라 점수: " + score.value());
    }

    public void printSavedGames(final List<GameSummary> gameSummaries) {
        System.out.println("저장된 게임 목록을 출력합니다.");

        gameSummaries.forEach(game -> System.out.printf(
            "%d. 상태=%s, 현재차례=%s%n",
            game.id(),
            GAME_STATUS_LABELS.get(game.status()),
            SIDE_SYMBOL.get(game.turn().getSide())
        ));
    }

    public void askGameId() {
        System.out.println("실행할 게임 ID 를 입력해주세요. 새로운 게임을 원할 경우 0을 입력해주세요.");
    }

    public void askServiceMenu() {
        System.out.println("원하는 서비스 번호를 입력해주세요.");
        System.out.println(ServiceMenu.convertDisplayFormat());
    }
}
