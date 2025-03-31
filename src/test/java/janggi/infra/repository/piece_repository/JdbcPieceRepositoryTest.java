package janggi.infra.repository.piece_repository;

import janggi.domain.Country;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.test_util.InMemoryConnector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static janggi.test_util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class JdbcPieceRepositoryTest {

    private final PieceRepository repository = new JdbcPieceRepository(new InMemoryConnector());

    @BeforeEach
    void setUp() {
        repository.createTable();
    }

    @AfterEach
    void tearDown() {
        repository.deleteTable();
    }

    @Test
    void 기물을_저장한다() {
        // given
        final List<Piece> pieces = List.of(
                new Piece(PieceType.PO, POSITION_5_5),
                new Piece(PieceType.GUNG, POSITION_3_3),
                new Piece(PieceType.JOL, POSITION_2_2)
        );

        // expected
        assertThatCode(() -> repository.saveAllPieces(5, Country.CHO, pieces))
                .doesNotThrowAnyException();
    }

    @Test
    void 모든_기물을_찾는다() {
        // given
        repository.saveAllPieces(5, Country.CHO, List.of(
                new Piece(PieceType.PO, POSITION_5_5),
                new Piece(PieceType.GUNG, POSITION_3_3),
                new Piece(PieceType.JOL, POSITION_2_2)
        ));
        repository.saveAllPieces(5, Country.HAN, List.of(
                new Piece(PieceType.SANG, POSITION_7_7),
                new Piece(PieceType.MA, POSITION_8_8)
        ));

        // when
        final Map<Country, List<Piece>> result = repository.findAllPieces(5);

        // then
        assertThat(result.get(Country.CHO)).extracting("position")
                .containsExactlyInAnyOrder(POSITION_5_5, POSITION_3_3, POSITION_2_2);
        assertThat(result.get(Country.HAN)).extracting("position")
                .containsExactlyInAnyOrder(POSITION_7_7, POSITION_8_8);
    }
}