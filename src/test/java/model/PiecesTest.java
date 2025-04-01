package model;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;
import model.piece.PieceType;
import model.piece.Piece;
import model.piece.PieceInitializer;
import model.piece.Team;
import model.position.Column;
import model.position.Position;
import model.position.Row;
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
        class ChariotRouteFinderMove {

            @Test
            @DisplayName("기본 위치 1,1 에서 3,1로 이동할 수 있어야 한다.")
            void move_1_1_to_3_1() {
                //given
                Position departure = new Position(1, 1);
                Position arrival = new Position(3, 1);

                //when
                pieces.move(departure, arrival);

                //then
                assertThat(pieces.findPieceOfNullable(departure)).isEmpty();
                assertThat(pieces.findPieceBy(arrival).getType()).isEqualTo(PieceType.CHARIOT.name());
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
                assertThat(pieces.findPieceBy(arrival).getType()).isEqualTo(PieceType.CHARIOT.name());
            }

            @Test
            @DisplayName("기본 위치 1,1 에서 4,1로 이동할 경우, 이미 다른 팀 피스가 있어 예외가 발생해야 한다.")
            void move_1_1_to_4_1_then_throw_exception() {
                //given
                Position departure = new Position(1, 1);
                Position arrival = new Position(4, 1);

                //when, then
                assertThatThrownBy(() -> pieces.move(departure, arrival))
                    .isInstanceOf(IllegalArgumentException.class);
            }

            @Test
            @DisplayName("차가 궁성 안에 있고, 대각선 움직임이 가능한 위치에 있을경우, 움직일 수 있어야 한다. - 8, 4 (한 칸)")
            void when_chariot_inside_castle_can_move_diagonal_in_8_4_only_one() {
                //given
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.EIGHT, Row.FOUR), new Piece(Team.RED, PieceType.CHARIOT));
                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.EIGHT, Row.FOUR);
                Position arrival = new Position(Column.NINE, Row.FIVE);

                //when
                pieces.move(departure, arrival);

                //then
                assertThat(pieces.findPieceBy(arrival).getType()).isEqualTo(PieceType.CHARIOT.name());
            }

            @Test
            @DisplayName("차가 궁성 안에 있고, 대각선 움직임이 가능한 위치에 있을경우, 움직일 수 있어야 한다. - 8, 4 (두 칸)")
            void when_chariot_inside_castle_can_move_diagonal_in_8_4_two_step() {
                //given
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.EIGHT, Row.FOUR), new Piece(Team.RED, PieceType.CHARIOT));
                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.EIGHT, Row.FOUR);
                Position arrival = new Position(Column.TEN, Row.SIX);

                //when
                pieces.move(departure, arrival);

                //then
                assertThat(pieces.findPieceBy(arrival).getType()).isEqualTo(PieceType.CHARIOT.name());
            }

            @Test
            @DisplayName("차가 궁성 안에 있고, 대각선 움직임이 가능한 위치에 있을경우, 움직일 수 있어야 한다. - 8,6 (한 칸)")
            void when_chariot_inside_castle_can_move_diagonal_in_8_6_only_one() {
                //given
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.EIGHT, Row.SIX), new Piece(Team.RED, PieceType.CHARIOT));
                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.EIGHT, Row.SIX);
                Position arrival = new Position(Column.NINE, Row.FIVE);

                //when
                pieces.move(departure, arrival);

                //then
                assertThat(pieces.findPieceBy(arrival).getType()).isEqualTo(PieceType.CHARIOT.name());
            }

            @Test
            @DisplayName("차가 궁성 안에 있고, 대각선 움직임이 가능한 위치에 있을경우, 움직일 수 있어야 한다. - 8,6 (두 칸)")
            void when_chariot_inside_castle_can_move_diagonal_in_8_6_two_step() {
                //given
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.EIGHT, Row.SIX), new Piece(Team.RED, PieceType.CHARIOT));
                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.EIGHT, Row.SIX);
                Position arrival = new Position(Column.TEN, Row.FOUR);

                //when
                pieces.move(departure, arrival);

                //then
                assertThat(pieces.findPieceBy(arrival).getType()).isEqualTo(PieceType.CHARIOT.name());
            }
        }

        @Nested
        @DisplayName("Cannon의 움직임을 테스트 한다.")
        class CannonRouteFinderMove {

            @Test
            @DisplayName("뛰어넘는 기물이 같은 Cannon 이라면, 예외를 발생시켜야 한다.")
            void move_3_2_to_9_2_then_throw_exception() {
                //given
                Position departure = new Position(3, 2);
                Position arrival = new Position(3, 9);

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
                assertThat(pieces.findPieceBy(arrival).getType()).isEqualTo(PieceType.CANNON.name());
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

            @Test
            @DisplayName("Cannon이 궁성 안에 있고, 대각 움직임이 가능한 좌표에 있지만, 뛰어넘을 기물이 없다면 예외를 발생시켜야 한다.")
            void when_cannon_inside_castle_can_move_diagonal_but_other_piece_not_exist() {
                //given
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.EIGHT, Row.FOUR), new Piece(Team.RED, PieceType.CANNON));
                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.EIGHT, Row.FOUR);
                Position arrival = new Position(Column.TEN, Row.SIX);

                //when, then
                assertThatThrownBy(() -> pieces.move(departure, arrival))
                    .isInstanceOf(IllegalArgumentException.class);
            }

            @Test
            @DisplayName("Cannon이 궁성 안에 있고, 대각 움직임이 가능한 좌표에 있고, 뛰어넘을 기물이 존재한다면, 대각 움직임이 가능해야 한다.")
            void when_cannon_inside_castle_can_move_diagonal_and_other_piece_exist() {
                //given
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.EIGHT, Row.FOUR), new Piece(Team.RED, PieceType.CANNON));
                temporaryPieces.put(new Position(Column.NINE, Row.FIVE), new Piece(Team.RED, PieceType.HORSE));
                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.EIGHT, Row.FOUR);
                Position arrival = new Position(Column.TEN, Row.SIX);

                //when
                pieces.move(departure, arrival);

                //then
                assertThat(pieces.findPieceBy(arrival).getType()).isEqualTo(PieceType.CANNON.name());
            }

            @Test
            @DisplayName("Cannon이 궁성 안에 있고, 대각 움직임이 가능한 좌표에 있지만, 뛰어넘는 기물이 같은 캐논이라면, 예외를 발생시켜야 한다.")
            void when_cannon_inside_castle_can_move_diagonal_but_other_cannon_exist() {
                //given
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.EIGHT, Row.FOUR), new Piece(Team.RED, PieceType.CANNON));
                temporaryPieces.put(new Position(Column.NINE, Row.FIVE), new Piece(Team.RED, PieceType.CANNON));
                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.EIGHT, Row.FOUR);
                Position arrival = new Position(Column.TEN, Row.SIX);

                //when, then
                assertThatThrownBy(() -> pieces.move(departure, arrival))
                    .isInstanceOf(IllegalArgumentException.class);
            }

            @Test
            @DisplayName("Cannon이 궁성 안에 있고, 대각 움직임이 가능한 좌표에 있고, 뛰어넘을 기물이 있지만, 도착지점에 같은 팀 기물이 있다면 예외를 발생시킨다.")
            void when_cannon_inside_castle_can_move_diagonal_but_other_piece_exist_but_arrival_same_team() {
                //given
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.EIGHT, Row.FOUR), new Piece(Team.RED, PieceType.CANNON));
                temporaryPieces.put(new Position(Column.NINE, Row.FIVE), new Piece(Team.RED, PieceType.GENERAL));
                temporaryPieces.put(new Position(Column.TEN, Row.SIX), new Piece(Team.RED, PieceType.HORSE));

                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.EIGHT, Row.FOUR);
                Position arrival = new Position(Column.TEN, Row.SIX);

                //when, then
                assertThatThrownBy(() -> pieces.move(departure, arrival))
                    .isInstanceOf(IllegalArgumentException.class);
            }

            @Test
            @DisplayName("Cannon이 궁성 안에 있고, 대각 움직임이 가능한 좌표에 있고, 뛰어넘을 기물이 있고, 도착지점에 다른 팀 기물이 있을 경우, 잡아먹는다")
            void when_cannon_inside_castle_can_move_diagonal_but_other_piece_exist_and_arrival_other_team() {
                //given
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.EIGHT, Row.FOUR), new Piece(Team.RED, PieceType.CANNON));
                temporaryPieces.put(new Position(Column.NINE, Row.FIVE), new Piece(Team.RED, PieceType.GENERAL));
                temporaryPieces.put(new Position(Column.TEN, Row.SIX), new Piece(Team.GREEN, PieceType.HORSE));

                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.EIGHT, Row.FOUR);
                Position arrival = new Position(Column.TEN, Row.SIX);

                //when
                pieces.move(departure, arrival);
                assertThat(pieces.findPieceBy(arrival).getType()).isEqualTo(PieceType.CANNON.name());
            }

            @Test
            @DisplayName("Cannon이 궁성 안에 있고, 대각 움직임이 가능한 좌표에 있고, 뛰어넘을 기물이 있고, 도착지점에 다른 팀 Cannon이 있을 경우, 예외를 발생시킨다.")
            void when_cannon_inside_castle_can_move_diagonal_but_other_piece_exist_and_arrival_other_team_of_cannon() {
                //given
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.EIGHT, Row.FOUR), new Piece(Team.RED, PieceType.CANNON));
                temporaryPieces.put(new Position(Column.NINE, Row.FIVE), new Piece(Team.RED, PieceType.GENERAL));
                temporaryPieces.put(new Position(Column.TEN, Row.SIX), new Piece(Team.GREEN, PieceType.CANNON));

                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.EIGHT, Row.FOUR);
                Position arrival = new Position(Column.TEN, Row.SIX);

                //when, then
                assertThatThrownBy(() -> pieces.move(departure, arrival))
                    .isInstanceOf(IllegalArgumentException.class);
            }

            @Test
            @DisplayName("Cannon이 궁성 안에 있지만, 대각 움직임이 불가능한 좌표에 있다면, 대각으로 움직여선 안 된다.")
            void when_cannon_inside_castle_and_cannot_move_diagonal_then_throw_exception() {
                //given
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.NINE, Row.FOUR), new Piece(Team.RED, PieceType.CANNON));
                temporaryPieces.put(new Position(Column.EIGHT, Row.FIVE), new Piece(Team.RED, PieceType.CHARIOT));
                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.NINE, Row.FOUR);
                Position arrival = new Position(Column.SEVEN, Row.SIX);

                //when, then
                assertThatThrownBy(() -> pieces.move(departure, arrival))
                    .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("Horse의 움직임을 테스트 한다.")
        class HorseRouteFinderMove {

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
                assertThat(pieces.findPieceBy(arrival).getType()).isEqualTo(PieceType.HORSE.name());
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
        class ElephantRouteFinderMove {

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
        class GeneralRouteFinderMove {

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
                assertThat(pieces.findPieceBy(arrival).getType()).isEqualTo(PieceType.GENERAL.name());
            }

            @Test
            @DisplayName("General이 궁성을 나가려고 한다면, 예외가 발생해야 한다.")
            void when_general_arrival_out_of_castle_then_throw_exception() {
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.ONE, Row.FOUR), new Piece(Team.RED, PieceType.GENERAL));
                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.ONE, Row.FOUR);
                Position arrival = new Position(Column.ONE, Row.THREE);
                assertThatThrownBy(() -> pieces.move(departure, arrival))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("해당 기물은 궁성 밖으로 이동할 수 없습니다.");
            }

            @Test
            @DisplayName("General이 대각 움직임이 가능한 위치라면, 대각선으로 움직일 수 있어야 한다. - 1, 4")
            void when_general_can_move_diagonal_1_4() {
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.ONE, Row.FOUR), new Piece(Team.RED, PieceType.GENERAL));
                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.ONE, Row.FOUR);
                Position arrival = new Position(Column.TWO, Row.FIVE);
                pieces.move(departure, arrival);
                assertThat(pieces.findPieceBy(arrival).getType()).isEqualTo(PieceType.GENERAL.name());
            }

            @Test
            @DisplayName("General이 대각 움직임이 가능한 위치라면, 대각선으로 움직일 수 있어야 한다. - 2, 5")
            void when_general_can_move_diagonal_2_5() {
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.TWO, Row.FIVE), new Piece(Team.RED, PieceType.GENERAL));
                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.TWO, Row.FIVE);
                Position arrival = new Position(Column.ONE, Row.FOUR);
                pieces.move(departure, arrival);
                assertThat(pieces.findPieceBy(arrival).getType()).isEqualTo(PieceType.GENERAL.name());
            }

            @Test
            @DisplayName("General이 대각 불가능한 위치에서 대각 움직임을 요구한다면, 예외를 발생시켜야 한다.")
            void when_general_cannot_move_diagonal_then_throw_exception() {
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.TWO, Row.FOUR), new Piece(Team.RED, PieceType.GENERAL));
                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.TWO, Row.FOUR);
                Position arrival = new Position(Column.ONE, Row.FIVE);
                assertThatThrownBy(() -> pieces.move(departure, arrival))
                    .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("Byeong의 움직임을 테스트 한다")
        class ByeongRouteFinderMove {

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
                assertThat(pieces.findPieceBy(arrival).getType()).isEqualTo(PieceType.BYEONG.name());
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

            @Test
            @DisplayName("병이 궁성 안에 있고, 대각선 움직임이 가능한 위치에 있을경우, 움직일 수 있어야 한다. - 8, 4")
            void when_byeong_inside_castle_can_move_diagonal_in_8_4() {
                //given
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.EIGHT, Row.FOUR), new Piece(Team.RED, PieceType.BYEONG));
                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.EIGHT, Row.FOUR);
                Position arrival = new Position(Column.NINE, Row.FIVE);

                //when
                pieces.move(departure, arrival);

                //then
                assertThat(pieces.findPieceBy(arrival).getType()).isEqualTo(PieceType.BYEONG.name());
            }

            @Test
            @DisplayName("병이 궁성 안에 있고, 대각선 움직임이 가능한 위치에 있을경우, 움직일 수 있어야 한다. - 8,6")
            void when_byeong_inside_castle_can_move_diagonal_in_8_6() {
                //given
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.EIGHT, Row.SIX), new Piece(Team.RED, PieceType.BYEONG));
                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.EIGHT, Row.SIX);
                Position arrival = new Position(Column.NINE, Row.FIVE);

                //when
                pieces.move(departure, arrival);

                //then
                assertThat(pieces.findPieceBy(arrival).getType()).isEqualTo(PieceType.BYEONG.name());
            }

            @Test
            @DisplayName("병이 궁성 안에 있고, 대각선 움직임이 가능한 위치에 있을경우, 움직일 수 있어야 한다. - 9, 5 (우측 대각)")
            void when_byeong_inside_castle_can_move_diagonal_in_9_5_and_diagonal_right() {
                //given
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.NINE, Row.FIVE), new Piece(Team.RED, PieceType.BYEONG));
                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.NINE, Row.FIVE);
                Position arrival = new Position(Column.TEN, Row.SIX);

                //when
                pieces.move(departure, arrival);

                //then
                assertThat(pieces.findPieceBy(arrival).getType()).isEqualTo(PieceType.BYEONG.name());
            }

            @Test
            @DisplayName("병이 궁성 안에 있고, 대각선 움직임이 가능한 위치에 있을경우, 움직일 수 있어야 한다. - 9, 5 (좌측 대각)")
            void when_byeong_inside_castle_can_move_diagonal_in_9_5_and_diagonal_left() {
                //given
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.NINE, Row.FIVE), new Piece(Team.RED, PieceType.BYEONG));
                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.NINE, Row.FIVE);
                Position arrival = new Position(Column.TEN, Row.FOUR);

                //when
                pieces.move(departure, arrival);

                //then
                assertThat(pieces.findPieceBy(arrival).getType()).isEqualTo(PieceType.BYEONG.name());
            }

            @Test
            @DisplayName("병의 대각 움직임은 궁성 안에서만 이뤄져야 한다.")
            void byeong_inside_castle_but_only_move_castle() {
                //given
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.EIGHT, Row.FOUR), new Piece(Team.RED, PieceType.BYEONG));
                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.EIGHT, Row.FOUR);
                Position arrival = new Position(Column.NINE, Row.THREE);

                //when, then
                assertThatThrownBy(() -> pieces.move(departure, arrival))
                    .isInstanceOf(IllegalArgumentException.class);
            }

            @Test
            @DisplayName("병이 궁성 안에 있을 경우, 뒤로 움직일 수는 없어야 한다.")
            void when_byeong_inside_castle_then_cannot_move_back() {
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.NINE, Row.FIVE), new Piece(Team.RED, PieceType.BYEONG));
                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.NINE, Row.FIVE);
                Position arrival = new Position(Column.EIGHT, Row.FOUR);

                //when, then
                assertThatThrownBy(() -> pieces.move(departure, arrival))
                    .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("Jol 움직임을 테스트 한다")
        class JolRouteFinderMove {

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
                assertThat(pieces.findPieceBy(arrival).getType()).isEqualTo(PieceType.JOL.name());
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

            @Test
            @DisplayName("졸이 궁성 안에 있고, 대각선 움직임이 가능한 위치에 있을경우, 움직일 수 있어야 한다. - 3, 4")
            void when_jol_inside_castle_can_move_diagonal_in_3_4() {
                //given
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.THREE, Row.FOUR), new Piece(Team.GREEN, PieceType.JOL));
                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.THREE, Row.FOUR);
                Position arrival = new Position(Column.TWO, Row.FIVE);

                //when
                pieces.move(departure, arrival);

                //then
                assertThat(pieces.findPieceBy(arrival).getType()).isEqualTo(PieceType.JOL.name());
            }

            @Test
            @DisplayName("졸이 궁성 안에 있고, 대각선 움직임이 가능한 위치에 있을경우, 움직일 수 있어야 한다. - 3,6")
            void when_jol_inside_castle_can_move_diagonal_in_3_6() {
                //given
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.THREE, Row.SIX), new Piece(Team.GREEN, PieceType.JOL));
                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.THREE, Row.SIX);
                Position arrival = new Position(Column.TWO, Row.FIVE);

                //when
                pieces.move(departure, arrival);

                //then
                assertThat(pieces.findPieceBy(arrival).getType()).isEqualTo(PieceType.JOL.name());
            }

            @Test
            @DisplayName("졸이 궁성 안에 있고, 대각선 움직임이 가능한 위치에 있을경우, 움직일 수 있어야 한다. - 2, 5 (우측 대각)")
            void when_jol_inside_castle_can_move_diagonal_in_2_5_and_diagonal_right() {
                //given
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.TWO, Row.FIVE), new Piece(Team.GREEN, PieceType.JOL));
                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.TWO, Row.FIVE);
                Position arrival = new Position(Column.ONE, Row.SIX);

                //when
                pieces.move(departure, arrival);

                //then
                assertThat(pieces.findPieceBy(arrival).getType()).isEqualTo(PieceType.JOL.name());
            }

            @Test
            @DisplayName("병이 궁성 안에 있고, 대각선 움직임이 가능한 위치에 있을경우, 움직일 수 있어야 한다. - 2, 5 (좌측 대각)")
            void when_jol_inside_castle_can_move_diagonal_in_2_5_and_diagonal_left() {
                //given
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.TWO, Row.FIVE), new Piece(Team.GREEN, PieceType.JOL));
                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.TWO, Row.FIVE);
                Position arrival = new Position(Column.ONE, Row.FOUR);

                //when
                pieces.move(departure, arrival);

                //then
                assertThat(pieces.findPieceBy(arrival).getType()).isEqualTo(PieceType.JOL.name());
            }

            @Test
            @DisplayName("졸의 대각 움직임은 궁성 안에서만 이뤄져야 한다.")
            void jol_inside_castle_but_only_move_castle() {
                //given
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.THREE, Row.FOUR), new Piece(Team.GREEN, PieceType.JOL));
                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.THREE, Row.FOUR);
                Position arrival = new Position(Column.TWO, Row.THREE);

                //when, then
                assertThatThrownBy(() -> pieces.move(departure, arrival))
                    .isInstanceOf(IllegalArgumentException.class);
            }

            @Test
            @DisplayName("졸이 궁성 안에 있을 경우, 뒤로 움직일 수는 없어야 한다.")
            void when_jol_inside_castle_then_cannot_move_back() {
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.TWO, Row.FIVE), new Piece(Team.GREEN, PieceType.JOL));
                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.TWO, Row.FIVE);
                Position arrival = new Position(Column.THREE, Row.FOUR);

                //when, then
                assertThatThrownBy(() -> pieces.move(departure, arrival))
                    .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("Guard 움직임을 테스트 한다")
        class GuardRouteFinderMove {

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

            @Test
            @DisplayName("Guard가 궁성을 나가려고 한다면, 예외가 발생해야 한다.")
            void when_guard_arrival_out_of_castle_then_throw_exception() {
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.ONE, Row.FOUR), new Piece(Team.RED, PieceType.GUARD));
                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.ONE, Row.FOUR);
                Position arrival = new Position(Column.ONE, Row.THREE);
                assertThatThrownBy(() -> pieces.move(departure, arrival))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("해당 기물은 궁성 밖으로 이동할 수 없습니다.");
            }

            @Test
            @DisplayName("Guard가 대각 움직임이 가능한 위치라면, 대각선으로 움직일 수 있어야 한다. - 1, 4")
            void when_guard_can_move_diagonal_1_4() {
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.ONE, Row.FOUR), new Piece(Team.RED, PieceType.GUARD));
                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.ONE, Row.FOUR);
                Position arrival = new Position(Column.TWO, Row.FIVE);
                pieces.move(departure, arrival);
                assertThat(pieces.findPieceBy(arrival).getType()).isEqualTo(PieceType.GUARD.name());
            }

            @Test
            @DisplayName("Guard가 대각 움직임이 가능한 위치라면, 대각선으로 움직일 수 있어야 한다. - 2, 5")
            void when_general_can_move_diagonal_2_5() {
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.TWO, Row.FIVE), new Piece(Team.RED, PieceType.GUARD));
                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.TWO, Row.FIVE);
                Position arrival = new Position(Column.ONE, Row.FOUR);
                pieces.move(departure, arrival);
                assertThat(pieces.findPieceBy(arrival).getType()).isEqualTo(PieceType.GUARD.name());
            }

            @Test
            @DisplayName("Guard가 대각 불가능한 위치에서 대각 움직임을 요구한다면, 예외를 발생시켜야 한다.")
            void when_general_cannot_move_diagonal_then_throw_exception() {
                Map<Position, Piece> temporaryPieces = new HashMap<>();
                temporaryPieces.put(new Position(Column.TWO, Row.FOUR), new Piece(Team.RED, PieceType.GUARD));
                Pieces pieces = new Pieces(temporaryPieces);
                Position departure = new Position(Column.TWO, Row.FOUR);
                Position arrival = new Position(Column.ONE, Row.FIVE);
                assertThatThrownBy(() -> pieces.move(departure, arrival))
                    .isInstanceOf(IllegalArgumentException.class);
            }
        }
    }
}
