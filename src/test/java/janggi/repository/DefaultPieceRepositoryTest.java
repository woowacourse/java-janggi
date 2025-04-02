package janggi.repository;

import static janggi.domain.Team.RED;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.TestFixture;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.direction.Position;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DefaultPieceRepositoryTest {

    private final PieceRepository pieceRepository = TestFixture.getPieceRepository();

    @BeforeEach
    void setUp() {
        pieceRepository.deleteAll();
    }

    @DisplayName("피스를 추가한다.")
    @Test
    void addTest() {

        // given
        final Piece soldier = new Soldier(new Position(1, 1), RED);

        // when
        pieceRepository.add(soldier);

        // then
        assertThat(pieceRepository.findAll().getFirst()).isEqualTo(soldier);
    }

    @DisplayName("피스들을 추가한다.")
    @Test
    void addAllTest() {

        // given
        final Piece soldier = new Soldier(new Position(1, 1), RED);
        final Piece cannon = new Cannon(new Position(1, 2), RED);

        // when
        pieceRepository.addAll(List.of(soldier, cannon));

        // then
        assertThat(pieceRepository.findAll().size()).isEqualTo(2);
    }

    @DisplayName("피스를 삭제한다.")
    @Test
    void deleteTest() {

        // given
        final Piece soldier = new Soldier(new Position(1, 1), RED);
        pieceRepository.add(soldier);

        // when
        pieceRepository.delete(new Position(1, 1));

        // then
        assertThat(pieceRepository.findAll().size()).isEqualTo(0);
    }

    @DisplayName("모든 피스를 삭제한다.")
    @Test
    void deleteAllPiece() {

        // given
        final Piece soldier = new Soldier(new Position(1, 1), RED);
        final Piece cannon = new Cannon(new Position(1, 2), RED);
        pieceRepository.addAll(List.of(soldier, cannon));

        // when
        pieceRepository.deleteAll();

        // then
        assertThat(pieceRepository.findAll().size()).isEqualTo(0);
    }

    @DisplayName("모든 피스를 찾는다.")
    @Test
    void findAllPiece() {

        // given
        final Piece soldier = new Soldier(new Position(1, 1), RED);
        final Piece cannon = new Cannon(new Position(1, 2), RED);
        pieceRepository.addAll(List.of(soldier, cannon));

        // when
        final int size = pieceRepository.findAll().size();

        // then
        assertThat(size).isEqualTo(2);
    }
}
