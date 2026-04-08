package controller;

import domain.ContinueOption;
import domain.Game;
import domain.board.Board;
import domain.board.Country;
import domain.board.Position;
import domain.board.TableSetting;
import dto.MoveResult;
import java.util.List;
import java.util.Optional;
import service.GameService;
import view.CountryFormatter;
import view.InputParser;
import view.InputView;
import view.OutputView;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;
    private final GameService gameService;

    public JanggiController(InputView inputView, OutputView outputView, GameService gameService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameService = gameService;
    }

    public void run() {
        Optional<Game> latestGame = gameService.loadLatestGame();
        if (latestGame.isPresent() && isGameContinue()) {
            Game game = latestGame.get();
            Board board = Board.load(gameService.loadBoard(game.getGameId()));
            playTurn(board, game.getGameId(), List.of(Country.CHO, Country.HAN));
            return;
        }
        startNewGame();
    }

    private boolean isGameContinue() {
        while (true) {
            try {
                String input = inputView.readContinueGame();
                return ContinueOption.from(input) == ContinueOption.CONTINUE;
            } catch (IllegalArgumentException exception) {
                outputView.printErrorMessage(exception.getMessage());
            }
        }
    }

    private void startNewGame() {
        TableSetting choTableSetting = readTableSetting(Country.CHO);
        TableSetting hanTableSetting = readTableSetting(Country.HAN);
        Board board = Board.create(choTableSetting, hanTableSetting);

        Game game = new Game(choTableSetting, hanTableSetting);
        Long gameId = gameService.saveGame(game, board.getPieceInfos());

        playTurn(board, gameId, List.of(Country.CHO, Country.HAN));
    }

    private void playTurn(Board board, Long gameId, List<Country> playOrders) {
        int turnIndex = 0;
        while (true) {
            Country country = playOrders.get(turnIndex);
            Country otherSide = playOrders.get((turnIndex + 1) % 2);
            outputView.printTurn(CountryFormatter.from(country));
            outputView.printCurrentScores(board.calculateScore());
            outputView.printBoard(board.getPieceInfos());

            MoveResult moveResult = movePiece(board, gameId, country);
            if (moveResult.isGeneralCaught()) {
                gameService.finishGame(gameId);
                outputView.printWinner(CountryFormatter.from(country), CountryFormatter.from(otherSide));
                return;
            }
            turnIndex = (turnIndex + 1) % 2;
        }
    }

    private TableSetting readTableSetting(Country country) {
        while (true) {
            try {
                String input = inputView.readTableSetting(CountryFormatter.from(country));
                String tableNames = InputParser.parseTableSetting(input);
                return TableSetting.from(tableNames);
            } catch (IllegalArgumentException exception) {
                outputView.printErrorMessage(exception.getMessage());
            }
        }
    }

    private MoveResult movePiece(Board board, Long gameId, Country country) {
        while (true) {
            try {
                Position from = makeFromPosition();
                board.validateFromPosition(from, country);

                Position to = makeToPosition();
                from.validatePositions(to);

                boolean isGeneralCaught = gameService.move(board, gameId, from, to);
                return MoveResult.of(from, to, isGeneralCaught);
            } catch (IllegalArgumentException exception) {
                outputView.printErrorMessage(exception.getMessage());
            }
        }
    }

    private Position makeFromPosition() {
        String input = inputView.readFromPosition();
        List<Integer> positions = InputParser.parsePosition(input);
        return new Position(positions.get(0), positions.get(1));
    }

    private Position makeToPosition() {
        String input = inputView.readToPosition();
        List<Integer> positions = InputParser.parsePosition(input);
        return new Position(positions.get(0), positions.get(1));
    }
}
