package controller;

import domain.JanggiGame;
import domain.Turn;
import domain.board.BoardGenerator;
import domain.piece.Team;
import domain.score.Score;
import domain.score.ScoreCalculator;
import util.ErrorHandler;
import view.InputView;
import view.MoveCommand;
import view.OutputView;
import view.ProgressCommand;
import view.SangMaOrderCommand;

import java.util.Map;

public class JanggiController {

    public void run() {
        OutputView.printStart();
        ProgressCommand progressCommand = ErrorHandler.retryUntilSuccess(InputView::inputProgress);
        if (progressCommand == ProgressCommand.START) {
            SangMaOrderCommand hanSangMaOrderCommand = createSangMaOrderCommandByTeam(Team.HAN);
            SangMaOrderCommand choSangMaOrderCommand = createSangMaOrderCommandByTeam(Team.CHO);
            JanggiGame janggiGame = new JanggiGame(new BoardGenerator(), hanSangMaOrderCommand, choSangMaOrderCommand,
                                                    new Turn(), new ScoreCalculator());
            start(janggiGame);
        }
    }

    private void start(JanggiGame janggiGame) {
        while (true) {
            OutputView.printBoard(janggiGame.board());
            OutputView.printTurn(janggiGame.turnTeam());
            ProgressCommand progressCommand = ErrorHandler.retryUntilSuccess(InputView::inputProgress);

            if (progressCommand == ProgressCommand.MOVE) {
                move(janggiGame);
                if (janggiGame.isStop()) {
                    OutputView.printBoard(janggiGame.board());
                    OutputView.printMatchResult(janggiGame.turnTeam());
                    break;
                }
                janggiGame.changeTurn();
            }

            if (progressCommand == ProgressCommand.STATUS) {
                printStatus(janggiGame);
                continue;
            }

            if (progressCommand == ProgressCommand.EXIT) {
                printStatus(janggiGame);
                OutputView.printMatchResult(janggiGame.findWinTeam());
                OutputView.printExit();
                break;
            }
        }
    }

    private void move(JanggiGame janggiGame) {
        ErrorHandler.retryUntilSuccess(() -> {
            MoveCommand moveCommand = InputView.inputMoveCommand(janggiGame.turnTeam());
            janggiGame.movePiece(moveCommand);
        });
    }

    private SangMaOrderCommand createSangMaOrderCommandByTeam(final Team team) {
        return ErrorHandler.retryUntilSuccess(() -> InputView.inputSangMaOrder(team));
    }

    private void printStatus(JanggiGame janggiGame) {
        Map<Team, Score> totalScoreByTeam = janggiGame.calculateTotalScoreByTeam();
        OutputView.printScore(totalScoreByTeam);
    }
}
