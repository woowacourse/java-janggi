package janggi;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PositionTest {

    @DisplayName("동쪽으로 이동한다.")
    @Test
    void moveEast(){
       //given
       Position position = new Position(Row.SIX, Column.THREE);

       //when & then
        assertThat(position.moveEast()).isEqualTo(new Position(Row.SIX, Column.FOUR));
    }

    @DisplayName("서쪽으로 이동한다.")
    @Test
    void moveWest(){
        //given
        Position position = new Position(Row.SIX, Column.THREE);

        //when & then
        assertThat(position.moveWest()).isEqualTo(new Position(Row.SIX, Column.TWO));
    }

    @DisplayName("남쪽으로 이동한다.")
    @Test
    void moveSouth(){
        //given
        Position position = new Position(Row.SIX, Column.THREE);

        //when & then
        assertThat(position.moveSouth()).isEqualTo(new Position(Row.SEVEN, Column.THREE));
    }

    @DisplayName("북쪽으로 이동한다.")
    @Test
    void moveNorth(){
        //given
        Position position = new Position(Row.SIX, Column.THREE);

        //when & then
        assertThat(position.moveNorth()).isEqualTo(new Position(Row.FIVE, Column.THREE));
    }

    @DisplayName("북서쪽으로 이동한다.")
    @Test
    void moveNorthAndWest(){
        //given
        Position position = new Position(Row.SIX, Column.THREE);

        //when & then
        assertThat(position.moveNorthAndWest()).isEqualTo(new Position(Row.FIVE, Column.TWO));
    }

    @DisplayName("북동쪽으로 이동한다.")
    @Test
    void moveNorthAndEast(){
        //given
        Position position = new Position(Row.SIX, Column.THREE);

        //when & then
        assertThat(position.moveNorthAndEast()).isEqualTo(new Position(Row.FIVE, Column.FOUR));
    }

    @DisplayName("남동쪽으로 이동한다.")
    @Test
    void moveSouthAndEast(){
        //given
        Position position = new Position(Row.SIX, Column.THREE);

        //when & then
        assertThat(position.moveSouthAndEast()).isEqualTo(new Position(Row.SEVEN, Column.FOUR));
    }

    @DisplayName("남서쪽으로 이동한다.")
    @Test
    void moveSouthAndWest(){
        //given
        Position position = new Position(Row.SIX, Column.THREE);

        //when & then
        assertThat(position.moveSouthAndWest()).isEqualTo(new Position(Row.SEVEN, Column.TWO));
    }

    @DisplayName("같은 행이면 true를 반환한다.")
    @Test
    void isSameRow() {
        //given
        Position position = new Position(Row.SIX, Column.THREE);
        Position other = new Position(Row.SIX, Column.FIVE);

        //when & then
        assertThat(position.isSameRow(other))
                .isEqualTo(true);
    }

    @DisplayName("같은 열이면 true를 반환한다.")
    @Test
    void isSameCollumn() {
        //given
        Position position = new Position(Row.SIX, Column.THREE);
        Position other = new Position(Row.FIVE, Column.THREE);

        //when & then
        assertThat(position.isSameColumn(other))
                .isEqualTo(true);
    }
}