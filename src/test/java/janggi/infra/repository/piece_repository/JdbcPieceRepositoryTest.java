package janggi.infra.repository.piece_repository;

import janggi.domain.Country;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import janggi.domain.piece.PieceType;
import janggi.infra.connector.InMemoryConnector;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static janggi.test_util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class JdbcPieceRepositoryTest {

    private void runWithPieceRepository(Consumer<PieceRepository> testCode) {
        final PieceRepository repository = new JdbcPieceRepository(new InMemoryConnector());
        repository.createTable();
        testCode.accept(repository);
        repository.deleteTable();
    }

    @Test
    void 기물을_저장한다() {
        runWithPieceRepository((repository) -> {
            // given
            final List<Piece> pieces = List.of(
                    PieceFactory.create(PieceType.포, POSITION_5_5),
                    PieceFactory.create(PieceType.장, POSITION_3_3),
                    PieceFactory.create(PieceType.졸, POSITION_2_2)
            );

            // expected
            assertThatCode(() -> repository.saveAllPieces(5, Country.CHO, pieces))
                    .doesNotThrowAnyException();
        });
    }

    @Test
    void 모든_기물을_찾는다() {
        runWithPieceRepository((repository) -> {
            // given
            repository.saveAllPieces(5, Country.CHO, List.of(
                    PieceFactory.create(PieceType.포, POSITION_5_5),
                    PieceFactory.create(PieceType.장, POSITION_3_3),
                    PieceFactory.create(PieceType.졸, POSITION_2_2)
            ));
            repository.saveAllPieces(5, Country.HAN, List.of(
                    PieceFactory.create(PieceType.상, POSITION_7_7),
                    PieceFactory.create(PieceType.마, POSITION_8_8)
            ));

            // when
            final Map<Country, List<Piece>> result = repository.findAllPieces(5);

            // then
            assertThat(result.get(Country.CHO)).extracting("position")
                    .containsExactlyInAnyOrder(POSITION_5_5, POSITION_3_3, POSITION_2_2);
            assertThat(result.get(Country.HAN)).extracting("position")
                    .containsExactlyInAnyOrder(POSITION_7_7, POSITION_8_8);
        });
    }
}