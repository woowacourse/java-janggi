package controller;

import domain.Game;
import domain.TurnResult;
import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.InitializeSetting;
import domain.board.Position;
import domain.piece.Piece;
import domain.piece.Team;
import dto.GameDto;
import dto.PieceDto;

import repository.GameRepository;
import view.InputView;
import view.OutputView;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class Controller {
    private final InputView inputView;
    private final OutputView outputView;
    private final GameRepository gameRepository;

    public Controller(InputView inputView, OutputView outputView, GameRepository gameRepository) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameRepository = gameRepository;
    }

    public void run() {
        long gameId = loadGame();
        Game game = gameRepository.findById(gameId);
        play(game, gameId);
    }

    private long loadGame() {
        String menu = retry(inputView::readMainMenu);
        if (menu.equals("1")) {
            return startNewGame();
        }
        return loadExistGame();
    }

    private long startNewGame() {
        InitializeSetting choSetting = retry(() -> inputView.readInitialSetting("초(CHO)"));
        InitializeSetting hanSetting = retry(() -> inputView.readInitialSetting("한(HAN)"));
        Board board = BoardFactory.createBoard(choSetting, hanSetting);
        Game game = new Game(board);
        return gameRepository.create(game);
    }


    private long loadExistGame() {
        Map<Long, String> savedGames = gameRepository.findAll();
        outputView.printSavedGames(savedGames);
        if (savedGames.isEmpty()) {
            outputView.printStartNewGame();
            return startNewGame();
        }
        return retry(inputView::readGameId);
    }


    private void play(Game game, long gameId) {
        while (true) {
            TurnResult result = playTurn(game);
            if (result == TurnResult.END) {
                outputView.printGameResult(game.getWinnerTeam());
                return;
            }
            if (result == TurnResult.QUIT) {
                outputView.printGameSaved(gameId);
                return;
            }
            gameRepository.save(gameId, game);
        }
    }

    private TurnResult playTurn(Game game) {

        if (game.isGameEnd()) {
            return TurnResult.END;
        }
        outputView.printGame(toGameDto(game));
        if (!executeMove(game)) {
            return TurnResult.QUIT;
        }
        return TurnResult.CONTINUE;
    }

    private boolean executeMove(Game game) {
        while (true) {
            Optional<Position> from = retry(inputView::readSourcePosition);
            if (from.isEmpty()) {
                return false;
            }
            if (tryMove(game, from.get())) {
                return true;
            }
        }
    }

    private boolean tryMove(Game game, Position from) {
        try {
            game.validateMoveAblePiece(from);
            Position to = inputView.readTargetPosition();
            game.move(from, to);
            return true;
        } catch (IllegalArgumentException | IllegalStateException e) {
            outputView.printError(e);
            return false;
        }
    }

    private PieceDto toPieceDto(Position position, Piece piece) {
        return new PieceDto(
                position.x(),
                position.y(),
                piece.getPieceType(),
                piece.getTeam()
        );
    }

    private List<PieceDto> toPieceDtos(Game game) {
        return game.getPieces().entrySet().stream()
                .map(e -> toPieceDto(e.getKey(), e.getValue()))
                .toList();
    }

    private GameDto toGameDto(Game game) {
        return new GameDto(
                game.getTurn(),
                game.getCurrentScore(Team.CHO),
                game.getCurrentScore(Team.HAN),
                toPieceDtos(game)
        );
    }

    private <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printError(e);
            }
        }
    }
}
