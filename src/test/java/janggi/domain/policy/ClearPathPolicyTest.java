package janggi.domain.policy;

import janggi.domain.board.BoardInterface;
import janggi.domain.Position;
import janggi.domain.Side;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ClearPathPolicyTest {
    private BoardInterface createBoardInterface(List<Position> isEmpty, List<Position> isEnemy, List<Position> isAlly){
        return new BoardInterface(){
            @Override
            public boolean isEmpty(Position position) {
                return isEmpty.contains(position);
            }

            @Override
            public boolean isPo(Position position) {
                return false;
            }

            @Override
            public boolean isEnemy(Side side, Position position) {
                return isEnemy.contains(position);
            }

            @Override
            public boolean isAlly(Side side, Position position) {
                return isAlly.contains(position);
            }
        };
    }

    @Test
    void 기물은_모든_경로와_도착지에_모두_비어있는_경우_움직일_수_있다() {
        List<Position> path = List.of(new Position(1,1), new Position(1,2), new Position(2,1), new Position(2,2));

        List<Position> isEmpty = List.of(new Position(1,1), new Position(1,2), new Position(2,1), new Position(2,2));
        List<Position> isEnemy = List.of();
        List<Position> isAlly = List.of();

        BoardInterface boardInterface = createBoardInterface(isEmpty, isEnemy, isAlly);
        ClearPathPolicy clearPathPolicy = new ClearPathPolicy();
        assertThat(clearPathPolicy.isMovable(path, Side.CHO, boardInterface)).isTrue();
    }

    @Test
    void 기물은_모든_경로가_모두_비어있고_도착지에_적이_있는_경우_움직일_수_있다() {
        List<Position> path = List.of(new Position(1,1), new Position(1,2), new Position(2,1), new Position(2,2));

        List<Position> isEmpty = List.of(new Position(1,1), new Position(1,2), new Position(2,1));
        List<Position> isEnemy = List.of(new Position(2,2));
        List<Position> isAlly = List.of();

        BoardInterface boardInterface = createBoardInterface(isEmpty, isEnemy, isAlly);
        ClearPathPolicy clearPathPolicy = new ClearPathPolicy();
        assertThat(clearPathPolicy.isMovable(path, Side.CHO, boardInterface)).isTrue();
    }

    @Test
    void 기물은_모든_경로가_모두_비어있고_도착지에_아군이_있는_경우_움직일_수_없다() {
        List<Position> path = List.of(new Position(1,1), new Position(1,2), new Position(2,1), new Position(2,2));

        List<Position> isEmpty = List.of(new Position(1,1), new Position(1,2), new Position(2,1));
        List<Position> isEnemy = List.of();
        List<Position> isAlly = List.of(new Position(2, 2));

        BoardInterface boardInterface = createBoardInterface(isEmpty, isEnemy, isAlly);
        ClearPathPolicy clearPathPolicy = new ClearPathPolicy();
        assertThat(clearPathPolicy.isMovable(path, Side.CHO, boardInterface)).isFalse();
    }

    @Test
    void 기물은_경로_중_일부에_기물이_있으면_움직일_수_없다() {
        List<Position> path = List.of(new Position(1,1), new Position(1,2), new Position(2,1), new Position(2,2));

        List<Position> isEmpty = List.of(new Position(1,1), new Position(2,1), new Position(2,2));
        List<Position> isEnemy = List.of(new Position(1,2));
        List<Position> isAlly = List.of();

        BoardInterface boardInterface = createBoardInterface(isEmpty, isEnemy, isAlly);
        ClearPathPolicy clearPathPolicy = new ClearPathPolicy();
        assertThat(clearPathPolicy.isMovable(path, Side.CHO, boardInterface)).isFalse();
    }
}