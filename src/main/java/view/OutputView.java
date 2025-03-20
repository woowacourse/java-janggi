package view;

import domain.Column;
import domain.Player;
import domain.Position;
import domain.Row;
import domain.piece.Piece;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OutputView {

    public void printBoard(List<Piece> pieces){
        String alphabet = IntStream.range(Column.MIN_COLUMN, Column.MAX_COLUMN+1)
                .mapToObj(value -> (char) ('a' + value))
                .map(Objects::toString)
                .collect(Collectors.joining(" "));

        System.out.println("    " + alphabet);
        for (int row = Row.MIN_ROW; row <= Row.MAX_ROW; row++) {
            System.out.printf("%2d  ", Row.MAX_ROW - row);
            for (int col = Column.MIN_COLUMN; col <= Column.MAX_COLUMN; col++) {
                Position position = Position.of(row,col);
                Piece findPiece = pieces.stream().filter(piece -> piece.hasSamePosition(position))
                        .findAny()
                        .orElse(null);
                if(findPiece==null){
                    System.out.print("- ");
                }else{
                    System.out.print(findPiece.getType().getDescription()+" ");
                }

            }
            System.out.println();
        }
        System.out.println();
    }

    public void printWinner(Player player){
        System.out.printf("%s가 승리했습니다!\n", player.getName());
    }
}
