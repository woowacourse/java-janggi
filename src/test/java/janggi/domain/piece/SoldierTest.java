package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.space.Position;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SoldierTest {

    @Test
    @DisplayName("초나라 졸은 상(Y 증가), 좌, 우로 한 칸씩 이동할 수 있다")
    void moveChoSoldier() {
        // given
        Position source = Position.of(4, 3);
        Board board = new Board(Map.of(source, PieceFactory.createSoldier(Side.CHO)));

        // when
        List<Position> destinations = board.findDestinations(source).getPositions();

        // then
        assertThat(destinations).containsExactlyInAnyOrder(
                Position.of(4, 4), // 상
                Position.of(3, 3), // 좌
                Position.of(5, 3)  // 우
        );
    }

    @Test
    @DisplayName("한나라 졸은 하(Y 감소), 좌, 우로 한 칸씩 이동할 수 있다")
    void moveHanSoldier() {
        // given
        Position source = Position.of(4, 6);
        Board board = new Board(Map.of(source, PieceFactory.createSoldier(Side.HAN)));

        // when
        List<Position> destinations = board.findDestinations(source).getPositions();

        // then
        assertThat(destinations).containsExactlyInAnyOrder(
                Position.of(4, 5), // 하
                Position.of(3, 6), // 좌
                Position.of(5, 6)  // 우
        );
    }

    @DisplayName("졸과 병은 궁성 내 대각선 중 자신의 진영 기준 전진 방향과 일치하는 대각선으로만 1칸 이동 경로를 추가한다.")
    @Test
    void getDestinations_includeDiagonals() {
        Position source = Position.of(4, 1);
        Board board = new Board(Map.of(source, PieceFactory.createSoldier(Side.HAN)));

        // when
        List<Position> destinations = board.findDestinations(source).getPositions();

        // then
        assertThat(destinations).containsExactlyInAnyOrder(
                Position.of(3, 1),
                Position.of(5, 1),
                Position.of(3, 0),
                Position.of(4, 0),
                Position.of(5, 0)
        );

    }
}
