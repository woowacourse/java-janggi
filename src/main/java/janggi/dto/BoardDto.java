package janggi.dto;

import janggi.domain.piece.PieceInfo;
import java.util.List;

public record BoardDto(List<List<PieceInfo>> board) {
}
