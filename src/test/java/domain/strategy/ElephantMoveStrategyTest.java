package domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Path;
import domain.Position;
import domain.country.CountryType;
import domain.piece.PieceInfo;
import domain.piece.PieceInfos;
import domain.piece.PieceType;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ElephantMoveStrategyTest {
    private final Path path = new Path();
    private final PieceInfo elephantPieceInfo = new PieceInfo(PieceType.ELEPHANT, CountryType.CHO);

    @Test
    @DisplayName("상의 위쪽-왼쪽위대각선 목적지까지의 경로를 정확히 계산한다.")
    void elephantUpAndLeftUpPathTest() {
        MoveStrategy moveStrategy = new ElephantMoveStrategy();

        Position from = new Position(4, 4);
        Position to = new Position(2, 7);

        path.add(new Position(4, 4));
        path.add(new Position(4, 5));
        path.add(new Position(3, 6));
        path.add(new Position(2, 7));

        assertThat(moveStrategy.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("상의 위쪽-오른쪽위대각선 목적지까지의 경로를 정확히 계산한다.")
    void elephantUpAndRightUpPathTest() {
        MoveStrategy moveStrategy = new ElephantMoveStrategy();

        Position from = new Position(4, 4);
        Position to = new Position(6, 7);

        path.add(new Position(4, 4));
        path.add(new Position(4, 5));
        path.add(new Position(5, 6));
        path.add(new Position(6, 7));

        assertThat(moveStrategy.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("상의 오른쪽-오른쪽위대각선 목적지까지의 경로를 정확히 계산한다.")
    void elephantRightAndRightUpPathTest() {
        MoveStrategy moveStrategy = new ElephantMoveStrategy();

        Position from = new Position(4, 4);
        Position to = new Position(7, 6);

        path.add(new Position(4, 4));
        path.add(new Position(5, 4));
        path.add(new Position(6, 5));
        path.add(new Position(7, 6));

        assertThat(moveStrategy.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("상의 오른쪽-오른쪽아래대각선 목적지까지의 경로를 정확히 계산한다.")
    void elephantRightAndRightDownPathTest() {
        MoveStrategy moveStrategy = new ElephantMoveStrategy();

        Position from = new Position(4, 4);
        Position to = new Position(7, 2);

        path.add(new Position(4, 4));
        path.add(new Position(5, 4));
        path.add(new Position(6, 3));
        path.add(new Position(7, 2));

        assertThat(moveStrategy.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("상의 아래쪽-오른쪽아래대각선 목적지까지의 경로를 정확히 계산한다.")
    void elephantDownAndRightDownPathTest() {
        MoveStrategy moveStrategy = new ElephantMoveStrategy();

        Position from = new Position(4, 4);
        Position to = new Position(6, 1);

        path.add(new Position(4, 4));
        path.add(new Position(4, 3));
        path.add(new Position(5, 2));
        path.add(new Position(6, 1));

        assertThat(moveStrategy.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("상의 아래쪽-왼쪽아래대각선 목적지까지의 경로를 정확히 계산한다.")
    void elephantDownAndLeftDownPathTest() {
        MoveStrategy moveStrategy = new ElephantMoveStrategy();

        Position from = new Position(4, 4);
        Position to = new Position(2, 1);

        path.add(new Position(4, 4));
        path.add(new Position(4, 3));
        path.add(new Position(3, 2));
        path.add(new Position(2, 1));

        assertThat(moveStrategy.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("상의 왼쪽-왼쪽아래대각선 목적지까지의 경로를 정확히 계산한다.")
    void elephantLeftAndLeftDownPathTest() {
        MoveStrategy moveStrategy = new ElephantMoveStrategy();

        Position from = new Position(4, 4);
        Position to = new Position(1, 2);

        path.add(new Position(4, 4));
        path.add(new Position(3, 4));
        path.add(new Position(2, 3));
        path.add(new Position(1, 2));

        assertThat(moveStrategy.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("상의 왼쪽-왼쪽위대각선 목적지까지의 경로를 정확히 계산한다.")
    void elephantLeftAndLeftUpPathTest() {
        MoveStrategy moveStrategy = new ElephantMoveStrategy();

        Position from = new Position(4, 4);
        Position to = new Position(1, 6);

        path.add(new Position(4, 4));
        path.add(new Position(3, 4));
        path.add(new Position(2, 5));
        path.add(new Position(1, 6));

        assertThat(moveStrategy.path(from, to)).isEqualTo(path);
    }

    @Test
    @DisplayName("상의 방향의 크기가 3이 아닌 경우 예외가 발생한다.")
    void elephantDirectionSizeExceptionTest() {
        MoveStrategy moveStrategy = new ElephantMoveStrategy();

        Position from = new Position(1, 0);
        Position to = new Position(4, 4);

        assertThatThrownBy(() -> moveStrategy.path(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 상이 이동할 수 있는 방향은 3개이어야 합니다.");
    }

    @Test
    @DisplayName("상의 2번째, 3번째 방향이 같지 않을 경우 예외가 발생한다.")
    void elephantDirectionNotSameExceptionTest() {
        MoveStrategy moveStrategy = new ElephantMoveStrategy();

        Position from = new Position(1, 3);
        Position to = new Position(2, 0);

        assertThatThrownBy(() -> moveStrategy.path(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 상의 2번째 방향과 3번째 방향은 동일해야 합니다.");
    }

    @Test
    @DisplayName("상의 1번째 방향이 대각선이거나, 2번째, 3번째 방향이 모두 대각선이 아닐 경우 예외가 발생한다.")
    void elephantDiagonalExceptionTest() {
        MoveStrategy moveStrategy = new ElephantMoveStrategy();

        Position from = new Position(1, 1);
        Position allDiagonalTo = new Position(4, 4);
        Position notExistDiagonalTo = new Position(1, 4);

        assertThatThrownBy(() -> moveStrategy.path(from, allDiagonalTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 상의 1번째 방향은 직선이고, 2, 3번째 방향은 대각선이어야 합니다.");
        assertThatThrownBy(() -> moveStrategy.path(from, notExistDiagonalTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 상의 1번째 방향은 직선이고, 2, 3번째 방향은 대각선이어야 합니다.");
    }

    @Test
    @DisplayName("상의 직선 경로에 다른 기물이 존재하면 예외가 발생한다.")
    void elephantOtherPieceExistStraightPathExceptionTest() {
        MoveStrategy moveStrategy = new ElephantMoveStrategy();

        Position from = new Position(1, 1);
        Position to = new Position(3, 4);

        Map<Position, PieceInfo> pathPieceInfos = new LinkedHashMap<>();
        pathPieceInfos.put(from, elephantPieceInfo);
        pathPieceInfos.put(new Position(1, 2), new PieceInfo(PieceType.SOLDIER, CountryType.HAN));
        // (2, 3)은 비어있음
        pathPieceInfos.put(to, new PieceInfo(PieceType.SOLDIER, CountryType.HAN));
        PieceInfos pieceInfos = new PieceInfos(pathPieceInfos);

        assertThatThrownBy(() -> moveStrategy.validateMove(pieceInfos, from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동 경로에 다른 기물이 존재해 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("상의 대각선 경로에 다른 기물이 존재하면 예외가 발생한다.")
    void elephantOtherPieceExistDiagonalPathExceptionTest() {
        MoveStrategy moveStrategy = new ElephantMoveStrategy();

        Position from = new Position(1, 1);
        Position to = new Position(3, 4);

        Map<Position, PieceInfo> pathPieceInfos = new LinkedHashMap<>();
        pathPieceInfos.put(from, elephantPieceInfo);
        // (1, 2)는 비어있음
        pathPieceInfos.put(new Position(2, 3), new PieceInfo(PieceType.SOLDIER, CountryType.HAN));
        pathPieceInfos.put(to, new PieceInfo(PieceType.SOLDIER, CountryType.HAN));
        PieceInfos pieceInfos = new PieceInfos(pathPieceInfos);

        assertThatThrownBy(() -> moveStrategy.validateMove(pieceInfos, from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동 경로에 다른 기물이 존재해 이동시킬 수 없습니다.");
    }
}
