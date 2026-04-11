package model.policy;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.List;
import java.util.stream.Stream;
import model.board.Board;
import model.board.Country;
import model.move.Move;
import model.pieces.Piece;
import model.pieces.PieceType;
import model.position.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CannonPathPolicyTest {
    Board board = new Board();
    CannonPathPolicy policy = new CannonPathPolicy();

    @Test
    void 포는_기물을_정확히_하나_넘어야_한다() {
        List<Position> emptyPath = List.of(Position.of(1, 3), Position.of(1, 4));

        assertThatThrownBy(() -> policy.validatePath(emptyPath, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 포는 기물을 1개 뛰어넘어야 합니다.");
    }

    @Test
    void 포는_포를_뛰어넘을_수_없다() {
        List<Position> pathWithCannon = List.of(Position.of(1, 3));
        board.place(Position.of(1, 3), new Piece(Country.HAN, PieceType.CANNON));

        assertThatThrownBy(() -> policy.validatePath(pathWithCannon, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 포는 포를 뛰어넘을 수 없습니다.");
    }

    @ParameterizedTest
    @MethodSource("provideInvalidDestinations")
    void 목적지_도착_불가_케이스(Position to, Country myCountry, String errorMsg) {
        Move move = new Move(Position.of(1, 2), to);
        board.place(Position.of(1, 5), new Piece(Country.CHO, PieceType.CANNON));
        board.place(Position.of(1, 6), new Piece(Country.HAN, PieceType.CANNON));

        assertThatThrownBy(() -> policy.validateDestination(move, board, myCountry))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(errorMsg);
    }

    static Stream<Arguments> provideInvalidDestinations() {
        return Stream.of(
                // 1. 목적지에 아군 기물이 있는 경우
                arguments(Position.of(1, 5), Country.CHO, "[ERROR] 아군 기물입니다."),
                // 2. 목적지에 적군 '포'가 있는 경우
                arguments(Position.of(1, 6), Country.CHO, "[ERROR] 포는 포를 먹을 수 없습니다.")
        );
    }
}