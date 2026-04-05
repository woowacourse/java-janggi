package service;

import domain.board.Board;
import domain.janggigame.JanggiGame;
import domain.piece.Side;
import domain.players.Players;
import domain.position.Movement;
import domain.position.Position;
import repository.GameRepository;
import view.InputView;
import view.OutputView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static global.util.Retry.retry;

public class JanggiService {

    private final GameRepository gameRepository;
    private final BoardService boardService;

    public JanggiService(GameRepository gameRepository, BoardService boardService) {
        this.gameRepository = gameRepository;
        this.boardService = boardService;
    }

    public void run() {
        selectSide();
        Board board = placeBoardBySide();
        Players players = new Players();
        JanggiGame janggiGame = new JanggiGame(board, players);
        Long gameId = gameRepository.save(janggiGame);
        boardService.save(gameId, janggiGame.getBoard(), janggiGame.getPlayers());

        playGame(gameId, janggiGame);

        // TODO: 기존 게임 시작하라
    }

    private void selectSide() {
        retry(() -> {
            int sideCode = InputView.inputSideChoice();
            Side side = generateSide(sideCode);
            OutputView.printSideChoiceResult(side);
        });
    }

    private Side generateSide(int sideCode) {
        List<Side> sides = Arrays.asList(Side.values());
        Collections.shuffle(sides);
        return sides.get(sideCode - 1);
    }

    private Board placeBoardBySide() {
        return retry(() -> {
            int hanPlacementCode = InputView.inputPlacementCodeBy(Side.HAN);
            int choPlacementCode = InputView.inputPlacementCodeBy(Side.CHO);
            Board board = boardService.initialState(hanPlacementCode, choPlacementCode);
            OutputView.printBoard(board.findState());
            return board;
        });
    }

    private void playGame(Long gameId, JanggiGame janggiGame) {
        retry(() -> {
            while (!janggiGame.isFinished()) {
                Movement movement = inputAndParseToMove();

                janggiGame.playGame(movement);
                boardService.update(gameId, janggiGame.getBoard(), janggiGame.getPlayers());

                janggiGame.switchTurn();
                gameRepository.update(gameId, janggiGame);
                OutputView.printBoard(boardService.findState(gameId));
            }
        });
    }

    private Movement inputAndParseToMove() {
        Position startPosition = InputView.inputStartPosition();
        Position endPosition = InputView.inputEndPosition();
        return new Movement(startPosition, endPosition);
    }
}
