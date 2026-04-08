package service;

import static domain.player.Team.CHO;
import static domain.player.Team.HAN;

import dao.GameInfo;
import dao.GameLoadResult;
import dao.GamePersistence;
import dao.PlayerNames;
import domain.board.Board;
import domain.board.Formation;
import domain.manager.JanggiGameManager;
import domain.piece.BasicPiece;
import domain.player.Name;
import domain.player.Player;
import domain.player.Team;
import domain.position.Position;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JanggiGameSetupService {
    private final GamePersistence gamePersistence;

    public JanggiGameSetupService(GamePersistence gamePersistence) {
        this.gamePersistence = gamePersistence;
    }

    public JanggiGameSession createNewGame(Player choPlayer, Player hanPlayer, Formation choFormation, Formation hanFormation) {
        JanggiGameManager janggiGameManager = new JanggiGameManager(choPlayer, hanPlayer, choFormation, hanFormation);
        long gameId = gamePersistence.createNewGame(
            choPlayer.getProfile().name().value(),
            hanPlayer.getProfile().name().value(),
            janggiGameManager.getBoard(),
            janggiGameManager.getCurrentPlayer().getProfile().team()
        );
        return new JanggiGameSession(gameId, janggiGameManager);
    }

    public List<GameInfo> findProgressGames() {
        return gamePersistence.findAllProgressGames();
    }

    public Optional<JanggiGameSession> loadSessionById(long gameId) {
        return loadGameById(gameId).map(this::toGameSession);
    }

    public Optional<GameLoadResult> loadProgress() {
        Optional<Long> gameIdOpt = gamePersistence.findProgressGame();
        if (gameIdOpt.isEmpty()) {
            return Optional.empty();
        }
        return loadGameById(gameIdOpt.get());
    }

    public Optional<GameLoadResult> loadGameById(long gameId) {
        try {
            return Optional.of(loadResult(gameId));
        } catch (Exception e) {
            System.err.println("게임 로드 실패 (gameId: " + gameId + "): " + e.getMessage());
            return Optional.empty();
        }
    }

    private GameLoadResult loadResult(long gameId) {
        Map<Position, BasicPiece> boardMap = gamePersistence.loadBoard(gameId);
        Team currentTeam = gamePersistence.getCurrentTurn(gameId);
        PlayerNames playerNames = gamePersistence.getPlayerNames(gameId);
        return new GameLoadResult(
            gameId,
            playerNames.choName(),
            playerNames.hanName(),
            currentTeam,
            boardMap
        );
    }

    private JanggiGameSession toGameSession(GameLoadResult state) {
        Board board = new Board(state.boardMap());
        JanggiGameManager janggiGameManager = JanggiGameManager.fromLoadedState(
            new Player(new Name(state.choName()), CHO),
            new Player(new Name(state.hanName()), HAN),
            board,
            state.currentTeam()
        );
        return new JanggiGameSession(state.gameId(), janggiGameManager);
    }
}
