package view;

import domain.game.Side;
import view.label.SideLabel;

public class ResultOutputView {

    public void printWinner(Side winner) {
        String winnerLabel = SideLabel.getLabel(winner);

        System.out.println(winnerLabel + "(이)가 승리했습니다.");
    }
}
