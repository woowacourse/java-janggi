package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.status.Team;
import janggi.dto.PositionInfo;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    @DisplayName("출발지에 기물이 없으면 예외가 발생한다")
    void moveFromEmptyPoint() {
        Board board = initBoard(
                PositionInfo.from(Team.CHO, "JANG", 4, 1),
                PositionInfo.from(Team.HAN, "JANG", 4, 8)
        );

        assertThatThrownBy(() -> board.move(Point.of(0, 0), Point.of(0, 1), Team.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("출발지에 이동할 기물이 없습니다.");
    }

    @Test
    @DisplayName("상대 기물은 움직일 수 없다")
    void moveOtherTeamPiece() {
        Board board = initBoard(
                PositionInfo.from(Team.CHO, "JANG", 4, 1),
                PositionInfo.from(Team.HAN, "JANG", 4, 8),
                PositionInfo.from(Team.HAN, "CHA", 0, 0)
        );

        assertThatThrownBy(() -> board.move(Point.of(0, 0), Point.of(0, 1), Team.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상대방의 기물은 움직일 수 없습니다.");
    }

    @Test
    @DisplayName("도착지에 아군 기물이 있으면 예외가 발생한다")
    void moveToSameTeamPiece() {
        Board board = initBoard(
                PositionInfo.from(Team.CHO, "JANG", 4, 1),
                PositionInfo.from(Team.HAN, "JANG", 4, 8),
                PositionInfo.from(Team.CHO, "CHA", 0, 0),
                PositionInfo.from(Team.CHO, "JOL", 0, 1)
        );

        assertThatThrownBy(() -> board.move(Point.of(0, 0), Point.of(0, 1), Team.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("도착지에 본인의 기물이 있습니다.");
    }

    @Test
    @DisplayName("이동 경로에 장애물이 있으면 예외가 발생한다")
    void moveBlockedRoute() {
        Board board = initBoard(
                PositionInfo.from(Team.CHO, "JANG", 4, 1),
                PositionInfo.from(Team.HAN, "JANG", 4, 8),
                PositionInfo.from(Team.CHO, "CHA", 0, 0),
                PositionInfo.from(Team.CHO, "JOL", 0, 1)
        );

        assertThatThrownBy(() -> board.move(Point.of(0, 0), Point.of(0, 3), Team.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동 경로에 장애물이 있거나 규칙에 어긋납니다.");
    }

    @Test
    @DisplayName("포는 상대 포를 잡을 수 없다")
    void phoCanNotCapturePho() {
        Board board = initBoard(
                PositionInfo.from(Team.CHO, "JANG", 4, 1),
                PositionInfo.from(Team.HAN, "JANG", 4, 8),
                PositionInfo.from(Team.CHO, "PHO", 1, 2),
                PositionInfo.from(Team.HAN, "CHA", 1, 4),
                PositionInfo.from(Team.HAN, "PHO", 1, 6)
        );

        assertThatThrownBy(() -> board.move(Point.of(1, 2), Point.of(1, 6), Team.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 타겟을 잡을 수 없습니다.");
    }

    @Test
    @DisplayName("기물을 이동하면 출발지는 비고 도착지에 기물이 위치한다")
    void movePiece() {
        Board board = initBoard(
                PositionInfo.from(Team.CHO, "JANG", 4, 1),
                PositionInfo.from(Team.HAN, "JANG", 4, 8),
                PositionInfo.from(Team.CHO, "CHA", 0, 0)
        );

        board.move(Point.of(0, 0), Point.of(0, 3), Team.CHO);

        assertThat(pieceAt(board, 0, 0)).isNull();
        assertThat(pieceAt(board, 0, 3).getType()).isEqualTo(PieceType.CHA);
        assertThat(pieceAt(board, 0, 3).isSameTeam(Team.CHO)).isTrue();
    }

    @Test
    @DisplayName("적 기물이 있는 위치로 이동하면 포획한다")
    void capturePiece() {
        Board board = initBoard(
                PositionInfo.from(Team.CHO, "JANG", 4, 1),
                PositionInfo.from(Team.HAN, "JANG", 4, 8),
                PositionInfo.from(Team.CHO, "CHA", 0, 0),
                PositionInfo.from(Team.HAN, "JOL", 0, 3)
        );

        board.move(Point.of(0, 0), Point.of(0, 3), Team.CHO);

        assertThat(pieceAt(board, 0, 0)).isNull();
        assertThat(pieceAt(board, 0, 3).getType()).isEqualTo(PieceType.CHA);
        assertThat(pieceAt(board, 0, 3).isSameTeam(Team.CHO)).isTrue();
    }

    @Test
    @DisplayName("팀의 왕이 살아있으면 죽지 않은 상태다")
    void kingAlive() {
        Board board = initBoard(
                PositionInfo.from(Team.CHO, "JANG", 4, 1),
                PositionInfo.from(Team.HAN, "JANG", 4, 8)
        );

        assertThat(board.isKingDie(Team.CHO)).isFalse();
        assertThat(board.isKingDie(Team.HAN)).isFalse();
    }

    @Test
    @DisplayName("팀의 왕이 없으면 죽은 상태다")
    void kingDie() {
        Board board = initBoard(
                PositionInfo.from(Team.CHO, "JANG", 4, 1)
        );

        assertThat(board.isKingDie(Team.HAN)).isTrue();
    }

    @Test
    @DisplayName("보드의 현재 상태 반환")
    void boardStatus() {
        // given
        Board board = initBoard(
                PositionInfo.from(Team.CHO, "JANG", 4, 1),
                PositionInfo.from(Team.HAN, "JANG", 4, 8)
        );

        // when
        List<PositionInfo> boardStatus = board.getBoardStatus();

        // then
        assertThat(boardStatus.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("팀별 현재 기물 점수 계산")
    void score_of_team() {
        // given
        Board board = initBoard(
                PositionInfo.from(Team.CHO, "JANG", 4, 1),
                PositionInfo.from(Team.CHO, "CHA", 0, 0),
                PositionInfo.from(Team.CHO, "JOL", 0, 3),
                PositionInfo.from(Team.HAN, "JANG", 4, 8),
                PositionInfo.from(Team.HAN, "MA", 1, 9),
                PositionInfo.from(Team.HAN, "SA", 3, 9)
        );

        // when
        int choScore = board.scoreOf(Team.CHO);
        int hanScore = board.scoreOf(Team.HAN);

        // then
        assertThat(choScore).isEqualTo(9);
        assertThat(hanScore).isEqualTo(9);
    }

    private Board initBoard(PositionInfo... positionInfos) {
        Board board = new Board();
        board.init(List.of(positionInfos));
        return board;
    }

    private Piece pieceAt(Board board, int x, int y) {
        return board.getPoints().get(y).get(x);
    }
}
