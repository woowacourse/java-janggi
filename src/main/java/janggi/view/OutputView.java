package janggi.view;
import janggi.dto.BoardDto;

import java.util.List;

public class OutputView {

    public void printBoard(BoardDto boardDto) {
        // 상단 가로 좌표 출력 (1~9)
        System.out.print("   ");
        for (int col = 1; col <= 9; col++) {
            System.out.print(col + "  ");
        }
        System.out.println();

        int rowIndex = 1;
        for (List<String> piecesByRow : boardDto.board()) {
            // 좌측 세로 좌표 출력 (1~10)
            System.out.printf("%2d ", rowIndex++);

            for (String pieceName : piecesByRow) {
                System.out.print(pieceName + " ");
            }
            System.out.println();
        }
    }

//    public static void main(String[] args) {
//        Map<Position, Piece> board = new BoardDesignPolicy(
//                Map.of(
//                        Dynasty.CHO, HorseElephantPosition.HEHE,
//                        Dynasty.HAN, HorseElephantPosition.HEEH)
//        ).initBoard();
//        new OutputView().printBoard(BoardDto.from(board));
//    }
}
