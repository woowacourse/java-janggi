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
import java.util.stream.IntStream;

public class OutputView {

    private static final String TAB = "\t";

    public static void printStart() {
        System.out.printf("%n장기 게임에 오신 것을 환영합니다.");
    }

    public static void printBoard(Board board) {
        Map<Point, Piece> pieceByPoint = board.getPiece();

        List<List<String>> boardString = new ArrayList<>();
        for (int row = MIN_ROW_INDEX; row <= MAX_ROW_INDEX; row++) {
            List<String> rowString = new ArrayList<>();
            boardString.add(rowString);

            rowString.add(String.format("%02d", row));
            rowString.add("|");
            for (int column = MIN_COLUMN_INDEX; column <= MAX_COLUMN_INDEX; column++) {
                Point point = Point.of(row, column);
                if (!board.existsPiece(point)) {
                    rowString.add(Painter.paintWhite("ㅁ"));
                    continue;
                }
                Piece piece = pieceByPoint.get(point);
                rowString.add(paintByTeam(piece.team(), piece.type().title()));
            }
        }

        System.out.println();
        for (List<String> rowString : boardString) {
            System.out.println(String.join(TAB, rowString));
        }

        List<String> lastRowString = new ArrayList<>();
        lastRowString.add("");
        lastRowString.add("|");
        IntStream.range(MIN_COLUMN_INDEX, MAX_COLUMN_INDEX + 1)
                .forEach(column -> lastRowString.add(String.format("%02d", column)));
        System.out.println(String.join(TAB, lastRowString));
    }

    private static String paintByTeam(Team team, String pieceName) {
        if (team == Team.CHO) {
            return Painter.paintGreen(pieceName);
        }
        return Painter.paintRed(pieceName);
    }

    public static void printTurn(Team team) {
        System.out.printf("%n이번 턴은 %s나라입니다.", team.title());
    }

    public static void printCannotMove(Point source, Point destination) {
        System.out.printf("%n%d,%d에서 %d,%d로 이동할 수 없습니다.%n",
                source.row(), source.column(),
                destination.row(), destination.column()
        );
    }

    public static void printStatus(double choScore, double hanScore) {
        System.out.printf("""
                %n초나라: %.1f점
                한나라: %.1f점
                """, choScore, hanScore);
    }

    public static void printMatchResult(Team winTeam) {
        System.out.printf("%n%s나라의 승리입니다.", winTeam.title());
    }

    public static void printEmpty(Point point) {
        System.out.printf("%n(%d,%d)에 기물이 없습니다.", point.row(), point.column());
    }
}
