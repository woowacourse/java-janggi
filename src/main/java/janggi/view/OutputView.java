package janggi.view;

import janggi.domain.Position;
import janggi.domain.PieceVO;
import janggi.util.PieceLabelMapper;
import janggi.dto.BoardDTO;
import java.util.Map;

public class OutputView {

    private static final String EMPTY_CELL = "　　";
    private static final String COLUMN_INDEXES = "　　║　　0　　　　1　　　　2　　　　3　　　　4　　　　5　　　　6　　　　7　　　　8";
    private static final String DIVIDER = "　　║===============================================================";
    private static final String VERTICAL_LINE = "　　║　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃";

    public void printBoardStatus(BoardDTO boardDto) {
        printLine(COLUMN_INDEXES);
        printLine(DIVIDER);
        for (int row = 0; row < 10; row++) {
            renderRow(row, boardDto.piecePosition());
            renderVerticalLine(row);
        }
    }

    private void renderRow(int row, Map<Position, PieceVO> status) {
        StringBuilder sb = new StringBuilder(toFullWidthRow(row) + "　║");
        for (int col = 0; col < 9; col++) {
            sb.append("［").append(getLabel(status, row, col)).append("］");
            if (col < 8) sb.append("━");
        }
        printLine(sb.toString());
    }

    private String getLabel(Map<Position, PieceVO> status, int r, int c) {
        PieceVO vo = status.get(new Position(r, c));
        return vo == null ? EMPTY_CELL : PieceLabelMapper.toFullWidth(vo);
    }

    private void renderVerticalLine(int row) {
        if (row < 9) printLine(VERTICAL_LINE);
    }

    private String toFullWidthRow(int i) {
        return String.valueOf((char) ('０' + i));
    }

    public void printLine(String message) {
        System.out.println(message);
    }

    private void printNewLine() {
        System.out.println();
    }

    public void printPlayerNameNotice(String sideName) {
        printLine(String.format(Message.PLAYER_NAME_NOTICE, sideName)); // 기존 규격 유지
    }
}
