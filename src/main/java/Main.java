import config.DataSourceConfig;
import domain.piece.PieceRepository;
import janggigame.JanggiGame;
import janggigame.JanggiGameRepository;
import util.SchemaInitializer;

import javax.sql.DataSource;

public class Main {
    public static void main(String[] args) {
        DataSource dataSource = DataSourceConfig.dataSource();
        SchemaInitializer.initialize(dataSource);
        JanggiGameRepository janggiGameRepository = new JanggiGameRepository(dataSource);
        PieceRepository pieceRepository = new PieceRepository(dataSource);
        JanggiGame janggiGame = new JanggiGame(janggiGameRepository, pieceRepository);
        janggiGame.run();
    }
}
