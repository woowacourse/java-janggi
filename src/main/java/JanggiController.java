import domain.board.*;
import domain.game.Game;
import domain.game.GameType;
import domain.game.Status;
import domain.vo.Position;
import entity.GameEntity;
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

            if (gameType == GameType.LOAD && loadAndPlayGame())
                    continue;
            if (gameType == GameType.NEW) {
                startNewGame();
            }
            if (gameType == GameType.EXIT) {
                break;
            }
        }
    }

    private boolean loadAndPlayGame() {
        List<GameEntity> savedGames = janggiService.findAllGames();
        if (savedGames.isEmpty()) {
            outputView.printMessage("기존에 진행하던 게임이 없습니다.");
            return true;
        }

        int gameId = inputView.readGameNumber(savedGames.stream()
                .map(GameEntity::getUpdatedAt)
                .toList());
        GameEntity findGame = savedGames.get(gameId - 1);

        Status status = Status.valueOf(findGame.getStatus());
        if (status != Status.PLAYING) {
            outputView.printGameResult(status);
            return true;
        }

        Game game = janggiService.loadGame(findGame, status);

        playGame(game, findGame);
        return false;
    }

    private void startNewGame() {
        Game game = initializeGame();
        GameEntity savedGame = janggiService.saveGame(game);
        playGame(game, savedGame);
    }

    private void playGame(Game game, GameEntity gameEntity) {
        while (true) {
            outputView.printBoard(game.getBoard().getBoard());
            boolean isContinue = handleMove(game, gameEntity.getId());
            outputView.printScore(game.calculateScore(Team.CHU), game.calculateScore(Team.HAN));

            if (!isContinue) {
                break;
            }
        }
        outputView.printGameResult(game.getStatus());
    }

    private Game initializeGame() {
        Formation hanFormation = inputView.readHorseElephantFormation(Team.HAN.getName());
        Formation chuFormation = inputView.readHorseElephantFormation(Team.CHU.getName());

        Board board = BoardFactory.setUp(hanFormation, chuFormation);
        return Game.of(board);
    }

    private boolean handleMove(Game game, Long gameId) {
        try {
            String turnName = game.getTurnDisplayName();
            return proceedMove(game, gameId, turnName);
        } catch (Exception e) {
            outputView.printMessage("[ERROR] " + e.getMessage());
            return handleMove(game, gameId);
        }
    }

    private boolean proceedMove(Game game, Long gameId, String turnName) {
        String currentInput = inputView.readPosition(turnName);
        if (handleQuitOrStopCommand(game, turnName, currentInput)) {
            return false;
        }
        Position from = parsePosition(currentInput);

        validatePieceAndTurn(game, from);

        String targetInput = inputView.readTargetPosition();
        if (handleQuitOrStopCommand(game, turnName, targetInput)) {
            return false;
        }
        Position to = parsePosition(targetInput);

        janggiService.moveAndSave(game, gameId, from, to);
        return game.getStatus() == Status.PLAYING;
    }

    private void validatePieceAndTurn(Game game, Position position) {
        Piece piece = game.getBoard().findPieceByPosition(position)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 위치에 기물이 존재하지 않습니다."));
        game.checkTurn(piece.getTeam());
    }

    private boolean handleQuitOrStopCommand(Game game, String turn, String input) {
        if (input.equals(QUIT_COMMAND)) {
            game.lose(turn);
            return true;
        }
        if (input.equals(STOP_COMMAND)) {
            return true;
        }
        return false;
    }

    private Position parsePosition(String input) {
        String[] tokens = input.split(" ");
        return Position.of(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
    }
}
