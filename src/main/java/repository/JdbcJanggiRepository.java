package repository;

import domain.JanggiGame;
import javax.sql.DataSource;

public class JdbcJanggiRepository implements JanggiRepository {

    private final DataSource dataSource;

    public JdbcJanggiRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(JanggiGame janggiGame) {

    }

    @Override
    public JanggiGame findJanggiGame() {
        return null;
    }
}
