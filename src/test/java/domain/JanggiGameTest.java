package domain;

import dao.BoardDao;
import dao.JanggiConnection;
import dao.PlayerDao;
import dao.TeamDao;
import domain.board.BoardPoint;
import domain.pieces.Piece;
import entity.BoardRepository;
import entity.PlayerRepository;
import entity.TeamRepository;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import util.TestDatabaseInitializer;

class JanggiGameTest {
    @Nested
    @DisplayName("보드를 생성할 때")
    class TestGenerateBoard {

        @BeforeEach
        void init() {
            TestDatabaseInitializer.init();
        }

        @Test
        @DisplayName("row 는 최대 8, column 은 최대 9를 갖는다")
        void test_containsOnlyRowUnder8() {
            // given
            JanggiGame janggiGame = new JanggiGame(
                    List.of(new Player(Team.HAN), new Player(Team.CHO)),
                    new BoardRepository(new BoardDao(new JanggiConnection())),
                    new PlayerRepository(new PlayerDao(new JanggiConnection())),
                    new TeamRepository(new TeamDao(new JanggiConnection()))
            );

            // when
            Map<BoardPoint, Piece> board = janggiGame.getBoard();

            // then
            assertThat(board.keySet()).allSatisfy(point -> {
                assertThat(point.row()).isBetween(0, 9);
                assertThat(point.column()).isBetween(0, 8);
            });
        }
    }

    @Nested
    class TestMove {

        @BeforeEach
        void init() {
            TestDatabaseInitializer.init();
        }

        @Test
        @DisplayName("시작 지점에서부터 말을 이동시킨다")
        void test_move() {
            // given
            JanggiGame janggiGame = new JanggiGame(
                    List.of(new Player(Team.HAN), new Player(Team.CHO)),
                    new BoardRepository(new BoardDao(new JanggiConnection())),
                    new PlayerRepository(new PlayerDao(new JanggiConnection())),
                    new TeamRepository(new TeamDao(new JanggiConnection()))
            );
            BoardPoint startBoardPoint = new BoardPoint(0, 0);
            BoardPoint arrivalBoardPoint = new BoardPoint(1, 0);

            Piece originalPieceAtStartPoint = janggiGame.getBoard().get(startBoardPoint);

            // when
            janggiGame.move(startBoardPoint, arrivalBoardPoint);

            // then
            Piece pieceAtArrivalPoint = janggiGame.getBoard().get(arrivalBoardPoint);
            assertThat(originalPieceAtStartPoint).isEqualTo(pieceAtArrivalPoint);
        }
    }
}
