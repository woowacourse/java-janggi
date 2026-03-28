package domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;


import domain.piece.Piece;
import domain.strategy.HorseMoveStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiGameTest {

    @Test
    @DisplayName("플레이어가 선택한 기물 위치에 플레이어 소유의 기물이 존재하는지")
    void player_select_piece_exist() {
        Map<Position, Piece> testBoard = new HashMap<>();
        Piece horsePiece = new Piece(PieceProperty.of(PieceType.HORSE, Team.GREEN),
                HorseMoveStrategy.of(Position.of(3, 3)));
        Piece soldierPiece = new Piece(PieceProperty.of(PieceType.SOLDIER, Team.GREEN),
                HorseMoveStrategy.of(Position.of(5, 2)));
        testBoard.put(horsePiece.position(), horsePiece);
        testBoard.put(soldierPiece.position(), soldierPiece);
        Board board = Board.of(testBoard);

        JanggiGame janggiGame = new JanggiGame(board);
        Position selectPosition = Position.of(3, 3);

        assertDoesNotThrow(() -> janggiGame.validatePieceSelection(selectPosition));

    }

    @Test
    @DisplayName("플레이어가 선택한 기물 위치가 장기판 범위를 벗어나면 예외를 던진다.")
    void player_select_position_out_of_range_throw_exception() {
        Map<Position, Piece> testBoard = new HashMap<>();
        Piece horsePiece = new Piece(PieceProperty.of(PieceType.HORSE, Team.GREEN),
                HorseMoveStrategy.of(Position.of(3, 3)));
        Piece soldierPiece = new Piece(PieceProperty.of(PieceType.SOLDIER, Team.GREEN),
                HorseMoveStrategy.of(Position.of(5, 2)));
        testBoard.put(horsePiece.position(), horsePiece);
        testBoard.put(soldierPiece.position(), soldierPiece);
        Board board = Board.of(testBoard);

        JanggiGame janggiGame = new JanggiGame(board);
        Position selectPosition = Position.of(10, 3);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> janggiGame.validatePieceSelection(selectPosition));
    }

    @Test
    @DisplayName("플레이어가 선택한 기물 위치에 아군 기물이 존재하지 않으면 예외를 던진다.")
    void player_select_position_piece_not_exist_throw_exception() {
        Map<Position, Piece> testBoard = new HashMap<>();
        Piece horsePiece = new Piece(PieceProperty.of(PieceType.HORSE, Team.RED),
                HorseMoveStrategy.of(Position.of(3, 3)));
        Piece soldierPiece = new Piece(PieceProperty.of(PieceType.SOLDIER, Team.GREEN),
                HorseMoveStrategy.of(Position.of(5, 2)));
        testBoard.put(horsePiece.position(), horsePiece);
        testBoard.put(soldierPiece.position(), soldierPiece);
        Board board = Board.of(testBoard);

        JanggiGame janggiGame = new JanggiGame(board);
        Position selectPosition = Position.of(3, 3);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> janggiGame.validatePieceSelection(selectPosition));
    }

    @Test
    @DisplayName("장기 게임은 게임 종료 알 수 있다.")
    void game_finished() {
        Map<Position, Piece> testBoard = new HashMap<>();
        Piece horsePiece = new Piece(PieceProperty.of(PieceType.HORSE, Team.RED),
                HorseMoveStrategy.of(Position.of(3, 3)));
        Piece soldierPiece = new Piece(PieceProperty.of(PieceType.SOLDIER, Team.GREEN),
                HorseMoveStrategy.of(Position.of(5, 2)));
        testBoard.put(horsePiece.position(), horsePiece);
        testBoard.put(soldierPiece.position(), soldierPiece);
        Board board = Board.of(testBoard);

        JanggiGame janggiGame = new JanggiGame(board);
        janggiGame.checkGameFinished();

        assertThat(janggiGame.isGameFinished()).isTrue();
    }

}