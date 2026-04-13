package janggi.controller;

import janggi.domain.Board;
import janggi.domain.BoardFactory;
import janggi.domain.Team;
import janggi.dto.BoardDto;
import janggi.exception.BusinessException;
import janggi.view.InputView;
import janggi.view.InputView.MoveCommand;
import janggi.view.OutputView;

public class Controller {
    private final InputView inputView;
    private final OutputView outputView;

    public Controller(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        while (handleMenuSelection()) {
        }
    }

    private boolean handleMenuSelection() {
        outputView.printMenu();

        String command = inputView.readMenuCommand();

        if (command.equals("2")) {
            return false;
        }

        startNewGame();
        return true;
    }

    private void startNewGame() {
        outputView.printStartMessage();
        Board board = new Board(BoardFactory.generate());
        playGame(board);
    }

    private void playGame(Board board) {
        Team currentTeam = Team.CHO;

        while (!isGameOver(board)) {
            outputView.printBoard(BoardDto.from(board));

            double choscore = board.calculateScore(Team.CHO);
            double hanscore = board.calculateScore(Team.HAN);
            outputView.printScore(choscore, hanscore);

            playTurn(board, currentTeam);
            currentTeam = currentTeam.switchTeam();
        }
    }

    private void playTurn(Board board, Team team) {
        boolean isSuccess = false;

        while (!isSuccess) {
            isSuccess = attemptMove(board, team);
        }
    }

    private boolean attemptMove(Board board, Team team) {
        try {
            MoveCommand command = inputView.readMoveCommand(team.getTeam());
            board.move(command.fromPosition(), command.toPosition());
            return true;
        } catch (BusinessException e) {
            outputView.printErrorMessage(e.getMessage());
            return false;
        }
    }

    private boolean isGameOver(Board board) {
        // TODO: 왕(궁)이 잡혔는지 판별하는 로직 추가
        return false;
    }
}
