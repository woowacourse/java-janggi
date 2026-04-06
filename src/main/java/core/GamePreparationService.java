package core;

import board.SangSetupType;
import db.repository.JanggiGameRepository;
import java.util.List;
import pieces.Side;
import view.JanggiView;

public class GamePreparationService {

    private final JanggiView view;
    private final JanggiGameRepository repository;

    public GamePreparationService(JanggiView view, JanggiGameRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public GameSession prepare() {
        List<GameSummary> savedGames = repository.findTop10GameRoomsOrderByCreatedAtDesc();

        if (savedGames.isEmpty()) {
            return createNewGameSession();
        }

        Long selectedGameId = view.askGameId(savedGames);
        if (selectedGameId == 0) {
            return createNewGameSession();
        }

        JanggiGame game = repository.findById(selectedGameId)
            .orElseThrow(() -> new IllegalArgumentException("선택한 게임이 존재하지 않습니다."));
        return new GameSession(selectedGameId, game, view, repository);
    }

    private GameSession createNewGameSession() {
        SangSetupType choSangSetupType = view.askSangSetupUntilSuccess(Side.CHO);
        SangSetupType hanSangSetupType = view.askSangSetupUntilSuccess(Side.HAN);
        JanggiGame game = JanggiGame.of(choSangSetupType, hanSangSetupType);
        Long gameId = repository.save(game);
        return new GameSession(gameId, game, view, repository);
    }
}
