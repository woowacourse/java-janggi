package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.turn.ChoActionTurn;
import janggi.domain.turn.PlayerTurn;
import janggi.dto.BoardDto;
import janggi.initializer.BoardInitializer;

public class Game {
    private static final String INVALID_WINNER_SIDE = "잘못된 승자 진영입니다.";

    private PlayerTurn playerTurn;

    public Game(Arrangement choArrangement, Arrangement hanArrangement) {
        this.playerTurn = new ChoActionTurn(new Board(BoardInitializer.createBoard(choArrangement, hanArrangement)));
    }

    public void move(Position start, Position end) {
        playerTurn = playerTurn.move(start, end);
    }

    public boolean isFinished() {
        return playerTurn.isFinished();
    }

    public BoardDto getCurrentBoardDto() {
        return new BoardDto(playerTurn.getCurrentBoard());
    }

    public Side getCurrentSide() {
        return playerTurn.getCurrentSide();
    }

    public Side getWinnerSide() {
        Side winnerSide = playerTurn.getWinnerSide();
        if (winnerSide.equals(Side.EMPTY)) {
            throw new IllegalStateException(INVALID_WINNER_SIDE);
        }
        return winnerSide;
    }
}
