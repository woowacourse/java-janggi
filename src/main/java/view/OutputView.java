package view;

import java.util.Map;

import dto.BoardDto;
import model.board.Board;

public class OutputView {

    public void startGame() {
        System.out.printf("장기 게임을 시작합니다.%n%n");
    }

    public void board(BoardDto dto) {
        Map<BoardDto.InnerPosition, String> pieces = dto.pieces();

        for (int y = 0; y < Board.HEIGHT_SIZE; y++) {
            StringBuilder line = new StringBuilder();
            line.append(y).append("  ");
            for (int x = 0; x < Board.WIDTH_SIZE; x++) {
                line.append(pieces.getOrDefault(new BoardDto.InnerPosition(x, y), "＿"));
                line.append(" ");
            }
            System.out.println(line);
        }
        System.out.println("     a  b  c  d  e   f   g  h   i");
    }
}
