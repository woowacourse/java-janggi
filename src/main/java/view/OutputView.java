package view;

import java.util.Map;
import model.position.Column;
import model.piece.Piece;
import model.position.Position;
import model.position.Row;
import model.Team;
import model.position.Score;

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
            loopForRow(pieces, column);
        }
    }

    private void loopForRow(Map<Position, Piece> pieces, Column column) {
        System.out.print(column.getValue() + " ");
        for (Row row : Row.values()) {
            printPieceByPosition(pieces, column, row);
        }
        System.out.println();
    }

    private void printPieceByPosition(Map<Position, Piece> pieces, Column column, Row row) {
        Piece piece = pieces.get(new Position(column, row));
        if (piece == null) {
            System.out.print("－");
            return;
        }
        System.out.print(piece.getName());
    }

    public void printGameResult(Score score) {
        System.out.println("게임 결과");
        System.out.println("레드 팀 : " + score.getRedScore());
        System.out.println("그린 팀 : " + score.getGreenScore());
    }

    public void printGeneralDie(Team currentTurn) {
        if (currentTurn == Team.RED) {
            System.out.println("왕이 처치되었습니다. 레드 팀 승리");
            return;
        }
        System.out.println("왕이 처치되었습니다. 그린 팀 승리");
    }
}
