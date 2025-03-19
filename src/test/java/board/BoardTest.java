package board;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.PieceType;
import piece.TeamType;

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

    @Nested
    @DisplayName("보드의 특정 위치에 피스가 존재하는지 검사")
    class ContainsPiece {

        @DisplayName("보드의 특정 위치에 피스가 존재한다면 true, 아니라면 false를 반환한다.")
        @Test
        void existPieceByPosition() {
            // given
            final Map<Position, Piece> map = Map.of(new Position(1, 1), new Piece(PieceType.GENERAL, TeamType.RED));
            final Board board = new Board(map);
            final Position existPosition = new Position(1, 1);
            final Position notExistPosition = new Position(1, 2);

            // when
            final boolean actual1 = board.existPieceByPosition(existPosition);
            final boolean actual2 = board.existPieceByPosition(notExistPosition);

            // then
            org.junit.jupiter.api.Assertions.assertAll(
                    () -> Assertions.assertThat(actual1).isTrue(),
                    () -> Assertions.assertThat(actual2).isFalse()
            );
        }

        @DisplayName("보드의 특정 위치가 포라면 true를 반환한다.")
        @Test
        void calculatePieceCountByPositions() {
            // given
            final Map<Position, Piece> map = Map.of(
                    new Position(1, 1), new Piece(PieceType.CANNON, TeamType.RED),
                    new Position(1, 2), new Piece(PieceType.SOLDIER, TeamType.RED)
            );
            final Board board = new Board(map);
            final Position cannonPosition = new Position(1, 1);
            final Position soldierPosition = new Position(1, 2);

            // when
            final boolean actualCannon = board.isCannonByPosition(cannonPosition);
            final boolean actualSoldier = board.isCannonByPosition(soldierPosition);

            // then
            assertThat(actualCannon).isTrue();
            assertThat(actualSoldier).isFalse();
        }
    }
}
