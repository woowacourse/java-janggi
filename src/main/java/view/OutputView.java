package view;

import dto.JanggiGameDto;
import dto.PieceInfoDto;
import dto.PiecePositionDto;
import dto.PiecesDto;
import dto.PositionDto;
import dto.ScoreDto;
import dto.TeamNameDto;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import view.formatter.ElephantSetupFormatter;
import view.formatter.PieceFormatter;
import view.formatter.TeamNameFormatter;

public class OutputView {

    private static final String EXCEPTION_PREFIX = "[ERROR] ";

    private static final String EMPTY_CELL_SYMBOL = "口";
    private static final String HEADER_PREFIX = "    ";
    private static final String CELL_PADDING = " ";
    private static final String HEADER_GAP = " \u3000";

    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final int PROMPT_ITEMS_PER_LINE = 5;

    private static final int MIN_COLUMN_RANGE = 1;
    private static final int MAX_COLUMN_RANGE = 10;
    private static final int MIN_ROW_RANGE = 1;
    private static final int MAX_ROW_RANGE = 9;

    public void printPlayNewGameOrPreviousGame() {
        System.out.println("새로운 장기 게임을 생성할까요? (1: 새 게임 생성, 2: 게임 불러오기)");
    }

    public void printChoosePreviousGameId(List<JanggiGameDto> previousGames) {
        System.out.println("플레이 할 게임 id를 입력하세요");
        System.out.println("id\t현재 턴\t승자");
        for (JanggiGameDto janggiGame : previousGames) {
            System.out.printf("#%d\t%s나라", janggiGame.id(), TeamNameFormatter.format(janggiGame.currentTurn()));
            if (janggiGame.winnerTeam() == null || janggiGame.winnerTeam().isBlank()) {
                System.out.println();
                continue;
            }
            System.out.printf("\t%s\n", janggiGame.winnerTeam());
        }
    }

    public void printChooseElephantSetupPrompt(final List<String> elephantSetupNames, final TeamNameDto teamName) {
        String formattedName = TeamNameFormatter.format(teamName.name());
        System.out.printf("%s나라 플레이어가 사용할 상차림 번호를 입력하세요:\n", formattedName);
        printElephantSetups(elephantSetupNames);
    }

    private void printElephantSetups(final List<String> elephantSetupNames) {
        StringBuilder promptBuilder = new StringBuilder();

        for (int index = 0; index < elephantSetupNames.size(); index++) {
            String elephantSetup = ElephantSetupFormatter.format(elephantSetupNames.get(index));
            promptBuilder.append(toOneBasedIndex(index))
                    .append(". ")
                    .append(elephantSetup)
                    .append(" ");
        }

        System.out.println(promptBuilder);
    }

    public void printJanggiBoard(final PiecesDto piecesDto) {
        StringBuilder promptBuilder = new StringBuilder();

        appendRowHeader(promptBuilder);
        appendBoard(promptBuilder, piecesDto.pieces());

        System.out.println(promptBuilder);
    }

    private void appendRowHeader(final StringBuilder promptBuilder) {
        promptBuilder.append(HEADER_PREFIX);

        for (int row = MIN_ROW_RANGE; row <= MAX_ROW_RANGE; row++) {
            promptBuilder.append(row)
                    .append(HEADER_GAP);
        }

        promptBuilder.append(LINE_SEPARATOR);
    }

    private void appendBoard(
            final StringBuilder promptBuilder,
            final Map<PositionDto, PieceInfoDto> pieces
    ) {
        for (int column = MIN_COLUMN_RANGE; column <= MAX_COLUMN_RANGE; column++) {
            appendBoardRow(promptBuilder, column, pieces);
        }
    }

    private void appendBoardRow(
            final StringBuilder promptBuilder,
            final int column,
            final Map<PositionDto, PieceInfoDto> pieceInfos
    ) {
        promptBuilder.append(String.format("%2d ", column));

        for (int row = MIN_ROW_RANGE; row <= MAX_ROW_RANGE; row++) {
            appendRenderedCell(promptBuilder, column, row, pieceInfos);
        }

        promptBuilder.append(LINE_SEPARATOR);
    }

    private void appendRenderedCell(
            final StringBuilder promptBuilder,
            final int column,
            final int row,
            final Map<PositionDto, PieceInfoDto> pieceInfos
    ) {
        promptBuilder.append(CELL_PADDING)
                .append(renderCell(new PositionDto(column, row), pieceInfos))
                .append(CELL_PADDING);
    }

    private String renderCell(
            final PositionDto position,
            final Map<PositionDto, PieceInfoDto> pieceInfos
    ) {
        PieceInfoDto pieceInfo = pieceInfos.get(position);
        if (pieceInfo == null) {
            return EMPTY_CELL_SYMBOL;
        }

        return PieceFormatter.format(pieceInfo.pieceType(), pieceInfo.team());
    }

    public void printChoosePieceToMovePrompt(final List<PiecePositionDto> piecePositions) {
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("움직일 기물을 선택하세요:")
                .append(LINE_SEPARATOR);

        for (int index = 0; index < piecePositions.size(); index++) {
            PiecePositionDto piecePosition = piecePositions.get(index);
            String formattedPiece = PieceFormatter.format(piecePosition.pieceType(), piecePosition.team());
            PositionDto position = piecePosition.position();

            promptBuilder.append(toOneBasedIndex(index)).append(". ")
                    .append(formattedPiece).append("(")
                    .append(position.column()).append(", ").append(position.row()).append(")  ");

            appendPromptLineSeparator(promptBuilder, toOneBasedIndex(index));
        }

        System.out.println(promptBuilder);
    }

    public void printChoosePositionToMovePrompt(final List<PositionDto> movablePositions) {
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("이동할 위치를 선택하세요:")
                .append(LINE_SEPARATOR);

        for (int index = 0; index < movablePositions.size(); index++) {
            PositionDto position = movablePositions.get(index);
            promptBuilder.append(toOneBasedIndex(index)).append(". (")
                    .append(position.column()).append(", ")
                    .append(position.row()).append(") ");

            appendPromptLineSeparator(promptBuilder, toOneBasedIndex(index));
        }

        System.out.println(promptBuilder);
    }

    private void appendPromptLineSeparator(final StringBuilder promptBuilder, final int itemCount) {
        if (itemCount % PROMPT_ITEMS_PER_LINE == 0) {
            promptBuilder.append(LINE_SEPARATOR);
        }
    }

    public void printExceptionMessage(final String exceptionMessage) {
        System.out.println(EXCEPTION_PREFIX + exceptionMessage);
    }

    private int toOneBasedIndex(int index) {
        return index + 1;
    }

    public void printScores(ScoreDto score) {
        DecimalFormat decimalFormat = new DecimalFormat("0.################");
        String choScore = decimalFormat.format(score.choScore());
        String hanScore = decimalFormat.format(score.hanScore());

        System.out.printf("초나라 점수: %s점\n", choScore);
        System.out.printf("한나라 점수: %s점\n", hanScore);
    }

    public void printWinner(TeamNameDto winner) {
        System.out.printf("%s나라 플레이어 승리!\n", TeamNameFormatter.format(winner.name()));
    }
}
