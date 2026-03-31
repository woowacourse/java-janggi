package janggi.dto;

import janggi.domain.piece.PieceManifest;
import java.util.List;

public record BoardDto(List<List<PieceManifest>> board) {
}
