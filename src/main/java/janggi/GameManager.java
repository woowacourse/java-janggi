package janggi;

import janggi.domain.board.Board;
import janggi.domain.game.Players;
import janggi.domain.game.Side;
import janggi.domain.repository.JanggiRepository;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.Optional;
import java.util.function.Supplier;

public class GameManager {
    private final OutputView outputView;
    private final InputView inputView;
    private final JanggiRepository repository;

    public GameManager(OutputView outputView, InputView inputView, JanggiRepository repository) {
        this.outputView = outputView;
        this.inputView = inputView;
        this.repository = repository;
    }

    public void run() {
        Optional<Long> lastGameId = repository.findInProgressGameId();

        if (lastGameId.isPresent() && isContinued()) {
            continuallyPlay(lastGameId.get());
            return;
        }

        startNewGame();
    }

    private void startNewGame() {
        Players players = initialPlayers();
        Long gameId = repository.save(players);
        Board board = Board.initialize();

        new JanggiGame(outputView, inputView, repository, board, gameId).play(players);
    }

    private void continuallyPlay(Long id) {
        Board board = repository.findBoardById(id);
        Players players = repository.findPlayersById(id);
        outputView.printResumeNotice();

        new JanggiGame(outputView, inputView, repository, board, id).play(players);
    }

    private Players initialPlayers() {
        return retry(() -> {
            String choPlayerName = readPlayerName(Side.CHO);
            String hanPlayerName = readPlayerName(Side.HAN);
            return Players.of(choPlayerName, hanPlayerName);
        });
    }

    private String readPlayerName(Side side) {
        outputView.printPlayerNameNotice(side.getDisplayName());
        return inputView.readPlayerName();
    }

    private boolean isContinued() {
        return retry(() -> {
            outputView.printContinueGameNotice(); // "진행 중인 게임을 계속하시겠습니까?" 출력
            return inputView.readContinueAnswer(); // 사용자의 y/n 응답
        });
    }

    private <T> T retry(Supplier<T> supplier) {
        Optional<T> result = Optional.empty();
        while (result.isEmpty()) {
            result = tryOnce(supplier);
        }
        return result.get();
    }

    private <T> Optional<T> tryOnce(Supplier<T> supplier) {
        try {
            return Optional.of(supplier.get());
        } catch (IllegalArgumentException e) {
            outputView.printLine(e.getMessage());
            return Optional.empty();
        }
    }
}
