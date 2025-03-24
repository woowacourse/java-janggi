package model;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PiecesTest {

    Pieces pieces;

    @BeforeEach
    void init() {
        pieces = new Pieces(PieceInitializer.generate());
    }

    @Nested
    @DisplayName("pieces의 움직임을 테스트 한다.")
    class PieceMove {

        @Nested
        @DisplayName("Chariot의 움직임을 테스트 한다.")
        class ChariotMove {

            @Test
            @DisplayName("기본 위치 1,1 에서 3,1로 이동할 수 있어야 한다.")
            void move_1_1_to_3_1() {
                //given
                Position departure = new Position(List.of(1, 1));
                Position arrival = new Position(List.of(3, 1));

                //when
                pieces.move(departure, arrival);

                //then
                assertThat(pieces.findPieceOfNullable(departure)).isEmpty();
                assertThat(pieces.findPieceOfNullable(arrival).get()).isInstanceOf(Chariot.class);
            }

            @Test
            @DisplayName("장애물을 치우고, 기본 위치 1,1 에서 7,1로 이동할 경우, 상대방 기물을 제거하고 움직일 수 있어야 한다.")
            void move_1_1_to_then_throw_exception() {
                pieces.move(new Position(List.of(4, 1)), new Position(List.of(4, 2))); // 기존 말 이동

                //given
                Position departure = new Position(List.of(1, 1));
                Position arrival = new Position(List.of(7, 1));

                //when
                pieces.move(departure, arrival);

                //then
                assertThat(pieces.findPieceBy(arrival)).isInstanceOf(Chariot.class);
            }

            @Test
            @DisplayName("기본 위치 1,1 에서 4,1로 이동할 경우, 이미 다른 팀 피스가 있어 예외가 발생해야 한다.")
            void move_1_1_to_4_1_then_throw_exception() {
                //given
                Position departure = new Position(List.of(1, 1));
                Position arrival = new Position(List.of(4, 1));

                //when, then
                assertThatThrownBy(() -> pieces.move(departure, arrival))
                    .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("Cannon의 움직임을 테스트 한다.")
        class CannonMove {
            @Test
            @DisplayName("뛰어넘는 기물이 같은 Cannon 이라면, 예외를 발생시켜야 한다.")
            void move_3_2_to_9_2_then_throw_exception() {
                //given
                Position departure = new Position(List.of(3, 2));
                Position arrival = new Position(List.of(3,9));

                assertThatThrownBy(() -> pieces.move(departure, arrival))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("같은 포 끼리는 넘을 수 없습니다.");
            }

            @Test
            @DisplayName("포가 아닌 다른 하나의 기물을 뛰어넘은 후 도착지점에 같은 Cannon이 있다면, 예외를 발생시켜야 한다.")
            void jump_and_move_3_2_to_9_2_but_exist_cannon_then_throw_exception() {
                //given
                // 넘을 수 있는 장애물 생성하기
                pieces.move(new Position(List.of(4,1)), new Position(List.of(4, 2)));
                Position departure = new Position(List.of(3, 2));
                Position arrival = new Position(List.of(8,2));

                //when, then
                assertThatThrownBy(() -> pieces.move(departure, arrival))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("같은 포 끼리는 넘을 수 없습니다.");
            }

            @Test
            @DisplayName("포가 아닌 하나의 장애물을 뛰어넘고, 상대 기물을 처치할 수 있어야 한다.")
            void jump_and_move_3_2_to_7_2() {
                //given
                // 넘을 수 있는 장애물 생성하기
                pieces.move(new Position(List.of(4,1)), new Position(List.of(4, 2)));
                // 잡을 수 있는 기물 옮기기
                pieces.move(new Position(List.of(7,1)), new Position(List.of(7,2)));
                Position departure = new Position(List.of(3, 2));
                Position arrival = new Position(List.of(7,2));

                //when, then
                pieces.move(departure, arrival);
                assertThat(pieces.findPieceBy(arrival)).isInstanceOf(Cannon.class);
            }

            @Test
            @DisplayName("포가 아닌 두 개의 기물을 뛰어넘으려고 하면, 예외가 발생해야 한다.")
            void jump_over_one_piece_then_throw_exception() {
                //given
                // 넘을 수 있는 장애물 생성하기
                pieces.move(new Position(List.of(1,2)), new Position(List.of(3,3)));
                pieces.move(new Position(List.of(2,5)), new Position(List.of(3,5)));

                Position departure = new Position(List.of(3, 2));
                Position arrival = new Position(List.of(3,6));

                //when, then
                assertThatThrownBy(() -> pieces.move(departure, arrival));
            }

            @Test
            @DisplayName("포가 뛰어넘을 기물이 없는데 움직이려고 할 경우, 예외가 발생해야 한다.")
            void when_cannon_move_but_not_other_piece_then_throw_exception() {
                //given
                Position departure = new Position(List.of(3, 2));
                Position arrival = new Position(List.of(3,4));
                //when, then
                assertThatThrownBy(() -> pieces.move(departure, arrival));
            }
        }
    }
}
