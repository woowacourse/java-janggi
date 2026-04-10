import domain.board.PiecePosition;
import domain.board.Position;
import domain.board.Route;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.TeamColor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GameRunnerTest {

    private final GameRunner gameRunner = new GameRunner();

    @Nested
    class 예외 {
        @Test
        void 시작_옵션_번호가_범위를_벗어나면_예외가_발생한다() {
            assertThatThrownBy(() -> invokePrivate("validateGameStartChoice",
                    new Class<?>[]{int.class},
                    3))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("시작 옵션 번호는 1 또는 2여야 합니다.");
        }

        @Test
        void 상차림_번호가_범위를_벗어나면_예외가_발생한다() {
            assertThatThrownBy(() -> invokePrivate("validateFormationChoice",
                    new Class<?>[]{int.class},
                    0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("상차림 번호는 1~4 사이여야 합니다.");
        }

        @Test
        void 기물_번호가_범위를_벗어나면_예외가_발생한다() {
            List<PiecePosition> pieces = List.of(
                    new PiecePosition(Position.of(4, 4), Piece.of(TeamColor.CHO, PieceType.PAWN))
            );

            assertThatThrownBy(() -> invokePrivate("getSelectedPiece",
                    new Class<?>[]{List.class, int.class},
                    pieces, 2))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("기물 번호가 범위를 벗어났습니다.");
        }

        @Test
        void 경로_번호가_범위를_벗어나면_예외가_발생한다() {
            List<Route> routes = List.of(
                    new Route(Position.of(4, 4), Position.of(3, 4), List.of())
            );

            assertThatThrownBy(() -> invokePrivate("getSelectedRoute",
                    new Class<?>[]{List.class, int.class},
                    routes, 2))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("경로 번호가 범위를 벗어났습니다.");
        }

        @Test
        void 이동_가능한_경로가_없으면_예외가_발생한다() {
            assertThatThrownBy(() -> invokePrivate("validateMovableRoutes",
                    new Class<?>[]{List.class},
                    List.of()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("선택한 기물은 이동 가능한 경로가 없습니다.");
        }
    }

    private Object invokePrivate(String methodName, Class<?>[] parameterTypes, Object... args) throws Throwable {
        Method method = GameRunner.class.getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        try {
            return method.invoke(gameRunner, args);
        } catch (InvocationTargetException exception) {
            throw exception.getCause();
        }
    }
}
