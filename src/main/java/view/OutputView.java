package view;

import domain.Team;

public class OutputView {

    public void showBoard() {
        System.out.println("""
                   1 2 3 4 5 6 7 8 9
                ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
                1 |차 마 상 사 ㅡ 사 상 마 차
                2 |ㅡ ㅡ ㅡ ㅡ 궁 ㅡ ㅡ ㅡ ㅡ
                3 |ㅡ 포 ㅡ ㅡ ㅡ ㅡ ㅡ 포 ㅡ
                4 |병 ㅡ 병 ㅡ 병 ㅡ 병 ㅡ 병
                5 |ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ
                6 |ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ
                7 |졸 ㅡ 졸 ㅡ 졸 ㅡ 졸 ㅡ 졸
                8 |ㅡ 포 ㅡ ㅡ ㅡ ㅡ ㅡ 포 ㅡ
                9 |ㅡ ㅡ ㅡ ㅡ 궁 ㅡ ㅡ ㅡ ㅡ
                10|차 마 상 사 ㅡ 사 상 마 차
                """);
    }

    public void showResult(Team team) {
        System.out.println("기물을 옮긴 팀 : " + team.toString());
//        Board board = turn.getBoard();
//        Map<BoardLocation, Piece> hanPieces = board.getHanBoard().getPieces();
//        Map<BoardLocation, Piece> choPieces = board.getChoBoard().getPieces();
//
//        System.out.println("================ 초나라 ======================");
//        for (Map.Entry<BoardLocation, Piece> entry : choPieces.entrySet()) {
//            System.out.println(entry.getKey().toString() + " : " + entry.getValue());
//        }
//        System.out.println("===========================================");
//
//        System.out.println("================ 한나라 ======================");
//        for (Map.Entry<BoardLocation, Piece> entry : hanPieces.entrySet()) {
//            System.out.println(entry.getKey().toString() + " : " + entry.getValue());
//        }
//        System.out.println("===========================================");
    }

    public void printTurn(Team turn) {
        System.out.println(turn.toString() + "턴 입니다.");
    }
}
