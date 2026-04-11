package domain.strategy;

import static org.junit.jupiter.api.Assertions.*;

import domain.*;
import domain.vo.Position;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GeneralMoveStrategyTest {

    MoveStrategy strategy;
    Piece mover;

    @BeforeEach
    void setUp() {
        strategy = new GeneralMoveStrategy();
        mover = Piece.of(Team.CHU, Type.GENERAL);
    }

    @Test
    @DisplayName("궁의 목적지에 기물이 없으면 이동한다.")
    void 궁_정상_이동() {
        // given
        Position from = Position.of(1, 4);
        Position to = Position.of(1, 5);

        // when // then
        assertTrue(strategy.canMove(mover, from, to, Map.of()));
    }

    @Test
    @DisplayName("궁의 목적지에 같은 팀 기물이 있으면 이동하지 않는다.")
    void 궁_목적지에_같은_팀_기물이_있으면_이동_불가() {
        // given
        Position from = Position.of(1, 4);
        Position to = Position.of(1, 5);
        Piece target = Piece.of(Team.CHU, Type.SOLDIER);

        // when // then
        assertFalse(strategy.canMove(mover, from, to, Map.of(to, target)));
    }

    @Test
    @DisplayName("궁의 목적지에 다른 팀 기물이 있으면 이동한다.")
    void 궁_목적지에_다른_팀_기물이_있으면_정상_이동() {
        // given
        Position from = Position.of(1, 4);
        Position to = Position.of(1, 5);
        Piece target = Piece.of(Team.HAN, Type.SOLDIER);

        // when // then
        assertTrue(strategy.canMove(mover, from, to, Map.of(to, target)));
    }

    @Test
    @DisplayName("궁의 목적지가 궁성 밖이라면 이동하지 않는다.")
    void 궁_목적지가_궁성_밖이라면_이동_불가() {
        // given
        Position from = Position.of(2, 3);
        Position to = Position.of(2, 2);
        Piece piece = Piece.of(Team.CHU, Type.GENERAL);
        HashMap<Position, Piece> pieceOfPath = new HashMap<>();

        pieceOfPath.put(to, null);

        // when // then
        assertFalse(piece.canMovePiece(from, to, pieceOfPath));
    }

    @Test
    @DisplayName("궁의 대각선 이동이 불가능한 위치로 이동 시 이동하지 않는다.")
    void 궁_대각선_이동이_불가능한_위치면_이동_불가() {
        // given
        Position from = Position.of(2, 4);
        Position to = Position.of(1, 3);
        Piece piece = Piece.of(Team.CHU, Type.GENERAL);
        HashMap<Position, Piece> pieceOfPath = new HashMap<>();

        pieceOfPath.put(to, null);

        // when // then
        assertFalse(piece.canMovePiece(from, to, pieceOfPath));
    }

    @ParameterizedTest
    @DisplayName("궁의 0,4 또는 9,4 위치에서 대각선 이동 시 이동하지 않는다.")
    @CsvSource({
            "0, 4, 1, 3",
            "0, 4, 1, 5",
            "9, 4, 8, 3",
            "9, 4, 8, 5"
    })
    void 궁_대각선_이동이_불가능한_위치면_이동_불가2(int fromRow, int fromCol, int toRow, int toCol) {
        // given
        Position from = Position.of(fromRow, fromCol);
        Position to = Position.of(toRow, toCol);
        HashMap<Position, Piece> pieceOfPath = new HashMap<>();

        pieceOfPath.put(to, null);

        // when // then
        assertFalse(mover.canMovePiece(from, to, pieceOfPath));
    }
}
