package domain;

import controller.dto.CurrentBoardStatus;
import controller.dto.MovedPieceRequest;
import java.util.List;
import java.util.Map;
import strategy.InitializeStrategy;

public class GameManager {
    private final Board board;

    public GameManager(Map<Team, InitializeStrategy> initializeStrategies) {
        this.board = new Board(
                initializeStrategies.get(Team.CHO),
                initializeStrategies.get(Team.HAN)
        );
    }

    public List<CurrentBoardStatus> getCurrentBoardStatus() {
        return board.getCurrentStatus();
    }

    public void movePiece(MovedPieceRequest request, Team team) {
        board.move(Position.from(request.currentRow(), request.currentColumn()),
                Position.from(request.nextRow(), request.nextColumn()),
                PieceType.getPieceType(request.pieceType()),
                team);
    }

    public boolean isGameFinished(Team currentTeam) {
        return !board.isExistPiece(PieceType.KING, currentTeam);
    }
}
