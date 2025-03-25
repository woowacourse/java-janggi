package view;

import java.util.Map;
import model.Column;
import model.Piece;
import model.Position;
import model.Row;
import model.Team;

public class OutputView {

    public void printJanggiStart() {
        System.out.println("장기 시작");
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
    }

    public void printCurrentTurnOfTeam(Team currentTurn) {
        System.out.println("현재 %s의 턴입니다.".formatted(currentTurn.getValue()));
    }

    public void showCurrentPositionOfPieces(Map<Position, Piece> pieces) {
        System.out.println("  １２３４５６７８９");
        for (Column column : Column.values()) {
            System.out.print(column.getValue()+" ");
            for (Row row : Row.values()) {
                Piece piece = pieces.get(new Position(column, row));
                if (piece == null) {
                    System.out.print("－");
                } else {
                    System.out.print(piece);
                }
            }
            System.out.println();
        }
    }
}
