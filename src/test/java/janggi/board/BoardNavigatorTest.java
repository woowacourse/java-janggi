package janggi.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardNavigatorTest {

    @DisplayName("정상: 두 위치 사이의 경로에 있는 모든 좌표들을 조회")
    @Test
    void findPositionsOnPath() {
        BoardNavigator boardNavigator = new BoardNavigator();

        assertThat(boardNavigator.findPositionsOnPath(new Position(1, 1), new Position(3, 3)))
                .containsExactly(new Position(2, 2));
    }
}
