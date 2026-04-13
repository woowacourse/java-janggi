package domain.piece.rule;

import domain.coordination.Coordination;
import domain.piece.Piece;
import domain.piece.Team;
import java.util.List;

public interface PieceRule {

    void validate(Coordination from, Coordination to, Team team);

    List<Coordination> resolvePath(Coordination from, Coordination to, Team team);

    void validatePath(List<Piece> piecesOnPath);
}
