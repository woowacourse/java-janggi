package console;

import console.util.Color;
import console.util.PieceSymbol;
import janggi.board.Board;
import janggi.piece.Piece;
import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import java.util.Map;

public class Output {

    public void startGame() {
        System.out.printf("장기 게임을 시작합니다.%n%n");
    }

    public void board(Board board) {
        Map<Position, Piece> onPosition = board.onPosition();
        for (Row row : Row.values()) {
            System.out.print(row.ordinal() + " ");
            for (Column column : Column.values()) {
                Piece piece = onPosition.get(new Position(column, row));
                if(piece!=null){
                    System.out.print(Color.apply(piece.getTeam(), PieceSymbol.from(piece) + " "));
                }
                else{
                    System.out.print("＿ ");
                }
            }
            System.out.println();
        }
        System.out.println("  A  B C  D E  F G  H I ");
    }

//    public void turn(TeamDto teamDto) {
//        System.out.printf("%n%s의 차례입니다.%n%n", Color.apply(teamDto, teamDto.getDisplayName()));
//    }
//
//    public void result(TeamDto winnerTeamDto) {
//        String winner = Color.apply(winnerTeamDto, winnerTeamDto.getDisplayName());
//        System.out.printf("%n%s가 승리했습니다. 게임을 종료합니다.%n", winner);
//    }

    public void retry(Exception e) {
        System.out.println(e.getMessage() + " 다시 입력해주세요.");
    }
}
