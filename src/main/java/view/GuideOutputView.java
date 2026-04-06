package view;

import domain.game.Side;
import view.label.SideLabel;

public class GuideOutputView {

    public void printAskNewGameGuide() {
        System.out.println("새로운 게임을 시작할까요? (y를 입력하면 새로운 게임을 시작합니다)");
    }

    public void printGameIdGuide() {
        System.out.println("기존에 진행중이던 게임의 아이디를 입력해주세요.");
    }

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
