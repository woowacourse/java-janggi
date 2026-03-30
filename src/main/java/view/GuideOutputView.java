package view;

import domain.game.Side;
import view.label.SideLabel;

public class GuideOutputView {

    public void printWingInputGuide(Side side) {
        String sideLabel = SideLabel.getLabel(side);

        System.out.println(sideLabel + "의 상차림을 입력해주세요. (ex_ 상마 상마)");
    }

    public void printSelectPieceGuide(Side side) {
        String sideLabel = SideLabel.getLabel(side);

        System.out.println(sideLabel + "의 차례입니다. 움직일 기물을 선택해주세요. (ex_ 1 1)");
    }

    public void printMovePieceGuide() {
        System.out.println("기물이 이동할 위치를 선택해주세요. (ex_ 2 2)");
    }
}
