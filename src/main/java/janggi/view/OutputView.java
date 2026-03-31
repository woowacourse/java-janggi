package janggi.view;

import janggi.constants.Color;
import janggi.dto.BoardDto;
import janggi.dto.PositionPieceDto;
import java.util.List;

public class OutputView {
    private static final int MIN_X = 1;
    private static final int MAX_X = 9;
    private static final int MIN_Y = 1;
    private static final int MAX_Y = 10;

    public void printBoardMap(BoardDto boardDto) {
        List<String> numbers = List.of("一", "二", "三", "四", "五", "六", "七", "八", "九", "十");
        List<PositionPieceDto> positionPieceDtos = boardDto.positionPieces();
        System.out.println("    一 二 三 四 五 六 七 八 九");
        for (int y = MAX_Y; y >= MIN_Y; y--) {
            System.out.printf("%s ", numbers.get(y - 1));
            for (int x = MIN_X; x <= MAX_X; x++) {
                String piece = "一";
                String teamColor = Color.RESET;
                for (PositionPieceDto positionPieceDto : positionPieceDtos) {
                    if (positionPieceDto.isSame(x, y)) {
                        piece = positionPieceDto.pieceName();
                        if (positionPieceDto.teamName().equals("CHO")) {
                            teamColor = Color.GREEN;
                        }

                        if (positionPieceDto.teamName().equals("HAN")) {
                            teamColor = Color.RED;
                        }
                        break;
                    }
                }
                System.out.printf(teamColor + " %s" + Color.RESET, piece);
            }
            System.out.println();
        }
    }

    public void printErrorMessage(String message) {
        System.out.println();
        System.out.println(message);
    }
}
