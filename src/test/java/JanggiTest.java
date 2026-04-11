import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Board;
import domain.moveStrategy.StubBoard;
import domain.place.moveStrategy.ChoSoldierMoveStrategy;
import domain.place.piece.Side;
import domain.place.piece.Soldier;
import domain.player.Players;
import domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiTest {

    private Board board;

    @BeforeEach
    void setup() {
        StubBoard stubBoard = new StubBoard();
        Position position1 = new Position(3, 1);
        Position position2 = new Position(7, 1);
        stubBoard.put(position1, new Soldier(Side.CHO, new ChoSoldierMoveStrategy()));
        stubBoard.put(position2, new Soldier(Side.CHO, new ChoSoldierMoveStrategy()));
        board = stubBoard.create();
    }

    @Test
    @DisplayName("기물 선택에서 없는 부분 예외")
    void 기물_선택_없는_부분_예외_테스트() {
        //given
        Janggi janggi = new Janggi(Players.from(List.of("jang", "gi")), board);
        Position from = new Position(4, 1);
        Position to = new Position(7, 1);

        //when & then
        assertThatThrownBy(() -> janggi.playOneTurn(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 현재 위치에 기물이 없습니다.");
    }

    @Test
    @DisplayName("상대 기물 선택 예외")
    void 상대_기물_선택_예외_테스트() {
        //given
        Janggi janggi = new Janggi(Players.from(List.of("jang", "gi")), board);
        Position from = new Position(3, 1);
        Position to = new Position(7, 1);

        //when & then
        assertThatThrownBy(() -> janggi.playOneTurn(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 이동하실 위치에 같은 편 기물이 존재합니다.");
    }

    @Test
    @DisplayName("같은 편 기물이 있는 위치로 이동하면 예외")
    void 같은_편_기물이_있는_위치로_이동_테스트() {
        // given
        Janggi janggi = new Janggi(Players.from(List.of("jang", "gi")), board);

        Position from = new Position(3, 1);
        Position to = new Position(7, 1);

        // when & then
        assertThatThrownBy(() -> janggi.playOneTurn(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 이동하실 위치에 같은 편 기물이 존재합니다.");
    }
}
