package view;

import static domain.board.Point.MAX_COLUMN_INDEX;
import static domain.board.Point.MAX_ROW_INDEX;
import static domain.board.Point.MIN_COLUMN_INDEX;
import static domain.board.Point.MIN_ROW_INDEX;

import domain.board.Board;
import domain.board.Point;
import domain.piece.Piece;
import domain.piece.Team;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OutputView {

    public static void printStart() {
        System.out.println("장기 게임에 오신 것을 환영합니다.");
    }

    public static void printBoard(Board board) {
        Map<Point, Piece> pieceByPoint = board.getPieceByPoint();

        List<List<String>> boardString = new ArrayList<>();
        for (int row = MIN_ROW_INDEX; row <= MAX_ROW_INDEX; row++) {

            List<String> rowString = new ArrayList<>();
            boardString.add(rowString);
            for (int column = MIN_COLUMN_INDEX; column <= MAX_COLUMN_INDEX; column++) {
                Point point = Point.of(row, column);
                if (!board.existsPieceByPoint(point)) {
                    rowString.add(Painter.paintWhite("ㅁ"));
                    continue;
                }
                Piece piece = pieceByPoint.get(point);
                if (board.hasTeamOfPiece(point, Team.CHO)) {
                    rowString.add(Painter.paintGreen(piece.type().title()));
                    continue;
                }
                rowString.add(Painter.paintRed(piece.type().title()));
            }
        }

        for (List<String> rowString : boardString) {
            System.out.println(String.join(" ", rowString));
        }

        Painter.clean();
    }

    public static void printMatchResult(Team winTeam) {
        System.out.printf("%s나라의 승리입니다.%n", winTeam.title());
    }

    public static void printTurn(Team team) {
        System.out.printf("이번 턴은 %s나라입니다.%n", team.title());
    }
}
