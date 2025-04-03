package service;

import static org.assertj.core.api.Assertions.assertThat;

import db.connection.DBConnection;
import db.dao.JanggiGameDao;
import db.dao.JanggiPieceDao;
import db.repository.JanggiGameRepository;
import fixture.TestDBConnectionFixture;
import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import janggiGame.piece.straightMovePiece.Chariot;
import janggiGame.position.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiGameProgressServiceTest {

    private DBConnection connection = TestDBConnectionFixture.getInstance();
    private JanggiGameRepository repository;
    private Long gameId;
    private JanggiGameProgressService service;

    @BeforeEach
    void clearDB() throws SQLException {
        try (Connection connection = TestDBConnectionFixture.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM janggi_game")) {
            preparedStatement.executeUpdate();
        }
    }

    @DisplayName("기물을 이동하면 내부 상태와 DB 상태가 모두 변경된다")
    @Test
    void takeTurn() {
        // given
        repository = new JanggiGameRepository(connection);
        gameId = insertTestGame();
        service = new JanggiGameProgressService(gameId, repository);

        Position origin = Position.getInstanceBy(0, 0);
        Position destination = Position.getInstanceBy(0, 2);

        // when
        service.takeTurn(origin, destination);
        Map<Position, Piece> dbPieces = new JanggiPieceDao(connection).findByGameId(gameId);
        Piece moved = service.getGame().getPieces().get(destination);

        // then
        assertThat(moved).isInstanceOf(Chariot.class);
        assertThat(service.getGame().getPieces()).doesNotContainKey(origin);

        assertThat(dbPieces).containsKey(destination);
        assertThat(dbPieces).doesNotContainKey(origin);
    }


    @DisplayName("턴을 스킵하면 게임 상태와 DB의 턴 정보가 변경된다")
    @Test
    void skipTurn() {
        // given
        repository = new JanggiGameRepository(connection);
        gameId = insertTestGame();
        service = new JanggiGameProgressService(gameId, repository);

        Dynasty beforeDynasty = service.getGame().getCurrentDynasty();
        JanggiGameDao gameDao = new JanggiGameDao(connection);

        // when
        service.skipTurn();
        Dynasty afterDynasty = service.getGame().getCurrentDynasty();
        String dbDynasty = gameDao.findById(gameId).get().currentDynasty();

        // then
        assertThat(afterDynasty).isNotEqualTo(beforeDynasty);
        assertThat(dbDynasty).isEqualTo(afterDynasty.name());
    }

    private Long insertTestGame() {
        JanggiGameDao gameDao = new JanggiGameDao(connection);
        Long gameId = gameDao.save("HAN");

        Map<Position, Piece> pieces = Map.of(
                Position.getInstanceBy(0, 0), new Chariot(Dynasty.HAN)
        );
        new JanggiPieceDao(connection).saveAll(gameId, pieces);
        return gameId;
    }
}
