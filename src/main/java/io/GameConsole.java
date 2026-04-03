package io;

import domain.game.JanggiGame;
import domain.room.GameRoom;
import infrastructure.repository.GameRoomRepository;
import java.util.List;
import java.util.function.Supplier;

public class GameConsole {
    private final OutputView outputView;
    private final InputView inputView;
    private final JanggiGame janggiGame;
    private final GameRoomRepository gameRoomRepository;

    public GameConsole(GameRoomRepository gameRoomRepository) {
        this.outputView = new OutputView();
        this.inputView = new InputView();
        this.janggiGame = new JanggiGame();
        this.gameRoomRepository = gameRoomRepository;
    }

    public void run() {
        GameRoom room = selectOrCreateRoom();
        outputView.printRoomEntered(room);

        while (true) {
            janggiGame.displayRequestCommand(outputView);
            if (janggiGame.isFinished()) {
                break;
            }
            retryUntilSuccess(() -> {
                janggiGame.processCommand(inputView.readCommand());
                return null;
            });
        }
    }

    private GameRoom selectOrCreateRoom() {
        while (true) {
            List<GameRoom> rooms = gameRoomRepository.findAllPlaying();
            outputView.printRoomMenu(rooms);
            int choice = retryUntilSuccess(inputView::readRoomMenuChoice);
            if (choice == 1) {
                return createRoom();
            }
            if (rooms.isEmpty()) {
                outputView.printErrorMessage("[ERROR] 입장할 수 있는 게임방이 없습니다. 새 게임방을 만들어주세요.");
                continue;
            }
            return enterRoom(rooms);
        }
    }

    private GameRoom createRoom() {
        outputView.printRoomNamePrompt();
        String name = retryUntilSuccess(inputView::readRoomName);
        return gameRoomRepository.save(name);
    }

    private GameRoom enterRoom(List<GameRoom> rooms) {
        outputView.printRoomNumberPrompt();
        int number = retryUntilSuccess(() -> inputView.readRoomNumber(rooms.size()));
        return rooms.get(number - 1);
    }

    private <T> T retryUntilSuccess(Supplier<T> action) {
        try {
            return action.get();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return retryUntilSuccess(action);
        }
    }
}
