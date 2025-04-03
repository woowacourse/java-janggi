package service;

import static org.assertj.core.api.Assertions.assertThat;

import db.connection.DBConnection;
import db.dao.JanggiGameDao.GameDto;
import db.dao.JanggiPieceDao;
import db.repository.JanggiGameRepository;
import fixture.TestDBConnectionFixture;
import janggiGame.arrangement.InnerElephantStrategy;
import janggiGame.piece.Piece;
import janggiGame.position.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiGameStartServiceTest {

    private DBConnection connection = TestDBConnectionFixture.getInstance();
    private JanggiGameRepository repository;
    private JanggiGameStartService service;

    @BeforeEach
    void setUp() {
        repository = new JanggiGameRepository(connection);
        service = new JanggiGameStartService(repository);
    }

    @BeforeEach
    void clearDB() throws SQLException {
        try (Connection connection = TestDBConnectionFixture.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM janggi_game")) {
            preparedStatement.executeUpdate();
        }
    }

    @DisplayName("새 게임을 생성하면 DB에 저장되고 ID가 반환된다")
    @Test
    void getNewGameId() {
        // given
        Long gameId = service.getNewGameId(new InnerElephantStrategy(), new InnerElephantStrategy());

        // when
        Map<Position, Piece> pieces = new JanggiPieceDao(connection).findByGameId(gameId);

        // then
        assertThat(gameId).isNotNull();
        assertThat(pieces).containsKeys(Position.getInstanceBy(0, 0), Position.getInstanceBy(0, 9));
        assertThat(pieces.get(Position.getInstanceBy(0, 0)).getDynasty().name()).isEqualTo("CHO");
        assertThat(pieces.get(Position.getInstanceBy(0, 9)).getDynasty().name()).isEqualTo("HAN");

    }

    @DisplayName("DB에 저장된 미완료 게임 목록을 조회한다")
    @Test
    void getSavedGames() {
        // given
        service.getNewGameId(new InnerElephantStrategy(), new InnerElephantStrategy());

        // when
        List<GameDto> result = service.getSavedGames();

        // then
        assertThat(result).hasSize(1);
    }
}
