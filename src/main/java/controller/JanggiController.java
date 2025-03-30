package controller;

import domain.JanggiGame;
import domain.piece.Team;
import domain.score.Score;
import service.JanggiDaoService;
import util.ErrorHandler;
import view.InputView;
import view.MoveCommand;
import view.OutputView;
import view.ProgressCommand;
import view.SangMaOrderCommand;

import java.util.Map;

public class JanggiController {

    private final JanggiDaoService janggiDaoService;

    public JanggiController(JanggiDaoService janggiDaoService) {
        this.janggiDaoService = janggiDaoService;
    }

    public void run() {
        OutputView.printStart();
        JanggiGame janggiGame = createJanggiGame();
        play(janggiGame);
    }

    private JanggiGame createJanggiGame() {
        if (janggiDaoService.hasSavedGame() && InputView.selectLoadGame()) {
            return new JanggiGame(janggiDaoService.findBoard(), janggiDaoService.findTurn());
        }
        SangMaOrderCommand hanSangMaOrderCommand = createSangMaOrderCommandByTeam(Team.HAN);
        SangMaOrderCommand choSangMaOrderCommand = createSangMaOrderCommandByTeam(Team.CHO);
        return new JanggiGame(hanSangMaOrderCommand, choSangMaOrderCommand);
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
                janggiDaoService.removeAllData();
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
            janggiDaoService.removeAllData();
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
