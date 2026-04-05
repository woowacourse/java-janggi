package janggiBoard.repositoryTest;

import repository.JdbcBoardRepository;
import domain.Position;
import domain.Team;
import domain.piece.King;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.strategy.PalaceStrategy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import repository.JdbcGameRepository;

import java.util.HashMap;
import java.util.Map;

public class JdbcRepositoryTest {

    private final JdbcBoardRepository jdbcBoardRepository = new JdbcBoardRepository();
    private final JdbcGameRepository jdbcGameRepository = new JdbcGameRepository();

    @Test
    void 보드를_저장하고_다시_불러왔을때_정보가_일치해야한다() {
        HashMap<Position, Piece> board = new HashMap<>();
        Position position = new Position(0, 0);
        board.put(position, new King(Team.CHO, new PalaceStrategy()));

        jdbcBoardRepository.save(board);
        Map<Position, Piece> result = jdbcBoardRepository.findAll();

        Assertions.assertThat(result.get(position).getPieceType()).isEqualTo(PieceType.KING);
    }

    @Test
    void 게임의_상태를_저장하고_다시_불러왔을떄_정보가_일치해야한다() {
        Team currentTurn = Team.HAN;
        boolean isFinished = false;
        double choScore = 10.0;
        double hanScore = 20.0;

        jdbcGameRepository.save(currentTurn, isFinished, choScore, hanScore);

        Team turn = jdbcGameRepository.findCurrentTurn();

        Assertions.assertThat(turn).isEqualTo(currentTurn);
    }
}
