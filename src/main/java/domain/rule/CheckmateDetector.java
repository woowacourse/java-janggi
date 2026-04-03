package domain.rule;

import domain.board.Board;
import domain.board.Position;
import domain.movement.Movement;
import domain.movement.MovementFactory;
import domain.movement.MovementValidator;
import domain.movement.Paths;
import domain.piece.Piece;
import domain.piece.Team;

public class CheckmateDetector {
    private final CheckDetector checkDetector = new CheckDetector();

    public boolean isCheckmate(Board board, Team checkedTeam) {
        return checkDetector.isInCheck(board, checkedTeam) && hasNoLegalMoves(board, checkedTeam);
    }

    public boolean hasNoLegalMoves(Board board, Team team) {
        return board.getAllPiecesOf(team).entrySet().stream()
                .allMatch(entry -> hasNoLegalMove(board, entry.getKey(), entry.getValue(), team));
    }

    private boolean hasNoLegalMove(Board board, Position from, Piece piece, Team team) {
        Movement movement = MovementFactory.create(piece);
        Paths paths = movement.candidatePaths(from);
        MovementValidator validator = new MovementValidator(board);

        return paths.allCandidatePositions().stream()
                .filter(to -> validator.isValid(piece, paths, to))
                .noneMatch(to -> {
                    Board simulated = board.simulateMove(from, to);
                    return !checkDetector.isInCheck(simulated, team);
                });
    }
}
