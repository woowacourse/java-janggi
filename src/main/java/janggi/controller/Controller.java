package janggi.controller;

import janggi.domain.Board;
import janggi.domain.BoardFactory;
import janggi.domain.Team;
import janggi.dto.BoardDto;
import janggi.exception.BusinessException;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.InputView.MoveCommand;
import janggi.view.OutputView;

public class Controller {
    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiService janggiService;

    public Controller(InputView inputView, OutputView outputView, JanggiService janggiService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiService = janggiService;
    }

    public void run() {
        while (handleMenuSelection()) {
        }
    }

    private boolean handleMenuSelection() {
        outputView.printMenu();

        String command = inputView.readMenuCommand();

        if (command.equals("3")) {
            return false;
        }

        startNewGame();
        return true;
    }

    private void startNewGame() {
        outputView.printStartMessage();
        int gameId = janggiService.createNewGame();
        Board board = new Board(BoardFactory.generate());
        playGame(gameId, board);
    }

    private void playGame(int gameId, Board board) {
        Team currentTeam = Team.CHO;

        while (!isGameOver(board)) {
            printCurrentState(board);
            playTurn(gameId, board, currentTeam);
            currentTeam = currentTeam.switchTeam();
        }

        printFinalResult(board);
    }

    private void printCurrentState(Board board) {
        outputView.printBoard(BoardDto.from(board));
        double choScore = board.calculateScore(Team.CHO);
        double hanScore = board.calculateScore(Team.HAN);
        outputView.printScore(choScore, hanScore);
    }

    private void playTurn(int gameId, Board board, Team team) {
        boolean isSuccess = false;

        while (!isSuccess) {
            isSuccess = attemptMove(gameId, board, team);
        }

        if (board.isKingCaptured()) {
            return;
        }

        notifyAnyCheck(board);
    }

    private boolean attemptMove(int gameId, Board board, Team team) {
        try {
            MoveCommand command = inputView.readMoveCommand(team.getTeam());
            janggiService.movePiece(gameId, board, command.fromPosition(), command.toPosition(), team);
            return true;
        } catch (BusinessException e) {
            outputView.printErrorMessage(e.getMessage());
            return false;
        }
    }

    private void notifyAnyCheck(Board board) {
        if (board.isCheck(Team.HAN)) {
            outputView.printCheckMessage(Team.HAN);
        }

        if (board.isCheck(Team.CHO)) {
            outputView.printCheckMessage(Team.CHO);
        }
    }

    private void printFinalResult(Board board) {
        outputView.printBoard(BoardDto.from(board));
        Team winner = board.getWinner();
        double choScore = board.calculateScore(Team.CHO);
        double hanScore = board.calculateScore(Team.HAN);
        outputView.printFinalResult(winner, choScore, hanScore);
    }

    private boolean isGameOver(Board board) {
        return board.isKingCaptured();
    }
}
