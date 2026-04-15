package janggi.dto;

import janggi.domain.Side;
import janggi.domain.board.Board;

public record GameDto(int gameId, Board board, Side turn) {
}
