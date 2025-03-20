package domain;

import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceDirection;
import domain.piece.PieceInit;
import domain.piece.Pieces;
import domain.piece.Position;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;

class BoardTest {

    @Test
    void 플레이어의_기물을_이동한다() {
        // given
        Position startPosition = Position.of(1, 4);
        Position targetPosition = Position.of(1, 5);

        Piece expected = new Pawn(1, 5, PieceDirection.HAN_PAWN.get());

        Player han = new Player("한", PieceColor.RED);
        Player cho = new Player("초", PieceColor.BLUE);

        List<Piece> hanPieces = PieceInit.initHanPieces();
        List<Piece> choPieces = PieceInit.initChoPieces();

        Map<Player, Pieces> boardElements = new HashMap<>();
        boardElements.put(han, new Pieces(hanPieces));
        boardElements.put(cho, new Pieces(choPieces));

        Board board = new Board(boardElements);

        // when
        board.move(han, startPosition, targetPosition);

        // then
        assertThat(hanPieces).contains(expected);
    }

    @Test
    void 타겟_위치에_플레이어의_기물이_있는_경우_예외가_발생한다() {
        // given
        Position startPosition = Position.of(1, 1);
        Position targetPosition = Position.of(1, 4);

        Player han = new Player("한", PieceColor.RED);
        Player cho = new Player("초", PieceColor.BLUE);

        List<Piece> hanPieces = PieceInit.initHanPieces();
        List<Piece> choPieces = PieceInit.initChoPieces();

        Map<Player, Pieces> boardElements = new HashMap<>();
        boardElements.put(han, new Pieces(hanPieces));
        boardElements.put(cho, new Pieces(choPieces));

        Board board = new Board(boardElements);

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> board.move(han, startPosition, targetPosition))
                .withMessage("[ERROR] 도착 위치에 아군의 기물이 존재해 이동할 수 없습니다.");
    }

    @Test
    void 포_이동_경로에_기물이_1개가_아닌_경우_예외가_발생한다() {
        // given
        Position startPosition = Position.of(2, 3);
        Position targetPosition1 = Position.of(4, 3);
        Position targetPosition2 = Position.of(7, 3);

        Player han = new Player("한", PieceColor.RED);
        Player cho = new Player("초", PieceColor.BLUE);

        List<Piece> hanPieces = PieceInit.initHanPieces();
        List<Piece> choPieces = PieceInit.initChoPieces();
        hanPieces.add(new Pawn(5, 3, PieceDirection.HAN_PAWN.get()));
        hanPieces.add(new Pawn(6, 3, PieceDirection.HAN_PAWN.get()));

        Map<Player, Pieces> boardElements = new HashMap<>();
        boardElements.put(han, new Pieces(hanPieces));
        boardElements.put(cho, new Pieces(choPieces));

        Board board = new Board(boardElements);

        // when & then
        assertAll(
                () -> assertThatIllegalArgumentException()
                        .isThrownBy(() -> board.move(han, startPosition, targetPosition1))
                        .withMessage("[ERROR] 포는 중간에 기물이 1개여야 합니다."),
                () -> assertThatIllegalArgumentException()
                        .isThrownBy(() -> board.move(han, startPosition, targetPosition2))
                        .withMessage("[ERROR] 포는 중간에 기물이 1개여야 합니다.")
        );
    }

    @Test
    void 이동한_위치에_존재하는_상대_기물을_삭제한다() {
        // given
        Position startPosition = Position.of(1, 1);
        Position targetPosition = Position.of(1, 7);

        Player han = new Player("한", PieceColor.RED);
        Player cho = new Player("초", PieceColor.BLUE);

        List<Piece> hanPieces = PieceInit.initHanPieces();
        List<Piece> choPieces = PieceInit.initChoPieces();

        Map<Player, Pieces> boardElements = new HashMap<>();
        boardElements.put(han, new Pieces(hanPieces));
        boardElements.put(cho, new Pieces(choPieces));

        Board board = new Board(boardElements);

        board.move(han, Position.of(1, 4), Position.of(2, 4));

        // when
        board.move(han, startPosition, targetPosition);

        // then
        assertAll(() -> {
            assertThat(choPieces).hasSize(15);
            assertThat(choPieces).doesNotContain(new Pawn(1, 7, PieceDirection.CHO_PAWN.get()));
        });
    }

    @Test
    void 게임_종료_여부를_판단한다() {
        // given
        Player han = new Player("한", PieceColor.RED);
        Player cho = new Player("초", PieceColor.BLUE);

        Position kingPosition = Position.of(5, 9);

        Pieces choPieces = new Pieces(PieceInit.initChoPieces());
        choPieces.deleteByPosition(kingPosition);

        Map<Player, Pieces> boardElements = new HashMap<>();
        boardElements.put(han, new Pieces(PieceInit.initHanPieces()));
        boardElements.put(cho, choPieces);

        Board board = new Board(boardElements);

        // when
        boolean result = board.isFinish();

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 우승자를_반환한다() {
        // given
        Player han = new Player("한", PieceColor.RED);
        Player cho = new Player("초", PieceColor.BLUE);

        Position kingPosition = Position.of(5, 9);

        Pieces choPieces = new Pieces(PieceInit.initChoPieces());
        choPieces.deleteByPosition(kingPosition);

        Map<Player, Pieces> boardElements = new HashMap<>();
        boardElements.put(han, new Pieces(PieceInit.initHanPieces()));
        boardElements.put(cho, choPieces);

        Board board = new Board(boardElements);

        // when
        Player winner = board.getWinner();

        // then
        assertThat(winner).isEqualTo(han);
    }
}
