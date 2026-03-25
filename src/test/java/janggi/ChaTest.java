package janggi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChaTest {

    @DisplayName("같은 행이나 열에 위치해있지 않으면 예외가 발생한다.")
    @Test
    void getLegalPath_invalidPath(){
        //given
        Position from = new Position(Row.SIX, Column.THREE);
        Position to = new Position(Row.TWO, Column.FIVE);
        Cha cha = new Cha(Team.CHO);

        //when & then
        assertThatThrownBy(() -> cha.getLegalPath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 경로로는 이동할 수 없습니다.");
    }

    @DisplayName("from과 to가 같으면 예외가 발생한다.")
    @Test
    void getLegalPath_not_move(){
        //given
        Position from = new Position(Row.SIX, Column.THREE);
        Position to =new Position(Row.SIX, Column.THREE);
        Cha cha = new Cha(Team.CHO);

        //when & then
        assertThatThrownBy(() -> cha.getLegalPath(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 경로로는 이동할 수 없습니다.");
    }

    @DisplayName("같은 행이면 이동할 수 있다.")
    @Test
    void getLegalPath_sameRow() {
        //given
        Position from = new Position(Row.SIX, Column.THREE);
        Position to = new Position(Row.SIX, Column.FIVE);
        Cha cha = new Cha(Team.CHO);

        //when
        Path path = cha.getLegalPath(from, to);

        //then
        assertThat(path.getDestination())
                .isEqualTo( new Position(Row.SIX, Column.FIVE));
    }
}