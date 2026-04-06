package service;

import domain.board.Board;
import domain.board.Intersection;
import domain.game.JanggiGame;
import domain.game.Side;
import dto.GameSummary;
import dto.GameWrapper;
import java.util.List;
import repository.JanggiGameRepository;

public final class JanggiService {

    private final JanggiGameRepository repository;

    public JanggiService(JanggiGameRepository repository) {
        this.repository = repository;
    }

    public GameWrapper createGame(Board board) {
        JanggiGame janggiGame = new JanggiGame(board);
        long generatedKey = repository.save(janggiGame);

        return new GameWrapper(generatedKey, janggiGame);
    }

    public void saveGame(JanggiGame janggiGame) {
        repository.save(janggiGame);
    }

    public void saveGame2(GameWrapper gameWrapper) {
        // TODO 게임 턴 업데이트랑 기물 업데이트를 리포지토리에서 한번에 하지말고 여기서 두 번에 호출하기
        repository.updateGameStatus(gameWrapper.game(), gameWrapper.gameId());
    }

    public GameWrapper loadGame(long gameId) {
        JanggiGame loadedGame = repository.findById(gameId);

        return new GameWrapper(gameId, loadedGame);
    }

    public List<GameSummary> loadAllGameSummaries() {
        return repository.findAll();
    }
}
