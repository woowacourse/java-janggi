package view;

import static domain.point.Point.MAX_COLUMN_INDEX;
import static domain.point.Point.MAX_ROW_INDEX;
import static domain.point.Point.MIN_COLUMN_INDEX;
import static domain.point.Point.MIN_ROW_INDEX;

import domain.PieceType;
import domain.Team;
import domain.board.Board;
import domain.piece.Piece;
import domain.point.Point;
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
        Map<Point, Piece> pieceByPoint = board.getPieces();

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
                rowString.add(paintByTeam(piece.team(), piece.type()));
            }
        }

        System.out.println();
        for (List<String> rowString : boardString) {
            System.out.println(String.join(TAB, rowString));
        }

        List<String> lastRowString = new ArrayList<>(List.of("", "|"));
        IntStream.range(MIN_COLUMN_INDEX, MAX_COLUMN_INDEX + 1)
                .forEach(column -> lastRowString.add(String.format("%02d", column)));
        System.out.println(String.join(TAB, lastRowString));
    }

    private static String paintByTeam(Team team, PieceType pieceType) {
        if (team == Team.CHO) {
            return Painter.paintGreen(pieceTypeToString(pieceType));
        }
        return Painter.paintRed(pieceTypeToString(pieceType));
    }

    public static void printTurn(Team team) {
        System.out.printf("%n이번 턴은 %s나라입니다.", teamToString(team));
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
        System.out.printf("%n%s나라의 승리입니다.", teamToString(winTeam));
    }

    public static void printEmpty(Point point) {
        System.out.printf("%n(%d,%d)에 기물이 없습니다.", point.row(), point.column());
    }

    private static String teamToString(Team team) {
        return switch (team) {
            case CHO -> "초";
            case HAN -> "한";
        };
    }

    private static String pieceTypeToString(PieceType pieceType) {
        return switch (pieceType) {
            case WANG -> "왕";
            case SA -> "사";
            case CHA -> "차";
            case MA -> "마";
            case SANG -> "상";
            case PO -> "포";
            case BYEONG -> "병";
        };
    }
}
