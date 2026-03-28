package controller;

import domain.board.Formation;
import domain.board.JanggiBoard;
import domain.board.JanggiIntersectionGenerator;
import domain.board.dto.JanggiBoardView;
import domain.piece.Team;
import domain.point.Command;
import view.InputReader;
import view.OutputWriter;

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
        Formation hanFormation = reader.requestFormation(Team.HAN);
        Formation choFormation = reader.requestFormation(Team.CHO);
        return new JanggiBoard(new JanggiIntersectionGenerator(hanFormation, choFormation));
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

    public void progressTurn(JanggiBoard janggiBoard, Team team) {
        retry(() -> {
            Command command = requestCommand(team);
            janggiBoard.tryToMove(command.start(), command.end(), team);
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
        } catch (RuntimeException e) {
            System.out.println("[ERROR] " + e.getMessage());
            retry(action);
        }
    }

}

