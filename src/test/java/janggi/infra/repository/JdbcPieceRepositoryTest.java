package janggi.infra.repository;

import janggi.domain.Country;
import janggi.domain.piece.Gung;
import janggi.domain.piece.Piece;
import janggi.domain.piece.impl.*;
import janggi.infra.connector.InMemoryConnector;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static janggi.test_util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class JdbcPieceRepositoryTest {

    private void runWithPieceRepository(Consumer<PieceRepository> testCode) {
        final JdbcPieceRepository jdbcPieceRepository = new JdbcPieceRepository(new InMemoryConnector());
        jdbcPieceRepository.createTable();
        testCode.accept(jdbcPieceRepository);
        jdbcPieceRepository.deleteTable();
    }

    @Test
    void 기물을_저장한다() {
        runWithPieceRepository((repository) -> {
            // given
            final List<Piece> pieces = List.of(
                    new Po(POSITION_5_5, new Gung()),
                    new Jang(POSITION_3_3, new Gung()),
                    new Jol(POSITION_2_2)
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
                    new Po(POSITION_5_5, new Gung()),
                    new Jang(POSITION_3_3, new Gung()),
                    new Jol(POSITION_2_2)
            ));
            repository.saveAllPieces(5, Country.HAN, List.of(
                    new Sang(POSITION_7_7),
                    new Ma(POSITION_8_8)
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