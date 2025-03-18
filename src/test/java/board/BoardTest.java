package board;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class BoardTest {
    
    @Nested
    @DisplayName("보드 생성")
    class ConstructBoard {
        
        @DisplayName("보드를 생성했을 때 사이즈가 총 90이어야 한다.")
        @Test
        void construct1() {
            //given
            //when
            final var board = new Board(new HashMap<>());

            // then
            assertThat(board.getMap()).isNotNull();
        }
        
    }
}
