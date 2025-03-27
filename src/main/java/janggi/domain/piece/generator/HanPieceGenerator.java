package janggi.domain.piece.generator;

import janggi.domain.piece.Piece;
import java.util.List;

public interface HanPieceGenerator {

    List<Piece> generate(KnightElephantSetting knightElephantSetting);
}
