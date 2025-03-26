package model;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import model.piece.Byeong;
import model.piece.Cannon;
import model.piece.Chariot;
import model.piece.General;
import model.piece.Horse;
import model.piece.Jol;
import model.position.Position;
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
                Position departure = new Position(1, 1);
                Position arrival = new Position(3,1 );

                //when
                pieces.move(departure, arrival);

                //then
                assertThat(pieces.findPieceOfNullable(departure)).isEmpty();
                assertThat(pieces.findPieceOfNullable(arrival).get()).isInstanceOf(Chariot.class);
            }

            @Test
            @DisplayName("장애물을 치우고, 기본 위치 1,1 에서 7,1로 이동할 경우, 상대방 기물을 제거하고 움직일 수 있어야 한다.")
            void move_1_1_to_then_throw_exception() {
                pieces.move(new Position(4, 1), new Position(4, 2)); // 기존 말 이동

                //given
                Position departure = new Position(1, 1);
                Position arrival = new Position(7, 1);

                //when
                pieces.move(departure, arrival);

                //then
                assertThat(pieces.findPieceBy(arrival)).isInstanceOf(Chariot.class);
            }

            @Test
            @DisplayName("기본 위치 1,1 에서 4,1로 이동할 경우, 이미 다른 팀 피스가 있어 예외가 발생해야 한다.")
            void move_1_1_to_4_1_then_throw_exception() {
                //given
                Position departure = new Position(1, 1);
                Position arrival = new Position(4,1);

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
                Position departure = new Position(3, 2);
                Position arrival = new Position(3,9);

                assertThatThrownBy(() -> pieces.move(departure, arrival))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("같은 포 끼리는 넘을 수 없습니다.");
            }

            @Test
            @DisplayName("포가 아닌 다른 하나의 기물을 뛰어넘은 후 도착지점에 같은 Cannon이 있다면, 예외를 발생시켜야 한다.")
            void jump_and_move_3_2_to_9_2_but_exist_cannon_then_throw_exception() {
                //given
                // 넘을 수 있는 장애물 생성하기
                pieces.move(new Position(4, 1), new Position(4, 2));
                Position departure = new Position(3, 2);
                Position arrival = new Position(8, 2);

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
                pieces.move(new Position(4, 1), new Position(4, 2));
                // 잡을 수 있는 기물 옮기기
                pieces.move(new Position(7, 1), new Position(7, 2));
                Position departure = new Position(3, 2);
                Position arrival = new Position(7, 2);

                //when, then
                pieces.move(departure, arrival);
                assertThat(pieces.findPieceBy(arrival)).isInstanceOf(Cannon.class);
            }

            @Test
            @DisplayName("포가 아닌 두 개의 기물을 뛰어넘으려고 하면, 예외가 발생해야 한다.")
            void jump_over_one_piece_then_throw_exception() {
                //given
                // 넘을 수 있는 장애물 생성하기
                pieces.move(new Position(1, 2), new Position(3, 3));
                pieces.move(new Position(2, 5), new Position(3, 5));

                Position departure = new Position(3, 2);
                Position arrival = new Position(3, 6);

                //when, then
                assertThatThrownBy(() -> pieces.move(departure, arrival));
            }

            @Test
            @DisplayName("포가 뛰어넘을 기물이 없는데 움직이려고 할 경우, 예외가 발생해야 한다.")
            void when_cannon_move_but_not_other_piece_then_throw_exception() {
                //given
                Position departure = new Position(3, 2);
                Position arrival = new Position(3, 4);
                //when, then
                assertThatThrownBy(() -> pieces.move(departure, arrival));
            }
        }

        @Nested
        @DisplayName("Horse의 움직임을 테스트 한다.")
        class HorseMove {

            /***
             * 이 부분 부터 시작하기
             */
            @Test
            @DisplayName("Horse가 가고자 하는 경로 중간 및 도착 경로에 아무것도 없다면, 이동해야 한다.")
            void horse_move_and_not_other_piece_then_move() {
                //given
                Position departure = new Position(1, 2);
                Position arrival = new Position(3, 3);

                //when
                pieces.move(departure, arrival);

                //then
                assertThat(pieces.findPieceBy(arrival)).isInstanceOf(Horse.class);
            }

            @Test
            @DisplayName("Horse가 가고자 하는 경로 중간에 장애물이 있다면, 예외가 발생해야 한다.")
            void horse_direction_already_exist_other_piece_then_throw_exception() {
                //given
                Position departure1 = new Position(1, 2);
                Position arrival1 = new Position(3, 3);
                pieces.move(departure1, arrival1); // 이전 이동

                Position departure2 = new Position(3, 3);
                Position arrival2 = new Position(5, 4);

                //when, then
                assertThatThrownBy(() -> pieces.move(departure2, arrival2))
                    .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("Elephant의 움직임을 테스트 한다.")
        class ElephantMove {

            @Test
            @DisplayName("Elephant의 도착 지점에 이동 경로에 다른 기물이 존재한다면, 예외가 발생해야 한다.")
            void elephant_move_but_arrive_direction_exist_other_piece_then_throw_exception() {
                //given
                Position departure = new Position(1, 3);
                Position arrival = new Position(4, 5);

                //when, then
                assertThatThrownBy(() -> pieces.move(departure, arrival))
                    .isInstanceOf(IllegalArgumentException.class);
            }

            @Test
            @DisplayName("Elephant의 이동 경로 중 첫 번째 이동 경로에 장재물이 존재한다면, 예외가 발생해야 한다.")
            void elephant_move_but_first_direction_exist_other_piece_then_throw_exception() {
                //given
                Position departure1 = new Position(2, 5);
                Position arrival1 = new Position(1, 5);
                pieces.move(departure1, arrival1); // 이전 이동
                Position departure = new Position(1, 3);
                Position arrival = new Position(3, 6);

                //when, then
                assertThatThrownBy(() -> pieces.move(departure, arrival))
                    .isInstanceOf(IllegalArgumentException.class);
            }

            @Test
            @DisplayName("Elephant의 이동 경로 중 두 번째 이동 경로에 장재물이 존재한다면, 예외가 발생해야 한다.")
            void elephant_move_but_second_direction_exist_other_piece_then_throw_exception() {
                //given
                Position departure1 = new Position(1, 4);
                Position arrival1 = new Position(2, 4);
                pieces.move(departure1, arrival1); // 이전 이동
                Position departure = new Position(1, 3);
                Position arrival = new Position(3, 6);

                //when, then
                assertThatThrownBy(() -> pieces.move(departure, arrival))
                    .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("Geneal의 움직임을 테스트 한다")
        class GeneralMove {
            @Test
            @DisplayName("움직이려는 경로에 장애물이 있을 경우, 예외가 발생해야 한다.")
            void general_move_but_other_piece_exist_then_throw_exception() {
                //given
                Position departure1 = new Position(1, 4);
                Position arrival1 = new Position(2, 4);
                pieces.move(departure1, arrival1);

                Position departure = new Position(2, 5);
                Position arrival = new Position(2, 4);

                //when
                assertThatThrownBy(() -> pieces.move(departure, arrival))
                    .isInstanceOf(IllegalArgumentException.class);
            }

            @Test
            @DisplayName("움직이려는 경로에 장애물이 없을 경우, 움직일 수 있어야 한다.")
            void general_successful_move() {
                //give
                Position departure = new Position(2, 5);
                Position arrival = new Position(2, 4);

                //when
                pieces.move(departure, arrival);
                assertThat(pieces.findPieceBy(arrival)).isInstanceOf(General.class);
            }
        }

        @Nested
        @DisplayName("Byeong의 움직임을 테스트 한다")
        class ByeongMove {
            @Test
            @DisplayName("Byeong은 이동 위치에 상대방 기물이 있다면, 제거 후 움직일 수 있어야 한다.")
            void byeong_move() {
                //given
                Position departure1 = new Position(4, 5);
                Position arrival1 = new Position(5, 5);
                pieces.move(departure1, arrival1);
                Position departure2 = new Position(5, 5);
                Position arrival2 = new Position(6, 5);
                pieces.move(departure2, arrival2);

                Position departure = new Position(6, 5);
                Position arrival = new Position(7, 5);

                //when
                pieces.move(departure, arrival);

                //then
                assertThat(pieces.findPieceBy(arrival)).isInstanceOf(Byeong.class);
            }

            @Test
            @DisplayName("움직이려는 경로에 같은 팀 기물이 있다면, 예외가 발생해야 한다")
            void byeong_move_but_other_piece_of_same_team_then_throw_exception() {
                //give
                Position departure1 = new Position(4, 3);
                Position arrival1 = new Position(4, 2);
                pieces.move(departure1, arrival1);

                Position departure = new Position(4, 2);
                Position arrival = new Position(4, 1);

                //when, then
                assertThatThrownBy(() -> pieces.move(departure, arrival))
                    .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("Jol 움직임을 테스트 한다")
        class JolMove {

            @Test
            @DisplayName("Jol은 이동 위치에 상대방 기물이 있다면, 제거 후 움직일 수 있어야 한다.")
            void jol_move() {
                //given
                Position departure1 = new Position(7, 5);
                Position arrival1 = new Position(6, 5);
                pieces.move(departure1, arrival1);
                Position departure2 = new Position(6, 5);
                Position arrival2 = new Position(5, 5);
                pieces.move(departure2, arrival2);

                Position departure = new Position(5, 5);
                Position arrival = new Position(4, 5);

                //when
                pieces.move(departure, arrival);

                //then
                assertThat(pieces.findPieceBy(arrival)).isInstanceOf(Jol.class);
            }

            @Test
            @DisplayName("움직이려는 경로에 같은 팀 기물이 있다면, 예외가 발생해야 한다")
            void jol_move_but_other_piece_of_same_team_then_throw_exception() {
                //give
                Position departure1 = new Position(7, 3);
                Position arrival1 = new Position(7, 2);
                pieces.move(departure1, arrival1);

                Position departure = new Position(7, 2);
                Position arrival = new Position(7, 1);

                //when, then
                assertThatThrownBy(() -> pieces.move(departure, arrival))
                    .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("Guard 움직임을 테스트 한다")
        class GuardMove {

            @Test
            @DisplayName("움직이려는 경로에 같은 팀 기물이 있다면, 예외가 발생해야 한다")
            void jol_move_but_other_piece_of_same_team_then_throw_exception() {
                //give
                Position departure1 = new Position(10, 4);
                Position arrival1 = new Position(9, 4);
                pieces.move(departure1, arrival1);

                Position departure = new Position(9, 4);
                Position arrival = new Position(9, 5);

                //when, then
                assertThatThrownBy(() -> pieces.move(departure, arrival))
                    .isInstanceOf(IllegalArgumentException.class);
            }
        }
    }
}
