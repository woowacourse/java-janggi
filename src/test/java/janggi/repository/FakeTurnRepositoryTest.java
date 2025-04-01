package janggi.repository;

import static janggi.domain.Team.RED;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Turn;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FakeTurnRepositoryTest {

    private final TurnRepository turnRepository = new FakeTurnRepository();

    @BeforeEach
    void setUp() {
        turnRepository.delete();
    }

    @DisplayName("턴을 추가한다.")
    @Test
    void addTest() {

        // given
        final Turn turn = new Turn(RED);

        // when
        turnRepository.add(turn);

        // then
        assertThat(turnRepository.find().get()).isEqualTo(turn);
    }

    @DisplayName("턴을 수정한다.")
    @Test
    void updateTurnTest() {

        // given
        final Turn turn = new Turn(RED);
        turnRepository.add(turn);

        // when
        turn.changeTurn();
        turnRepository.update(turn);

        // then
        assertThat(turnRepository.find().get()).isEqualTo(turn);
    }

    @DisplayName("턴을 찾는다.")
    @Test
    void findTest() {

        // given
        final Turn turn = new Turn(RED);
        turnRepository.add(turn);

        // when
        final Optional<Turn> findTurn = turnRepository.find();

        // then
        assertThat(turn).isEqualTo(findTurn.get());
    }

    @DisplayName("턴을 삭제한다.")
    @Test
    void deleteTest() {

        // given
        final Turn turn = new Turn(RED);
        turnRepository.add(turn);

        // when
        turnRepository.delete();

        // then
        assertThat(turnRepository.find()).isEmpty();
    }
}
