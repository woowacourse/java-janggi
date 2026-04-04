package janggi.view;

import java.util.List;

public class OutputView {

    public void printBoardInitialTypeMessage() {
        System.out.println("상차림 유형 번호를 입력해 주세요 (1.왼상차림 2.오른상차림 3.안상차림 4.바깥상차림)");
    }

    public void printFromPositionMessage() {
        System.out.println("움직일 기물의 위치를 입력하세요:");
    }

    public void printToPositionMessage() {
        System.out.println("이동 시킬 위치를 입력하세요:");
    }

    public void printErrorMessage(String message) {
        System.out.println("[ERROR] " + message);
    }

    public void printBoard(String boardRender, String teamName) {
        System.out.println(boardRender);
        System.out.println(teamName + "의 차례입니다.");
    }

    public void printScore(double choScore, double hanScore) {
        System.out.println("초: " + choScore + "점 / 한: " + hanScore + "점");
    }

    public void printGameSelectionMessage() {
        System.out.println("1. 새게임 시작 2. 기존 게임 이어하기");
    }

    public void printGameNameMessage() {
        System.out.println("게임방 이름을 입력하세요:");
    }

    public void printGameList(List<String> gameNames) {
        System.out.println("진행 중인 게임 목록:");
        gameNames.forEach(System.out::println);
    }
}
