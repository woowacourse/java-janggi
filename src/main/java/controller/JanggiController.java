package controller;

import model.board.ScoreResult;
import model.coordinate.Position;
import model.formation.JanggiFormation;
import model.game.MoveResult;
import model.game.Team;
import model.piece.Piece;
import service.JanggiService;
import view.InputView;
import view.OutputView;
import view.command.CommandType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static controller.Retrier.retry;
import static model.game.Team.CHO;
import static model.game.Team.HAN;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiService janggiService;
    private final Map<CommandType, Runnable> commandMap;

    public JanggiController(InputView inputView, OutputView outputView, JanggiService janggiService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiService = janggiService;
        this.commandMap = Map.of(
                CommandType.MOVE, this::handleMove,
                CommandType.SCORE, this::handleScore,
                CommandType.QUIT, this::handleQuit
        );
    }

    private void handleMove() {
        Team currentTurn = janggiService.getTurn();
        Position current = inputView.readSource(currentTurn);
        Piece piece = janggiService.findPieceAt(current, currentTurn);

        Position next = inputView.readDestination(currentTurn, piece);
        MoveResult moveResult = janggiService.move(current, next);

        outputView.displayBoard(moveResult.board());
        moveResult.winner().ifPresent(
                winner -> outputView.displayWinner(winner.getKoreanName()))
        ;
    }

    private void handleScore() {
        ScoreResult scoreResult = janggiService.calculateScoreResult();

        outputView.displayScores(scoreResult.choScore(), scoreResult.hanScore());
        outputView.displayWinner(scoreResult.winner().getKoreanName());
    }

    private void handleQuit() {
        janggiService.quit();
        outputView.displaySaved();
    }

    public void run() {
        startGame();
        outputView.displayBoard(janggiService.getBoard());
        processCommand();
    }

    private void startGame() {
        if (janggiService.tryResumeGame()) {
            outputView.displayResume();
            return;
        }
        startNewGame();
    }

    private void startNewGame() {
        List<JanggiFormation> formations = Arrays.asList(JanggiFormation.values());
        JanggiFormation hanFormation = retry(() -> inputView.readFormationNumber(HAN, formations), processError());
        JanggiFormation choFormation = retry(() -> inputView.readFormationNumber(CHO, formations), processError());
        janggiService.startNewGame(hanFormation, choFormation);
    }

    private Consumer<IllegalArgumentException> processError() {
        return (e) -> outputView.displayError(e.getMessage());
    }

    private void processCommand() {
        while (janggiService.isPlaying()) {
            CommandType commandType = retry(() -> inputView.readCommand(janggiService.getTurn()), processError());
            retry(() -> commandMap.get(commandType).run(), processError());
        }
    }
}
