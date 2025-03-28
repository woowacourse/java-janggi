package view;

import static domain.point.Point.MAX_COLUMN_INDEX;
import static domain.point.Point.MAX_ROW_INDEX;
import static domain.point.Point.MIN_COLUMN_INDEX;
import static domain.point.Point.MIN_ROW_INDEX;

import domain.piece.Piece;
import domain.piece.character.PieceType;
import domain.piece.character.Team;
import domain.point.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class OutputView {

    private static final String TAB = "\t";

    public static void printStart(String gameRoomName) {
        System.out.printf("""
                %n장기 게임에 오신 것을 환영합니다.
                '%s'에 입장하셨습니다.
                """, gameRoomName);
    }

    public static void printBoard(Map<Point, Piece> pieces) {
        List<List<String>> boardString = new ArrayList<>();
        for (int row = MIN_ROW_INDEX; row <= MAX_ROW_INDEX; row++) {
            List<String> rowString = new ArrayList<>(List.of(String.format("%02d", row), "|"));
            for (int column = MIN_COLUMN_INDEX; column <= MAX_COLUMN_INDEX; column++) {
                Point point = Point.of(row, column);
                pieceToString(pieces, point, rowString);
            }
            boardString.add(rowString);
        }
        List<String> lastRowString = new ArrayList<>(List.of("", "|"));
        IntStream.range(MIN_COLUMN_INDEX, MAX_COLUMN_INDEX + 1)
                .forEach(column -> lastRowString.add(String.format("%02d", column)));

        System.out.println();
        for (List<String> rowString : boardString) {
            System.out.println(String.join(TAB, rowString));
        }
        System.out.println(String.join(TAB, lastRowString));
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

    private static void pieceToString(Map<Point, Piece> pieces, Point point, List<String> rowString) {
        if (!pieces.containsKey(point)) {
            String emptyPoint = emptyPointToString(point);
            rowString.add(Painter.paintWhite(emptyPoint));
            return;
        }
        Piece piece = pieces.get(point);
        rowString.add(paintPieceByTeam(piece.team(), piece.type()));
    }

    private static String emptyPointToString(Point point) {
        if (point.isInPalace()) {
            return "△";
        }
        return "ㅁ";
    }

    private static String paintPieceByTeam(Team team, PieceType pieceType) {
        if (team == Team.CHO) {
            return Painter.paintGreen(pieceTypeToString(pieceType));
        }
        return Painter.paintRed(pieceTypeToString(pieceType));
    }

    private static String teamToString(Team team) {
        return switch (team) {
            case CHO -> Painter.paintGreen("초");
            case HAN -> Painter.paintRed("한");
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
