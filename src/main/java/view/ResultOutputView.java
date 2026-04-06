package view;

import domain.game.Side;
import java.util.List;
import view.dto.ScoreDto;
import view.label.SideLabel;

public class ResultOutputView {

    public void printWinner(Side winner, List<ScoreDto> scores) {
        String winnerLabel = SideLabel.getLabel(winner);

        System.out.println(winnerLabel + "(이)가 승리했습니다.");
        for (ScoreDto score : scores) {
            String sideLabel = SideLabel.getLabel(score.side());
            System.out.println(sideLabel + "의 기물 점수: " + score.score());
        }
    }
}
