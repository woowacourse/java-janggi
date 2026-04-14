package janggi.repositiory.game;

import janggi.domain.board.Board;
import janggi.domain.janggiGame.JanggiGame;
import janggi.domain.piece.Team;
import janggi.domain.vo.FinishStatus;
import janggi.repositiory.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class JdbcGameRepositoryTest extends RepositoryTest {
    private JdbcGameRepository gameRepository;

    @BeforeEach
    void setUp() {
        gameRepository = new JdbcGameRepository(dataSource);
    }

    @Test
    void save_테스트() throws SQLException {
        // given
        Connection conn = dataSource.getConnection();

        // when
        Long id = gameRepository.save(conn, new FinishStatus(false), Team.CHO);

        // then
        assertThat(id).isNotNull();
        assertThat(id).isPositive();
    }

    @Test
    void findLatest_테스트() throws SQLException {
        // given
        Connection conn = dataSource.getConnection();
        gameRepository.save(conn, new FinishStatus(true), Team.HAN);
        Long latestId = gameRepository.save(conn, new FinishStatus(false), Team.CHO);

        // when
        Optional<GameData> latest = gameRepository.findLatestGame(conn);

        // then
        assertThat(latest).isPresent();
        assertThat(latest.get().gameId()).isEqualTo(latestId);
        assertThat(latest.get().currentTurn()).isEqualTo(Team.CHO);
    }

    @Test
    void update_Status_테스트() throws SQLException{
        // given
        Connection conn = dataSource.getConnection();
        Long id = gameRepository.save(conn, new FinishStatus(false), Team.CHO);
        JanggiGame janggiGame = new JanggiGame(new Board(), Team.HAN);

        // when
        gameRepository.updateStatus(conn, id, janggiGame);

        // then
        GameData updated = gameRepository.findLatestGame(conn).get();
        assertThat(updated.isFinished()).isTrue();
        assertThat(updated.currentTurn()).isEqualTo(Team.HAN);
    }
}