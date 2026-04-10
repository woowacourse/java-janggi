package janggi.domain.policy;

import janggi.domain.PieceInfo;
import janggi.domain.ScoreStatus;
import janggi.domain.board.BaseBoard;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.piece.PieceType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ClearPathPolicyTest {
    private BaseBoard createBoardInterface(List<Position> isEmpty, List<Position> isAlly) {
        return new BaseBoard() {
            @Override
            public boolean isEmpty(Position position) {
                return isEmpty.contains(position);
            }

            @Override
            public boolean isEqualPieceType(Position position, PieceType pieceType) {
                return false;
            }

            @Override
            public boolean isAlly(Side side, Position position) {
                return isAlly.contains(position);
            }

            @Override
            public PieceInfo[][] getCurrentBoard() {
                return new PieceInfo[0][];
            }

            @Override
            public ScoreStatus getScoreStatus() {
                throw new UnsupportedOperationException("이 테스트에서는 getScoreStatus()를 호출하면 안 됩니다.");
            }
        };
    }

    @Test
    void 기물은_모든_경로와_도착지에_모두_비어있는_경우_움직일_수_있다() {
        List<Position> path = List.of(new Position(1, 1), new Position(1, 2), new Position(2, 1), new Position(2, 2));

        List<Position> isEmpty = List.of(new Position(1, 2), new Position(2, 1), new Position(2, 2));
        List<Position> isAlly = List.of();

        BaseBoard baseBoard = createBoardInterface(isEmpty, isAlly);
        ClearPathPolicy clearPathPolicy = new ClearPathPolicy();
        assertThat(clearPathPolicy.isMovable(path, Side.CHO, baseBoard)).isTrue();
    }

    @Test
    void 기물은_모든_경로가_모두_비어있고_도착지에_적이_있는_경우_움직일_수_있다() {
        List<Position> path = List.of(new Position(1, 1), new Position(1, 2), new Position(2, 1), new Position(2, 2));

        List<Position> isEmpty = List.of(new Position(1, 2), new Position(2, 1));
        List<Position> isEnemy = List.of(new Position(2, 2));
        List<Position> isAlly = List.of();

        BaseBoard baseBoard = createBoardInterface(isEmpty, isAlly);
        ClearPathPolicy clearPathPolicy = new ClearPathPolicy();
        assertThat(clearPathPolicy.isMovable(path, Side.CHO, baseBoard)).isTrue();
    }

    @Test
    void 기물은_모든_경로가_모두_비어있고_도착지에_아군이_있는_경우_움직일_수_없다() {
        List<Position> path = List.of(new Position(1, 1), new Position(1, 2), new Position(2, 1), new Position(2, 2));

        List<Position> isEmpty = List.of(new Position(1, 2), new Position(2, 1));
        List<Position> isAlly = List.of(new Position(2, 2));

        BaseBoard baseBoard = createBoardInterface(isEmpty, isAlly);
        ClearPathPolicy clearPathPolicy = new ClearPathPolicy();
        assertThat(clearPathPolicy.isMovable(path, Side.CHO, baseBoard)).isFalse();
    }

    @Test
    void 기물은_경로_중_일부에_기물이_있으면_움직일_수_없다() {
        List<Position> path = List.of(new Position(1, 1), new Position(1, 2), new Position(2, 1), new Position(2, 2));

        List<Position> isEmpty = List.of(new Position(2, 1), new Position(2, 2));
        List<Position> isAlly = List.of();

        BaseBoard baseBoard = createBoardInterface(isEmpty, isAlly);
        ClearPathPolicy clearPathPolicy = new ClearPathPolicy();
        assertThat(clearPathPolicy.isMovable(path, Side.CHO, baseBoard)).isFalse();
    }
}