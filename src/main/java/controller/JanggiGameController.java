package controller;

import domain.JanggiGame;
import domain.JanggiScore;
import domain.SettingType;
import domain.board.BoardStatus;
import domain.piece.Piece;
import domain.position.Position;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import service.JanggiGameService;
import view.ActionType;
import view.BoardStatusDto;
import view.InputView;
import view.JanggiScoreDto;
import view.PositionDto;
import view.ResultView;
import view.TeamDto;

public class JanggiGameController {
    private final InputView inputView;
    private final ResultView resultView;
    private final JanggiGameService janggiGameService;

    public JanggiGameController(InputView inputView, ResultView resultView, JanggiGameService janggiGameService) {
        this.inputView = inputView;
        this.resultView = resultView;
        this.janggiGameService = janggiGameService;
    }

    public void play() {
        janggiGameService.initializeDatabase();
        boolean playingGameExist = janggiGameService.isPlayingGameExist();

        JanggiGame game = null;

        if (playingGameExist) {
            boolean isContinue = inputView.readGameContinueYesOrNo();
            if (isContinue) {
                game = retry(() -> {
                    JanggiGame loadedGame = janggiGameService.loadPlayingGame();
                    printBoardStatus(loadedGame.getJanggiGameStatus());
                    return loadedGame;
                });
            } else {
                janggiGameService.abandonGame();
            }
        }

        if (game == null) {
            game = retry(this::initializeGame);
        }

        playTurn(game);
    }

    private void playTurn(JanggiGame game) {
        while (!game.isFinished()) {
            ActionType actionType = retry(() -> inputView.readAction(game.getTurnOwnTeam()));
            if (actionType == ActionType.MOVE) {
                retry(this::executeMove, game);
            }
            if (actionType == ActionType.PASS) {
                janggiGameService.passTurn(game);
            }
            BoardStatus currentBoardStatus = game.getJanggiGameStatus();
            printBoardStatus(currentBoardStatus);
            printGameScore(game, currentBoardStatus);
        }
        resultView.printWinner(TeamDto.toDto(game.getWinner()));
    }

    private void executeMove(JanggiGame game) {
        PositionDto positionDto = inputView.readMovePositions(game.getTurnOwnTeam());

        Position startPosition = Position.of(positionDto.getStartRow(), positionDto.getStartColumn());
        Position destinationPosition = Position.of(positionDto.getDestinationRow(),
                positionDto.getDestinationColumn());
        janggiGameService.doMove(game, startPosition, destinationPosition);
    }

    private JanggiGame initializeGame() {
        List<SettingType> settingTypes = inputView.readSettings();
        SettingType choSettingType = settingTypes.getFirst();
        SettingType hanSettingType = settingTypes.getLast();

        JanggiGame game = janggiGameService.startNewGame(choSettingType, hanSettingType);
        printBoardStatus(game.getJanggiGameStatus());
        return game;
    }

    private void printBoardStatus(BoardStatus status) {
        Map<Position, Piece> boardStatus = status.status();
        BoardStatusDto statusDto = BoardStatusDto.from(boardStatus);

        resultView.printBoard(statusDto);
    }

    private void printGameScore(JanggiGame game, BoardStatus currentBoardStatus) {
        JanggiScore currentGameScore = game.getGameScore(currentBoardStatus);
        resultView.printGameScore(JanggiScoreDto.toDto(currentGameScore));
    }

    private <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                resultView.printRetryDescription(e);
            }
        }
    }

    private void retry(Consumer<JanggiGame> consumer, JanggiGame janggiGame) {
        while (true) {
            try {
                consumer.accept(janggiGame);
                return;
            } catch (IllegalArgumentException e) {
                resultView.printRetryDescription(e);
            }
        }
    }
}
