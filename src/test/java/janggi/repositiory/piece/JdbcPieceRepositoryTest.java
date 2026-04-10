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
        Map<Position, Piece> all = pieceRepository.findAll(gameId);
        assertThat(all).hasSize(2);

    }

    @Test
    @DisplayName("다시 updateAll을 호출하면 기존 기물은 삭제되고 새로운 상태만 남는다")
    void updateAll_재호출_테스트() {
        // given
        Position oldPosition = new Position(0, 0);
        pieceRepository.updateALL(gameId, Map.of(oldPosition, new Tank(Team.HAN)));

        // when
        Position updatedPosition =new Position(8, 4);
        Map<Position, Piece> newBoard = Map.of(updatedPosition, new King(Team.CHO));
        pieceRepository.updateALL(gameId, newBoard);

        // then
        Map<Position, Piece> all = pieceRepository.findAll(gameId);
        assertThat(all).hasSize(1);
        assertThat(all.getOrDefault(oldPosition, EmptyPiece.getInstance())).isEqualTo(EmptyPiece.getInstance());
        assertThat(all.get(updatedPosition).pieceType()).isEqualTo(PieceType.KING);
    }

    @Test
    @DisplayName("Empty 기물은 DB에 저장되지 않아야 한다")
    void saveAll_ShouldFilterEmptyPieces() {
        // Given
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(0, 0), new King(Team.HAN));
        board.put(new Position(0, 1), EmptyPiece.getInstance());
        board.put(new Position(0, 2), EmptyPiece.getInstance());

        // When
        pieceRepository.updateALL(gameId, board);

        // Then
        Map<Position, Piece> result = pieceRepository.findAll(gameId);

        assertThat(result).hasSize(1);

        assertThat(result.values())
                .extracting(Piece::pieceType)
                .containsExactly(PieceType.KING);
    }
}