package janggi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;
import janggi.config.TestDataInitializer;
import janggi.config.TestDataSourceConfig;
import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.game.GameRoom;
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
    private GameRoomRepository gameRoomRepository;

    private long gameRoomId;

    @BeforeEach
    void setUp() {
        DataSource dataSource = TestDataSourceConfig.getDataSource();
        TestDataInitializer.initialize(dataSource);
        pieceRepository = new PieceRepository(dataSource);
        gameRoomRepository = new GameRoomRepository(dataSource);

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
        List<PlacedPiece> pieces = pieceRepository.findAllByGameRoomId(gameRoomId);
        Map<Position, Piece> result = Board.restore(pieces).getBoard();
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
        List<PlacedPiece> pieces = pieceRepository.findAllByGameRoomId(gameRoomId);
        Map<Position, Piece> result = Board.restore(pieces).getBoard();
        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(result).hasSize(2);
            softly.assertThat(result.get(new Position(0, 0))).isEqualTo(new Piece(PieceRule.CHARIOT, CampType.CHO));
            softly.assertThat(result.get(new Position(9, 8))).isEqualTo(new Piece(PieceRule.CHARIOT, CampType.HAN));
        });
    }

    @Test
    void 게임_아이디와_기물_위치로_기물을_조회한다() {
        // given
        pieceRepository.save(new PlacedPiece(gameRoomId, CampType.CHO, PieceRule.CHARIOT, 0, 0));
        // when
        PlacedPiece result = pieceRepository.findByGameRoomIdAndPosition(gameRoomId, 0, 0);
        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(result.getGameRoomId()).isEqualTo(gameRoomId);
            softly.assertThat(result.getCampType()).isEqualTo(CampType.CHO);
            softly.assertThat(result.getPieceRule()).isEqualTo(PieceRule.CHARIOT);
            softly.assertThat(result.getRowPosition()).isEqualTo(0);
            softly.assertThat(result.getColPosition()).isEqualTo(0);
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
        List<PlacedPiece> pieces = pieceRepository.findAllByGameRoomId(gameRoomId);
        Map<Position, Piece> result = Board.restore(pieces).getBoard();
        assertThat(result).containsEntry(new Position(1, 1), new Piece(PieceRule.CHARIOT, CampType.CHO));
        assertThat(result.get(new Position(0, 0))).isNull();
    }

    @Test
    void 기물_정보를_삭제한다() {
        // given
        long pieceId = pieceRepository.save(new PlacedPiece(gameRoomId, CampType.CHO, PieceRule.CHARIOT, 0, 0));
        PlacedPiece pieceToDelete = new PlacedPiece(pieceId, gameRoomId, CampType.CHO, PieceRule.CHARIOT, 0, 0);
        // when
        pieceRepository.delete(pieceToDelete);
        // then
        List<PlacedPiece> pieces = pieceRepository.findAllByGameRoomId(gameRoomId);
        assertThat(pieces).isEmpty();
    }
}
