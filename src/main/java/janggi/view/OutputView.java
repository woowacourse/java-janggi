package janggi.view;

import janggi.domain.board.BoardDesignPolicy;
import janggi.domain.board.HorseElephantPosition;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.dto.BoardDto;

import java.util.List;
import java.util.Map;

public class OutputView {

    public void printBoard(BoardDto boardDto) {
        for (List<String> piecesByRow : boardDto.board()) {
            for (String pieceName : piecesByRow) {
                System.out.print(pieceName);
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
