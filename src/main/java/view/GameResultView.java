package view;

import model.piece.Score;
import model.piece.Team;

public class GameResultView {

    public void printGameResult(Score score) {
        System.out.println("게임 결과");
        System.out.println("레드 팀 : " + score.getRedScore());
        System.out.println("그린 팀 : " + score.getGreenScore());
    }

    public void printGeneralDie(Team currentTurn) {
        if (currentTurn == Team.RED) {
            System.out.println("왕이 처치되었습니다. 레드 팀 승리");
            return;
        }
        System.out.println("왕이 처치되었습니다. 그린 팀 승리");
    }
}
