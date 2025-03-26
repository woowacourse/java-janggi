package domain;

import domain.direction.PieceDirection;
import domain.piece.Piece;
import domain.piece.Pieces;
import domain.piece.category.Soldier;
import domain.spatial.Position;
import domain.strategy.InnerElephantInitializer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;

class BoardTest {

    @Test
    void 플레이어의_기물을_이동한다() {
        // given
        Position startPosition = new Position(1, 4);
        Position targetPosition = new Position(1, 5);

        Piece expected = new Soldier(new Position(1, 5), PieceDirection.HAN_SOLDIER.get());

        Player han = new Player(Team.HAN);
        Player cho = new Player(Team.CHO);

        Pieces hanPieces = createPiecesByPlayer(han);
        Pieces choPieces = createPiecesByPlayer(cho);

        Map<Player, Pieces> boardElements = new HashMap<>();
        boardElements.put(han, hanPieces);
        boardElements.put(cho, choPieces);

        Board board = new Board(boardElements);

        // when
        board.moveAndCapture(han, startPosition, targetPosition);

        // then
        assertThat(hanPieces.pieces()).contains(expected);
    }

    @Test
    void 타겟_위치에_플레이어의_기물이_있는_경우_예외가_발생한다() {
        // given
        Position startPosition = new Position(1, 1);
        Position targetPosition = new Position(1, 4);

        Player han = new Player(Team.HAN);
        Player cho = new Player(Team.CHO);

        Pieces hanPieces = createPiecesByPlayer(han);
        Pieces choPieces = createPiecesByPlayer(cho);

        Map<Player, Pieces> boardElements = new HashMap<>();
        boardElements.put(han, hanPieces);
        boardElements.put(cho, choPieces);

        Board board = new Board(boardElements);

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> board.moveAndCapture(han, startPosition, targetPosition))
                .withMessage("도착 위치에 아군의 기물이 존재해 이동할 수 없습니다.");
    }

    @Test
    void 포_이동_경로에_기물이_1개가_아닌_경우_예외가_발생한다() {
        // given
        Position startPosition = new Position(2, 3);
        Position targetPosition1 = new Position(4, 3);
        Position targetPosition2 = new Position(7, 3);

        Player han = new Player(Team.HAN);
        Player cho = new Player(Team.CHO);

        Pieces hanPieces = createPiecesByPlayer(han);
        Pieces choPieces = createPiecesByPlayer(cho);

        hanPieces.pieces().add(new Soldier(new Position(5, 3), PieceDirection.HAN_SOLDIER.get()));
        hanPieces.pieces().add(new Soldier(new Position(6, 3), PieceDirection.HAN_SOLDIER.get()));

        Map<Player, Pieces> boardElements = new HashMap<>();
        boardElements.put(han, hanPieces);
        boardElements.put(cho, choPieces);

        Board board = new Board(boardElements);

        // when & then
        assertAll(
                () -> assertThatIllegalArgumentException()
                        .isThrownBy(() -> board.moveAndCapture(han, startPosition, targetPosition1))
                        .withMessage("포는 중간에 기물이 1개여야 합니다."),
                () -> assertThatIllegalArgumentException()
                        .isThrownBy(() -> board.moveAndCapture(han, startPosition, targetPosition2))
                        .withMessage("포는 중간에 기물이 1개여야 합니다.")
        );
    }

    @Test
    void 포의_목적지에_상대_포가_존재할_경우_예외가_발생한다() {
        // given
        Position startPosition = new Position(2, 3);
        Position targetPosition = new Position(2, 8);

        Player han = new Player(Team.HAN);
        Player cho = new Player(Team.CHO);

        Pieces hanPieces = createPiecesByPlayer(han);
        Pieces choPieces = createPiecesByPlayer(cho);
        hanPieces.pieces().add(new Soldier(new Position(2, 5), PieceDirection.HAN_SOLDIER.get()));

        Map<Player, Pieces> boardElements = new HashMap<>();
        boardElements.put(han, hanPieces);
        boardElements.put(cho, choPieces);

        Board board = new Board(boardElements);

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> board.moveAndCapture(han, startPosition, targetPosition))
                .withMessage("포는 상대 포를 잡을 수 없습니다.");
    }

    @Test
    void 포의_경로에_다른_포가_존재할_경우_예외가_발생한다() {
        // given
        Position startPosition = new Position(2, 3);
        Position targetPosition = new Position(2, 9);

        Player han = new Player(Team.HAN);
        Player cho = new Player(Team.CHO);

        Pieces hanPieces = createPiecesByPlayer(han);
        Pieces choPieces = createPiecesByPlayer(cho);

        Map<Player, Pieces> boardElements = new HashMap<>();
        boardElements.put(han, hanPieces);
        boardElements.put(cho, choPieces);

        Board board = new Board(boardElements);

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> board.moveAndCapture(han, startPosition, targetPosition))
                .withMessage("포는 다른 포를 지나칠 수 없습니다.");
    }

    @Test
    void 이동한_위치에_존재하는_상대_기물을_삭제한다() {
        // given
        Position startPosition = new Position(1, 1);
        Position targetPosition = new Position(1, 7);

        Player han = new Player(Team.HAN);
        Player cho = new Player(Team.CHO);

        Pieces hanPieces = createPiecesByPlayer(han);
        Pieces choPieces = createPiecesByPlayer(cho);

        Map<Player, Pieces> boardElements = new HashMap<>();
        boardElements.put(han, hanPieces);
        boardElements.put(cho, choPieces);

        Board board = new Board(boardElements);

        board.moveAndCapture(han, new Position(1, 4), new Position(2, 4));

        // when
        board.moveAndCapture(han, startPosition, targetPosition);

        // then
        assertAll(() -> {
            assertThat(choPieces.pieces()).hasSize(15);
            assertThat(choPieces.pieces()).doesNotContain(
                    new Soldier(new Position(1, 7), PieceDirection.CHO_SOLDIER.get()));
        });
    }

    @Test
    void 게임_종료_여부를_판단한다() {
        // given
        Player han = new Player(Team.HAN);
        Player cho = new Player(Team.CHO);

        Map<Player, Pieces> boardElements = new HashMap<>();
        boardElements.put(han, new Pieces(List.of()));
        boardElements.put(cho, createPiecesByPlayer(cho));

        Board board = new Board(boardElements);

        // when
        boolean result = board.isFinish();

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 우승자를_반환한다() {
        // given
        Player han = new Player(Team.HAN);
        Player cho = new Player(Team.CHO);

        Map<Player, Pieces> boardElements = new HashMap<>();
        boardElements.put(han, createPiecesByPlayer(han));
        boardElements.put(cho, new Pieces(List.of()));

        Board board = new Board(boardElements);

        // when
        Player winner = board.getWinner();

        // then
        assertThat(winner).isEqualTo(han);
    }

    private Pieces createPiecesByPlayer(final Player player) {
        InnerElephantInitializer strategy = new InnerElephantInitializer();
        return strategy.initPieces(player);
    }
}
