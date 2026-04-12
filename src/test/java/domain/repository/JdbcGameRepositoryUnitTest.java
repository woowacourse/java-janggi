package domain.repository;

import domain.Game;
import domain.board.Board;
import domain.coordinate.Position;
import domain.entity.GameRoomEntity;
import domain.piece.Chariot;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.state.ChuSide;
import domain.state.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class JdbcGameRepositoryUnitTest {

    private final GameRepository repository = new FakeGameRepository();

    @Test
    @DisplayName("메모리 저장소를 이용해 게임을 저장하고 불러온다")
    void saveAndLoadWithFake() {
        // given
        Game game = createSampleGame();

        // when
        repository.save(game);
        Optional<Game> loadedGame = repository.load(game.getId());

        // then
        assertThat(loadedGame).isPresent();
        assertThat(loadedGame.get().getId()).isEqualTo(game.getId());
    }

    @Test
    @DisplayName("여러 게임을 저장해도 메모리에서 독립적으로 관리된다")
    void findAllRoomsWithFake() {
        // given
        repository.save(createSampleGame());
        repository.save(createSampleGame());

        // when
        List<GameRoomEntity> rooms = repository.findAllRooms();

        // then
        assertThat(rooms.size()).isEqualTo(2);
    }

    private Game createSampleGame() {
        Map<Position, Piece> pieceMap = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                pieceMap.put(Position.of(i, j), EmptyPiece.getInstance());
            }
        }
        pieceMap.put(Position.of(0, 0), new Chariot(Side.CHU));

        return new Game(new Board(pieceMap), new ChuSide());
    }
}