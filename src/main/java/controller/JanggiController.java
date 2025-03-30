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
        JanggiGame janggiGame = initializeJanggiGame();
        play(janggiGame);
    }

    private JanggiGame initializeJanggiGame() {
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
            printBoardAndTurn(janggiGame);
            ProgressCommand progressCommand = ErrorHandler.retryUntilSuccess(InputView::inputProgress);
            if (progressCommand == ProgressCommand.MOVE) {
                isPlayable = executeMove(janggiGame);
            }
            if (progressCommand == ProgressCommand.STATUS) {
                printScore(janggiGame);
                continue;
            }
            if (progressCommand == ProgressCommand.SAVE) {
                executeSave(janggiGame);
                break;
            }
            if (progressCommand == ProgressCommand.EXIT) {
                executeExit(janggiGame);
                break;
            }
        }
    }

    private void printBoardAndTurn(final JanggiGame janggiGame) {
        OutputView.printBoard(janggiGame.board());
        OutputView.printTurn(janggiGame.turnTeam());
    }

    private boolean executeMove(final JanggiGame janggiGame) {
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

    private void executeSave(final JanggiGame janggiGame) {
        OutputView.printSaveResult();
        janggiDaoService.saveAllData(janggiGame.board(), janggiGame.turnTeam());
    }

    private void printScore(final JanggiGame janggiGame) {
        Map<Team, Score> totalScoreByTeam = janggiGame.calculateTotalScoreByTeam();
        OutputView.printScore(totalScoreByTeam);
    }

    private void executeExit(final JanggiGame janggiGame) {
        printScore(janggiGame);
        OutputView.printMatchResult(janggiGame.findWinTeam());
        OutputView.printExit();
        janggiDaoService.removeAllData();
    }
}
