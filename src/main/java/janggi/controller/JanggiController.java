package janggi.controller;

import janggi.controller.dto.PositionRequest;
import janggi.domain.Janggi;
import janggi.domain.position.Position;
import janggi.exception.DuplicateGameException;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;

import java.util.List;
import java.util.Optional;

public class JanggiController {
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
            int option = inputView.readMenuOption();

            if (option == 3) {
                deleteGame();
                continue;
            }

            if (option == 4) {
                return;
            }

            Optional<String> gameIdOpt = selectGame(option);

            if (gameIdOpt.isEmpty()) {
                continue;
            }

            String gameId = gameIdOpt.get();

            while (janggiService.isRunning(gameId)) {
                outputView.printBoard(janggiService.getBoardStatus(gameId), janggiService.getCurrentCamp(gameId));
                playTurn(gameId);
            }

            outputView.printGameResult(janggiService.getWinner(gameId));
        }
    }

    private void deleteGame() {
        List<String> names = janggiService.findAllNames();
        Optional<String> gameName = inputView.readGameName(names);

        if (gameName.isEmpty()) {
            return;
        }

        janggiService.deleteByName(gameName.get());
    }

    private Optional<String> selectGame(int option) {
        if (option == 1) {
            return createGame();
        }
        if (option == 2) {
            return loadGame();
        }
        return Optional.empty();
    }

    private Optional<String> createGame() {
        while (true) {
            Optional<String> gameName = inputView.readGameName();
            if (gameName.isEmpty()) {
                return Optional.empty();
            }

            Optional<Integer> choFormation = inputView.readFormationChoice("초나라");
            if (choFormation.isEmpty()) {
                return Optional.empty();
            }

            Optional<Integer> hanFormation = inputView.readFormationChoice("한나라");
            if (hanFormation.isEmpty()) {
                return Optional.empty();
            }

            try {
                String gameId = janggiService.createGame(
                        gameName.get(),
                        choFormation.get(),
                        hanFormation.get()
                );

                return Optional.of(gameId);
            } catch (DuplicateGameException e) {
                System.out.println("[ERROR] " + e.getMessage());
                continue;

            } catch (RuntimeException e) {
                System.out.println("[ERROR] 치명적인 시스템 오류가 발생했습니다: " + e.getMessage());
                return Optional.empty();
            }
        }
    }

    private Optional<String> loadGame() {
        List<String> gameNames = janggiService.findAllNames();
        Optional<String> gameNameOpt = inputView.readGameName(gameNames);

        if (gameNameOpt.isEmpty()) {
            return Optional.empty();
        }
        String gameId= janggiService.findGameByName(gameNameOpt.get());
        return Optional.of(gameId);
    }

    private void playTurn(String gameId) {
        while (true) {
            try {
                int command = inputView.readCommand();

                if (command == 1) {
                    janggiService.surrender(gameId);
                    return;
                }
                if (command == 2) {
                    if (inputView.confirmDraw()) {
                        janggiService.draw(gameId);
                        return;
                    }
                    outputView.printErrorMessage("상대가 무승부를 거절했습니다.");
                    continue;
                }

                Optional<PositionRequest> selection = inputView.readPieceSelection();
                if (selection.isEmpty()) {
                    continue;
                }

                Position from = selection.get().toPosition();
                janggiService.validateTurn(gameId, from);

                Optional<PositionRequest> destination = inputView.readMoveDestination();

                if (destination.isEmpty()) {
                    continue;
                }

                Position to = destination.get().toPosition();

                janggiService.play(gameId, from, to);
                break;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
