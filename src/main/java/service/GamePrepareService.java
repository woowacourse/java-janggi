package service;

import board.SangSetupType;
import core.JanggiGame;
import db.repository.JanggiGameRepository;
import java.util.List;
import pieces.Side;
import util.Retry;
import view.JanggiView;
import view.SelectedGame;

public class GamePrepareService {

    private final JanggiView view;
    private final DbTemplate dbTemplate;
    private final JanggiGameRepository repository;

    public GamePrepareService(JanggiView view, DbTemplate dbTemplate, JanggiGameRepository repository) {
        this.view = view;
        this.dbTemplate = dbTemplate;
        this.repository = repository;
    }

    public PreparedGame prepare() {
        final List<GameSummary> savedGames = dbTemplate.readOnly(
            repository::findTop10GameRoomsOrderByCreatedAtDesc);

        if (savedGames.isEmpty() || view.askNewGame()) {
            return createNewGame();
        }

        final SelectedGame selectedGame = Retry.untilSuccess(() -> {
            final SelectedGame gameId = view.askGameId(savedGames);
            if (gameId.isOver()) {
                throw new IllegalArgumentException("종료된 게임은 실행할 수 없습니다.");
            }
            return gameId;
        });
        return findSavedGame(selectedGame);
    }

    private PreparedGame createNewGame() {
        final SangSetupType choSangSetupType = view.askSangSetupUntilSuccess(Side.CHO);
        final SangSetupType hanSangSetupType = view.askSangSetupUntilSuccess(Side.HAN);
        final JanggiGame game = JanggiGame.of(choSangSetupType, hanSangSetupType);

        final Long gameId = dbTemplate.inTransaction(connection ->
            repository.saveGame(connection, game));
        return new PreparedGame(gameId, game, MoveCount.init());
    }

    private PreparedGame findSavedGame(final SelectedGame selectedGame) {
        return dbTemplate.readOnly(connection -> {
            final JanggiGame game = repository.findGameById(connection, selectedGame.getId())
                .orElseThrow(() -> new IllegalArgumentException("선택한 게임이 존재하지 않습니다."));
            final int moveCount = repository.findMoveHistoriesByGameId(connection, selectedGame.getId()).size();

            return new PreparedGame(selectedGame.getId(), game, new MoveCount(moveCount));
        });
    }
}
