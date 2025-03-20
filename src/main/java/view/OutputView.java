package view;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import domain.piece.Piece;

public class OutputView {
    public static void printJanggiBoard(JanggiBoard board){
        StringBuilder builder = new StringBuilder();

        for(int row = JanggiBoard.BOARD_MIN_SIZE; row < JanggiBoard.ROW_SIZE; row++){
            for(int col = JanggiBoard.BOARD_MIN_SIZE; col < JanggiBoard.COL_SIZE; col++){
                JanggiCoordinate coordinate = new JanggiCoordinate(row,col);
                if(board.isBlankCoordinate(coordinate)){
                    builder.append("_");
                    continue;
                }
                builder.append(board.getPieceType(coordinate));
            }
            builder.append('\n');
        }
        System.out.println(builder);
    }
}
