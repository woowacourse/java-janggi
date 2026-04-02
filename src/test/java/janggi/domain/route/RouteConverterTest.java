package janggi.domain.route;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Board;
import janggi.domain.common.Position;
import janggi.domain.common.Team;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RouteConverterTest {
    RouteConverter routeConverter = new RouteConverter();

    @Test
    @DisplayName("초나라 졸의 왼쪽 방향이 보드 범위를 넘어서 불가능한 좌표이다")
    void 초나라_졸_보드_범위_내_경로_가능() {
        // given
        Board board = new Board();
        Position position = new Position(1, 5);
        Piece piece = new Piece(Team.CHO, PieceType.ZOL);
        board.place(position, piece);

        // when
        Map<Position, List<Position>> result = routeConverter.convertToPosition(board, position);

        // then
        assertThat(result.keySet()).containsExactlyInAnyOrder(
                new Position(1, 4), new Position(2, 5)
        );
    }

    @Test
    @DisplayName("초나라 졸의 세 방향 모두 보드 범위 내에 있다")
    void 초나라_졸_모든_경로_가능() {
        // given
        Board board = new Board();
        Position position = new Position(2, 5);
        Piece piece = new Piece(Team.CHO, PieceType.ZOL);
        board.place(position, piece);

        // when
        Map<Position, List<Position>> result = routeConverter.convertToPosition(board, position);

        // then
        assertThat(result.keySet()).containsExactlyInAnyOrder(
                new Position(1, 5), new Position(2, 4), new Position(3, 5)
        );
    }

    @Test
    @DisplayName("한나라 졸의 세 방향 모두 보드 범위 내에 있다")
    void 한나라_졸_모든_경로_가능() {
        // given
        Board board = new Board();
        Position position = new Position(3, 4);
        Piece piece = new Piece(Team.HAN, PieceType.ZOL);
        board.place(position, piece);

        // when
        Map<Position, List<Position>> result = routeConverter.convertToPosition(board, position);

        // then
        assertThat(result.keySet()).containsExactlyInAnyOrder(
                new Position(3, 5), new Position(2, 4), new Position(4, 4)
        );
    }

    @Test
    @DisplayName("마의 8방향 모두 보드 범위 내에 있다")
    void 마_모든_경로_가능() {
        // given
        Board board = new Board();
        Position position = new Position(4, 6);
        Piece piece = new Piece(Team.CHO, PieceType.MA);
        board.place(position, piece);

        // when
        Map<Position, List<Position>> result = routeConverter.convertToPosition(board, position);

        // then
        assertThat(result.keySet()).containsExactlyInAnyOrder(
                new Position(3, 4), new Position(5, 4), new Position(6, 5), new Position(6, 7),
                new Position(3, 8), new Position(5, 8), new Position(2, 5), new Position(2, 7)
        );
    }

    @Test
    @DisplayName("상의 8방향 모두 보드 범위 내에 있다")
    void 상_모든_경로_가능() {
        // given
        Board board = new Board();
        Position position = new Position(5, 7);
        Piece piece = new Piece(Team.CHO, PieceType.SANG);
        board.place(position, piece);

        // when
        Map<Position, List<Position>> result = routeConverter.convertToPosition(board, position);

        // then
        assertThat(result.keySet()).containsExactlyInAnyOrder(
                new Position(3, 4), new Position(7, 4), new Position(8, 5), new Position(8, 9),
                new Position(3, 10), new Position(7, 10), new Position(2, 5), new Position(2, 9)
        );
    }

    @Test
    @DisplayName("사의 8방향 모두 보드 범위 내에 있다")
    void 사_모든_경로_가능() {
        // given
        Board board = new Board();
        Position position = new Position(5, 9);
        Piece piece = new Piece(Team.CHO, PieceType.SA);
        board.place(position, piece);

        // when
        Map<Position, List<Position>> result = routeConverter.convertToPosition(board, position);

        // then
        assertThat(result.keySet()).containsExactlyInAnyOrder(
                new Position(5, 8), new Position(6, 9), new Position(5, 10), new Position(4, 9),
                new Position(4, 8), new Position(6, 8), new Position(6, 10), new Position(4, 10)
        );
    }

    @Test
    @DisplayName("왕의 8방향 모두 보드 범위 내에 있다")
    void 왕_모든_경로_가능() {
        // given
        Board board = new Board();
        Position position = new Position(5, 9);
        Piece piece = new Piece(Team.CHO, PieceType.KING);
        board.place(position, piece);

        // when
        Map<Position, List<Position>> result = routeConverter.convertToPosition(board, position);

        // then
        assertThat(result.keySet()).containsExactlyInAnyOrder(
                new Position(5, 8), new Position(6, 9), new Position(5, 10), new Position(4, 9),
                new Position(4, 8), new Position(6, 8), new Position(6, 10), new Position(4, 10)
        );
    }

    @Test
    @DisplayName("차는 현재 위치에서 보드 범위 내에서 상하좌우로 계속 갈 수 있다")
    void 차_보드_범위_내_상하좌우_계속_가능() {
        // given
        Board board = new Board();
        Position position = new Position(2, 8);
        Piece piece = new Piece(Team.HAN, PieceType.CHA);
        board.place(position, piece);

        // when
        Map<Position, List<Position>> result = routeConverter.convertToPosition(board, position);

        // then
        assertThat(result.keySet()).containsExactlyInAnyOrder(
                new Position(2, 1), new Position(2, 2), new Position(2, 3), new Position(2, 4),
                new Position(2, 5), new Position(2, 6), new Position(2, 7),
                new Position(2, 9), new Position(2, 10),

                new Position(1, 8),
                new Position(3, 8), new Position(4, 8), new Position(5, 8), new Position(6, 8),
                new Position(7, 8), new Position(8, 8), new Position(9, 8)
        );
    }

    @Test
    @DisplayName("포는 현재 위치에서 보드 범위 내에서 상하좌우로 계속 갈 수 있다")
    void 포_보드_범위_내_상하좌우_계속_가능() {
        // given
        Board board = new Board();
        Position position = new Position(2, 8);
        Piece piece = new Piece(Team.HAN, PieceType.PO);
        board.place(position, piece);

        // when
        Map<Position, List<Position>> result = routeConverter.convertToPosition(board, position);

        // then
        assertThat(result.keySet()).containsExactlyInAnyOrder(
                new Position(2, 1), new Position(2, 2), new Position(2, 3), new Position(2, 4),
                new Position(2, 5), new Position(2, 6), new Position(2, 7),
                new Position(2, 9), new Position(2, 10),

                new Position(1, 8),
                new Position(3, 8), new Position(4, 8), new Position(5, 8), new Position(6, 8),
                new Position(7, 8), new Position(8, 8), new Position(9, 8)
        );
    }
}
