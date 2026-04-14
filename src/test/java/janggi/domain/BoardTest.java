package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.piece.Jol;
import janggi.domain.team.TeamType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    @DisplayName("시작 위치가 장기판 범위를 벗어나면 이동할 수 없다.")
    void cannotMoveWhenStartPositionIsOutOfRange() {
        // given
        Board board = Board.createInitialBoard();
        Position outOfBound = new Position(0, 0);

        // when & then
        assertThatThrownBy(() -> board.validateMove(outOfBound, new Position(1, 4), TeamType.CHU))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("입력한 좌표가 장기판 범위 밖입니다.");
    }

    @Test
    @DisplayName("도착 위치가 장기판 범위를 벗어나면 이동할 수 없다.")
    void cannotMoveWhenEndPositionIsOutOfRange() {
        // given
        Board board = Board.createInitialBoard();
        Position outOfBound = new Position(1, 11);

        // when & then
        assertThatThrownBy(() -> board.validateMove(new Position(1, 4), outOfBound, TeamType.CHU))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("입력한 좌표가 장기판 범위 밖입니다.");
    }

    @Test
    @DisplayName("현재 팀의 기물이 없는 시작 위치에서 시작할 수 없다.")
    void cannotMoveWhenStartPositionHasNoCurrentTeamPiece() {
        // given
        Board board = Board.createInitialBoard();
        Position notExistPosition = new Position(2, 2);

        // when & then
        assertThatThrownBy(() -> board.validateMove(notExistPosition, new Position(1, 1), TeamType.CHU))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("입력한 위치에 기물이 없습니다.");
    }

    @Test
    @DisplayName("다른 팀의 기물이 있는 위치에서 시작할 수 없다.")
    void cannotStartAtOpponentTeamPosition() {
        // given
        Board board = Board.createInitialBoard();
        Position opponentTeamPosition = new Position(1, 7);

        // when & then
        assertThatThrownBy(() -> board.validateMove(opponentTeamPosition, new Position(1, 6), TeamType.CHU))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("입력한 위치에 기물이 없습니다.");
    }

    @Test
    @DisplayName("같은 팀의 기물이 있는 위치로는 이동할 수 없다.")
    void cannotMoveToSameTeamPiecePosition() {
        // given
        Board board = Board.createInitialBoard();
        Position currentTeamPosition = new Position(2, 1);

        // when & then
        assertThatThrownBy(() -> board.validateMove(new Position(1, 1), currentTeamPosition, TeamType.CHU))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("같은 팀의 기물이 있는 위치로는 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("이동 패턴에 맞지 않는 사의 이동은 장애물과 관계없이 막는다.")
    void cannotMoveWhenMovePatternIsInvalid() {
        // given
        Board board = Board.createInitialBoard();
        Position saStartPosition = new Position(4, 1);
        Position saEndPosition = new Position(4, 3);

        // when & then
        assertThatThrownBy(() -> board.validateMove(saStartPosition, saEndPosition, TeamType.CHU))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이동할 수 없는 위치입니다.");
    }

    @Test
    @DisplayName("궁은 궁성 대각선으로 이동할 수 있다.")
    void canMoveGungDiagonallyInsidePalace() {
        // given
        Board board = Board.createInitialBoard();

        // when & then
        assertThatCode(() -> board.validateMove(new Position(5, 2), new Position(4, 3), TeamType.CHU))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("궁은 궁성 밖으로 이동할 수 없다.")
    void cannotMoveGungOutsidePalace() {
        // given
        Board board = Board.createInitialBoard();

        // when & then
        assertThatThrownBy(() -> board.validateMove(new Position(5, 2), new Position(3, 2), TeamType.CHU))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이동할 수 없는 위치입니다.");
    }

    @Test
    @DisplayName("장애물이 있는 차의 이동은 막는다.")
    void cannotMoveChaWhenPathIsBlocked() {
        // given
        Board board = Board.createInitialBoard();
        Position chaStartPosition = new Position(1, 1);
        Position chaEndPosition = new Position(1, 5);

        // when & then
        assertThatThrownBy(() -> board.validateMove(chaStartPosition, chaEndPosition, TeamType.CHU))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이동할 수 없는 위치입니다.");
    }

    @Test
    @DisplayName("마의 경로 중간에 장애물이 있으면 이동할 수 없다.")
    void cannotMoveMaWhenLegIsBlocked() {
        // given
        Board board = Board.createInitialBoard();
        Position maStartPosition = new Position(2, 1);
        Position maEndPosition = new Position(4, 2);

        // when & then
        assertThatThrownBy(() -> board.validateMove(maStartPosition, maEndPosition, TeamType.CHU))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이동할 수 없는 위치입니다.");
    }

    @Test
    @DisplayName("포는 사이에 기물이 없으면 이동할 수 없다.")
    void cannotMovePoWithoutBridge() {
        // given
        Board board = Board.createInitialBoard();
        Position poStartPosition = new Position(2, 3);
        Position poEndPosition = new Position(2, 6);

        // when & then
        assertThatThrownBy(() -> board.validateMove(poStartPosition, poEndPosition, TeamType.CHU))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이동할 수 없는 위치입니다.");
    }

    @Test
    @DisplayName("졸은 궁성 안에서 전진 대각선으로 이동할 수 있다.")
    void canMoveJolDiagonallyInsidePalace() {
        // given
        Board board = Board.createInitialBoard();
        Board firstMovedBoard = board.move(new Position(5, 7), new Position(5, 6), TeamType.HAN);
        Board secondMovedBoard = firstMovedBoard.move(new Position(5, 6), new Position(5, 5), TeamType.HAN);
        Board thirdMovedBoard = secondMovedBoard.move(new Position(5, 5), new Position(5, 4), TeamType.HAN);
        Board fourthMovedBoard = thirdMovedBoard.move(new Position(5, 4), new Position(5, 3), TeamType.HAN);
        Board movedBoard = fourthMovedBoard.move(new Position(5, 3), new Position(4, 3), TeamType.HAN);

        // when & then
        assertThatCode(() -> movedBoard.validateMove(new Position(4, 3), new Position(5, 2), TeamType.HAN))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("궁성 대각선 경로가 막혀 있으면 차는 이동할 수 없다.")
    void cannotMoveChaDiagonallyInsidePalaceWhenRouteIsBlocked() {
        // given
        Board board = Board.createInitialBoard();
        Board firstMovedBoard = board.move(new Position(5, 2), new Position(5, 3), TeamType.CHU);
        Board secondMovedBoard = firstMovedBoard.move(new Position(4, 1), new Position(4, 2), TeamType.CHU);
        Board thirdMovedBoard = secondMovedBoard.move(new Position(1, 4), new Position(2, 4), TeamType.CHU);
        Board fourthMovedBoard = thirdMovedBoard.move(new Position(2, 3), new Position(2, 6), TeamType.CHU);
        Board fifthMovedBoard = fourthMovedBoard.move(new Position(4, 2), new Position(5, 2), TeamType.CHU);
        Board sixthMovedBoard = fifthMovedBoard.move(new Position(1, 1), new Position(1, 3), TeamType.CHU);
        Board seventhMovedBoard = sixthMovedBoard.move(new Position(1, 3), new Position(4, 3), TeamType.CHU);
        Board movedBoard = seventhMovedBoard.move(new Position(4, 3), new Position(4, 1), TeamType.CHU);

        // when & then
        assertThatThrownBy(() -> movedBoard.validateMove(new Position(4, 1), new Position(6, 3), TeamType.CHU))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이동할 수 없는 위치입니다.");
    }

    @Test
    @DisplayName("궁성 대각선 경로가 비어 있으면 차는 이동할 수 있다.")
    void canMoveChaDiagonallyInsidePalaceWhenRouteIsEmpty() {
        // given
        Board board = Board.createInitialBoard();
        Board firstMovedBoard = board.move(new Position(5, 2), new Position(5, 3), TeamType.CHU);
        Board secondMovedBoard = firstMovedBoard.move(new Position(4, 1), new Position(4, 2), TeamType.CHU);
        Board thirdMovedBoard = secondMovedBoard.move(new Position(1, 4), new Position(2, 4), TeamType.CHU);
        Board fourthMovedBoard = thirdMovedBoard.move(new Position(2, 3), new Position(2, 6), TeamType.CHU);
        Board fifthMovedBoard = fourthMovedBoard.move(new Position(4, 2), new Position(5, 2), TeamType.CHU);
        Board sixthMovedBoard = fifthMovedBoard.move(new Position(1, 1), new Position(1, 3), TeamType.CHU);
        Board seventhMovedBoard = sixthMovedBoard.move(new Position(1, 3), new Position(4, 3), TeamType.CHU);
        Board eighthMovedBoard = seventhMovedBoard.move(new Position(4, 3), new Position(4, 1), TeamType.CHU);
        Board movedBoard = eighthMovedBoard.move(new Position(5, 2), new Position(6, 2), TeamType.CHU);

        // when & then
        assertThatCode(() -> movedBoard.validateMove(new Position(4, 1), new Position(6, 3), TeamType.CHU))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("이동 패턴과 장애물 조건을 모두 만족하면 이동할 수 있다.")
    void canMoveWhenPatternAndObstacleRulesAreSatisfied() {
        // given
        Board board = Board.createInitialBoard();
        Position jolStartPosition = new Position(1, 4);
        Position jolEndPosition = new Position(2, 4);
        Board movedBoard = board.move(jolStartPosition, jolEndPosition, TeamType.CHU);
        Position chaStartPosition = new Position(1, 1);
        Position chaEndPosition = new Position(1, 3);
        Position poStartPosition = new Position(2, 3);
        Position poEndPosition = new Position(2, 6);

        // when & then
        assertAll(
            () -> assertThatCode(() -> movedBoard.validateMove(chaStartPosition, chaEndPosition, TeamType.CHU))
                .doesNotThrowAnyException(),
            () -> assertThatCode(() -> movedBoard.validateMove(poStartPosition, poEndPosition, TeamType.CHU))
                .doesNotThrowAnyException()
        );
    }

    @Test
    @DisplayName("기물을 이동하면 새 보드를 반환하고 원본 보드는 유지한다.")
    void moveReturnsNewBoardWithoutMutatingOriginalBoard() {
        // given
        Board board = Board.createInitialBoard();
        Position start = new Position(1, 4);
        Position end = new Position(1, 5);

        // when
        Board movedBoard = board.move(start, end, TeamType.CHU);

        // then
        assertAll(
            () -> assertThat(board.findPiece(start)).get().isInstanceOf(Jol.class),
            () -> assertThat(board.findPiece(end)).isEmpty(),
            () -> assertThat(movedBoard.findPiece(start)).isEmpty(),
            () -> assertThat(movedBoard.findPiece(end)).get().isInstanceOf(Jol.class)
        );
    }

    @Test
    @DisplayName("상대 기물이 있는 칸으로 이동하면 상대 기물을 잡는다.")
    void moveCapturesOpponentPiece() {
        // given
        Board board = Board.createInitialBoard();

        // when
        Board firstMovedBoard = board.move(new Position(1, 4), new Position(1, 5), TeamType.CHU);
        Board secondMovedBoard = firstMovedBoard.move(new Position(1, 5), new Position(1, 6), TeamType.CHU);
        Board capturedBoard = secondMovedBoard.move(new Position(1, 6), new Position(1, 7), TeamType.CHU);

        // then
        assertAll(
            () -> assertThat(secondMovedBoard.findPiece(new Position(1, 7))).get().isInstanceOf(Jol.class),
            () -> assertThat(capturedBoard.findPiece(new Position(1, 6))).isEmpty(),
            () -> assertThat(capturedBoard.findPiece(new Position(1, 7))).get().isInstanceOf(Jol.class)
        );
    }

    @Test
    @DisplayName("궁이 잡히면 승자를 확인할 수 있다.")
    void findWinnerWhenGungIsCaptured() {
        // given
        Board board = Board.createInitialBoard();

        // when
        Board firstMovedBoard = board.move(new Position(1, 4), new Position(2, 4), TeamType.CHU);
        Board secondMovedBoard = firstMovedBoard.move(new Position(1, 1), new Position(1, 4), TeamType.CHU);
        Board thirdMovedBoard = secondMovedBoard.move(new Position(1, 4), new Position(1, 7), TeamType.CHU);
        Board fourthMovedBoard = thirdMovedBoard.move(new Position(1, 7), new Position(1, 9), TeamType.CHU);
        Board capturedBoard = fourthMovedBoard.move(new Position(1, 9), new Position(5, 9), TeamType.CHU);

        // then
        assertAll(
            () -> assertThat(capturedBoard.hasGung(TeamType.HAN)).isFalse(),
            () -> assertThat(capturedBoard.findWinner()).contains(TeamType.CHU)
        );
    }

    @Test
    @DisplayName("초기 보드에서 팀의 기물 점수를 계산할 수 있다.")
    void calculateInitialScore() {
        // given
        Board board = Board.createInitialBoard();

        // when & then
        assertAll(
            () -> assertThat(board.calculateScore(TeamType.CHU)).isEqualTo(72),
            () -> assertThat(board.calculateScore(TeamType.HAN)).isEqualTo(72)
        );
    }

    @Test
    @DisplayName("상대 기물을 잡은 뒤 팀의 기물 점수를 계산할 수 있다.")
    void calculateScoreAfterCapture() {
        // given
        Position hanJolPosition = new Position(1, 7);
        Board board = Board.createInitialBoard();
        Board firstMovedBoard = board.move(new Position(1, 4), new Position(1, 5), TeamType.CHU);
        Board secondMovedBoard = firstMovedBoard.move(new Position(1, 5), new Position(1, 6), TeamType.CHU);
        Board capturedBoard = secondMovedBoard.move(new Position(1, 6), hanJolPosition, TeamType.CHU);

        // when & then
        assertAll(
            () -> assertThat(capturedBoard.calculateScore(TeamType.CHU)).isEqualTo(72),
            () -> assertThat(capturedBoard.calculateScore(TeamType.HAN)).isEqualTo(70)
        );
    }
}
