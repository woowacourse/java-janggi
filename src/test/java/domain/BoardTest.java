package domain;

import static domain.piece.Team.HAN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import domain.board.Board;
import domain.board.BoardLocation;
import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Score;
import domain.piece.Team;
import fixture.BoardFixture;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    @DisplayName("보드판에는 궁 2개, 차 4개, 포 4개, 마 4개, 상 4개, 사 4개, 졸 10개를 갖는다")
    void createBoard() {
        // given & when
        BoardLocation hanHorseBoardLocation1 = new BoardLocation(2, 1);
        BoardLocation hanHorseBoardLocation2 = new BoardLocation(8, 1);
        BoardLocation hanElephantBoardLocation1 = new BoardLocation(3, 1);
        BoardLocation hanElephantBoardLocation2 = new BoardLocation(7, 1);

        BoardLocation choHorseBoardLocation1 = new BoardLocation(2, 10);
        BoardLocation choHorseBoardLocation2 = new BoardLocation(8, 10);
        BoardLocation choElephantBoardLocation1 = new BoardLocation(3, 10);
        BoardLocation choElephantBoardLocation2 = new BoardLocation(7, 10);

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
    void getByLocationOrThrow() {
        //given
        Board board = BoardFixture.createBoard();
        BoardLocation boardLocation = new BoardLocation(1, 1);

        //when
        Piece piece = board.getByLocationOrThrow(boardLocation);

        //then
        assertSoftly(softly -> {
            softly.assertThat(piece.getType()).isEqualTo(PieceType.CHARIOT);
            softly.assertThat(piece.getTeam()).isEqualTo(HAN);
        });
    }

    @DisplayName("기물이 이동할 경우, 현재 위치는 제거하고 목표 위치를 추가한다")
    @Test
    void occupy() {
        // given
        Board board = BoardFixture.createBoard();
        BoardLocation current = new BoardLocation(1, 1);
        Piece currentPiece = board.getPieces().get(current);
        BoardLocation destination = new BoardLocation(1, 3);

        // when
        board.occupy(current, destination);

        // then
        assertThat(board.getPieces().get(current)).isNull();
        assertThat(board.getPieces().get(destination)).isEqualTo(currentPiece);
    }

    @DisplayName("해당 경로 내에 위치하고 있는 모든 기물을 반환한다")
    @Test
    void extractPathPiece() {
        // given
        Board board = BoardFixture.createBoard();
        BoardLocation boardLocation1 = new BoardLocation(1, 1);
        BoardLocation boardLocation2 = new BoardLocation(4, 1);
        BoardLocation boardLocation3 = new BoardLocation(4, 2);

        List<BoardLocation> allPath = List.of(boardLocation1, boardLocation2, boardLocation3);

        // when
        List<Piece> pathPieces = board.extractPathPiece(allPath);

        // then
        assertSoftly(softly -> {
            softly.assertThat(pathPieces).hasSize(2);
            softly.assertThat(pathPieces).containsExactlyInAnyOrderElementsOf(
                    List.of(board.getPieces().get(boardLocation1), board.getPieces().get(boardLocation2)
            ));
        });
    }

    @DisplayName("입력된 팀 기물들의 총 점수를 반환한다")
    @Test
    void calculateScoreByTeam() {
        // given
        Team team = HAN;
        Map<BoardLocation, Piece> pieces = Map.of(
                new BoardLocation(1, 1), new King(team, new Score(13)),
                new BoardLocation(1, 2), new King(team, new Score(13)),
                new BoardLocation(1, 3), new King(team, new Score(13))
        );

        Board board = new Board(pieces);

        // when
        Score score = board.calculateScoreByTeam(team);

        // then
        assertThat(score.score()).isEqualTo(39);
    }

    @DisplayName("게임이 종료의 기준이 되는 기물의 개수는 2개라면 false를 반환한다")
    @Test
    void isGameStopped1() {
        //given
        Map<BoardLocation, Piece> pieces = Map.of(
                new BoardLocation(1, 1), new King(HAN, new Score(13)),
                new BoardLocation(1, 2), new King(HAN, new Score(13)),
                new BoardLocation(1, 3), new Horse(HAN)
        );

        Board board = new Board(pieces);

        //when
        boolean isGameStopped = board.isGameStopped();

        //then
        assertThat(isGameStopped).isFalse();
    }

    @DisplayName("게임이 종료의 기준이 되는 기물의 개수는 2개아니라면 true를 반환한다")
    @Test
    void isGameStopped2() {
        //given
        Map<BoardLocation, Piece> pieces = Map.of(
                new BoardLocation(1, 1), new King(HAN, new Score(13)),
                new BoardLocation(1, 3), new Horse(HAN)
        );

        Board board = new Board(pieces);

        //when
        boolean isGameStopped = board.isGameStopped();

        //then
        assertThat(isGameStopped).isTrue();
    }
}
