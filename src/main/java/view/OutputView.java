package view;

import domain.board.Board;
import domain.board.ElephantSetup;
import domain.piece.Position;
import domain.piece.Team;
import dto.PieceInfoDto;
import dto.PieceInfosDto;
import java.util.List;
import java.util.Map;

public class OutputView {

    private static final String BLUE_CODE = "\u001B[34m";
    private static final String RED_CODE = "\u001B[31m";
    private static final String RESET_CODE = "\u001B[0m";

    private static final String EMPTY_CELL_SYMBOL = "口";
    private static final String HEADER_PREFIX = "    ";
    private static final String CELL_PADDING = " ";
    private static final String HEADER_GAP = " \u3000";
    private static final String LINE_SEPARATOR = System.lineSeparator();

    public void printEnterChoPlayerNamePrompt() {
        System.out.println("초나라 플레이어의 이름을 입력하세요(2~5자, 영어만 사용):");
    }

    public void printEnterHanPlayerNamePrompt() {
        System.out.println("한나라 플레이어의 이름을 입력하세요(2~5자, 영어만 사용):");
    }

    public void printChoiceChoElephantSetupPrompt() {
        System.out.println("초나라 플레이어가 사용할 상차림 번호를 입력하세요");
        printElephantSetups();
    }

    public void printChoiceHanElephantSetupPrompt() {
        System.out.println("한나라 플레이어가 사용할 상차림 번호를 입력하세요");
        printElephantSetups();
    }

    private void printElephantSetups() {
        List<String> descriptions = ElephantSetup.descriptions();
        StringBuilder builder = new StringBuilder();

        for (int index = 0; index < descriptions.size(); index++) {
            builder.append(index + 1)
                    .append(". ")
                    .append(descriptions.get(index))
                    .append(" ");
        }

        System.out.println(builder);
    }

    public void printBoardWithPieces(final PieceInfosDto pieceInfos) {
        StringBuilder builder = new StringBuilder();
        Map<Position, PieceInfoDto> pieceByPosition = pieceInfos.pieceInfos();

        appendRowHeader(builder);
        appendBoardRows(builder, pieceByPosition);

        System.out.println(builder);
    }

    private void appendRowHeader(final StringBuilder builder) {
        builder.append(HEADER_PREFIX);

        for (int row = Board.MIN_ROW_RANGE; row <= Board.MAX_ROW_RANGE; row++) {
            builder.append(row)
                    .append(HEADER_GAP);
        }

        builder.append(LINE_SEPARATOR);
    }

    private void appendBoardRows(final StringBuilder builder, final Map<Position, PieceInfoDto> pieceByPosition) {
        for (int column = Board.MIN_COLUMN_RANGE; column <= Board.MAX_COLUMN_RANGE; column++) {
            appendBoardRow(builder, column, pieceByPosition);
        }
    }

    private void appendBoardRow(final StringBuilder builder, final int column,
                                final Map<Position, PieceInfoDto> pieceByPosition
    ) {
        builder.append(String.format("%2d ", column));

        for (int row = Board.MIN_ROW_RANGE; row <= Board.MAX_ROW_RANGE; row++) {
            appendRenderedCell(builder, column, row, pieceByPosition);
        }

        builder.append(LINE_SEPARATOR);
    }

    private void appendRenderedCell(
            final StringBuilder builder,
            final int column,
            final int row,
            final Map<Position, PieceInfoDto> pieceByPosition
    ) {
        Position position = Position.of(column, row);
        builder.append(CELL_PADDING)
                .append(renderCell(position, pieceByPosition))
                .append(CELL_PADDING);
    }

    private String renderCell(
            final Position position,
            final Map<Position, PieceInfoDto> pieceByPosition
    ) {
        PieceInfoDto pieceInfo = pieceByPosition.get(position);

        if (pieceInfo == null) {
            return EMPTY_CELL_SYMBOL;
        }

        return colorize(pieceInfo);
    }

    private String colorize(final PieceInfoDto pieceInfo) {
        return colorCodeBy(pieceInfo.team()) + pieceInfo.pieceType() + RESET_CODE;
    }

    private String colorCodeBy(final Team team) {
        if (team == Team.CHO) {
            return BLUE_CODE;
        }

        return RED_CODE;
    }
}
