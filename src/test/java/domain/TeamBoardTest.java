package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.Piece;
import domain.piece.PieceType;
import fixture.BoardFixture;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TeamBoardTest {

    @Test
    @DisplayName("보드내 위치로 기물을 찾을 수 있다")
    void test1() {
        //given
        TeamBoard teamBoard = new HanBoard(BoardFixture.createTeamBoard());
        BoardLocation boardLocation = new BoardLocation(1, 1);

        //when
        Piece piece = teamBoard.findByLocation(boardLocation);

        //then
        assertThat(piece).isEqualTo(new Piece(PieceType.CHARIOT));
    }

    @Test
    @DisplayName("이동 경로에 우리팀 기물이 존재할 경우 예외를 발생시킨다")
    void test2() {
        // given
        List<BoardLocation> allPath = List.of(new BoardLocation(1, 1));
        BoardLocation destination = new BoardLocation(1, 3);
        TeamBoard hanBoard = new HanBoard(BoardFixture.createTeamBoard());

        // when & then
        assertThatThrownBy(() -> {
            hanBoard.validateAllyMove(allPath, destination);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("목적지에 우리팀 기물이 존재할 경우 예외를 발생시킨다")
    void test3() {
        // given
        List<BoardLocation> allPath = List.of(new BoardLocation(1, 3));
        BoardLocation destination = new BoardLocation(1, 1);
        TeamBoard hanBoard = new HanBoard(BoardFixture.createTeamBoard());

        // when & then
        assertThatThrownBy(() -> {
            hanBoard.validateAllyMove(allPath, destination);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이동 경로에 적팀 기물이 존재할 경우 예외를 발생시킨다")
    void test4() {
        // given
        List<BoardLocation> allPath = List.of(new BoardLocation(1, 1));
        TeamBoard hanBoard = new HanBoard(BoardFixture.createTeamBoard());

        // when & then
        assertThatThrownBy(() -> {
            hanBoard.validateEnemyMove(allPath);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("기물이 이동할 경우, 현재 위치는 제거하고 목표 위치를 추가한다")
    @Test
    void test5() {
        // given
        Map<BoardLocation, Piece> teamBoard = BoardFixture.createTeamBoard();
        TeamBoard hanBoard = new HanBoard(teamBoard);
        BoardLocation current = new BoardLocation(1, 1);
        Piece currentPiece = teamBoard.get(current);
        BoardLocation destination = new BoardLocation(1, 3);

        // when
        hanBoard.move(current, destination);

        // then
        assertThat(teamBoard.get(current)).isNull();
        assertThat(teamBoard.get(destination)).isEqualTo(currentPiece);
    }

    @DisplayName("이동경로에 포가 아닌 기물이 1개가 아니라면 예외를 발생시킨다")
    @Test
    void test6() {
        // given
        List<BoardLocation> allPath = List.of(new BoardLocation(1, 1), new BoardLocation(4, 1));
        BoardLocation destination = new BoardLocation(1, 2);
        TeamBoard teamBoard = new HanBoard(BoardFixture.createTeamBoard());

        // when & then
        assertThatThrownBy(() -> {
            teamBoard.validateAllyMove(allPath, destination);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이동경로에 포가 아닌 기물이 1개지만 목적지에 포가 있다면 예외를 발생시킨다")
    @Test
    void test8() {
        // given
        List<BoardLocation> allPath = List.of(new BoardLocation(1, 1));
        BoardLocation destination = new BoardLocation(2, 3);
        TeamBoard teamBoard = new HanBoard(BoardFixture.createTeamBoard());

        // when & then
        assertThatThrownBy(() -> {
            teamBoard.validateAllyMove(allPath, destination);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
