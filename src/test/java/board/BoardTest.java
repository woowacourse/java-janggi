package board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import game.Turn;
import piece.Cannon;
import piece.Chariot;
import piece.Piece;
import piece.Soldier;
import piece.Team;

class BoardTest {

    @Test
    void 장기판에_기물을_놓을_수_있다() {
        Position position = new Position(1, 1);
        Piece piece = new Chariot(Team.BLUE);
        Board board = new Board(Map.of(position, piece));

        assertThat(board.findPieceByPosition(position)).isEqualTo(new Chariot(Team.BLUE));
    }

    @Test
    void 장기_게임은_초나라부터_시작한다() {
        Position position = new Position(4, 4);
        Board board = new Board(Map.of(position, new Soldier(Team.BLUE)));
        Turn turn = new Turn();

        assertDoesNotThrow(() -> board.isValidTurn(position, turn));
    }

    @Test
    void 초나라_다음_차례는_한나라이다() {
        Position position = new Position(4, 4);
        Board board = new Board(Map.of(position, new Soldier(Team.RED)));
        Turn turn = new Turn();
        turn.increaseRound();

        assertDoesNotThrow(() -> board.isValidTurn(position, turn));
    }

    @Test
    void 초나라_차례에_한나라_기물을_선택하면_시작할_수_없다() {
        Position position = new Position(4, 4);
        Board board = new Board(Map.of(position, new Soldier(Team.RED)));
        Turn turn = new Turn();

        assertThatThrownBy(() -> board.isValidTurn(position, turn))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 한나라_차례에_초나라_기물을_선택하면_시작할_수_없다() {
        Position position = new Position(4, 4);
        Board board = new Board(Map.of(position, new Soldier(Team.BLUE)));
        Turn turn = new Turn();
        turn.increaseRound();

        assertThatThrownBy(() -> board.isValidTurn(position, turn))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 시작_위치와_목적지를_알려주면_기물을_움직인다() {
        Position start = new Position(4, 1);
        Position destination = new Position(5, 1);
        Piece piece = new Soldier(Team.RED);
        Board board = new Board(Map.of(start, piece));

        board.move(start, destination);

        assertThat(board.findPieceByPosition(destination))
                .isEqualTo(new Soldier(Team.RED));
    }

    @Test
    void 갈_수_없는_목적지를_알려주면_기물을_움직일_수_없다() {
        Position start = new Position(4, 1);
        Position destination = new Position(3, 1);
        Piece piece = new Soldier(Team.RED);
        Board board = new Board(Map.of(start, piece));

        assertThatThrownBy(() -> board.move(start, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 장기판_기물이_존재하는지_확인한다() {
        Position position = new Position(1, 1);
        Piece piece = new Chariot(Team.BLUE);
        Board board = new Board(Map.of(position, piece));

        assertThat(board.isExists(position)).isTrue();
    }

    @CsvSource(value = {
            "BLUE,true", "RED,false"
    })
    @ParameterizedTest
    void 장기판_기물이_같은팀_기물인지_확인한다(Team team, boolean expected) {
        Team blue = Team.BLUE;
        Chariot piece = new Chariot(team);
        Board board = new Board(Map.of(
                new Position(1, 1), piece
        ));

        assertThat(board.isSameTeamPosition(blue, new Position(1, 1))).isEqualTo(expected);
    }

    @Test
    void 장기판에서_특정_위치의_기물을_찾는다() {
        Piece piece = new Chariot(Team.BLUE);
        Board board = new Board(Map.of(new Position(2, 1), piece));

        assertThat(board.findPieceByPosition(new Position(2, 1))).isEqualTo(piece);
    }

    @Test
    void 장기판에서_특정_위치의_기물을_찾지_못한다() {
        Piece piece = new Chariot(Team.BLUE);
        Board board = new Board(Map.of(new Position(2, 1), piece));

        assertThatThrownBy(() -> board.findPieceByPosition(new Position(2, 2)))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @MethodSource
    @ParameterizedTest
    void 위치가_포의_위치인지_알려준다(Piece piece, boolean expected) {
        Position position = new Position(4, 4);
        Board board = new Board(Map.of(position, piece));

        assertThat(board.isCannonPosition(position)).isEqualTo(expected);
    }

    private static Stream<Arguments> 위치가_포의_위치인지_알려준다() {
        return Stream.of(
                Arguments.of(new Cannon(Team.RED), true),
                Arguments.of(new Chariot(Team.RED), false)
        );
    }

}
