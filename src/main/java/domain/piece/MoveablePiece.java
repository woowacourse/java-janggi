package domain.piece;

import domain.PieceProvider;
import domain.position.Position;
import domain.Team;
import domain.strategy.Strategy;
import java.util.List;

public abstract class MoveablePiece extends Piece {
    protected final Strategy moveStrategy;

    public MoveablePiece(Team team, Strategy moveStrategy) {
        super(team);
        this.moveStrategy = moveStrategy;
    }

    public abstract boolean canMove(Position from, Position to, PieceProvider pieceProvider);

    public List<Position> getMoveCandidates(Position from, PieceProvider board) {
        return moveStrategy.getMoveCandidates(from, team, board);
    }

    public void validateTarget(Piece targetPiece) {
        if (targetPiece.getTeam() == team) {
            throw new IllegalArgumentException("[ERROR] 같은 팀의 기물은 잡을 수 없습니다.");
        }
    }
}
