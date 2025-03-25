package service;

import dto.BoardDto;
import dto.TeamDto;
import model.Position;
import model.Team;
import model.board.Board;
import model.board.TableSetting;

public class JanggiService {

    private Board board;
    private Team currentTurn;

    public void startGame() {
        board = new Board();
        currentTurn = Team.CHO;
    }

    public void tableSettingForCurrentTurn(TableSetting tableSetting) {
        board.addTeamPieces(currentTurn, tableSetting);
    }

    public BoardDto getBoard() {
        return BoardDto.from(board);
    }

    public TeamDto currentTurn() {
        return TeamDto.from(currentTurn);
    }

    public void move(Position source, Position destination) {
        board.movePiece(source, destination, currentTurn);
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
