package janggi.domain.policy;

import janggi.domain.PieceInfo;
import janggi.domain.board.BoardInterface;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.piece.PieceType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JumpPolicyTest {
    private BoardInterface createBoardInterface(List<Position> isEmpty, List<Position> isPo, List<Position> isAlly) {
        return new BoardInterface() {
            @Override
            public boolean isEmpty(Position position) {
                return isEmpty.contains(position);
            }

            @Override
            public boolean isEqualPieceType(Position position, PieceType pieceType) {
                return isPo.contains(position);
            }

            @Override
            public boolean isAlly(Side side, Position position) {
                return isAlly.contains(position);
            }

            @Override
            public PieceInfo[][] getCurrentBoard() {
                return new PieceInfo[0][];
            }
        };
    }

    @Test
    void 포는_경로에_포가_아닌_기물이_한_개_있으며_도착_지점에_기물이_없으면_움직일_수_있다() {
        List<Position> path = List.of(new Position(8, 2), new Position(8, 3), new Position(8, 4), new Position(8, 5), new Position(8, 6), new Position(8, 7));

        List<Position> isEmpty = List.of(new Position(8, 4), new Position(8, 5), new Position(8, 6), new Position(8, 7));
        List<Position> isPo = List.of(new Position(8, 2));
        List<Position> isAlly = List.of(new Position(8, 2), new Position(8, 3));

        BoardInterface boardInterface = createBoardInterface(isEmpty, isPo, isAlly);
        JumpPolicy jumpPolicy = new JumpPolicy();
        assertThat(jumpPolicy.isMovable(path, Side.CHO, boardInterface)).isTrue();
    }


    @Test
    void 포는_경로에_포가_아닌_기물이_한_개_있으며_도착_지점에_포가_아닌_적이_있으면_움직일_수_있다() {
        List<Position> path = List.of(new Position(1, 1), new Position(1, 2), new Position(1, 3), new Position(1, 4));

        List<Position> isEmpty = List.of(new Position(1, 3));
        List<Position> isPo = List.of(new Position(1, 1));
        List<Position> isAlly = List.of(new Position(1, 1));

        BoardInterface boardInterface = createBoardInterface(isEmpty, isPo, isAlly);
        JumpPolicy jumpPolicy = new JumpPolicy();
        assertThat(jumpPolicy.isMovable(path, Side.CHO, boardInterface)).isTrue();
    }

    @Test
    void 포는_경로에_포가_아닌_기물이_한_개_있으며_도착_지점에_포인_적이_있으면_움직일_수_없다() {
        List<Position> path = List.of(new Position(1, 1), new Position(1, 2), new Position(1, 3), new Position(1, 4));

        List<Position> isEmpty = List.of(new Position(1, 3));
        List<Position> isPo = List.of(new Position(1, 1), new Position(1, 4));
        List<Position> isAlly = List.of(new Position(1, 1));

        BoardInterface boardInterface = createBoardInterface(isEmpty, isPo, isAlly);
        JumpPolicy jumpPolicy = new JumpPolicy();
        assertThat(jumpPolicy.isMovable(path, Side.CHO, boardInterface)).isFalse();
    }

    @Test
    void 포는_경로에_포가_아닌_기물이_한_개_있으며_도착_지점에_아군이_있으면_움직일_수_없다() {
        List<Position> path = List.of(new Position(1, 1), new Position(1, 2), new Position(1, 3), new Position(1, 4));

        List<Position> isEmpty = List.of(new Position(1, 3));
        List<Position> isPo = List.of(new Position(1, 1));
        List<Position> isAlly = List.of(new Position(1, 1), new Position(1, 2), new Position(1, 4));

        BoardInterface boardInterface = createBoardInterface(isEmpty, isPo, isAlly);
        JumpPolicy jumpPolicy = new JumpPolicy();
        assertThat(jumpPolicy.isMovable(path, Side.CHO, boardInterface)).isFalse();
    }

    @Test
    void 포는_경로에_기물이_두_개_있으면_움직일_수_없다() {
        List<Position> path = List.of(new Position(1, 1), new Position(1, 2), new Position(1, 3), new Position(1, 4));

        List<Position> isEmpty = List.of(new Position(1, 4));
        List<Position> isPo = List.of(new Position(1, 1));
        List<Position> isAlly = List.of(new Position(1, 1));

        BoardInterface boardInterface = createBoardInterface(isEmpty, isPo, isAlly);
        JumpPolicy jumpPolicy = new JumpPolicy();
        assertThat(jumpPolicy.isMovable(path, Side.CHO, boardInterface)).isFalse();
    }

    @Test
    void 포는_경로에_포인_기물이_한_개_있으면_움직일_수_없다() {
        List<Position> path = List.of(new Position(1, 1), new Position(1, 2), new Position(1, 3), new Position(1, 4));

        List<Position> isEmpty = List.of(new Position(1, 3), new Position(1, 4));
        List<Position> isPo = List.of(new Position(1, 1), new Position(1, 2));
        List<Position> isAlly = List.of(new Position(1, 1));

        BoardInterface boardInterface = createBoardInterface(isEmpty, isPo, isAlly);
        JumpPolicy jumpPolicy = new JumpPolicy();
        assertThat(jumpPolicy.isMovable(path, Side.CHO, boardInterface)).isFalse();
    }
}
