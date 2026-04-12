package infra.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.game.GameStatus;
import domain.game.JanggiGame;
import domain.pieces.Cha;
import domain.pieces.EmptyPiece;
import domain.pieces.Gung;
import domain.pieces.Piece;
import domain.pieces.PieceType;
import domain.pieces.Side;
import domain.position.Position;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class SavedGameWriteMapperTest {

    @Test
    void 진행중인_게임을_SavedGameDto로_변환한다() {
        // given
        Clock fixedClock = Clock.fixed(Instant.parse("2026-04-07T03:00:00Z"), ZoneId.of("Asia/Seoul"));
        SavedGameWriteMapper mapper = new SavedGameWriteMapper(fixedClock);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(0, 0), new Cha(Side.CHO));
        pieces.put(new Position(1, 0), new EmptyPiece());
        pieces.put(new Position(8, 4), new Gung(Side.HAN));

        JanggiGame janggiGame = new JanggiGame(new Board(pieces));

        // when
        SavedGameDto savedGameDto = mapper.toSavedGameDto(janggiGame);

        // then
        assertThat(savedGameDto.gameId()).isNull();
        assertThat(savedGameDto.currentTurn()).isEqualTo(Side.CHO);
        assertThat(savedGameDto.status()).isEqualTo(GameStatus.RUNNING);
        assertThat(savedGameDto.winner()).isNull();
        assertThat(savedGameDto.createdAt()).isEqualTo(LocalDateTime.of(2026, 4, 7, 12, 0));
        assertThat(savedGameDto.updatedAt()).isEqualTo(LocalDateTime.of(2026, 4, 7, 12, 0));
        assertThat(savedGameDto.pieces()).containsExactlyInAnyOrder(
                new SavedPieceDto(0, 0, Side.CHO, PieceType.CHA),
                new SavedPieceDto(8, 4, Side.HAN, PieceType.GUNG)
        );
    }

    @Test
    void 종료된_게임을_SavedGameDto로_변환한다() {
        // given
        Clock fixedClock = Clock.fixed(Instant.parse("2026-04-07T03:30:00Z"), ZoneId.of("Asia/Seoul"));
        SavedGameWriteMapper mapper = new SavedGameWriteMapper(fixedClock);
        LocalDateTime createdAt = LocalDateTime.of(2026, 4, 7, 12, 0);

        Position departure = new Position(7, 4);
        Position destination = new Position(8, 4);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(departure, new Cha(Side.CHO));
        pieces.put(destination, new Gung(Side.HAN));

        JanggiGame janggiGame = new JanggiGame(new Board(pieces));
        janggiGame.move(departure, destination);

        // when
        SavedGameDto savedGameDto = mapper.toSavedGameDto(1L, createdAt, janggiGame);

        // then
        assertThat(savedGameDto.gameId()).isEqualTo(1L);
        assertThat(savedGameDto.currentTurn()).isEqualTo(Side.CHO);
        assertThat(savedGameDto.status()).isEqualTo(GameStatus.ENDED);
        assertThat(savedGameDto.winner()).isEqualTo(Side.CHO);
        assertThat(savedGameDto.createdAt()).isEqualTo(createdAt);
        assertThat(savedGameDto.updatedAt()).isEqualTo(LocalDateTime.of(2026, 4, 7, 12, 30));
        assertThat(savedGameDto.pieces()).containsExactlyInAnyOrder(
                new SavedPieceDto(8, 4, Side.CHO, PieceType.CHA)
        );
    }
}
