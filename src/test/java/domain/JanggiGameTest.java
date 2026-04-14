package domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import exception.JanggiBusinessException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiGameTest {

    private Board testBoard() {
        Map<Position, Piece> testBoard = new HashMap<>();

        Piece horsePiece = new Piece(PieceProperty.of(PieceType.HORSE, Team.RED), Position.of(3, 3));
        Piece soldierPiece = new Piece(PieceProperty.of(PieceType.GREEN_SOLDIER, Team.GREEN), Position.of(5, 2));
        Piece generalPiece = new Piece(PieceProperty.of(PieceType.GENERAL, Team.GREEN), Position.of(1, 4));

        testBoard.put(horsePiece.position(), horsePiece);
        testBoard.put(soldierPiece.position(), soldierPiece);
        testBoard.put(generalPiece.position(), generalPiece);

        return Board.of(testBoard);
    }

    @Test
    @DisplayName("플레이어가 선택한 기물 위치가 목적지까지 이동할 수 있다면 예외를 발생시키지 않는다.")
    void player_select_piece_exist() {
        JanggiGame janggiGame = new JanggiGame(testBoard(), GameStatus.GREEN_PLAYER_TURN);

        Position from = Position.of(3, 3);
        Position to = Position.of(5, 2);

        assertDoesNotThrow(() -> janggiGame.move(from, to));
    }

    @Test
    @DisplayName("플레이어가 선택한 기물 위치가 장기판 범위를 벗어나면 예외를 던진다.")
    void player_select_position_out_of_range_throw_exception() {
        JanggiGame janggiGame = new JanggiGame(testBoard(), GameStatus.RED_PLAYER_TURN);
        Position selected = Position.of(10, 12);

        assertThatThrownBy(() -> janggiGame.findPieceInfoAt(selected))
                .isExactlyInstanceOf(JanggiBusinessException.class);
    }

    @Test
    @DisplayName("플레이어가 선택한 기물 위치에 아군 기물이 존재하지 않으면 예외를 던진다.")
    void player_select_position_piece_not_exist_throw_exception() {
        JanggiGame janggiGame = new JanggiGame(testBoard(), GameStatus.GREEN_PLAYER_TURN);

        Position redPiecePosition = Position.of(3, 3);
        assertThatThrownBy(() -> janggiGame.findPieceInfoAt(redPiecePosition))
                .isExactlyInstanceOf(JanggiBusinessException.class);
    }

    @Test
    @DisplayName("장기 게임은 게임 종료 알 수 있다.")
    void game_finished() {
        JanggiGame janggiGame = new JanggiGame(testBoard(), GameStatus.GREEN_PLAYER_TURN);

        Position from = Position.of(3, 3);
        Position to = Position.of(1, 4);

        janggiGame.move(from, to);

        assertThat(janggiGame.isFinished()).isTrue();
    }
}
