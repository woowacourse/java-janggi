package view;

import domain.BoardPosition;
import domain.Piece;
import domain.Team;
import java.util.Map;

public class OutputView {

    public void printBoard(
        final Map<BoardPosition, Piece> pieces,
        final Team team
    ) {
        System.out.println("현재 턴: " + team.getTitle());
        pieces.forEach((key, value) -> {
            System.out.print("(" +
                key.x() + " , " +
                key.y() +
                ")");
            System.out.println(": " +
                value
                    .getPieceType()
                    .getTitle() +
                " (" +
                value.getTeam()
                    .getTitle() +
                ")");
        });
    }
}
