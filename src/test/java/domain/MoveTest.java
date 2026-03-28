package domain;

import domain.board.JanggiBoard;
import domain.fixture.TestIntersectionGenerator;
import domain.intersection.Intersection;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.point.Point;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MoveTest {

    @Test
    @DisplayName("이동이 끝난 뒤 출발지는 비어있고, 도착지는 기물이 존재한다.")
    void shouldMovePieceToDestinationAndLeaveSourceEmpty() {
        Team currentTurn = Team.CHO;
        Point start = new Point(0, 0);
        Point end = new Point(3, 0);

        Piece chariot = new Piece(currentTurn, PieceType.CHARIOT);
        Piece soldier = new Piece(Team.HAN, PieceType.SOLDIER);

        Intersection from = new Intersection(start, chariot);
        Intersection to = new Intersection(end, soldier);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(from, to)));
        janggiBoard.tryToMove(start, end, currentTurn);

        Intersection expectedEmpty = Intersection.empty(start);
        Intersection expectedChariot = new Intersection(end, chariot);

        Assertions.assertThat(from).isEqualTo(expectedEmpty);
        Assertions.assertThat(to).isEqualTo(expectedChariot);
    }

    @Test
    @DisplayName("상대팀의 기물을 움직이려고 하면 예외가 발생한다.")
    void shouldThrowExceptionTryToMoveOpponentPiece() {
        Team currentTurn = Team.CHO;
        Team opponentTeam = Team.HAN;
        Point start = new Point(0, 0);
        Point end = new Point(3, 0);

        Piece chariot = new Piece(currentTurn, PieceType.CHARIOT);
        Piece soldier = new Piece(opponentTeam, PieceType.SOLDIER);

        Intersection from = new Intersection(start, chariot);
        Intersection to = new Intersection(end, soldier);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(from, to)));

        Assertions.assertThatThrownBy(() -> {
                    janggiBoard.tryToMove(start, end, opponentTeam);
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상대방 기물은 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("기물이 없는 칸을 움직이려고하면 예외가 발생한다.")
    void shouldThrowExceptionTryToMoveEmptyIntersection() {
        Team currentTeam = Team.HAN;
        Point start = new Point(0, 0);
        Point end = new Point(3, 0);

        Piece empty = Piece.none();
        Piece soldier = new Piece(currentTeam, PieceType.SOLDIER);

        Intersection from = new Intersection(start, empty);
        Intersection to = new Intersection(end, soldier);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(from, to)));

        Assertions.assertThatThrownBy(() -> {
                    janggiBoard.tryToMove(start, end, currentTeam);
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("기물이 없어 움직일 수 없습니다.");
    }

}
