package janggi.view;

import janggi.constants.Color;
import janggi.domain.board.BoardRange;
import janggi.dto.BoardDto;
import janggi.dto.PositionPieceDto;
import java.util.List;

public class OutputView {
    private static final String EMPTY_PIECE = "一";
    private static final String BOARD_HEADER = "    一 二 三 四 五 六 七 八 九";
    private static final List<String> ROW_LABELS = List.of("一", "二", "三", "四", "五",
            "六", "七", "八", "九", "十");

    public void printBoardMap(BoardDto boardDto) {
        List<PositionPieceDto> positionPieceDtos = boardDto.positionPieces();

        System.out.println(BOARD_HEADER);
        for (int y = BoardRange.MAX_Y; y >= BoardRange.MIN_Y; y--) {
            printRow(y, positionPieceDtos);
        }
    }

    public void printErrorMessage(String message) {
        System.out.println();
        System.out.println(message);
    }

    public void printScore(double hanScore, double choScore) {
        System.out.println();
        System.out.printf("한 점수: %.1f%n", hanScore);
        System.out.printf("초 점수: %.1f%n", choScore);
    }

    private void printRow(int y, List<PositionPieceDto> positionPieceDtos) {
        System.out.printf("%s ", ROW_LABELS.get(y - 1));
        for (int x = BoardRange.MIN_X; x <= BoardRange.MAX_X; x++) {
            printPieceAt(x, y, positionPieceDtos);
        }
        System.out.println();
    }

    private void printPieceAt(int x, int y, List<PositionPieceDto> positionPieceDtos) {
        PositionPieceDto positionPieceDto = findPieceAt(x, y, positionPieceDtos);
        if (positionPieceDto == null) {
            System.out.printf(" %s", EMPTY_PIECE);
            return;
        }
        System.out.printf(resolveColor(positionPieceDto.teamName()) + " %s" + Color.RESET,
                positionPieceDto.pieceName());
    }

    private PositionPieceDto findPieceAt(int x, int y, List<PositionPieceDto> positionPieceDtos) {
        for (PositionPieceDto positionPieceDto : positionPieceDtos) {
            if (positionPieceDto.isSame(x, y)) {
                return positionPieceDto;
            }
        }
        return null;
    }

    private String resolveColor(String teamName) {
        if (teamName.equals("CHO")) {
            return Color.GREEN;
        }
        if (teamName.equals("HAN")) {
            return Color.RED;
        }
        return Color.RESET;
    }
}
