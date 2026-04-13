package service;

import domain.board.formation.FormationType;
import domain.game.JanggiGame;
import domain.position.Position;
import java.util.List;
import java.util.Optional;
import repository.GameRepository;
import repository.GameRoomSummary;
import repository.StoredGame;

public class JanggiGameService {
    private final GameRepository gameRepository;

    public JanggiGameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public StoredGame createGame(String roomName, FormationType choFormation, FormationType hanFormation) {
        JanggiGame game = JanggiGame.of(choFormation, hanFormation);
        return gameRepository.createGame(roomName, game);
    }

    public Optional<StoredGame> enterGame(long roomId) {
        return gameRepository.loadGame(roomId);
    }

    public List<GameRoomSummary> listRooms() {
        return gameRepository.listRooms();
    }

    public void move(StoredGame stored, Position source, Position destination) {
        stored.game().move(source, destination);
        gameRepository.saveGame(stored);
    }

    public void pass(StoredGame stored) {
        stored.game().pass();
        gameRepository.saveGame(stored);
    }
}
