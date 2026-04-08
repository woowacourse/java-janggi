package janggi.repositiory.piece;

import janggi.domain.piece.*;
import janggi.domain.vo.position.Position;
import janggi.repositiory.RepositoryTest;
import janggi.repositiory.game.JdbcGameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class JdbcPieceRepositoryTest extends RepositoryTest {
    private JdbcPieceRepository pieceRepository;
    private Long gameId;

    @BeforeEach
    void setUp() {
        pieceRepository = new JdbcPieceRepository(dataSource);

        JdbcGameRepository gameRepository = new JdbcGameRepository(dataSource);
        gameId = gameRepository.save(false, Team.CHO);
    }

    @Test
    @DisplayName("보드 상태를 한 번에 저장하고 전체를 조회할 수 있다")
    void updateAll_findAll_테스트() {
        // given
        Map<Position, Piece> board = Map.of(
                new Position(0, 0), new Tank(Team.HAN),
                new Position(9, 0), new Soldier(Team.CHO)
        );

        // when
        pieceRepository.updateALL(gameId, board);

        // then
        List<PieceData> all = pieceRepository.findAll(gameId);
        assertThat(all).hasSize(2);
        assertThat(all).extracting("type")
                .containsExactlyInAnyOrder(PieceType.TANK, PieceType.SOLDIER);
    }

    @Test
    @DisplayName("다시 updateAll을 호출하면 기존 기물은 삭제되고 새로운 상태만 남는다")
    void updateAll_재호출_테스트() {
        // given
        pieceRepository.updateALL(gameId, Map.of(new Position(0, 0), new Tank(Team.HAN)));

        // when
        Map<Position, Piece> newBoard = Map.of(new Position(8, 4), new King(Team.CHO));
        pieceRepository.updateALL(gameId, newBoard);

        // then
        List<PieceData> all = pieceRepository.findAll(gameId);
        assertThat(all).hasSize(1);
        assertThat(all.get(0).type()).isEqualTo(PieceType.KING);
        assertThat(all.get(0).row()).isEqualTo(8);
    }

    @Test
    @DisplayName("Empty 기물은 DB에 저장되지 않아야 한다")
    void saveAll_ShouldFilterEmptyPieces() {
        // Given
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(0, 0), new King(Team.HAN));
        board.put(new Position(0, 1), new EmptyPiece(Team.NONE));
        board.put(new Position(0, 2), new EmptyPiece(Team.NONE));

        // When
        pieceRepository.updateALL(gameId, board);

        // Then
        List<PieceData> result = pieceRepository.findAll(gameId);

        assertThat(result)
                .as("Empty를 제외한 실제 기물만 저장되어야 합니다.")
                .hasSize(1);

        assertThat(result.get(0).type())
                .isNotEqualTo(PieceType.EMPTY);
    }
}