package service;

import core.MoveHistory;
import db.repository.JanggiGameRepository;
import java.util.List;
import view.JanggiView;
import view.SelectedGame;

public class MoveHistoryShowService {

    private final JanggiView view;
    private final DbTemplate dbTemplate;
    private final JanggiGameRepository repository;

    public MoveHistoryShowService(
        final JanggiView view,
        final DbTemplate dbTemplate,
        final JanggiGameRepository repository
    ) {
        this.view = view;
        this.dbTemplate = dbTemplate;
        this.repository = repository;
    }

    public void show() {
        final List<GameSummary> savedGames = dbTemplate.readOnly(
            repository::findTop10GameRoomsOrderByCreatedAtDesc
        );

        final SelectedGame selectedGameId = view.askGameId(savedGames);

        List<MoveHistory> moveHistories = dbTemplate.readOnly(connection ->
            repository.findMoveHistoriesByGameId(connection, selectedGameId.getId())
        );
        view.printMoveHistories(moveHistories);
    }
}
