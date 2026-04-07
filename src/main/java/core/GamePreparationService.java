package core;

import board.SangSetupType;
import db.repository.JanggiGameRepository;
import java.util.List;
import pieces.Side;
import view.InputGameId;
import view.JanggiView;

public class GamePreparationService {

    private final JanggiView view;
    private final JanggiGameRepository repository;

    public GamePreparationService(final JanggiView view, final JanggiGameRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public GameSession prepare() {
        final List<GameSummary> savedGames = repository.findTop10GameRoomsOrderByCreatedAtDesc();
        if (savedGames.isEmpty()) {
            return createNewGameSession();
        }

        final InputGameId selectedGameId = view.askGameId(savedGames);
        if (selectedGameId.isNewGame()) {
            return createNewGameSession();
        }
        return findSavedGameSession(selectedGameId);
    }

    private GameSession createNewGameSession() {
        final SangSetupType choSangSetupType = view.askSangSetupUntilSuccess(Side.CHO);
        final SangSetupType hanSangSetupType = view.askSangSetupUntilSuccess(Side.HAN);
        final JanggiGame game = JanggiGame.of(choSangSetupType, hanSangSetupType);
        final Long gameId = repository.save(game);

        return new GameSession(gameId, game, view, repository);
    }

    private GameSession findSavedGameSession(final InputGameId selectedGameId) {
        final JanggiGame game = repository.findById(selectedGameId.id())
            .orElseThrow(() -> new IllegalArgumentException("선택한 게임이 존재하지 않습니다."));
        return new GameSession(selectedGameId.id(), game, view, repository);
    }
}
