package janggi.view;

import janggi.model.Board;

public class OutputView {

    public void printBoard(Board board, String team) {
        System.out.println(board);
        System.out.println(team + "의 차례입니다.");
    }


    public void printBoardInitialTypeMessage() {
        System.out.println("상차림 유형 번호를 입력해 주세요 (1.왼상차림 2.오른상차림 3.안상차림 4.바깥상차림)");
    }

    public void printFromPositionMessage() {
        System.out.println("움직일 기물의 위치를 입력하세요:");
    }

    public void printToPositionMessage() {
        System.out.println("이동 시킬 위치를 입력하세요:");
    }
}
