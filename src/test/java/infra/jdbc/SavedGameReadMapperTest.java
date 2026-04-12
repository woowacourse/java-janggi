package infra.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.GameStatus;
import domain.game.JanggiGame;
import domain.pieces.EmptyPiece;
import domain.pieces.PieceType;
import domain.pieces.Side;
import domain.position.Position;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class SavedGameReadMapperTest {

    @Test
    void 저장된_진행중_게임을_JanggiGame으로_복원한다() {
        // given
        SavedGameReadMapper mapper = new SavedGameReadMapper();
        SavedGameDto savedGameDto = new SavedGameDto(
                1L,
                Side.HAN,
                GameStatus.RUNNING,
                null,
                LocalDateTime.of(2026, 4, 7, 12, 0),
                LocalDateTime.of(2026, 4, 7, 12, 5),
                java.util.List.of(
                        new SavedPieceDto(0, 0, Side.CHO, PieceType.CHA),
                        new SavedPieceDto(8, 4, Side.HAN, PieceType.GUNG)
                )
        );

        // when
        JanggiGame janggiGame = mapper.toJanggiGame(savedGameDto);

        // then
        assertThat(janggiGame.currentTurn()).isEqualTo(Side.HAN);
        assertThat(janggiGame.gameResult().status()).isEqualTo(GameStatus.RUNNING);
        assertThat(janggiGame.gameResult().winner()).isNull();
        assertThat(janggiGame.board().pieces().get(new Position(0, 0)).getType()).isEqualTo(PieceType.CHA);
        assertThat(janggiGame.board().pieces().get(new Position(8, 4)).getType()).isEqualTo(PieceType.GUNG);
        assertThat(janggiGame.board().pieces().get(new Position(4, 4))).isInstanceOf(EmptyPiece.class);
    }

    @Test
    void 저장된_종료_게임을_JanggiGame으로_복원한다() {
        // given
        SavedGameReadMapper mapper = new SavedGameReadMapper();
        SavedGameDto savedGameDto = new SavedGameDto(
                2L,
                Side.CHO,
                GameStatus.ENDED,
                Side.CHO,
                LocalDateTime.of(2026, 4, 7, 12, 0),
                LocalDateTime.of(2026, 4, 7, 12, 30),
                java.util.List.of(
                        new SavedPieceDto(8, 4, Side.CHO, PieceType.CHA)
                )
        );

        // when
        JanggiGame janggiGame = mapper.toJanggiGame(savedGameDto);

        // then
        assertThat(janggiGame.currentTurn()).isEqualTo(Side.CHO);
        assertThat(janggiGame.gameResult().status()).isEqualTo(GameStatus.ENDED);
        assertThat(janggiGame.gameResult().winner()).isEqualTo(Side.CHO);
        assertThat(janggiGame.board().pieces().get(new Position(8, 4)).getType()).isEqualTo(PieceType.CHA);
    }
}
