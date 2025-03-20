package view;

import domain.Board;
import domain.Position;
import domain.Team;
import domain.piece.Piece;
import java.util.Optional;
import java.util.stream.IntStream;

public class OutputView {

    private static final String red = "\u001B[31m";
    private static final String blue = "\u001B[34m";
    private static final String exit = "\u001B[0m";
    private static final String BLANK_CELL = "ㅁ";

    public void printBoard(Board board) {
        CustomStringBuilder stringBuilder = new CustomStringBuilder();
        appendHeader(stringBuilder);
        IntStream.range(1, 11).forEach(rowIndex -> appendRow(board, stringBuilder, rowIndex));
    }

    private void appendRow(Board board, CustomStringBuilder stringBuilder, int rowIndex) {
        stringBuilder.append(String.valueOf(rowIndex % 10));
        appendBoardByRow(board, stringBuilder, rowIndex);
        stringBuilder.lineSplit();
    }

    private void appendBoardByRow(Board board, CustomStringBuilder stringBuilder, int rowIndex) {
        IntStream.range(1, 10).forEach(columnIndex -> stringBuilder.append(getCellContent(board.findPiece(new Position(
                columnIndex,
                rowIndex)))
        ));
    }

    private String getCellContent(Optional<Piece> piece) {
        String cell = BLANK_CELL;
        if (piece.isPresent()) {
            cell = convertContentColor(piece.get().getTeam(), piece.toString());
        }
        return cell;
    }

    private static void appendHeader(CustomStringBuilder stringBuilder) {
        stringBuilder.appendBlankCell();
        IntStream.range(1, 10).forEach(columnIndex -> stringBuilder.append(String.valueOf(columnIndex)));
    }

    public void printError(String content) {
        System.out.println(content);
    }

    private String convertContentColor(Team team, String content) {
        if (team == Team.BLUE) {
            return String.format("%s%s%s", blue, content, exit);
        }
        return String.format("%s%s%s", red, content, exit);
    }
}
