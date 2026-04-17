package domain.piece;

import domain.board.PieceProvider;
import domain.position.Position;
import domain.Team;
import domain.strategy.Strategy;
import java.util.List;

public abstract class MoveablePiece extends Piece {
    protected final Strategy moveStrategy;

    public MoveablePiece(Team team, PieceType pieceType, Strategy moveStrategy) {
        super(team, pieceType);
        this.moveStrategy = moveStrategy;
    }

    public boolean canMove(Position from, Position to, PieceProvider pieceProvider){
        List<Position> moveCandidates = moveStrategy.getMoveCandidates(from, team, pieceProvider);
        if (moveCandidates.contains(to)) {
            validateTarget(pieceProvider.getPiece(to));
            return true;
        }
        return false;
    }

    public List<Position> getMoveCandidates(Position from, PieceProvider board) {
        return moveStrategy.getMoveCandidates(from, team, board);
    }

    public void validateTarget(Piece targetPiece) {
        if (!targetPiece.isOtherTeam(team)) {
            throw new IllegalArgumentException("[ERROR] 같은 팀의 기물은 잡을 수 없습니다.");
        }
    }
}
