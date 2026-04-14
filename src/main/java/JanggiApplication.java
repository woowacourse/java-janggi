import controller.JanggiController;
import domain.game.JanggiGameRepository;
import domain.piece.PieceRepository;
import domain.player.PlayerRepository;
import repository.jdbc.*;
import service.JanggiGameService;
import view.InputView;
import view.OutputView;

import javax.sql.DataSource;

public class JanggiApplication {

    public static void main(String[] args) {
        final DataSource dataSource = createDataSource();
        initializeSchema(dataSource);
        createController(dataSource).run();
    }

    private static DataSource createDataSource() {
        return new DataSourceFactory().create();
    }

    private static void initializeSchema(final DataSource dataSource) {
        new SchemaInitializer(dataSource).initialize();
    }

    private static JanggiController createController(final DataSource dataSource) {
        final JanggiGameRepository janggiGameRepository = new JdbcJanggiGameRepository();
        final PlayerRepository playerRepository = new JdbcPlayerRepository();
        final PieceRepository pieceRepository = new JdbcPieceRepository();
        final JanggiGameService janggiGameService = new JanggiGameService(
                dataSource,
                janggiGameRepository,
                playerRepository,
                pieceRepository
        );

        return new JanggiController(
                new InputView(),
                new OutputView(),
                janggiGameService
        );
    }
}
