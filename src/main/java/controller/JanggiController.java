package controller;

import domain.board.PlacementInputMapper;
import domain.board.formation.FormationType;
import domain.game.JanggiGame;
import domain.game.Turn;
import domain.game.dto.BoardDto;
import java.util.List;
import util.Retry;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private static final String CHO = "초";
    private static final String HAN = "한";

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        String inputCho = inputView.inputPlacementOption(CHO);
        String inputHan = inputView.inputPlacementOption(HAN);

        FormationType choFormation = PlacementInputMapper.toFormationType(inputCho);
        FormationType hanFormation = PlacementInputMapper.toFormationType(inputHan);
        JanggiGame janggiGame = JanggiGame.of(choFormation, hanFormation);

        outputView.printBoard(janggiGame.createBoardDto());
        while (!janggiGame.isGameEnd()) {
            Turn turn = janggiGame.turn();

            outputView.printTurn(turn);

            List<Integer> from = choosePiece(janggiGame, turn);
            chooseDestinationAndGameStart(janggiGame, from);
        }
        outputView.printGameEnd(janggiGame.turn());
    }

    private List<Integer> choosePiece(JanggiGame janggiGame, Turn turn) {
        return Retry.repeatUntilSuccess(() -> {
            List<Integer> inputTokens = inputView.inputPieceLocation();
            janggiGame.checkSameTeam(inputTokens, turn);
            return inputTokens;
        });
    }

    private void chooseDestinationAndGameStart(JanggiGame janggiGame, List<Integer> from) {
        Retry.repeatUntilSuccess(() -> {
            List<Integer> to = inputView.inputDestination();
            janggiGame.playTurn(from, to);
            BoardDto boardDto = janggiGame.createBoardDto();
            outputView.printBoard(boardDto);
        });
    }
}
