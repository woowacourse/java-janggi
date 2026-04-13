package janggi.controller;

import janggi.domain.dto.MoveCommand;
import janggi.domain.piece.Team;
import janggi.repositiory.game.GameRepository;
import janggi.repositiory.piece.PieceRepository;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiController {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final JanggiService janggiService;

    public JanggiController(GameRepository gameRepository, PieceRepository pieceRepository) {
        this.janggiService = JanggiService.startGame(gameRepository, pieceRepository);
    }

    public void run() {
        printIntroMessage();

        while (!janggiService.isFinished()) {
            Team currentTeam = janggiService.getCurrentTeam();
            TurnCommand command = TurnCommand.fromOption(inputView.readTurnBehavior(currentTeam));

            if (command == TurnCommand.PLAY) {
                playTurn(currentTeam);
            }

            if (command == TurnCommand.SKIP) {
                skipTurn();
            }

            if (command == TurnCommand.RESIGN) {
                resignTrun(currentTeam);
            }
        }

        outputView.printWinner(janggiService.decideWinner());
    }

    private void resignTrun(Team currentTeam) {
        janggiService.resign();
        outputView.printResign(currentTeam);
    }

    private void skipTurn() {
        janggiService.skipTurn();
        outputView.skipTurn();
    }

    private void playTurn(Team currentTeam) {
        MoveCommand moveCommand = inputView.readMovePositions(currentTeam);
        janggiService.playTurn(moveCommand);
        outputView.printBoard(janggiService.getBoard());
    }

    private void printIntroMessage() {
        if (janggiService.isResumed()) {
            outputView.printResumed();
        } else {
            outputView.printIntroduce();
        }
        
        outputView.printBoard(janggiService.getBoard());
    }
}


