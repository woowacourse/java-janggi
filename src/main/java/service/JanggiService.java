package service;

import dto.BoardDto;
import dto.TeamDto;
import model.Position;
import model.Team;
import model.board.Board;
import model.piece.Piece;

public class JanggiService {
    private Team currentTurn;

    private Board board;

    public void startGame() {
        board = Board.generate();
        currentTurn = Team.CHO;
    }

    public BoardDto getBoard() {
        return BoardDto.from(board);
    }

    public TeamDto currentTurn() {
        return TeamDto.from(currentTurn);
    }

    public void move(Position source, Position destination) {
        Piece piece = board.get(source);
        piece.move(board, currentTurn, destination.x() - source.x(), destination.y() - source.y());
    }

    public boolean isPlaying() {
        return board.getWinnerIfGameOver() == null;
    }

    public void nextTurn() {
        currentTurn = currentTurn.nextTurn();
    }

    public TeamDto getWinner() {
        return TeamDto.from(board.getWinnerIfGameOver());
    }
}

/**
 * TODO
 * 2. MVC 조립(턴마다 현재 팀 바꿔서 출력, 자기 팀 기물만 움직일 수 있게. 게임종료처리
 * 3. 프로덕션 코드 리팩토링
 * 4. 테스트 코드 리팩토링
 */
