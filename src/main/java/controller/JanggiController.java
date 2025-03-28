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
        SangMaOrderCommand hanSangMaOrderCommand = createSangMaOrderCommandByTeam(Team.HAN);
        SangMaOrderCommand choSangMaOrderCommand = createSangMaOrderCommandByTeam(Team.CHO);
        JanggiGame janggiGame = new JanggiGame(new BoardGenerator(), hanSangMaOrderCommand, choSangMaOrderCommand,
                                                new Turn(), new ScoreCalculator());
        play(janggiGame);
    }

    private SangMaOrderCommand createSangMaOrderCommandByTeam(final Team team) {
        return ErrorHandler.retryUntilSuccess(() -> InputView.inputSangMaOrder(team));
    }

    private void play(final JanggiGame janggiGame) {
        boolean isPlayable = true;
        while (isPlayable) {
            OutputView.printBoard(janggiGame.board());
            OutputView.printTurn(janggiGame.turnTeam());
            ProgressCommand progressCommand = ErrorHandler.retryUntilSuccess(InputView::inputProgress);
            if (progressCommand == ProgressCommand.MOVE) {
                isPlayable = move(janggiGame);
            }
            if (progressCommand == ProgressCommand.STATUS) {
                printStatus(janggiGame);
                continue;
            }
            if (progressCommand == ProgressCommand.EXIT) {
                printResult(janggiGame);
                break;
            }
        }
    }

    private boolean move(final JanggiGame janggiGame) {
        ErrorHandler.retryUntilSuccess(() -> {
            MoveCommand moveCommand = InputView.inputMoveCommand();
            janggiGame.movePiece(moveCommand);
        });
        if (janggiGame.isStop()) {
            OutputView.printBoard(janggiGame.board());
            OutputView.printMatchResult(janggiGame.turnTeam());
            return false;
        }
        janggiGame.changeTurn();
        return true;
    }

    private void printStatus(final JanggiGame janggiGame) {
        Map<Team, Score> totalScoreByTeam = janggiGame.calculateTotalScoreByTeam();
        OutputView.printScore(totalScoreByTeam);
    }

    private void printResult(JanggiGame janggiGame) {
        printStatus(janggiGame);
        OutputView.printMatchResult(janggiGame.findWinTeam());
        OutputView.printExit();
    }
}
