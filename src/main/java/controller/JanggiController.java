package controller;

import domain.Game;
import domain.entity.GameRoomEntity;
import domain.state.Side;
import mapper.BoardMapper;
import mapper.ScoreMapper;
import service.JanggiService;
import view.InputHandler;
import view.InputView;
import view.OutputView;

import java.util.List;

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
        do {
            Game game = readyGame();
            play(game);
            outputView.printVictoryMessage(game.getSide());
        } while (inputView.requestRetry());
    }

    private Game readyGame() {
        List<GameRoomEntity> rooms = janggiService.findAllRooms();
        outputView.printGameRooms(rooms);
        int choice = InputHandler.readUntilValid(() -> validateRoomNumber(rooms, inputView.selectRoom()));

        return janggiService.prepareGame(
                choice,
                rooms,
                () -> InputHandler.readUntilValid(() -> inputView.requestInitialType(Side.HAN)),
                () -> InputHandler.readUntilValid(() -> inputView.requestInitialType(Side.CHU))
        );
    }

    private void play(Game game) {
        while (!game.isFinished()) {
            displayCurrentState(game);
            janggiService.execute(getGameCommand(game.getSide()), game);
        }
    }

    private void displayCurrentState(Game game) {
        outputView.printBoard(BoardMapper.toDto(game.getBoard()));
        outputView.printScore(ScoreMapper.toDto(
                game.calculateScore(Side.CHU),
                game.calculateScore(Side.HAN))
        );
    }

    private GameCommand getGameCommand(Side currentSide) {
        CommandType type = InputHandler.readUntilValid(() -> inputView.requestGameCommand(currentSide));
        return switch (type) {
            case MOVE -> new MoveController(inputView, outputView);
            case PASS -> new PassController(outputView);
            case SURRENDER -> new SurrenderController(outputView);
        };
    }

    private int validateRoomNumber(List<GameRoomEntity> rooms, int choice) {
        if (choice < 0 || choice > rooms.size()) {
            throw new IllegalArgumentException("\n올바른 번호를 입력해주세요.\n");
        }
        return choice;
    }
}
