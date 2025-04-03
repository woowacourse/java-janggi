package db.repository;

import static org.assertj.core.api.Assertions.assertThat;

import db.connection.DBConnection;
import db.dao.JanggiGameDao;
import db.dao.JanggiPieceDao;
import fixture.TestDBConnectionFixture;
import janggiGame.JanggiGame;
import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import janggiGame.piece.straightMovePiece.Chariot;
import janggiGame.position.Position;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiGameRepositoryTest {

    private final DBConnection testConnection = TestDBConnectionFixture.getInstance();
    private final JanggiGameRepository repository = new JanggiGameRepository(testConnection);

    @DisplayName("id 기반으로 JanggiGame 객체를 초기화한다")
    @Test
    void load() {
        // given
        JanggiGameDao gameDao = new JanggiGameDao(testConnection);
        JanggiPieceDao pieceDao = new JanggiPieceDao(testConnection);

        Long gameId = gameDao.save("HAN");
        Map<Position, Piece> pieces = Map.of(
                Position.getInstanceBy(1, 2), new Chariot(Dynasty.HAN)
        );
        pieceDao.saveAll(gameId, pieces);

        // when
        JanggiGame game = repository.load(gameId);

        // then
        assertThat(game.getCurrentDynasty()).isEqualTo(Dynasty.HAN);
        assertThat(game.getPieces()).isNotEmpty();
        assertThat(game.getPieces()).containsKey(Position.getInstanceBy(1, 2));
    }
}
