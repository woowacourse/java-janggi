package controller;

import domain.JanggiGame;
import domain.piece.Team;
import domain.score.Score;
import service.JanggiDaoService;
import util.ErrorHandler;
import view.InputView;
import view.command.MoveCommand;
import view.OutputView;
import view.command.ProgressCommand;
import view.command.SangMaOrderCommand;

import java.util.Map;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiDaoService janggiDaoService;

    public JanggiController(InputView inputView, OutputView outputView, JanggiDaoService janggiDaoService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiDaoService = janggiDaoService;
    }

    public void run() {
        outputView.printStart();
        final JanggiGame janggiGame = initializeJanggiGame();
        play(janggiGame);
    }

    private JanggiGame initializeJanggiGame() {
        if (janggiDaoService.hasSavedGame() && inputView.selectLoadGame()) {
            return new JanggiGame(janggiDaoService.findBoard(), janggiDaoService.findTurn());
        }
        final SangMaOrderCommand hanSangMaOrderCommand = createSangMaOrderCommandByTeam(Team.HAN);
        final SangMaOrderCommand choSangMaOrderCommand = createSangMaOrderCommandByTeam(Team.CHO);
        return new JanggiGame(hanSangMaOrderCommand, choSangMaOrderCommand);
    }

    private SangMaOrderCommand createSangMaOrderCommandByTeam(final Team team) {
        return ErrorHandler.retryUntilSuccess(() -> inputView.inputSangMaOrder(team));
    }

    private void play(final JanggiGame janggiGame) {
        boolean isPlayable = true;
        while (isPlayable) {
            printBoardAndTurn(janggiGame);
            final ProgressCommand progressCommand = ErrorHandler.retryUntilSuccess(inputView::inputProgress);
            if (progressCommand.isMove()) {
                isPlayable = executeMove(janggiGame);
            }
            if (progressCommand.isStatus()) {
                printScore(janggiGame);
                continue;
            }
            if (progressCommand.isSave()) {
                executeSave(janggiGame);
                break;
            }
            if (progressCommand.isExit()) {
                executeExit(janggiGame);
                break;
            }
        }
    }

    private void printBoardAndTurn(final JanggiGame janggiGame) {
        outputView.printBoard(janggiGame.board());
        outputView.printTurn(janggiGame.turnTeam());
    }

    private boolean executeMove(final JanggiGame janggiGame) {
        ErrorHandler.retryUntilSuccess(() -> {
            final MoveCommand moveCommand = inputView.inputMoveCommand();
            janggiGame.movePiece(moveCommand);
        });
        if (janggiGame.isStop()) {
            outputView.printBoard(janggiGame.board());
            outputView.printMatchResult(janggiGame.turnTeam());
            janggiDaoService.removeAllData();
            return false;
        }
        janggiGame.changeTurn();
        return true;
    }

    private void printScore(final JanggiGame janggiGame) {
        final Map<Team, Score> totalScoreByTeam = janggiGame.calculateTotalScoreByTeam();
        outputView.printScore(totalScoreByTeam);
    }

    private void executeSave(final JanggiGame janggiGame) {
        outputView.printSaveResult();
        janggiDaoService.saveAllData(janggiGame.board(), janggiGame.turnTeam());
    }

    private void executeExit(final JanggiGame janggiGame) {
        printScore(janggiGame);
        outputView.printMatchResult(janggiGame.findWinTeam());
        outputView.printExit();
        janggiDaoService.removeAllData();
    }
}
