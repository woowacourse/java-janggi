package janggi.view;

import janggi.view.dto.GameStatus;

public class OutputView {

    public void printGameStatus(GameStatus gameStatus) {
        System.out.println(gameStatus.board());
        System.out.println("현재 팀은 " + gameStatus.team() + "입니다.");
    }

    public void printBoardInitialTypeMessage() {
        System.out.println("상차림 유형 번호를 입력해 주세요 (1.왼상차림 2.오른상차림 3.안상차림 4.바깥상차림)");
    }

    public void printFromPositionMessage() {
        System.out.println("움직일 기물의 위치를 입력하세요[(행,열) 형식으로]:");
    }

    public void printToPositionMessage() {
        System.out.println("이동 시킬 위치를 입력하세요:");
    }

    public void printInputContinuePrompt() {
        System.out.println("게임을 계속 진행하시겠습니까? (y, n)");
    }
}
