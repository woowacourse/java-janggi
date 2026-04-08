package janggi.service.dto;

import janggi.domain.Side;
import janggi.domain.board.Board;

public record GameInformation(
        Long gameId,
        Board board,
        Side currentSide
) {
}
