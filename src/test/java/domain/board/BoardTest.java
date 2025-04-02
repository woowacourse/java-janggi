package domain.board;

import domain.piece.Cha;
import domain.piece.Piece;
import domain.piece.character.Team;
import domain.point.Direction;
import domain.point.Path;
import domain.point.Point;
import fixture.BoardFixture;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BoardTest {

    @Nested
    @DisplayName("예외가 발생하지 않는 테스트")
    class Success {

        @Test
        void 특정_위치에_기물이_존재하면_true를_반환한다() {
            // given
            Point point = Point.of(1, 1);

            Map<Point, Piece> pieceByPoint = new HashMap<>();
            pieceByPoint.put(point, new Cha(Team.CHO));
            Board board = BoardFixture.createTestBoard(pieceByPoint);

            // when
            final boolean actual = board.existsPiece(point);

            // then
            Assertions.assertThat(actual).isTrue();
        }

        @Test
        void 특정_위치에_기물이_존재하지_않으면_false를_반환한다() {
            // given
            Point point = Point.of(1, 1);

            Map<Point, Piece> pieceByPoint = new HashMap<>();
            pieceByPoint.put(point, new Cha(Team.CHO));
            Board board = BoardFixture.createTestBoard(pieceByPoint);

            Point otherPoint = Point.of(5, 5);

            // when
            final boolean actual = board.existsPiece(otherPoint);

            // then
            Assertions.assertThat(actual).isFalse();
        }

        @Test
        void 특정_위치에_특정_팀인_기물이_존재하면_true를_반환한다() {
            // given
            Team team = Team.CHO;
            Point point = Point.of(1, 1);

            Map<Point, Piece> pieceByPoint = new HashMap<>();
            pieceByPoint.put(point, new Cha(team));
            Board board = BoardFixture.createTestBoard(pieceByPoint);

            // when
            final boolean actual = board.matchTeam(point, team);

            // then
            Assertions.assertThat(actual).isTrue();
        }

        @Test
        void 특정_위치에_특정_팀인_기물이_존재하지_않으면_false를_반환한다() {
            // given
            Team team = Team.CHO;
            Point point = Point.of(1, 1);

            Map<Point, Piece> pieceByPoint = new HashMap<>();
            pieceByPoint.put(point, new Cha(team.inverse()));
            Board board = BoardFixture.createTestBoard(pieceByPoint);

            // when
            final boolean actual = board.matchTeam(point, team);

            // then
            Assertions.assertThat(actual).isFalse();
        }


        @ParameterizedTest
        @CsvSource(value = {
                "1,1,UP,false", "1,1,RIGHT,true", "1,1,DOWN,true", "1,1,LEFT,false",
                "1,9,UP,false", "1,9,RIGHT,false", "1,9,DOWN,true", "1,9,LEFT,true",
                "10,1,UP,true", "10,1,RIGHT,true", "10,1,DOWN,false", "10,1,LEFT,false",
                "10,9,UP,true", "10,9,RIGHT,false", "10,9,DOWN,false", "10,9,LEFT,true"
        })
        void 특정_위치에서_특정_방향으로의_이동_경로가_있으면_true_없으면_false를_반환한다(int row, int column, Direction direction,
                                                              boolean expected) {
            // given
            Point source = Point.of(row, column);
            Map<Point, Piece> pieceByPoint = new HashMap<>();
            Board board = BoardFixture.createTestBoard(pieceByPoint);

            // when
            final boolean actual = board.existsNextPoint(source, direction);

            // then
            Assertions.assertThat(actual).isEqualTo(expected);
        }

        @ParameterizedTest
        @CsvSource(value = {
                "1,1,UP_PATH,false", "1,1,RIGHT_PATH,true", "1,1,DOWN_PATH,true", "1,1,LEFT_PATH,false",
                "1,9,UP_PATH,false", "1,9,RIGHT_PATH,false", "1,9,DOWN_PATH,true", "1,9,LEFT_PATH,true",
                "10,1,UP_PATH,true", "10,1,RIGHT_PATH,true", "10,1,DOWN_PATH,false", "10,1,LEFT_PATH,false",
                "10,9,UP_PATH,true", "10,9,RIGHT_PATH,false", "10,9,DOWN_PATH,false", "10,9,LEFT_PATH,true",

                "1,1,DOWN_DOWN_RIGHT_PATH,true",
                "1,1,DOWN_DOWN_LEFT_PATH,false",
                "1,1,RIGHT_RIGHT_DOWN_PATH,true",
                "1,1,RIGHT_RIGHT_UP_PATH,false",

                "1,9,LEFT_LEFT_DOWN_PATH,true",
                "1,9,LEFT_LEFT_UP_PATH,false",
                "1,9,DOWN_DOWN_LEFT_PATH,true",
                "1,9,DOWN_DOWN_RIGHT_PATH,false",

                "10,1,UP_UP_RIGHT_PATH,true",
                "10,1,UP_UP_LEFT_PATH,false",
                "10,1,RIGHT_RIGHT_UP_PATH,true",
                "10,1,RIGHT_RIGHT_DOWN_PATH,false",

                "10,9,LEFT_LEFT_UP_PATH,true",
                "10,9,LEFT_LEFT_DOWN_PATH,false",
                "10,9,UP_UP_LEFT_PATH,true",
                "10,9,UP_UP_RIGHT_PATH,false"
        })
        void 특정_위치에서_특정_경로를_따라_이동했을_때_격자_내의_위치면_true_격자_밖으로_벗어나면_false를_반환한다(int row, int column, Path path,
                                                                             boolean expected) {
            // given
            Point source = Point.of(row, column);
            Map<Point, Piece> pieceByPoint = new HashMap<>();
            Board board = BoardFixture.createTestBoard(pieceByPoint);

            // when
            final boolean actual = board.canMoveByPath(source, path);

            // then
            Assertions.assertThat(actual).isEqualTo(expected);
        }
    }
}
