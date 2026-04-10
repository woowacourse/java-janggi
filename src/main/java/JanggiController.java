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
                startNewGame();

            if (gameType == GameType.LOAD) {
                if (loadAndPlayGame()) {
                    continue;
                }
            }

            if (gameType == GameType.EXIT)
                break;
        }
    }

    private void startNewGame() {
        Game game = initializeGame();
        Game savedGame = janggiService.saveGame(game);
        playGame(savedGame);
    }

    private Game initializeGame() {
        Formation hanFormation = inputView.readHorseElephantFormation(Team.HAN.getName());
        Formation chuFormation = inputView.readHorseElephantFormation(Team.CHU.getName());

        Board board = BoardFactory.setUp(hanFormation, chuFormation);
        return Game.of(board);
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
        while (true) {
            outputView.printBoard(game.getBoard().getBoard());
            boolean isContinue = handleMove(game);
            outputView.printScore(game.calculateScore(Team.CHU), game.calculateScore(Team.HAN));

            if (!isContinue) {
                break;
            }
        }
        outputView.printGameResult(game.getStatus());
    }

    private boolean handleMove(Game game) {
        try {
            String turnName = game.getTurnDisplayName();
            return proceedMove(game, turnName);
        } catch (Exception e) {
            outputView.printMessage("[ERROR] " + e.getMessage());
            return handleMove(game);
        }
    }

    private boolean proceedMove(Game game, String turnName) {
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

        janggiService.moveAndSave(game, from, to);
        return game.getStatus() == Status.PLAYING;
    }

    private boolean handleQuitOrStopCommand(Game game, String turn, String input) {
        if (input.equals(QUIT_COMMAND)) {
            game.lose(turn);
            janggiService.saveGame(game);
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

    private void validatePieceAndTurn(Game game, Position position) {
        Piece piece = game.getBoard().findPieceByPosition(position)
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다."));
        game.checkTurn(piece.getTeam());
    }
}
