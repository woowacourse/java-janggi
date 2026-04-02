package domain.rule;

import domain.board.Board;
import domain.board.Position;
import domain.movement.Movement;
import domain.movement.MovementFactory;
import domain.movement.MovementValidator;
import domain.movement.Paths;
import domain.piece.Piece;
import domain.piece.Team;

public class CheckDetector {

    public boolean isInCheck(Board board, Team targetTeam) {
        Position generalPosition = board.findGeneral(targetTeam);
        return board.getAllPiecesOf(targetTeam.enemy()).entrySet().stream()
                .anyMatch(entry -> canAttack(board, entry.getKey(), entry.getValue(), generalPosition));
    }

    private boolean canAttack(Board board, Position from, Piece attacker, Position target) {
        Movement movement = MovementFactory.create(attacker);
        Paths paths = movement.candidatePaths(from);
        return new MovementValidator(board).isValid(attacker, paths, target);
    }
}
