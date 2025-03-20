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

    public void abstain() {
        board.abstain(currentTurn);
    }
}
