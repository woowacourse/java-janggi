package domain.janggiPiece;

import domain.hurdlePolicy.HurdlePolicy;
import domain.path.JanggiPath;
import domain.position.JanggiPosition;
import domain.score.Score;
import domain.type.JanggiPieceType;
import domain.type.JanggiTeam;

import java.util.List;

public interface JanggiPiece {
    List<JanggiPath> getCoordinatePaths(JanggiPosition startPosition);
    JanggiPieceType getChessPieceType();
    HurdlePolicy getHurdlePolicy();
    JanggiTeam getTeam();
    Score getScore();
}
