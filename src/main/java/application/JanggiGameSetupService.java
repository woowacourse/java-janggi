package application;

import static domain.player.Team.CHO;
import static domain.player.Team.HAN;

import dao.BoardRepository;
import dao.GameInfo;
import dao.GameLoadResult;
import dao.GameRoom;
import dao.PlayerNames;
import domain.board.Board;
import domain.board.Formation;
import domain.game.GameStatus;
import domain.manager.GameManager;
import domain.piece.BasicPiece;
import domain.player.Name;
import domain.player.Player;
import domain.player.Team;
import domain.position.Position;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JanggiGameSetupService {
    private final GameRoom gameRoom;
    private final BoardRepository boardRepository;

    public JanggiGameSetupService(GameRoom gameRoom, BoardRepository boardRepository) {
        this.gameRoom = gameRoom;
        this.boardRepository = boardRepository;
    }

    public JanggiGameSession createNewGame(Player choPlayer, Player hanPlayer, Formation choFormation, Formation hanFormation) {
        GameManager gameManager = new GameManager(choPlayer, hanPlayer, choFormation, hanFormation);
        long gameId = gameRoom.createGame(choPlayer.getProfile().name().value(), hanPlayer.getProfile().name().value());
        boardRepository.save(gameId, gameManager.getBoard());
        gameRoom.updateGameState(gameId, gameManager.getCurrentPlayer().getProfile().team(), GameStatus.PROGRESS);
        return new JanggiGameSession(gameId, gameManager);
    }

    public List<GameInfo> findProgressGames() {
        return gameRoom.findAllProgressGames();
    }

    public Optional<JanggiGameSession> loadSessionById(long gameId) {
        return loadGameById(gameId).map(this::toGameSession);
    }

    public Optional<GameLoadResult> loadProgress() {
        Optional<Long> gameIdOpt = gameRoom.findProgressGame();
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
        Map<Position, BasicPiece> boardMap = boardRepository.loadBoard(gameId);
        Team currentTeam = gameRoom.getCurrentTurn(gameId);
        PlayerNames playerNames = gameRoom.getPlayerNames(gameId);
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
        GameManager gameManager = GameManager.fromLoadedState(
            new Player(new Name(state.choName()), CHO),
            new Player(new Name(state.hanName()), HAN),
            board,
            state.currentTeam()
        );
        return new JanggiGameSession(state.gameId(), gameManager);
    }
}

