package janggi.controller;

import janggi.db.repository.GameRepository;
import janggi.domain.board.BoardInitializer;
import janggi.domain.board.MoveResult;
import janggi.domain.board.Position;
import janggi.domain.game.JanggiGame;
import janggi.domain.piece.Team;
import janggi.dto.BoardDto;
import janggi.dto.TurnDto;
import janggi.view.InputView;
import janggi.view.OutputView;

import java.util.List;
import java.util.function.Supplier;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;
    private final GameRepository gameRepository;

    public JanggiController(InputView inputView, OutputView outputView, GameRepository gameRepository) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameRepository = gameRepository;
    }

    public void start() {
        boolean running = true;
        while (running) {
            try {
                outputView.printMenu();
                int choice = retryOnException(inputView::readMenuChoice);
                running = executeMenu(choice);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private boolean executeMenu(int choice) {
        if (choice == 1) {
            createAndPlayGame();
            return true;
        }
        if (choice == 2) {
            showGameRoomList();
            return true;
        }
        if (choice == 3) {
            enterGameRoom();
            return true;
        }
        return false;
    }

    private void enterGameRoom() {
        Long gameId = retryOnException(inputView::readGameId);
        JanggiGame game = gameRepository.load(gameId);
        playGame(gameId, game);
    }

    private void showGameRoomList() {
        List<Long> gameIds = gameRepository.findAllGameIds();
        outputView.printGameRoomList(gameIds);
    }

    private void createAndPlayGame() {
        List<Integer> openingFormationChoices = retryOnException(inputView::readOpeningFormationChoice);
        JanggiGame game = new JanggiGame(BoardInitializer.initializeBoard(
                openingFormationChoices.getFirst(),
                openingFormationChoices.getLast()), Team.HAN);
        Long gameId = gameRepository.save(game);
        outputView.printRoomCreated(gameId);
        playGame(gameId, game);
    }

    private void playGame(Long gameId, JanggiGame game) {
        while (!game.isOver()) {
            try {
                outputView.printBoardMap(BoardDto.from(game.getBoard()));
                outputView.printCurrentScore(game.calculateScore(Team.HAN), game.calculateScore(Team.CHO));
                outputView.printCurrentTurn(TurnDto.from(game.getTurn()));

                Position startPiecePosition = retryOnException(this::getStartPiecePosition);
                Position endPiecePosition = retryOnException(this::getEndPiecePosition);
                MoveResult result = game.move(startPiecePosition, endPiecePosition);
                gameRepository.updateGame(gameId, game, result);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
        outputView.printGameOverMessage(TurnDto.from(game.getTurn()));
        gameRepository.delete(gameId);
    }

    private Position getEndPiecePosition() {
        List<Integer> endPosition = inputView.readEndPiecePosition();
        return new Position(endPosition.getFirst(), endPosition.getLast());
    }

    private Position getStartPiecePosition() {
        List<Integer> startPosition = inputView.readStartPiecePosition();
        return new Position(startPosition.getFirst(), startPosition.getLast());
    }

    private <T> T retryOnException(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
