package core;

import db.jdbc.ConnectionManager;
import db.repository.JanggiGameRepository;
import view.JanggiView;

public class ShowMoveHistoryService {

    private final JanggiView view;
    private final JanggiGameRepository repository;
    private final ConnectionManager connectionManager;

    public ShowMoveHistoryService(
        final JanggiView view,
        final JanggiGameRepository repository,
        final ConnectionManager connectionManager
    ) {
        this.view = view;
        this.repository = repository;
        this.connectionManager = connectionManager;
    }

    public void show() {

    }
}
