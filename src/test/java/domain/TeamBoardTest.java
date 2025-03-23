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
    @DisplayName("보드판에는 궁 2개, 차 4개, 포 4개, 마 4개, 상 4개, 사 4개, 졸 10개를 갖는다")
    void test() {
        // given & when
        BoardLocation hanHorseBoardLocation1 = new BoardLocation(2, 1);
        BoardLocation hanHorseBoardLocation2 = new BoardLocation(8,1);
        BoardLocation hanElephantBoardLocation1 = new BoardLocation(3,1);
        BoardLocation hanElephantBoardLocation2 = new BoardLocation(7,1);

        BoardLocation choHorseBoardLocation1 = new BoardLocation(2, 10);
        BoardLocation choHorseBoardLocation2 = new BoardLocation(8,10);
        BoardLocation choElephantBoardLocation1 = new BoardLocation(3,10);
        BoardLocation choElephantBoardLocation2 = new BoardLocation(7,10);

        List<BoardLocation> hanHorseBoardLocations = List.of(hanHorseBoardLocation1, hanHorseBoardLocation2);
        List<BoardLocation> hanElephantBoardLocations = List.of(hanElephantBoardLocation1, hanElephantBoardLocation2);
        List<BoardLocation> choHorseBoardLocations = List.of(choHorseBoardLocation1, choHorseBoardLocation2);
        List<BoardLocation> choElephantBoardLocations = List.of(choElephantBoardLocation1, choElephantBoardLocation2);

        Map<BoardLocation, Piece> teamPieces = BoardFixture.createTeamPieces(
                hanElephantBoardLocations,
                hanHorseBoardLocations,
                choElephantBoardLocations,
                choHorseBoardLocations);

        //when
        TeamBoard teamBoard = new TeamBoard(teamPieces);
        Map<BoardLocation, Piece> pieces = teamBoard.getPieces();

        // then
        assertThat(pieces).containsExactlyInAnyOrderEntriesOf(teamPieces);
    }

//    @Test
//    @DisplayName("한나라의 마와 상의 위치는 입력값으로 정한다")
//    void test2() {
//        // given & when
//        BoardLocation horseBoardLocation1 = new BoardLocation(2, 1);
//        BoardLocation horseBoardLocation2 = new BoardLocation(8,1);
//        BoardLocation elephantBoardLocation1 = new BoardLocation(3,1);
//        BoardLocation elephantBoardLocation2 = new BoardLocation(7,1);
//        List<BoardLocation> horseBoardLocations = List.of(horseBoardLocation1, horseBoardLocation2);
//        List<BoardLocation> elephantBoardLocations = List.of(elephantBoardLocation1, elephantBoardLocation2);
//
//        //when
//        Map<BoardLocation, Piece> hanPieces = BoardFixture.createTeamPieces(horseBoardLocations, elephantBoardLocations);
//
//        // then
//        Piece horsePiece1 = hanPieces.get(horseBoardLocation1);
//        Piece horsePiece2 = hanPieces.get(horseBoardLocation2);
//        Piece elephantPiece1 = hanPieces.get(elephantBoardLocation1);
//        Piece elephantPiece2 = hanPieces.get(elephantBoardLocation2);
//
//        SoftAssertions.assertSoftly(softly -> {
//            softly.assertThat(horsePiece1).isEqualTo(new Piece(PieceType.HORSE));
//            softly.assertThat(horsePiece2).isEqualTo(new Piece(PieceType.HORSE));
//            softly.assertThat(elephantPiece1).isEqualTo(new Piece(PieceType.ELEPHANT));
//            softly.assertThat(elephantPiece2).isEqualTo(new Piece(PieceType.ELEPHANT));
//        });
//    }

    @Test
    @DisplayName("보드내 위치로 기물을 찾을 수 있다")
    void test1() {
        //given
        TeamBoard teamBoard = new TeamBoard(BoardFixture.createTeamBoard());
        BoardLocation boardLocation = new BoardLocation(1, 1);

        //when
        Piece piece = teamBoard.findByLocation(boardLocation);

        //then
        assertThat(piece).isEqualTo(new Piece(PieceType.CHARIOT, Team.HAN));
    }

    @Test
    @DisplayName("이동 경로에 우리팀 기물이 존재할 경우 예외를 발생시킨다")
    void test2() {
        // given
        List<BoardLocation> allPath = List.of(new BoardLocation(1, 1));
        BoardLocation destination = new BoardLocation(1, 3);
        TeamBoard hanBoard = new TeamBoard(BoardFixture.createTeamBoard());

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
        TeamBoard hanBoard = new TeamBoard(BoardFixture.createTeamBoard());

        // when & then
        assertThatThrownBy(() -> {
            hanBoard.validateAllyMove(allPath, destination);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이동 경로에 기물이 존재할 경우 예외를 발생시킨다")
    void test4() {
        // given
        List<BoardLocation> allPath = List.of(new BoardLocation(1, 1));
        TeamBoard teamBoard = new TeamBoard(BoardFixture.createTeamBoard());

        // when & then
        assertThatThrownBy(() -> {
            teamBoard.validatePaths(allPath);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("기물이 이동할 경우, 현재 위치는 제거하고 목표 위치를 추가한다")
    @Test
    void test5() {
        // given
        Map<BoardLocation, Piece> teamPiece = BoardFixture.createTeamBoard();
        TeamBoard teamBoard = new TeamBoard(teamPiece);
        BoardLocation current = new BoardLocation(1, 1);
        Piece currentPiece = teamBoard.getPieces().get(current);
        BoardLocation destination = new BoardLocation(1, 3);

        // when
        teamBoard.move(current, destination);

        // then
        assertThat(teamBoard.getPieces().get(current)).isNull();
        assertThat(teamBoard.getPieces().get(destination)).isEqualTo(currentPiece);
    }

    @DisplayName("이동경로에 포가 아닌 기물이 1개가 아니라면 예외를 발생시킨다")
    @Test
    void test6() {
        // given
        List<BoardLocation> allPath = List.of(new BoardLocation(1, 1), new BoardLocation(4, 1));
        BoardLocation destination = new BoardLocation(1, 2);
        TeamBoard teamBoard = new TeamBoard(BoardFixture.createTeamBoard());

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
        TeamBoard teamBoard = new TeamBoard(BoardFixture.createTeamBoard());

        // when & then
        assertThatThrownBy(() -> {
            teamBoard.validateAllyMove(allPath, destination);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
