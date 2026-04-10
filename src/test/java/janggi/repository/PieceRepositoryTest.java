package janggi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;
import janggi.TestDataInitializer;
import janggi.config.TestDataSourceConfig;
import janggi.domain.Position;
import janggi.domain.game.Game;
import janggi.domain.game.GameStatus;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRule;
import janggi.domain.piece.PlacedPiece;
import janggi.domain.piece.camp.CampType;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PieceRepositoryTest {

    private PieceRepository pieceRepository;
    private GameRepository gameRepository;

    private long gameId;

    @BeforeEach
    void setUp() {
        DataSource dataSource = TestDataSourceConfig.getDataSource();
        TestDataInitializer.initialize(dataSource);
        pieceRepository = new PieceRepository(dataSource);
        gameRepository = new GameRepository(dataSource);

        Game game = new Game(CampType.CHO, GameStatus.PLAYING, List.of());
        gameId = gameRepository.save(game);
    }

    @Test
    void 기물_정보를_저장한다() {
        // given
        PlacedPiece placedPiece = new PlacedPiece(gameId, CampType.CHO, PieceRule.CHARIOT, 0, 0);
        // when
        long pieceId = pieceRepository.save(placedPiece);
        // then
        Map<Position, Piece> result = pieceRepository.findByGameId(gameId);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(result).hasSize(1);
            softly.assertThat(result.get(new Position(0, 0))).isEqualTo(new Piece(PieceRule.CHARIOT, CampType.CHO));
        });
    }

    @Test
    void 게임_아이디로_기물들을_조회한다() {
        // given
        pieceRepository.save(new PlacedPiece(gameId, CampType.CHO, PieceRule.CHARIOT, 0, 0));
        pieceRepository.save(new PlacedPiece(gameId, CampType.HAN, PieceRule.CHARIOT, 9, 8));

        // when
        Map<Position, Piece> pieces = pieceRepository.findByGameId(gameId);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(pieces).hasSize(2);
            softly.assertThat(pieces.get(new Position(0, 0))).isEqualTo(new Piece(PieceRule.CHARIOT, CampType.CHO));
            softly.assertThat(pieces.get(new Position(9, 8))).isEqualTo(new Piece(PieceRule.CHARIOT, CampType.HAN));
        });
    }

    @Test
    void 기물_정보를_수정한다() {
        // given
        long pieceId = pieceRepository.save(new PlacedPiece(gameId, CampType.CHO, PieceRule.CHARIOT, 0, 0));
        PlacedPiece updatedPiece = new PlacedPiece(pieceId, gameId, CampType.CHO, PieceRule.CHARIOT, 1, 1);
        // when
        pieceRepository.update(updatedPiece);
        // then
        Map<Position, Piece> pieces = pieceRepository.findByGameId(gameId);
        assertThat(pieces).containsEntry(new Position(1, 1), new Piece(PieceRule.CHARIOT, CampType.CHO));
        assertThat(pieces.get(new Position(0, 0))).isNull();
    }

    @Test
    void 기물_정보를_삭제한다() {
        // given
        long pieceId = pieceRepository.save(new PlacedPiece(gameId, CampType.CHO, PieceRule.CHARIOT, 0, 0));
        PlacedPiece pieceToDelete = new PlacedPiece(pieceId, gameId, CampType.CHO, PieceRule.CHARIOT, 0, 0);
        // when
        pieceRepository.delete(pieceToDelete);
        // then
        Map<Position, Piece> pieces = pieceRepository.findByGameId(gameId);
        assertThat(pieces).isEmpty();
    }
}
