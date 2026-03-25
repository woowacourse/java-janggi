package janggi.gimul;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.Column;
import janggi.Path;
import janggi.Position;
import janggi.Row;
import janggi.Team;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MaTest {

    @DisplayName("경로 상에 다른 기물이 존재하면 false를 반환한다.")
    @Test
    void canPassThrough() {
        //given
        List<Gimul> gimuls = List.of(
                new Cha(Team.CHO),
                new Cha(Team.HAN)
        );
        Ma ma = new Ma(Team.CHO);

        //when & then
        assertThat(ma.canPassThrough(gimuls))
                .isFalse();
    }
}