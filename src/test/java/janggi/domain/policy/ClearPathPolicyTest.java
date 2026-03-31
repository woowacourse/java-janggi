package janggi.domain.policy;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.board.BoardInfo;
import janggi.domain.piece.PieceType;
import janggi.domain.Route;
import java.util.List;
import org.junit.jupiter.api.Test;

class ClearPathPolicyTest {
    private boolean checkMovable(List<Position> path, BoardInfo board) {
        Route route = new Route(path);
        ClearPathPolicy policy = new ClearPathPolicy();
        return policy.isMovable(route, Side.CHO, board);
    }

    private BoardInfo createBoardInterface(List<Position> isEmpty, List<Position> isAlly) {
        return new BoardInfo() {
            @Override
            public boolean isEmpty(Position position) {
                return isEmpty.contains(position);
            }

            @Override
            public boolean isAlly(Side side, Position position) {
                return isAlly.contains(position);
            }

            @Override
            public boolean isEqualPieceType(Position position, PieceType pieceType) {
                return false;
            }
        };
    }

    @Test
    void 기물은_모든_경로와_도착지에_모두_비어있는_경우_움직일_수_있다() {
        List<Position> path = List.of(new Position(1, 1), new Position(1, 2), new Position(2, 1), new Position(2, 2));
        List<Position> isEmpty = List.of(new Position(1, 2), new Position(2, 1), new Position(2, 2));
        BoardInfo board = createBoardInterface(isEmpty, List.of());

        assertThat(checkMovable(path, board)).isTrue();
    }

    @Test
    void 기물은_모든_경로가_모두_비어있고_도착지에_적이_있는_경우_움직일_수_있다() {
        List<Position> path = List.of(new Position(1, 1), new Position(1, 2), new Position(2, 1), new Position(2, 2));
        List<Position> isEmpty = List.of(new Position(1, 2), new Position(2, 1));
        BoardInfo board = createBoardInterface(isEmpty, List.of());

        assertThat(checkMovable(path, board)).isTrue();
    }

    @Test
    void 기물은_모든_경로가_모두_비어있고_도착지에_아군이_있는_경우_움직일_수_없다() {
        List<Position> path = List.of(new Position(1, 1), new Position(1, 2), new Position(2, 1), new Position(2, 2));
        List<Position> isEmpty = List.of(new Position(1, 2), new Position(2, 1));
        List<Position> isAlly = List.of(new Position(2, 2));
        BoardInfo board = createBoardInterface(isEmpty, isAlly);

        assertThat(checkMovable(path, board)).isFalse();
    }

    @Test
    void 기물은_경로_중_일부에_기물이_있으면_움직일_수_없다() {
        List<Position> path = List.of(new Position(1, 1), new Position(1, 2), new Position(2, 1), new Position(2, 2));
        List<Position> isEmpty = List.of(new Position(2, 1), new Position(2, 2));
        BoardInfo board = createBoardInterface(isEmpty, List.of());

        assertThat(checkMovable(path, board)).isFalse();
    }
}