package controller;

import domain.board.SangSetup;
import domain.board.SangSetupType;
import domain.game.GameResult;
import domain.game.GameScore;
import domain.game.JanggiGame;
import domain.pieces.Side;
import domain.position.Position;
import view.BoardViewMapper;
import view.GameResultViewMapper;
import view.GameScoreResultViewMapper;
import view.InputView;
import view.OutputView;
import view.dto.PositionInput;

public class GameController {
    private final InputView inputView;
    private final OutputView outputView;
    private final BoardViewMapper boardViewMapper = new BoardViewMapper();
    private final GameResultViewMapper gameResultViewMapper = new GameResultViewMapper();
    private final GameScoreResultViewMapper gameScoreResultViewMapper = new GameScoreResultViewMapper();

    public GameController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        SangSetup choSangSetup = readSangSetup(Side.CHO);
        SangSetup hanSangSetup = readSangSetup(Side.HAN);

        JanggiGame game = JanggiGame.of(choSangSetup, hanSangSetup);
        outputView.printBoard(boardViewMapper.map(game.board()));

        while (true) {
            try {
                outputView.printTurn(game.currentTurn());
                outputView.printMoveGuide();

                PositionInput departureInput = inputView.readDeparturePosition();
                if (departureInput.quit()) {
                    finishGameByScore(game);
                    break;
                }
                PositionInput destinationInput = inputView.readDestinationPosition();
                if (destinationInput.quit()) {
                    finishGameByScore(game);
                    break;
                }
                Position departure = departureInput.position();
                Position destination = destinationInput.position();

                game.move(departure, destination);
                outputView.printBoard(boardViewMapper.map(game.board()));

                if (game.gameResult().isEnded()) {
                    outputView.printGameResult(gameResultViewMapper.map(game.gameResult()));
                    break;
                }

            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private SangSetup readSangSetup(Side side) {
        try {
            outputView.printSangSetupType(side);
            SangSetupType type = inputView.readSangSetupType();
            return type.create();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return readSangSetup(side);
        }
    }

    private void finishGameByScore(JanggiGame game) {
        GameScore gameScore = game.calculateScore();
        GameResult gameResult = GameResult.ended(gameScore.winner());

        outputView.printGameScoreResult(gameScoreResultViewMapper.map(gameResult, gameScore));
    }
}
