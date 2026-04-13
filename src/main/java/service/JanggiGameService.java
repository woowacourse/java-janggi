package service;

import domain.board.formation.FormationType;
import domain.game.JanggiGame;
import domain.position.Position;
import java.util.List;
import java.util.Optional;
import repository.GameRepository;
import repository.GameRoomSummary;

public class JanggiGameService {
    private final GameRepository gameRepository;

    public JanggiGameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public JanggiGame createGame(String roomName, FormationType choFormation, FormationType hanFormation) {
        JanggiGame game = JanggiGame.of(choFormation, hanFormation);
        gameRepository.createGame(roomName, game);
        return game;
    }

    public Optional<JanggiGame> enterGame(long roomId) {
        return gameRepository.loadGame(roomId);
    }

    public List<GameRoomSummary> listRooms() {
        return gameRepository.listRooms();
    }

    public void move(JanggiGame game, Position source, Position destination) {
        game.move(source, destination);
        gameRepository.saveGame(game);
    }

    public void pass(JanggiGame game) {
        game.pass();
        gameRepository.saveGame(game);
    }
}
