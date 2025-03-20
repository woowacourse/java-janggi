package service;

import dto.BoardDto;
import model.board.Board;

public class JanggiService {

    private Board board;

    public void startGame() {
        board = Board.generate();
    }

    public BoardDto getBoard() {
        return BoardDto.from(board);
    }
}

/**
 * TODO
 * 1. 하던거: 장기판 출력
 * 2. MVC 조립
 * 3. 프로덕션 코드 리팩토링
 * 4. 테스트 코드 리팩토링
 */
