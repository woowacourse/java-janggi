package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.game.Board;
import domain.game.SetUp;
import domain.piece.MovementRule;
import domain.piece.Piece;
import domain.piece.PieceInitializer;
import domain.piece.PieceType;
import domain.piece.Pieces;
import domain.player.Player;
import domain.player.Team;
import domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void 플레이어의_기물을_이동한다() {
        // given
        Position startPosition = Position.of(1, 4);
        Position targetPosition = Position.of(1, 5);

        Piece expected = new Piece(Position.of(1, 5), PieceType.SOLDIER, MovementRule.HAN_SOLDIER);

        Player han = new Player("한", Team.HAN);
        Player cho = new Player("초", Team.CHO);

        List<Piece> hanPieces = PieceInitializer.createTeamPieces(Team.HAN, SetUp.INNER_ELEPHANT);
        List<Piece> choPieces = PieceInitializer.createTeamPieces(Team.CHO, SetUp.INNER_ELEPHANT);

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

        Player han = new Player("한", Team.HAN);
        Player cho = new Player("초", Team.CHO);

        List<Piece> hanPieces = PieceInitializer.createTeamPieces(Team.HAN, SetUp.INNER_ELEPHANT);
        List<Piece> choPieces = PieceInitializer.createTeamPieces(Team.CHO, SetUp.INNER_ELEPHANT);

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

        Player han = new Player("한", Team.HAN);
        Player cho = new Player("초", Team.CHO);

        List<Piece> hanPieces = PieceInitializer.createTeamPieces(Team.HAN, SetUp.INNER_ELEPHANT);
        List<Piece> choPieces = PieceInitializer.createTeamPieces(Team.CHO, SetUp.INNER_ELEPHANT);

        hanPieces.add(new Piece(Position.of(5, 3), PieceType.SOLDIER, MovementRule.HAN_SOLDIER));
        hanPieces.add(new Piece(Position.of(6, 3), PieceType.SOLDIER, MovementRule.HAN_SOLDIER));

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
    void 포의_목적지에_상대_포가_존재할_경우_예외가_발생한다() {
        // given
        Position startPosition = Position.of(2, 3);
        Position targetPosition = Position.of(2, 8);

        Player han = new Player("한", Team.HAN);
        Player cho = new Player("초", Team.CHO);

        List<Piece> hanPieces = PieceInitializer.createTeamPieces(Team.HAN, SetUp.INNER_ELEPHANT);
        List<Piece> choPieces = PieceInitializer.createTeamPieces(Team.CHO, SetUp.INNER_ELEPHANT);

        hanPieces.add(new Piece(Position.of(2, 5), PieceType.SOLDIER, MovementRule.HAN_SOLDIER));

        Map<Player, Pieces> boardElements = new HashMap<>();
        boardElements.put(han, new Pieces(hanPieces));
        boardElements.put(cho, new Pieces(choPieces));

        Board board = new Board(boardElements);

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> board.move(han, startPosition, targetPosition))
                .withMessage("[ERROR] 포는 상대 포를 잡을 수 없습니다.");
    }

    @Test
    void 포의_경로에_다른_포가_존재할_경우_예외가_발생한다() {
        // given
        Position startPosition = Position.of(2, 3);
        Position targetPosition = Position.of(2, 9);

        Player han = new Player("한", Team.HAN);
        Player cho = new Player("초", Team.CHO);

        List<Piece> hanPieces = PieceInitializer.createTeamPieces(Team.HAN, SetUp.INNER_ELEPHANT);
        List<Piece> choPieces = PieceInitializer.createTeamPieces(Team.CHO, SetUp.INNER_ELEPHANT);

        Map<Player, Pieces> boardElements = new HashMap<>();
        boardElements.put(han, new Pieces(hanPieces));
        boardElements.put(cho, new Pieces(choPieces));

        Board board = new Board(boardElements);

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> board.move(han, startPosition, targetPosition))
                .withMessage("[ERROR] 포는 다른 포를 지나칠 수 없습니다.");
    }

    @Test
    void 이동한_위치에_존재하는_상대_기물을_삭제한다() {
        // given
        Position startPosition = Position.of(1, 1);
        Position targetPosition = Position.of(1, 7);

        Player han = new Player("한", Team.HAN);
        Player cho = new Player("초", Team.CHO);

        List<Piece> hanPieces = PieceInitializer.createTeamPieces(Team.HAN, SetUp.INNER_ELEPHANT);
        List<Piece> choPieces = PieceInitializer.createTeamPieces(Team.CHO, SetUp.INNER_ELEPHANT);

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
            assertThat(choPieces).doesNotContain(
                    new Piece(Position.of(1, 7), PieceType.SOLDIER, MovementRule.CHO_SOLDIER));
        });
    }

    @Test
    void 게임_종료_여부를_판단한다() {
        // given
        Player han = new Player("한", Team.HAN);
        Player cho = new Player("초", Team.CHO);

        Position kingPosition = Position.of(5, 9);

        Pieces choPieces = new Pieces(PieceInitializer.createTeamPieces(Team.CHO, SetUp.INNER_ELEPHANT));
        choPieces.deleteByPosition(kingPosition);

        Map<Player, Pieces> boardElements = new HashMap<>();
        boardElements.put(han, new Pieces(PieceInitializer.createTeamPieces(Team.HAN, SetUp.INNER_ELEPHANT)));
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
        Player han = new Player("한", Team.HAN);
        Player cho = new Player("초", Team.CHO);

        Position kingPosition = Position.of(5, 9);

        Pieces choPieces = new Pieces(PieceInitializer.createTeamPieces(Team.CHO, SetUp.INNER_ELEPHANT));
        choPieces.deleteByPosition(kingPosition);

        Map<Player, Pieces> boardElements = new HashMap<>();
        boardElements.put(han, new Pieces(PieceInitializer.createTeamPieces(Team.HAN, SetUp.INNER_ELEPHANT)));
        boardElements.put(cho, choPieces);

        Board board = new Board(boardElements);

        // when
        Player winner = board.getWinner();

        // then
        assertThat(winner).isEqualTo(han);
    }

    @Test
    void 플레이어들의_기물_총_점수를_계산한다() {
        // given
        Player han = new Player("한", Team.HAN);
        Player cho = new Player("초", Team.CHO);

        Pieces choPieces = new Pieces(PieceInitializer.createTeamPieces(Team.CHO, SetUp.INNER_ELEPHANT));
        choPieces.deleteByPosition(Position.of(4, 10));

        Map<Player, Pieces> boardElements = new HashMap<>();
        boardElements.put(han, new Pieces(PieceInitializer.createTeamPieces(Team.HAN, SetUp.INNER_ELEPHANT)));
        boardElements.put(cho, choPieces);

        Board board = new Board(boardElements);

        // when
        board.calculateScores();

        // then
        assertAll(() -> {
            assertThat(han.score()).isEqualTo(73.5f);
            assertThat(cho.score()).isEqualTo(69.0f);
        });
    }
}
