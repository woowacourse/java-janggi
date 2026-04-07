package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DestinationsTest {

    @Test
    @DisplayName("빈 도착지 객체를 생성하면 내부는 비어있어야 한다")
    void createEmptyDestinations() {
        Destinations empty = Destinations.empty();
        assertThat(empty.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("두 도착지 그룹을 병합하면 두 좌표가 모두 포함된 새로운 객체를 반환해야 한다")
    void addDestinationsProperly() {
        Destinations first = Destinations.of(List.of(new Position(0, 0)));
        Destinations second = Destinations.of(List.of(new Position(1, 1)));
        Destinations merged = first.addDestinations(second);
        assertThat(merged.getDestinations()).hasSize(2);
    }

    @Test
    @DisplayName("교집합 연산을 수행하면 양쪽에 모두 존재하는 좌표만 남아야 한다")
    void retainDestinationsProperly() {
        Destinations first = Destinations.of(List.of(new Position(0, 0), new Position(1, 1)));
        Destinations second = Destinations.of(List.of(new Position(1, 1), new Position(2, 2)));
        Destinations retained = first.retainDestination(second);
        assertThat(retained.containsDestination(new Position(1, 1))).isTrue();
        assertThat(retained.getDestinations()).hasSize(1);
    }
}
