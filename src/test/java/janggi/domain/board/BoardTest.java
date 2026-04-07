package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.game.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceDTO;
import janggi.domain.piece.PieceType;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = Board.initialize();
    }

    @Test
    @DisplayName("보드를 초기화시, 32개의 기물이 배치된다.")
    void 보드_초기화_개수_테스트() {
        Map<Position, Piece> piecePosition = board.getPiecePosition();
        assertThat(piecePosition).hasSize(32);
    }

    @DisplayName("보드 초기화 시, 특정 위치에 올바른 기물이 배치된다.")

    @Test
    void 보드_초기화_위치_테스트() {
        Map<Position, Piece> piecePosition = board.getPiecePosition();

        // (0, 0) 위치에 한나라 차(Chariot)가 있는지 확인
        Piece piece = piecePosition.get(new Position(0, 0));

        PieceDTO pieceDTO = PieceDTO.from(piece);

        assertAll(
                () -> assertThat(pieceDTO.side()).isEqualTo(Side.HAN),
                () -> assertThat(pieceDTO.pieceType()).isEqualTo(PieceType.CHARIOT)
        );
    }

    @DisplayName("상대방의 기물을 선택하면 예외가 발생한다.")
    @Test
    void 상대_기물_선택_예외_테스트() {
        // given
        Position hanPiecePosition = new Position(0, 0); // 한나라 기물 위치

        // when & then
        assertThatThrownBy(() -> board.calculateDestinations(hanPiecePosition, Side.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 상대방의 기물은 선택할 수 없습니다.");
    }

    @DisplayName("기물을 이동시키는 경우, 이전 위치는 비고, 새로운 위치에 기물이 존재한다.")
    @Test
    void 기물_이동_테스트() {
        // given
        Position selected = new Position(6, 0); // 초나라 졸 위치
        Position target = new Position(5, 0);

        // when
        board.movePiece(selected, target);

        // then
        Map<Position, Piece> piecePosition = board.getPiecePosition();
        assertAll(
                () -> assertThat(piecePosition.get(selected)).isNull(),
                () -> assertThat(piecePosition.get(target)).isNotNull()
        );
    }

    @DisplayName("기물이 없는 빈 칸을 선택하려 하는 경우, IllegalArgumentException이 발생한다.")
    @Test
    void 빈_칸_선택_예외_테스트() {
        // given
        Board board = Board.initialize();
        Position emptyPosition = new Position(5, 5);
        Position targetPosition = new Position(4, 4);

        // when & then
        assertThatThrownBy(() -> board.movePiece(emptyPosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 해당 위치에 기물이 없습니다.");

        assertThat(board.getPiecePosition().get(targetPosition)).isNull();
    }

    @DisplayName("양쪽 궁이 모두 존재하는 경우, 게임은 계속 진행된다.")
    @Test
    void 양쪽_궁_모두_존재_테스트() {
        // given
        Board board = Board.initialize();

        // when & then
        assertThat(board.isGameOver()).isFalse();
    }

    @DisplayName("한쪽 궁이 잡히면, 게임은 종료된다.")
    @Test
    void 궁_제거_게임_종료_테스트() {
        // given
        Board board = Board.initialize();

        // when
        Position selectedPosition = new Position(6, 4);
        Position targetPosition = new Position(1, 4);
        board.movePiece(selectedPosition, targetPosition);

        // then
        assertThat(board.isGameOver()).isTrue();
    }

    @DisplayName("초기 보드 상태에서, 각 진영의 점수를 계산한다.")
    @Test
    void 초기_점수_계산_테스트() {
        // given & when
        double choScore = board.calculateScore(Side.CHO);
        double hanScore = board.calculateScore(Side.HAN);

        // then
        assertAll(
                () -> assertThat(choScore).isEqualTo(72.0),
                () -> assertThat(hanScore).isEqualTo(73.5) // 72.0 + 1.5
        );
    }

    @DisplayName("기물을 잡았을 때, 해당 진영의 점수가 감소한다.")
    @Test
    void 기물_제거_점수_감소_테스트() {
        // given
        Position choChariot = new Position(9, 0); // 초 진영 차 (13점)
        Position target = new Position(0, 0); // 한 진영 차

        // when
        board.movePiece(choChariot, target);

        // then
        // 한 진영 점수: 73.5 - 13.0 = 60.5
        assertThat(board.calculateScore(Side.HAN)).isEqualTo(60.5);
    }

    @DisplayName("점수가 더 높더라도, 궁이 잡히면 게임이 종료된다.")
    @Test
    void 점수_상관없이_궁_잡히면_게임_종료_테스트() {
        // given
        // 초기 상태 73.5 vs 72.0
        assertThat(board.calculateScore(Side.HAN)).isGreaterThan(board.calculateScore(Side.CHO));

        // when
        // 한 진영 궁 제거
        Position choSoldier = new Position(6, 4);
        Position hanGeneral = new Position(1, 4);
        board.movePiece(choSoldier, hanGeneral);

        // then
        assertAll(
                () -> assertThat(board.isGameOver()).isTrue(),
                () -> assertThat(board.calculateScore(Side.HAN)).isEqualTo(73.5)
        );
    }
}
