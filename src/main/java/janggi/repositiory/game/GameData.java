package janggi.repositiory.game;

import janggi.domain.piece.Team;

public record GameData(Long gameId, boolean isFinished, Team currentTurn) {
}
