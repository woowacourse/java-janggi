package janggi.controller;

import janggi.domain.Board;
import janggi.domain.JanggiGame;
import janggi.domain.dto.MoveCommand;
import janggi.domain.piece.Team;
import janggi.domain.vo.Position;
import janggi.view.InputView;

public class JanggiController {

    private final InputView inputView = new InputView();

    public void run() {
        Board board = new Board();

        JanggiGame janggiGame = new JanggiGame();

        while (!janggiGame.isFinished()) {
            Team currentTeam = janggiGame.findCurrentTeam();
            attemptMove(board, currentTeam);
            janggiGame.changeTurn();
        }
    }

    private void attemptMove(Board board, Team currentTeam) {
        boolean moved = false;
        while (!moved) {
            moved = tryMove(board, currentTeam);
        }
    }

    private boolean tryMove(Board board, Team currentTeam) {
        try {
            MoveCommand moveCommand = inputView.readMovePositions();
            Position from = moveCommand.getFrom();
            Position to = moveCommand.getTo();
            board.move(from, to, currentTeam);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
