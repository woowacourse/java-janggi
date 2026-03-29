package janggi.domain.position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class DirectionTest {
    
    @ParameterizedTest
    @CsvSource(value = {
            "NORTH,NORTHEAST",
            "NORTHEAST,EAST",
            "EAST,SOUTHEAST",
            "SOUTHEAST,SOUTH",
            "SOUTH,SOUTHWEST",
            "SOUTHWEST,WEST",
            "WEST,NORTHWEST",
            "NORTHWEST,NORTH",
    })
    @DisplayName("다음 Direction을 반환한다.")
    public void next_success(Direction current, Direction expectedNext) throws Exception {
        // when
        Direction next = current.next();

        // then
        assertThat(next).isEqualTo(expectedNext);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "NORTH,NORTHWEST",
            "NORTHWEST,WEST",
            "WEST,SOUTHWEST",
            "SOUTHWEST,SOUTH",
            "SOUTH,SOUTHEAST",
            "SOUTHEAST,EAST",
            "EAST,NORTHEAST",
            "NORTHEAST,NORTH",
    })
    @DisplayName("이전 Direction을 반환한다.")
    public void prev_success(Direction current, Direction expectedPrev) throws Exception {
        // when
        Direction prev = current.prev();

        // then
        assertThat(prev).isEqualTo(expectedPrev);
    }


    @ParameterizedTest
    @CsvSource(value = {
            "NORTH,SOUTH",
            "NORTHEAST,SOUTHWEST",
            "EAST,WEST",
            "SOUTHEAST,NORTHWEST",
            "SOUTH,NORTH",
            "SOUTHWEST,NORTHEAST",
            "WEST,EAST",
            "NORTHWEST,SOUTHEAST",
    })
    @DisplayName("뒷방향의 Direction을 반환한다.")
    public void back_success(Direction current, Direction expectedBack) throws Exception {
        // when
        Direction prev = current.back();

        // then
        assertThat(prev).isEqualTo(expectedBack);
    }

}
