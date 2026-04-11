package domain.state;

import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import domain.game.JanggiGame;
import domain.piece.Team;
import domain.setup.Command;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("PlayingState 클래스 테스트")
class PlayingStateTest {

    private JanggiGame game;

    @BeforeEach
    void setUp() {
        game = new JanggiGame();
        game.processCommand(new Command("1"));
        game.processCommand(new Command("1"));
    }

    @Test
    @DisplayName("설정 완료 후 CHO 팀 턴으로 시작한다")
    void gameStartsWithChoTurn() {
        assertThat(game.getTurn().getTeam()).isEqualTo(Team.CHO);
    }

    @Test
    @DisplayName("유효한 이동 명령을 처리하면 기물이 이동한다")
    void handleValidMoveCommandMovesPiece() {
        game.processCommand(new Command("e6 e5"));

        assertThat(game.getBoard().isEmpty(new Position(Column.E, Row.SIX))).isTrue();
        assertThat(game.getBoard().findPieceByPosition(new Position(Column.E, Row.FIVE))).isPresent();
    }

    @Test
    @DisplayName("이동 후 턴이 상대 팀으로 바뀐다")
    void turnChangesToOpponentAfterMove() {
        game.processCommand(new Command("e6 e5")); // CHO moves

        assertThat(game.getTurn().getTeam()).isEqualTo(Team.HAN);
    }

    @Test
    @DisplayName("handle은 PlayingState를 계속 반환한다")
    void handleReturnsPlayingState() {
        PlayingState state = new PlayingState();

        GameState nextState = state.handle(game, new Command("e6 e5"));

        assertThat(nextState).isInstanceOf(PlayingState.class);
    }

    @Test
    @DisplayName("유효하지 않은 좌표 형식은 예외를 던진다")
    void invalidCoordinateFormatThrowsException() {
        assertThatThrownBy(() -> game.processCommand(new Command("invalid")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("빈 위치에서 이동 시 예외를 던진다")
    void moveFromEmptyPositionThrowsException() {
        assertThatThrownBy(() -> game.processCommand(new Command("a4 a5")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상대 팀의 기물을 이동 시 예외를 던진다")
    void moveOpponentPieceThrowsException() {
        assertThatThrownBy(() -> game.processCommand(new Command("a3 a4")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("유효하지 않은 이동 경로는 예외를 던진다")
    void invalidMovePathThrowsException() {
        assertThatThrownBy(() -> game.processCommand(new Command("e6 e7")))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
