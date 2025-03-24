package view;


import model.Team;

public class OutputView {

    public void printJanggiStart() {
        System.out.println("장기 시작");
    }

    public void printCurrentPosition(String currentPosition) {
        System.out.println("  １２３４５６７８９");
        System.out.println(currentPosition);
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
    }

    public void printCurrentTurnOfTeam(Team currentTurn) {
        System.out.println("현재 %s의 턴입니다.".formatted(currentTurn.getValue()));
    }
}
