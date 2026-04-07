package controller;

import domain.Game;
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
        String menu = retry(inputView::readMainMenu);

        if (menu.equals("1")) {
            startNewGame();
        } else {
            loadExistGame();
        }
    }

    private void play(Game game, long gameId) {
        while (!game.isGameEnd()) {
            outputView.printGame(toGameDto(game));
            boolean isContinue = executeMove(game);
            if (!isContinue) {
                outputView.printGameSaved(gameId);
                return;
            }
            gameRepository.save(gameId, game);
        }
        outputView.printGameResult(game.getWinnerTeam());
    }

    private void startNewGame() {
        InitializeSetting choSetting = retry(() -> inputView.readInitialSetting("초(CHO)"));
        InitializeSetting hanSetting = retry(() -> inputView.readInitialSetting("한(HAN)"));
        Board board = BoardFactory.createBoard(choSetting, hanSetting);
        Game game = new Game(board);

        long gameId = gameRepository.create(game);
        play(game, gameId);
    }

    private void loadExistGame() {
        Map<Long, String> savedGames = gameRepository.findAll();
        outputView.printSavedGames(savedGames);

        if (savedGames.isEmpty()) {
            outputView.printStartNewGame();
            startNewGame();
            return;
        }

        long gameId = retry(inputView::readGameId);
        try {
            Game game = gameRepository.findById(gameId);
            outputView.printGameLoaded(gameId);
            play(game, gameId);
        } catch (Exception e) {
            outputView.printError(new IllegalArgumentException("게임을 불러오는데 실패했습니다. 방 번호를 확인하세요."));
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

    private boolean executeMove(Game game) {
        while (true) {
            try {
                Optional<Position> sourcePosition = inputView.readSourcePosition();
                if (sourcePosition.isEmpty()) {
                    return false;
                }

                game.validateMoveAblePiece(sourcePosition.get());
                Position to = inputView.readTargetPosition();
                game.move(sourcePosition.get(), to);
                return true;
            } catch (IllegalArgumentException | IllegalStateException e) {
                outputView.printError(e);
            }
        }
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
