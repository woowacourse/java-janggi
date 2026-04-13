package controller;

import view.FormationType;
import domain.game.JanggiGame;
import domain.game.Turn;
import domain.piece.Team;
import java.util.List;
import service.GamePersistenceService;
import util.Retry;
import view.InputView;
import view.OutputView;
import view.dto.BoardDto;

public class JanggiController {

    private static final String CHO = "초";
    private static final String HAN = "한";

    private final InputView inputView;
    private final OutputView outputView;
    private final GamePersistenceService gamePersistenceService;

    public JanggiController(
            InputView inputView,
            OutputView outputView,
            GamePersistenceService gamePersistenceService
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gamePersistenceService = gamePersistenceService;
    }

    public void start() {
        long gameId = gamePersistenceService.loadOrCreateGameId(this::createNewGame);
        JanggiGame janggiGame = gamePersistenceService.loadGame(gameId);

        printBoardAndScore(janggiGame);
        while (!janggiGame.isGameEnd()) {
            Turn turn = janggiGame.turn();

            outputView.printTurn(turn);

            List<Integer> from = choosePiece(janggiGame, turn);
            chooseDestinationAndGameStart(gameId, janggiGame, from);
        }
        outputView.printGameEnd(janggiGame.turn());
    }

    private JanggiGame createNewGame() {
        String inputCho = inputView.inputPlacementOption(CHO);
        String inputHan = inputView.inputPlacementOption(HAN);

        FormationType choFormation = FormationType.from(inputCho);
        FormationType hanFormation = FormationType.from(inputHan);
        return JanggiGame.of(choFormation, hanFormation);
    }

    private List<Integer> choosePiece(JanggiGame janggiGame, Turn turn) {
        return Retry.repeatUntilSuccess(() -> {
            List<Integer> inputTokens = inputView.inputPieceLocation();
            janggiGame.checkSameTeam(inputTokens, turn);
            return inputTokens;
        });
    }

    private void chooseDestinationAndGameStart(long gameId, JanggiGame janggiGame, List<Integer> from) {
        Retry.repeatUntilSuccess(() -> {
            List<Integer> to = inputView.inputDestination();
            gamePersistenceService.playTurnAndSave(gameId, janggiGame, from, to);
            printBoardAndScore(janggiGame);
        });
    }

    private void printBoardAndScore(JanggiGame janggiGame) {
        outputView.printBoard(BoardDto.from(janggiGame.snapshot().board()));
        outputView.printScore(CHO, janggiGame.scoreOf(Team.CHO));
        outputView.printScore(HAN, janggiGame.scoreOf(Team.HAN));
    }
}
