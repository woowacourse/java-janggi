package domain;

import domain.direction.PieceDirection;
import domain.piece.Pieces;
import domain.piece.Score;
import domain.piece.category.King;
import domain.piece.category.Soldier;
import domain.spatial.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameResultTest {

    @Test
    void 플레이어가_왕을_가지고_있지_않으면_패배한다() {
        // given
        Player han = new Player(Team.HAN, new Score(0));
        Player cho = new Player(Team.CHO, new Score(0));

        Pieces hanPieces = new Pieces(List.of(new Soldier(new Position(1, 1), PieceDirection.HAN_SOLDIER.get())));
        Pieces choPieces = createPiecesWithKing(cho);

        Map<Player, Pieces> gamePlayers = new HashMap<>();
        gamePlayers.put(han, hanPieces);
        gamePlayers.put(cho, choPieces);

        // when
        GameResult gameResult = new GameResult(gamePlayers);

        // then
        assertThat(gameResult.getResultMap().get(han)).isEqualTo(Result.LOSE);
        assertThat(gameResult.getResultMap().get(cho)).isEqualTo(Result.WIN);
    }

    @Test
    void 상대_플레이어가_왕을_가지고_있지_않으면_승리한다() {
        // given
        Player han = new Player(Team.HAN, new Score(0));
        Player cho = new Player(Team.CHO, new Score(0));

        Pieces hanPieces = createPiecesWithKing(han);
        Pieces choPieces = new Pieces(List.of(new Soldier(new Position(1, 1), PieceDirection.CHO_SOLDIER.get())));

        Map<Player, Pieces> gamePlayers = new HashMap<>();
        gamePlayers.put(han, hanPieces);
        gamePlayers.put(cho, choPieces);

        // when
        GameResult gameResult = new GameResult(gamePlayers);

        // then
        assertThat(gameResult.getResultMap().get(han)).isEqualTo(Result.WIN);
        assertThat(gameResult.getResultMap().get(cho)).isEqualTo(Result.LOSE);
    }

    @Test
    void 두_플레이어가_모두_왕을_가지고_있으면_점수로_결정된다() {
        // given
        Player han = new Player(Team.HAN, new Score(10));
        Player cho = new Player(Team.CHO, new Score(5));

        Pieces hanPieces = createPiecesWithKing(han);
        Pieces choPieces = createPiecesWithKing(cho);

        Map<Player, Pieces> gamePlayers = new HashMap<>();
        gamePlayers.put(han, hanPieces);
        gamePlayers.put(cho, choPieces);

        // when
        GameResult gameResult = new GameResult(gamePlayers);

        // then
        assertThat(gameResult.getResultMap().get(han)).isEqualTo(Result.WIN);
        assertThat(gameResult.getResultMap().get(cho)).isEqualTo(Result.LOSE);
    }

    @Test
    void 두_플레이어가_모두_왕을_가지고_있고_점수가_같으면_무승부다() {
        // given
        Player han = new Player(Team.HAN, new Score(10));
        Player cho = new Player(Team.CHO, new Score(10));

        Pieces hanPieces = createPiecesWithKing(han);
        Pieces choPieces = createPiecesWithKing(cho);

        Map<Player, Pieces> gamePlayers = new HashMap<>();
        gamePlayers.put(han, hanPieces);
        gamePlayers.put(cho, choPieces);

        // when
        GameResult gameResult = new GameResult(gamePlayers);

        // then
        assertThat(gameResult.getResultMap().get(han)).isEqualTo(Result.DRAW);
        assertThat(gameResult.getResultMap().get(cho)).isEqualTo(Result.DRAW);
    }

    private Pieces createPiecesWithKing(Player player) {
        return new Pieces(List.of(new King(new Position(1, 1), PieceDirection.KING.get())));
    }
}
