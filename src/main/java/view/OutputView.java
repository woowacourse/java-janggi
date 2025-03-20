package view;

import domain.BoardPosition;
import domain.Piece;
import java.util.Map;

public class OutputView {

    public void printBoard(final Map<BoardPosition, Piece> pieces) {
        pieces.forEach((key, value) -> {
            System.out.print("(" +
                key
                    .x() + " , " +
                key
                    .y() + ")");
            System.out.println(" : " + value
                .getPieceType()
                .getTitle());
        });
    }
}
