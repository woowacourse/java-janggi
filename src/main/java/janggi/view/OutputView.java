package janggi.view;

import janggi.Team;

public class OutputView {

    public void printGameStartMessage() {
        System.out.println("장기 게임을 시작합니다. 선공은 초나라입니다.");
        System.out.println("ex) 1,1 2,2 (1,1 에 위치한 기물을 2,2로 이동)");
    }

    public void printGameOver(Team winningTeam) {
        System.out.println("%s가 승리했습니다.".formatted(winningTeam.getName()));
    }
}
