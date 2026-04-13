package model;

import java.util.List;
import java.util.Map;
import model.board.Board;
import model.coordinate.Direction;
import model.coordinate.Displacement;
import model.coordinate.Position;
import model.piece.Piece;

public class JanggiReferee {

    private static final Team START_TURN = Team.startTurn();
    private static final Team AFTER_TURN = Team.afterTurn();

    private static final double AFTER_TURN_BONUS_SCORE = 1.5;

    private JanggiReferee() {
    }

    public static Map<Team, Double> collectScores(Board board) {
        return Map.of(
                START_TURN, board.calculateBaseScore(START_TURN),
                AFTER_TURN, board.calculateBaseScore(AFTER_TURN) + AFTER_TURN_BONUS_SCORE
        );
    }

    public static Team judgeBigJangWinner(Board board) {
        Map<Team, Double> scores = collectScores(board);
        double startTurnScore = scores.get(START_TURN);
        double afterTurnScore = scores.get(AFTER_TURN);

        if (startTurnScore > afterTurnScore) {
            return START_TURN;
        }
        return AFTER_TURN;
    }

    public static boolean checkBigJang(Board board, Team turn) {
        Position allyGeneralPosition = board.findGeneralPositionByTeam(turn);
        Position enemyGeneralPosition = board.findGeneralPositionByTeam(turn.opposite());
        Displacement displacement = enemyGeneralPosition.toDisplacement(allyGeneralPosition);
        if (displacement.isNotStraight()) {
            return false;
        }

        Direction direction = displacement.extractCardinal();
        List<Position> path = direction.pathTo(allyGeneralPosition, enemyGeneralPosition);
        List<Piece> pieces = board.extractPiecesByPath(path);
        return pieces.isEmpty();
    }
}
