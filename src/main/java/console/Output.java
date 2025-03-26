package console;

import console.util.Color;
import console.util.PieceSymbol;
import console.util.TeamSymbol;
import janggi.piece.Piece;
import janggi.piece.Team;
import janggi.piece.normalPiece.Blank;
import janggi.position.Board;
import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import java.util.Map;

public class Output {

    public void startGame() {
        System.out.printf("장기 게임을 시작합니다.%n%n");
    }

    public void display(Board board) {
        Map<Position, Piece> onPosition = board.pieceOfPosition();
        for (Row row : Row.values()) {
            System.out.print(row.ordinal() + " ");
            displayColumn(row, onPosition);
        }
        System.out.println("  A  B C  D E  F G  H I ");
    }

    private static void displayColumn(Row row, Map<Position, Piece> onPosition) {
        for (Column column : Column.values()) {
            Piece piece = onPosition.getOrDefault(new Position(column, row), new Blank(new Position(column, row)));
            System.out.print(Color.apply(piece.team(), PieceSymbol.from(piece) + " "));
        }
        System.out.println();
    }

    public void displayTurn(Board board) {
        Team currentTeam = board.currentTeam();
        System.out.printf("%n%s의 차례입니다.%n%n", Color.apply(currentTeam, TeamSymbol.from(currentTeam)));
    }

    public void retry(Exception e) {
        System.out.println(e.getMessage() + " 다시 입력해주세요.");
    }

    public void end(Team winnerTeam) {
        String winner = Color.apply(winnerTeam, TeamSymbol.from(winnerTeam));
        System.out.printf("%n%s가 승리했습니다. 게임을 종료합니다.%n", winner);
    }
}
