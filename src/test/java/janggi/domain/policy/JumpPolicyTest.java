package janggi.domain.policy;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.board.BaseBoard;
import janggi.domain.piece.PieceType;
import janggi.domain.Route;
import java.util.List;
import org.junit.jupiter.api.Test;

class JumpPolicyTest {
    private boolean checkMovable(List<Position> path, Side side, BaseBoard boardInfo) {
        Route route = new Route(path);
        JumpPolicy jumpPolicy = new JumpPolicy();
        return jumpPolicy.isMovable(route, side, boardInfo);
    }

    private BaseBoard createBoardInterface(List<Position> isEmpty, List<Position> isPo, List<Position> isAlly) {
        return new BaseBoard() {
            @Override
            public boolean isEmpty(Position position) {
                return isEmpty.contains(position);
            }

            @Override
            public boolean isEqualPieceType(Position position, PieceType pieceType) {
                return pieceType == PieceType.PO && isPo.contains(position);
            }

            @Override
            public boolean isAlly(Side side, Position position) {
                return isAlly.contains(position);
            }
        };
    }

    @Test
    void 포는_경로에_포가_아닌_기물이_한_개_있으며_도착_지점에_기물이_없으면_움직일_수_있다() {
        List<Position> path = List.of(new Position(8, 2), new Position(8, 3), new Position(8, 4), new Position(8, 5), new Position(8, 6), new Position(8, 7));

        List<Position> isEmpty = List.of(new Position(8, 4), new Position(8, 5), new Position(8, 6), new Position(8, 7));
        List<Position> isPo = List.of(new Position(8, 2)); // 출발지(8,2)만 포
        List<Position> isAlly = List.of(new Position(8, 2), new Position(8, 3));

        BaseBoard boardInfo = createBoardInterface(isEmpty, isPo, isAlly);
        assertThat(checkMovable(path, Side.CHO, boardInfo)).isTrue();
    }

    @Test
    void 포는_경로에_포가_아닌_기물이_한_개_있으며_도착_지점에_포가_아닌_적이_있으면_움직일_수_있다() {
        List<Position> path = List.of(new Position(1, 1), new Position(1, 2), new Position(1, 3), new Position(1, 4));

        List<Position> isEmpty = List.of(new Position(1, 3));
        List<Position> isPo = List.of(new Position(1, 1));
        List<Position> isAlly = List.of(new Position(1, 1));

        BaseBoard boardInfo = createBoardInterface(isEmpty, isPo, isAlly);
        assertThat(checkMovable(path, Side.CHO, boardInfo)).isTrue();
    }

    @Test
    void 포는_경로에_포가_아닌_기물이_한_개_있으며_도착_지점에_포인_적이_있으면_움직일_수_없다() {
        List<Position> path = List.of(new Position(1, 1), new Position(1, 2), new Position(1, 3), new Position(1, 4));

        List<Position> isEmpty = List.of(new Position(1, 3));
        List<Position> isPo = List.of(new Position(1, 1), new Position(1, 4)); // 도착지(1,4)도 포
        List<Position> isAlly = List.of(new Position(1, 1));

        BaseBoard boardInfo = createBoardInterface(isEmpty, isPo, isAlly);
        assertThat(checkMovable(path, Side.CHO, boardInfo)).isFalse();
    }

    @Test
    void 포는_경로에_포가_아닌_기물이_한_개_있으며_도착_지점에_아군이_있으면_움직일_수_없다() {
        List<Position> path = List.of(new Position(1, 1), new Position(1, 2), new Position(1, 3), new Position(1, 4));

        List<Position> isEmpty = List.of(new Position(1, 3));
        List<Position> isPo = List.of(new Position(1, 1));
        List<Position> isAlly = List.of(new Position(1, 1), new Position(1, 4)); // 도착지(1,4) 아군

        BaseBoard boardInfo = createBoardInterface(isEmpty, isPo, isAlly);
        assertThat(checkMovable(path, Side.CHO, boardInfo)).isFalse();
    }

    @Test
    void 포는_경로에_기물이_두_개_있으면_움직일_수_없다() {
        List<Position> path = List.of(new Position(1, 1), new Position(1, 2), new Position(1, 3), new Position(1, 4));

        List<Position> isEmpty = List.of(new Position(1, 4));
        List<Position> isPo = List.of(new Position(1, 1));
        List<Position> isAlly = List.of(new Position(1, 1));

        BaseBoard boardInfo = createBoardInterface(isEmpty, isPo, isAlly);
        assertThat(checkMovable(path, Side.CHO, boardInfo)).isFalse();
    }

    @Test
    void 포는_경로에_포인_기물이_한_개_있으면_움직일_수_없다() {
        List<Position> path = List.of(new Position(1, 1), new Position(1, 2), new Position(1, 3), new Position(1, 4));

        List<Position> isEmpty = List.of(new Position(1, 3), new Position(1, 4));
        List<Position> isPo = List.of(new Position(1, 1), new Position(1, 2));
        List<Position> isAlly = List.of(new Position(1, 1));

        BaseBoard boardInfo = createBoardInterface(isEmpty, isPo, isAlly);
        assertThat(checkMovable(path, Side.CHO, boardInfo)).isFalse();
    }
}
