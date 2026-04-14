import domain.Board;
import domain.Camp;

public record GameContext(Long gameId, Board board, Camp camp) {

}
