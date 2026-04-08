package repository;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.board.BoardPiece;
import domain.board.Position;
import domain.board.SetUp;
import domain.game.Game;
import domain.piece.Camp;
import domain.piece.PieceType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameMapperTest {

    private final GameMapper gameMapper = new GameMapper();

    @Test
    @DisplayName("기물 상태 목록으로 보드를 생성할 수 있다")
    void toBoard_With_BoardPieces() {
        List<BoardPiece> boardPieces = List.of(
                new BoardPiece(new Position(5, 2), Camp.HAN, PieceType.GENERAL),
                new BoardPiece(new Position(5, 9), Camp.CHO, PieceType.GENERAL),
                new BoardPiece(new Position(1, 7), Camp.CHO, PieceType.SOLDIER)
        );

        Board board = gameMapper.toBoard(boardPieces);

        assertThat(board.findBy(new Position(5, 2)).type()).isEqualTo(PieceType.GENERAL);
        assertThat(board.findBy(new Position(5, 2)).camp()).isEqualTo(Camp.HAN);
        assertThat(board.findBy(new Position(5, 9)).type()).isEqualTo(PieceType.GENERAL);
        assertThat(board.findBy(new Position(5, 9)).camp()).isEqualTo(Camp.CHO);
        assertThat(board.findBy(new Position(1, 7)).type()).isEqualTo(PieceType.SOLDIER);
        assertThat(board.findBy(new Position(1, 7)).camp()).isEqualTo(Camp.CHO);
    }

    @Test
    @DisplayName("게임 상태를 저장용 기물 상태 목록으로 변환할 수 있다")
    void toBoardPieces_With_Game() {
        Game game = Game.start(SetUp.INNER_ELEPHANT, SetUp.LEFT_ELEPHANT);

        List<BoardPiece> boardPieces = gameMapper.toBoardPieces(game);

        assertThat(boardPieces).contains(
                new BoardPiece(new Position(5, 2), Camp.HAN, PieceType.GENERAL),
                new BoardPiece(new Position(5, 9), Camp.CHO, PieceType.GENERAL),
                new BoardPiece(new Position(1, 4), Camp.HAN, PieceType.SOLDIER),
                new BoardPiece(new Position(1, 7), Camp.CHO, PieceType.SOLDIER)
        );
    }

    @Test
    @DisplayName("기물 상태 목록과 게임 정보로 게임을 복원할 수 있다")
    void toGame_With_BoardPieces() {
        List<BoardPiece> boardPieces = List.of(
                new BoardPiece(new Position(5, 2), Camp.HAN, PieceType.GENERAL),
                new BoardPiece(new Position(5, 9), Camp.CHO, PieceType.GENERAL),
                new BoardPiece(new Position(1, 6), Camp.CHO, PieceType.SOLDIER)
        );

        Game game = gameMapper.toGame(Camp.HAN, true, boardPieces);

        assertThat(game.currentTurn()).isEqualTo(Camp.HAN);
        assertThat(game.isFinished()).isTrue();
        assertThat(game.board().findBy(new Position(5, 2)).type()).isEqualTo(PieceType.GENERAL);
        assertThat(game.board().findBy(new Position(5, 9)).type()).isEqualTo(PieceType.GENERAL);
        assertThat(game.board().findBy(new Position(1, 6)).type()).isEqualTo(PieceType.SOLDIER);
        assertThat(game.board().findBy(new Position(1, 6)).camp()).isEqualTo(Camp.CHO);
    }
}
