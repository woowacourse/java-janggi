package view;

import domain.board.Board;
import domain.board.Node;
import domain.board.Point;
import domain.piece.Team;
import java.util.ArrayList;
import java.util.List;

public class OutputView {

    public static void printStart() {
        System.out.println("장기 게임에 오신 것을 환영합니다.");
    }

    public static void printBoard(Board board) {
        List<List<String>> boardString = new ArrayList<>();
        for (int row = Board.START_ROW_INDEX; row <= Board.END_ROW_INDEX; row++) {

            List<String> rowString = new ArrayList<>();
            boardString.add(rowString);
            for (int column = Board.START_COLUMN_INDEX; column <= Board.END_COLUMN_INDEX; column++) {
                Node node = board.findNodeByPoint(Point.of(row, column));
                if (!board.existsPieceByNode(node)) {
                    rowString.add(Painter.paintWhite("ㅁ"));
                    continue;
                }
                if (board.hasTeamPieceByNode(node, Team.CHO)) {
                    rowString.add(Painter.paintGreen(board.findPieceTypeByNode(node).getTitle()));
                    continue;
                }
                rowString.add(Painter.paintRed(board.findPieceTypeByNode(node).getTitle()));
            }
        }

        for (List<String> rowString : boardString) {
            System.out.println(String.join(" ", rowString));
        }

        Painter.clean();
    }

    public static void printMatchResult(Team winTeam) {
        System.out.printf("%s나라의 승리입니다.", winTeam.title());
    }
}
