package janggi.view;

import janggi.constants.Color;
import janggi.dto.BoardDto;
import janggi.dto.PositionPieceDto;

import java.util.List;

public class OutputView {

    public OutputView() {
    }

    public void printBoardMap(BoardDto boardDto) {
        List<String> numbers = List.of("一", "二", "三", "四", "五", "六", "七", "八", "九", "十");
        List<PositionPieceDto> positionPieceDtos = boardDto.positionPieces();
        System.out.println("    一 二 三 四 五 六 七 八 九");
        for (int y = 10; y >= 1; y--) {
            System.out.printf("%s ", numbers.get(y - 1));
            for (int x = 1; x <= 9; x++) {
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
}
