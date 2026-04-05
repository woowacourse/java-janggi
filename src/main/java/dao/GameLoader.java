package dao;

import java.util.Optional;

public class GameLoader {
    private final GameRoom gameRoom;
    private final BoardRepository boardRepository;

    public GameLoader(GameRoom gameRoom, BoardRepository boardRepository) {
        this.gameRoom = gameRoom;
        this.boardRepository = boardRepository;
    }

    public Optional<GameLoadResult> loadProgress() {
        Optional<Long> gameIdOpt = gameRoom.findProgressGame();
        if (gameIdOpt.isEmpty()) {
            return Optional.empty();
        }

        long gameId = gameIdOpt.get();
        try {
            var boardMap = boardRepository.loadBoard(gameId);
            var currentTeam = gameRoom.getCurrentTurn(gameId);
            var playerNames = gameRoom.getPlayerNames(gameId);
            return Optional.of(new GameLoadResult(
                gameId,
                playerNames.choName(),
                playerNames.hanName(),
                currentTeam,
                boardMap
            ));
        } catch (Exception e) {
            throw new IllegalStateException("게임 불러오기에 실패했습니다: " + e.getMessage(), e);
        }
    }

    public Optional<GameLoadResult> loadGameById(long gameId) {
        try {
            var boardMap = boardRepository.loadBoard(gameId);
            var currentTeam = gameRoom.getCurrentTurn(gameId);
            var playerNames = gameRoom.getPlayerNames(gameId);
            return Optional.of(new GameLoadResult(
                gameId,
                playerNames.choName(),
                playerNames.hanName(),
                currentTeam,
                boardMap
            ));
        } catch (Exception e) {
            System.err.println("게임 로드 실패 (gameId: " + gameId + "): " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
