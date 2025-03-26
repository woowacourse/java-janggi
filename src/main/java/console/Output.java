package console;

import console.util.Color;
import console.util.PieceSymbol;
import console.util.TeamSymbol;
import janggi.piece.Piece;
import janggi.piece.Team;
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
            for (Column column : Column.values()) {
                Piece piece = onPosition.get(new Position(column, row));
                if (piece != null) {
                    System.out.print(Color.apply(piece.team(), PieceSymbol.from(piece) + " "));
                } else {
                    System.out.print("＿ ");
                }
            }
            System.out.println();
        }
        System.out.println("  A  B C  D E  F G  H I ");
    }

    public void displayTurn(Board board) {
        Team currentTeam = board.currentTeam();
        System.out.printf("%n%s의 차례입니다.%n%n", Color.apply(currentTeam, TeamSymbol.from(currentTeam)));
    }

//    public void result(TeamDto winnerTeamDto) {
//        String winner = Color.apply(winnerTeamDto, winnerTeamDto.getDisplayName());
//        System.out.printf("%n%s가 승리했습니다. 게임을 종료합니다.%n", winner);
//    }

    public void retry(Exception e) {
        System.out.println(e.getMessage() + " 다시 입력해주세요.");
    }

}
