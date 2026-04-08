package service;

import controller.GameCommand;
import domain.Game;
import domain.board.BasicBoardInitializer;
import domain.board.formation.InitialFormationType;
import domain.entity.GameRoomEntity;
import domain.repository.GameRepository;
import domain.state.Side;

import java.util.List;
import java.util.function.Supplier;

public class JanggiService {

    private final GameRepository gameRepository;

    public JanggiService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void execute(GameCommand gameCommand, Game game) {
        gameCommand.execute(game);
        save(game);
    }

    public Game prepareGame(int choice, List<GameRoomEntity> rooms,
                            Supplier<InitialFormationType> hanTypeSupplier,
                            Supplier<InitialFormationType> chuTypeSupplier) {

        if (choice == 0) {
            Game newGame = createNewGame(hanTypeSupplier.get(), chuTypeSupplier.get());
            save(newGame);
            return newGame;
        }

        GameRoomEntity selectedRoom = rooms.get(choice - 1);

        if (selectedRoom.isFinished()) {
            Game newGame = createNewGame(hanTypeSupplier.get(), chuTypeSupplier.get());
            newGame.assignId(selectedRoom.getId());
            save(newGame);
            return newGame;
        }

        return gameRepository.load(selectedRoom.getId())
                .orElseThrow(() -> new IllegalArgumentException("게임을 불러올 수 없습니다. ID: " + selectedRoom.getId()));
    }

    public List<GameRoomEntity> findAllRooms() {
        return gameRepository.findAllRooms();
    }

    private void save(Game game) {
        gameRepository.save(game);
    }

    private Game createNewGame(InitialFormationType hanType, InitialFormationType chuType) {
        return new Game(new BasicBoardInitializer(
                hanType.create(Side.HAN),
                chuType.create(Side.CHU)
        ));
    }
}
