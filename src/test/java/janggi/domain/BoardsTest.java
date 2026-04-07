package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.status.Team;
import janggi.dto.PositionInfo;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardsTest {

    @Test
    @DisplayName("팀의 왕이 살아있으면 죽지 않은 상태다")
    void kingAlive() {
        Boards boards = initBoards(
                PositionInfo.from(Team.CHO, "JANG", 4, 1),
                PositionInfo.from(Team.HAN, "JANG", 4, 8)
        );

        assertThat(boards.isKingDie(Team.CHO)).isFalse();
        assertThat(boards.isKingDie(Team.HAN)).isFalse();
    }

    @Test
    @DisplayName("팀의 왕이 없으면 죽은 상태다")
    void kingDie() {
        Boards boards = initBoards(
                PositionInfo.from(Team.CHO, "JANG", 4, 1)
        );

        assertThat(boards.isKingDie(Team.HAN)).isTrue();
    }

    @Test
    @DisplayName("보드의 현재 상태를 저장용 목록으로 반환한다")
    void boardStatus() {
        Boards boards = initBoards(
                PositionInfo.from(Team.CHO, "JANG", 4, 1),
                PositionInfo.from(Team.HAN, "JANG", 4, 8)
        );

        Map<Point, Piece> boardStatus = boards.getBoardStatus();

        assertThat(boardStatus).hasSize(2);
        assertThat(boardStatus).containsKeys(Point.of(4, 1), Point.of(4, 8));
    }

    @Test
    @DisplayName("팀별 현재 기물 점수를 계산한다")
    void scoreOfTeam() {
        Boards boards = initBoards(
                PositionInfo.from(Team.CHO, "JANG", 4, 1),
                PositionInfo.from(Team.CHO, "CHA", 0, 0),
                PositionInfo.from(Team.CHO, "JOL", 0, 3),
                PositionInfo.from(Team.HAN, "JANG", 4, 8),
                PositionInfo.from(Team.HAN, "MA", 1, 9),
                PositionInfo.from(Team.HAN, "SA", 3, 9)
        );

        int choScore = boards.scoreOf(Team.CHO);
        int hanScore = boards.scoreOf(Team.HAN);

        assertThat(choScore).isEqualTo(9);
        assertThat(hanScore).isEqualTo(9);
    }

    @Test
    @DisplayName("보드 현재 기물 배치를 조회한다")
    void getPoints() {
        Boards boards = initBoards(
                PositionInfo.from(Team.CHO, "CHA", 0, 0),
                PositionInfo.from(Team.HAN, "JANG", 4, 8)
        );

        List<List<Piece>> points = boards.getPoints();

        assertThat(points).hasSize(10);
        assertThat(points.get(0)).hasSize(9);
        assertThat(points.get(0).get(0).getType()).isEqualTo(PieceType.CHA);
        assertThat(points.get(8).get(4).getType()).isEqualTo(PieceType.JANG);
        assertThat(points.get(1).get(0)).isNull();
    }

    private Boards initBoards(PositionInfo... positionInfos) {
        Board board = new Board();
        board.init(PositionInfo.toPiecesByPoint(List.of(positionInfos)));
        return new Boards(board);
    }
}
