package controller;

import common.exception.JanggiException;
import domain.board.Formation;
import domain.board.JanggiBoard;
import domain.board.JanggiIntersectionGenerator;
import domain.board.dto.JanggiBoardView;
import domain.piece.Team;
import domain.point.Command;
import view.InputReader;
import view.OutputWriter;

import java.util.function.Supplier;

public class JanggiGame {

    private final InputReader reader;
    private final OutputWriter writer;

    public JanggiGame(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public void run() {
        JanggiBoard janggiBoard = generateJanggiBoard();
        startGame(janggiBoard);
        announceWinner(janggiBoard.getWinner());
    }

    private JanggiBoard generateJanggiBoard() {
        Formation hanFormation = requestFormation(Team.HAN);
        Formation choFormation = requestFormation(Team.CHO);
        return new JanggiBoard(new JanggiIntersectionGenerator(hanFormation, choFormation));
    }

    private Formation requestFormation(Team team) {
        return retry(() -> {
            return reader.requestFormation(team);
        });
    }

    private void startGame(JanggiBoard janggiBoard) {
        Team currentTeam = Team.HAN;
        printJanggiBoard(JanggiBoardView.from(janggiBoard));
        progressGame(janggiBoard, currentTeam);
    }

    private void progressGame(JanggiBoard janggiBoard, Team currentTeam) {
        while (!janggiBoard.isGameOver()) {
            progressTurn(janggiBoard, currentTeam);
            currentTeam = currentTeam.nextTurn();
        }
    }

    public void progressTurn(JanggiBoard janggiBoard, Team currentTeam) {
        retry(() -> {
            Command command = requestCommand(currentTeam);
            janggiBoard.tryToMove(command.start(), command.end(), currentTeam);
            printJanggiBoard(JanggiBoardView.from(janggiBoard));
        });
    }

    private void printJanggiBoard(JanggiBoardView boardView) {
        writer.printJanggiBoard(boardView);
    }

    private Command requestCommand(Team team) {
        return reader.requestCommand(team);
    }

    public void announceWinner(Team team) {
        writer.printWinner(team);
    }

    private void retry(Runnable action) {
        try {
            action.run();
        } catch (JanggiException e) {
            writer.printErrorMessage(e.getMessage());
            retry(action);
        }
    }

    private <T> T retry(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (JanggiException e) {
            writer.printErrorMessage(e.getMessage());
            return retry(supplier);
        }
    }

}

