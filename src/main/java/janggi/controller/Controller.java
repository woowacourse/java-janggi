package janggi.controller;

import janggi.domain.Board;
import janggi.domain.BoardFactory;
import janggi.domain.Column;
import janggi.domain.Position;
import janggi.domain.Row;
import janggi.domain.Team;
import janggi.dto.BoardDto;
import janggi.exception.BusinessException;
import janggi.view.InputView;
import janggi.view.OutputView;

import java.util.List;

public class Controller {
    private final InputView inputView;
    private final OutputView outputView;

    public Controller(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Board board = initializeGame();
        playGame(board);
    }

    private Board initializeGame() {
        outputView.printStartMessage();
        Board board = new Board(BoardFactory.generate());
        outputView.printBoard(BoardDto.from(board));
        return board;
    }

    private void playGame(Board board) {
        Team currentTeam = Team.CHO;

        for (int i = 0; i < 10; i++) {
            processTurn(board, currentTeam);
            currentTeam = currentTeam.switchTeam();
        }
    }

    private void processTurn(Board board, Team team) {
        while (true) {
            try {
                List<Integer> positions = inputView.playTurn(team.getTeam());
                Position from = Position.of(Row.of(positions.get(0)), Column.of(positions.get(1)));
                Position to = Position.of(Row.of(positions.get(2)), Column.of(positions.get(3)));

                board.move(from, to);
                outputView.printBoard(BoardDto.from(board));

                break;

            } catch (BusinessException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
