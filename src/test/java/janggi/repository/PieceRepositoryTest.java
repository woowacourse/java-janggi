package janggi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;
import janggi.TestDataInitializer;
import janggi.config.TestDataSourceConfig;
import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.game.GameRoom;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRule;
import janggi.domain.piece.PlacedPiece;
import janggi.domain.piece.camp.CampType;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PieceRepositoryTest {

    private PieceRepository pieceRepository;
    private GameRoomRepository gameRoomRepository;

    private long gameRoomId;

    @BeforeEach
    void setUp() {
        DataSource dataSource = TestDataSourceConfig.getDataSource();
        TestDataInitializer.initialize(dataSource);
        pieceRepository = new PieceRepository(dataSource);
        gameRoomRepository = new GameRoomRepository(dataSource);

        Board board = new Board(Map.of());
        GameRoom gameRoom = GameRoom.create();
        gameRoomId = gameRoomRepository.save(gameRoom);
    }

    @Test
    void 기물_정보를_저장한다() {
        // given
        PlacedPiece placedPiece = new PlacedPiece(gameRoomId, CampType.CHO, PieceRule.CHARIOT, 0, 0);
        // when
        pieceRepository.save(placedPiece);
        // then
        Map<Position, Piece> result = pieceRepository.findByGameRoomId(gameRoomId);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(result).hasSize(1);
            softly.assertThat(result.get(new Position(0, 0))).isEqualTo(new Piece(PieceRule.CHARIOT, CampType.CHO));
        });
    }

    @Test
    void 게임_아이디로_기물들을_조회한다() {
        // given
        pieceRepository.save(new PlacedPiece(gameRoomId, CampType.CHO, PieceRule.CHARIOT, 0, 0));
        pieceRepository.save(new PlacedPiece(gameRoomId, CampType.HAN, PieceRule.CHARIOT, 9, 8));

        // when
        Map<Position, Piece> pieces = pieceRepository.findByGameRoomId(gameRoomId);

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
        long pieceId = pieceRepository.save(new PlacedPiece(gameRoomId, CampType.CHO, PieceRule.CHARIOT, 0, 0));
        PlacedPiece updatedPiece = new PlacedPiece(pieceId, gameRoomId, CampType.CHO, PieceRule.CHARIOT, 1, 1);
        // when
        pieceRepository.update(updatedPiece);
        // then
        Map<Position, Piece> pieces = pieceRepository.findByGameRoomId(gameRoomId);
        assertThat(pieces).containsEntry(new Position(1, 1), new Piece(PieceRule.CHARIOT, CampType.CHO));
        assertThat(pieces.get(new Position(0, 0))).isNull();
    }

    @Test
    void 기물_정보를_삭제한다() {
        // given
        long pieceId = pieceRepository.save(new PlacedPiece(gameRoomId, CampType.CHO, PieceRule.CHARIOT, 0, 0));
        PlacedPiece pieceToDelete = new PlacedPiece(pieceId, gameRoomId, CampType.CHO, PieceRule.CHARIOT, 0, 0);
        // when
        pieceRepository.delete(pieceToDelete);
        // then
        Map<Position, Piece> pieces = pieceRepository.findByGameRoomId(gameRoomId);
        assertThat(pieces).isEmpty();
    }
}
