package service;

import board.Board;
import board.Position;
import board.Turn;
import dto.BoardDto;
import dto.TeamDto;
import piece.Piece;

public class JanggiService {

    private Board board;
    private Turn turn;

    public void startGame() {
        board = Board.generate();
        turn = Turn.start();
    }

    public BoardDto getBoard() {
        return BoardDto.from(board);
    }

    public TeamDto currentTurn() {
        return TeamDto.from(turn.getCurrentTeam());
    }

    public void move(Position source, Position destination) {
        Piece piece = board.get(source);
        piece.move(board, turn.getCurrentTeam(), destination.x() - source.x(), destination.y() - source.y());
    }

    public boolean isPlaying() {
        return board.getWinnerIfGameOver() == null;
    }

    public void nextTurn() {
        turn.next();
    }

    public TeamDto getWinner() {
        return TeamDto.from(board.getWinnerIfGameOver());
    }

    public void abstain() {
        board.abstain(turn.getCurrentTeam());
    }
}
