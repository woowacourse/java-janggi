package service;

import dao.GameInfo;
import dao.GameLoadResult;
import dao.PlayerNames;
import domain.board.Board;
import domain.board.Formation;
import domain.manager.JanggiGameManager;
import domain.piece.BasicPiece;
import domain.player.Name;
import domain.player.Player;
import domain.player.Team;
import domain.position.Position;
import domain.rule.BigJangDrawRule;
import domain.rule.DrawGameWinnerRule;
import domain.rule.GameResultEngine;
import domain.rule.NormalGameWinnerRule;
import repository.JanggiGameRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static domain.player.Team.CHO;
import static domain.player.Team.HAN;

public class JanggiGameSetupService {
    private final JanggiGameRepository janggiGameRepository;
    private final GameResultEngine gameResultEngine;

    public JanggiGameSetupService(JanggiGameRepository janggiGameRepository) {
        this(janggiGameRepository, new GameResultEngine(
                List.of(new BigJangDrawRule()),
                List.of(new NormalGameWinnerRule(), new DrawGameWinnerRule())
        ));
    }

    public JanggiGameSetupService(JanggiGameRepository janggiGameRepository, GameResultEngine gameResultEngine) {
        this.janggiGameRepository = janggiGameRepository;
        this.gameResultEngine = gameResultEngine;
    }

    public JanggiGameSession createNewGame(Player choPlayer, Player hanPlayer, Formation choFormation, Formation hanFormation) {
        JanggiGameManager janggiGameManager = new JanggiGameManager(
                choPlayer,
                hanPlayer,
                choFormation,
                hanFormation,
                gameResultEngine
        );
        long gameId = janggiGameRepository.createNewGame(
                choPlayer.getProfile().name().value(),
                hanPlayer.getProfile().name().value(),
                janggiGameManager.getBoard(),
                janggiGameManager.getCurrentPlayer().getProfile().team()
        );
        return new JanggiGameSession(gameId, janggiGameManager);
    }

    public List<GameInfo> findProgressGames() {
        return janggiGameRepository.findAllProgressGames();
    }

    public Optional<JanggiGameSession> loadSessionById(long gameId) {
        return loadGameById(gameId).map(this::toGameSession);
    }

    private Optional<GameLoadResult> loadGameById(long gameId) {
        try {
            return Optional.of(loadResult(gameId));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private GameLoadResult loadResult(long gameId) {
        Map<Position, BasicPiece> boardMap = janggiGameRepository.loadBoard(gameId);
        Team currentTeam = janggiGameRepository.getCurrentTurn(gameId);
        PlayerNames playerNames = janggiGameRepository.getPlayerNames(gameId);
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
                state.currentTeam(),
                gameResultEngine
        );
        return new JanggiGameSession(state.gameId(), janggiGameManager);
    }
}
