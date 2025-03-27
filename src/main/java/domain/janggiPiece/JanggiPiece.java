package domain.janggiPiece;

import domain.hurdlePolicy.HurdlePolicy;
import domain.path.Path;
import domain.position.JanggiPosition;
import domain.score.Score;
import domain.type.JanggiPieceType;
import domain.type.JanggiTeam;

import java.util.List;

public interface JanggiPiece {
    List<Path> getCoordinatePaths(JanggiPosition startPosition);
    JanggiPieceType getChessPieceType();
    HurdlePolicy getHurdlePolicy();
    JanggiTeam getTeam();
    Score getScore();
}
