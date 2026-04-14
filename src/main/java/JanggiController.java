import domain.board.*;
import domain.game.Game;
import domain.game.GameType;
import domain.game.Status;
import domain.vo.Position;
import repository.dto.GameDto;
import view.InputView;
import view.OutputView;

import java.util.List;

public class JanggiController {

    private static final String QUIT_COMMAND = "r";
    private static final String STOP_COMMAND = "n";

    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiService janggiService;

    public JanggiController(InputView inputView, OutputView outputView, JanggiService janggiService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiService = janggiService;
    }

    public void run() {
        while (true) {
            GameType gameType = inputView.readGameType();

            if (gameType == GameType.NEW)
                initializeGame();

            if (gameType == GameType.LOAD) {
                if (loadAndPlayGame()) {
                    continue;
                }
            }

            if (gameType == GameType.EXIT)
                break;
        }
    }

    private void initializeGame() {
        Formation hanFormation = inputView.readHorseElephantFormation(Team.HAN.getName());
        Formation chuFormation = inputView.readHorseElephantFormation(Team.CHU.getName());

        Game savedGame = janggiService.createAndSaveGame(hanFormation, chuFormation);
        playGame(savedGame);
    }

    private boolean loadAndPlayGame() {
        List<GameDto> savedGames = janggiService.findAllGames();
        if (savedGames.isEmpty()) {
            outputView.printMessage("기존에 진행하던 게임이 없습니다.");
            return true;
        }

        Long gameId = inputView.readGameNumber(savedGames);
        Game game = janggiService.loadGame(gameId);
        if (game.getStatus() != Status.PLAYING) {
            outputView.printGameResult(game.getStatus());
            return true;
        }

        playGame(game);
        return false;
    }


    private void playGame(Game game) {
        game = handleMove(game);
        outputView.printGameResult(game.getStatus());
    }

    private Game handleMove(Game game) {
        try {
            return proceedMove(game);
        } catch (IllegalArgumentException | IllegalStateException e) {
            outputView.printMessage("[ERROR] " + e.getMessage());
            return handleMove(game);
        } catch (RuntimeException e) {
            outputView.printMessage("[ERROR] 시스템 오류: " + e.getMessage());
            throw e;
        }
    }

    private Game proceedMove(Game game) {
        outputView.printBoard(game.getBoard().getBoard());
        String turnName = game.getTurnDisplayName();

        String currentInput = inputView.readPosition(turnName);
        if (currentInput.equals(QUIT_COMMAND)) {
            return janggiService.forfeit(game.getId(), turnName);
        }
        if (currentInput.equals(STOP_COMMAND)) {
            return game;
        }
        Position from = parsePosition(currentInput);

        game.validateFromPosition(from);

        String targetInput = inputView.readTargetPosition();
        if (targetInput.equals(QUIT_COMMAND)) {
            return janggiService.forfeit(game.getId(), turnName);
        }
        if (targetInput.equals(STOP_COMMAND)) {
            return game;
        }
        Position to = parsePosition(targetInput);

        Game movedGame = janggiService.move(game.getId(), from, to);

        outputView.printScore(movedGame.calculateScore(Team.CHU), movedGame.calculateScore(Team.HAN));
        if (movedGame.getStatus() != Status.PLAYING) {
            return game;
        }
        return proceedMove(movedGame);
    }

    private Position parsePosition(String input) {
        String[] tokens = input.split(" ");
        return Position.of(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
    }
}
