package domain;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.board.BoardLocation;
import domain.piece.Chariot;
import domain.piece.Piece;
import domain.piece.Team;
import fixture.BoardFixture;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

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
        Board board = new Board(teamPieces);
        Map<BoardLocation, Piece> pieces = board.getPieces();

        // then
        assertThat(pieces).containsExactlyInAnyOrderEntriesOf(teamPieces);
    }

    @Test
    @DisplayName("보드내 위치로 기물을 찾을 수 있다")
    void test1() {
        //given
        Board board = new Board(BoardFixture.createTeamBoard());
        BoardLocation boardLocation = new BoardLocation(1, 1);

        //when
        Piece piece = board.getByLocationOrThrow(boardLocation);

        //then
        assertThat(piece).isEqualTo(new Chariot(Team.HAN));
    }

    @DisplayName("기물이 이동할 경우, 현재 위치는 제거하고 목표 위치를 추가한다")
    @Test
    void test5() {
        // given
        Map<BoardLocation, Piece> teamPiece = BoardFixture.createTeamBoard();
        Board board = new Board(teamPiece);
        BoardLocation current = new BoardLocation(1, 1);
        Piece currentPiece = board.getPieces().get(current);
        BoardLocation destination = new BoardLocation(1, 3);

        // when
        board.occupy(current, destination);

        // then
        assertThat(board.getPieces().get(current)).isNull();
        assertThat(board.getPieces().get(destination)).isEqualTo(currentPiece);
    }
}
