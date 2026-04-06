package janggi.view;

import janggi.model.Team;
import janggi.service.dto.GameOptionResponse;
import janggi.view.dto.GameStatus;
import java.util.List;

public class OutputView {

    public void printGameStatus(GameStatus gameStatus) {
        System.out.println(gameStatus.board());
        System.out.println("현재 팀은 " + gameStatus.team() + "입니다.");
    }

    public void printBoardInitialTypeMessage() {
        System.out.println("새로운 장기판을 생성합니다.");
        System.out.println("상차림 유형 번호를 입력해 주세요 (1.왼상차림 2.오른상차림 3.안상차림 4.바깥상차림)");
    }

    public void printFromPositionMessage() {
        System.out.println("움직일 기물의 위치를 입력하세요[(행,열) 형식으로]:");
    }

    public void printToPositionMessage() {
        System.out.println("이동 시킬 위치를 입력하세요(행,열) 형식으로]:");
    }

    public void printInputDrawAcceptPrompt() {
        System.out.println("무승부 처리하시겠습니까? (y, n)");
    }

    public void printWinner(Team winner) {
        String winnerDisplayName = "한나라";
        if (winner == Team.CHO) {
            winnerDisplayName = "초나라";
        }

        System.out.println("승리한 팀은 " + winnerDisplayName + "입니다.");
    }

    public void printInputContinuePrompt() {
        System.out.println("프로그램을 계속하시겠습니까? (y, n)");
    }

    public void printGameOptions(List<GameOptionResponse> responses) {
        for (GameOptionResponse response : responses) {
            System.out.println("게임 id: " + response.gameId()
                    + ", 마지막 턴: " + response.currentTurn());
        }

        System.out.println("플레이할 게임을 고르세요(게임 id) -  새로운 게임을 시작하고 싶으면 0을 입력하세요: ");
    }
}
