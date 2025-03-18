import static org.assertj.core.api.Assertions.*;

import move.CastleMovement;
import move.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.General;

public class CastleMovementTest {

    @Test
    @DisplayName("궁성 내 기물은 위로 한 칸 이동할 수 있다.")
    void test1() {
        // given
        int x = 0;
        int y = 0;
        Position from = new Position(x, y);
        Position to = new Position(x, y+1);
        CastleMovement castleMovement = new CastleMovement();

        // when
        Position result = castleMovement.move(from, to);

        // then
        assertThat(result)
                .isEqualTo(to);
    }

    @Test
    @DisplayName("궁성 내 기물은 위로 두 칸 이상 이동할 수 없다.")
    void test2() {
        // given
        int x = 0;
        int y = 0;
        Position from = new Position(x, y);
        Position to = new Position(x, y+2);
        CastleMovement castleMovement = new CastleMovement();

        // when
        // then
        assertThatThrownBy(() -> castleMovement.move(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("궁성 내 기물은 아래로 한 칸 이동할 수 있다.")
    void test3() {
        // given
        int x = 0;
        int y = 1;
        Position from = new Position(x, y);
        Position to = new Position(x, y-1);
        CastleMovement castleMovement = new CastleMovement();

        // when
        Position result = castleMovement.move(from, to);

        // then
        assertThat(result)
                .isEqualTo(to);
    }

    @Test
    @DisplayName("장군은 아래로 두 칸 이상 이동할 수 없다.")
    void test4() {
        // given
        int x = 0;
        int y = 0;
        Position from = new Position(x, y);
        Position to = new Position(x, y-2);
        CastleMovement castleMovement = new CastleMovement();

        // when
        // then
        assertThatThrownBy(() -> castleMovement.move(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("장군은 왼쪽으로 한 칸 이동할 수 있다.")
    void test5() {
        // given
        int x = 1;
        int y = 0;
        Position from = new Position(x, y);
        Position to = new Position(x-1, y);
        CastleMovement castleMovement = new CastleMovement();

        // when
        Position result = castleMovement.move(from, to);

        // then
        assertThat(result)
                .isEqualTo(to);
    }

    @Test
    @DisplayName("장군은 왼쪽으로 두 칸 이상 이동할 수 없다.")
    void test6() {
        // given
        int x = 0;
        int y = 0;
        Position from = new Position(x, y);
        Position to = new Position(x-2, y);
        CastleMovement castleMovement = new CastleMovement();

        // when
        // then
        assertThatThrownBy(() -> castleMovement.move(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("장군은 오른쪽으로 한 칸 이동할 수 있다.")
    void test7() {
        // given
        int x = 0;
        int y = 0;
        Position from = new Position(x, y);
        Position to = new Position(x+1, y);
        CastleMovement castleMovement = new CastleMovement();

        // when
        Position result = castleMovement.move(from, to);

        // then
        assertThat(result)
                .isEqualTo(to);
    }

    @Test
    @DisplayName("장군은 오른쪽으로 두 칸 이상 이동할 수 없다.")
    void test8() {
        // given
        int x = 0;
        int y = 0;
        Position from = new Position(x, y);
        Position to = new Position(x+2, y);
        CastleMovement castleMovement = new CastleMovement();

        // when
        // then
        assertThatThrownBy(() -> castleMovement.move(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
