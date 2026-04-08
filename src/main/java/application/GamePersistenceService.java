package application;

import domain.board.SangSetup;
import domain.game.JanggiGame;
import java.util.Optional;
import repository.GameRepository;
import repository.SavedGameDto;
import repository.SavedGameReadMapper;
import repository.SavedGameWriteMapper;

public class GamePersistenceService {

    private final GameRepository gameRepository;
    private final SavedGameWriteMapper writeMapper;
    private final SavedGameReadMapper readMapper;

    public GamePersistenceService(
            GameRepository gameRepository,
            SavedGameWriteMapper writeMapper,
            SavedGameReadMapper readMapper
    ) {
        this.gameRepository = gameRepository;
        this.writeMapper = writeMapper;
        this.readMapper = readMapper;
    }

    public Optional<GameSession> loadLatestRunningGame() {
        return gameRepository.findLatestRunningGame()
                .map(savedGameDto -> new GameSession(
                        savedGameDto.gameId(),
                        savedGameDto.createdAt(),
                        readMapper.toJanggiGame(savedGameDto)
                ));
    }

    public GameSession createNewGame(SangSetup choSangSetup, SangSetup hanSangSetup) {
        JanggiGame game = JanggiGame.of(choSangSetup, hanSangSetup);
        SavedGameDto savedGameDto = writeMapper.toSavedGameDto(game);
        long gameId = gameRepository.save(savedGameDto);

        return new GameSession(gameId, savedGameDto.createdAt(), game);
    }

    public void saveProgress(GameSession session) {
        SavedGameDto savedGameDto = writeMapper.toSavedGameDto(
                session.gameId(),
                session.createdAt(),
                session.game()
        );
        gameRepository.update(savedGameDto);
    }
}
