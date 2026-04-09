package controller;

import domain.ContinueOption;
import domain.Game;
import domain.board.Board;
import domain.board.Country;
import domain.board.Position;
import domain.board.TableSetting;
import dto.CountryInfo;
import dto.MoveResult;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
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
        return retryUntilSuccessWithReturn(() -> {
            String input = inputView.readContinueGame();
            return ContinueOption.from(input) == ContinueOption.CONTINUE;
        });
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
            CountryInfo countryInfo = printOneTurn(board, turnIndex, playOrders);
            MoveResult moveResult = movePiece(board, gameId, countryInfo.country());
            if (moveResult.isGeneralCaught()) {
                finish(gameId, countryInfo);
                return;
            }
            turnIndex = (turnIndex + 1) % 2;
        }
    }

    private TableSetting readTableSetting(Country country) {
        return retryUntilSuccessWithReturn(() -> {
            String input = inputView.readTableSetting(CountryFormatter.from(country));
            String tableNames = InputParser.parseTableSetting(input);
            return TableSetting.from(tableNames);
        });
    }

    private CountryInfo printOneTurn(Board board, int turnIndex, List<Country> playOrders) {
        Country country = playOrders.get(turnIndex);
        Country otherSide = playOrders.get((turnIndex + 1) % 2);
        outputView.printTurn(CountryFormatter.from(country));
        outputView.printCurrentScores(board.calculateScore());
        outputView.printBoard(board.getPieceInfos());

        return new CountryInfo(country, otherSide);
    }

    private MoveResult movePiece(Board board, Long gameId, Country country) {
        return retryUntilSuccessWithReturn(() -> {
            Position from = makeFromPosition();
            board.validateFromPosition(from, country);

            Position to = makeToPosition();
            from.validatePositions(to);

            boolean isGeneralCaught = gameService.move(board, gameId, from, to);
            return MoveResult.of(from, to, isGeneralCaught);
        });
    }

    private void finish(Long gameId, CountryInfo countryInfo) {
        gameService.finishGame(gameId);
        outputView.printWinner(CountryFormatter.from(countryInfo.country()),
                CountryFormatter.from(countryInfo.otherSide()));
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

    private <T> T retryUntilSuccessWithReturn(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException exception) {
                outputView.printErrorMessage(exception.getMessage());
            }
        }
    }
}
