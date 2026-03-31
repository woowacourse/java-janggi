package view;

import domain.board.Board;
import domain.board.ElephantSetup;
import dto.PieceInfoDto;
import dto.PieceInfosDto;
import dto.PieceNameDto;
import dto.PositionDto;
import java.util.List;
import java.util.Map;

public class OutputView {

    private static final String BLUE_CODE = "\u001B[34m";
    private static final String RED_CODE = "\u001B[31m";
    private static final String COLOR_RESET_CODE = "\u001B[0m";

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

    public void printChooseChoElephantSetupPrompt() {
        System.out.println("초나라 플레이어가 사용할 상차림 번호를 입력하세요");
        printElephantSetups();
    }

    public void printChooseHanElephantSetupPrompt() {
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
        Map<PositionDto, PieceNameDto> pieceByPosition = pieceInfos.pieceInfos();

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

    private void appendBoardRows(final StringBuilder builder, final Map<PositionDto, PieceNameDto> pieceByPosition) {
        for (int column = Board.MIN_COLUMN_RANGE; column <= Board.MAX_COLUMN_RANGE; column++) {
            appendBoardRow(builder, column, pieceByPosition);
        }
    }

    private void appendBoardRow(final StringBuilder builder, final int column,
                                final Map<PositionDto, PieceNameDto> pieceByPosition
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
            final Map<PositionDto, PieceNameDto> pieceByPosition
    ) {
        PositionDto position = new PositionDto(column, row);
        builder.append(CELL_PADDING)
                .append(renderCell(position, pieceByPosition))
                .append(CELL_PADDING);
    }

    private String renderCell(
            final PositionDto position,
            final Map<PositionDto, PieceNameDto> pieceByPosition
    ) {
        PieceNameDto pieceInfo = pieceByPosition.get(position);

        if (pieceInfo == null) {
            return EMPTY_CELL_SYMBOL;
        }

        return colorize(pieceInfo);
    }

    private String colorize(final PieceNameDto pieceInfo) {
        if (pieceInfo.isCho()) {
            return BLUE_CODE + pieceInfo.displayName() + COLOR_RESET_CODE;
        }
        return RED_CODE + pieceInfo.displayName() + COLOR_RESET_CODE;
    }

    public void printChoosePieceToMovePrompt(List<PieceInfoDto> pieceInfos) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("현재 보드 움직일 기물을 선택하세요:")
                .append(LINE_SEPARATOR);

        for (int index = 0; index < pieceInfos.size(); index++) {
            String pieceName = pieceInfos.get(index).pieceName();
            PositionDto position = pieceInfos.get(index).position();
            prompt.append(index + 1).append(". ")
                    .append(pieceName).append("(")
                    .append(position.column()).append(", ").append(position.row()).append(")  ");
        }

        System.out.println(prompt);
    }

    public void printChoosePositionToMovePrompt(List<PositionDto> movablePositions) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("해당 기물이 이동할 위치의 번호를 입력하세요:")
                .append(LINE_SEPARATOR);

        for (int index = 0; index < movablePositions.size(); index++) {
            PositionDto position = movablePositions.get(index);
            prompt.append(index + 1).append(". (")
                    .append(position.column()).append(", ")
                    .append(position.row()).append(") ");
        }
        System.out.println(prompt);
    }

    public void printNoMovablePiecePrompt() {
        System.out.println("현재 움직일 수 있는 기물이 없습니다. 한 턴 쉽니다.");
    }

    public void printExceptionMessage(String exceptionMessage) {
        System.out.println("[ERROR] " + exceptionMessage);
    }
}
