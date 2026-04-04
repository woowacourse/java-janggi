package view;

import domain.dto.BoardDto;
import domain.dto.PieceDto;

import java.util.List;

public class OutputView {

    public void printJanggiBoard(BoardDto boardDto) {
        System.out.println("  0 1 2 3 4 5 6 7 8");
        List<List<PieceDto>> board = boardDto.board();

        for (int i = 0; i < board.size(); i++) {
            System.out.print(i + " ");
            for (PieceDto piece : board.get(i)) {
                System.out.print(piece.name() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
