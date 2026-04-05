package janggi.dto;

import janggi.domain.piece.PieceAttribute;
import java.util.List;

public record BoardDto(List<List<PieceAttribute>> board) {
}
