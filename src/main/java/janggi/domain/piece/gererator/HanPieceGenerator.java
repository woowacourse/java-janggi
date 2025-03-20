package janggi.domain.piece.gererator;

import janggi.domain.piece.Piece;
import java.util.List;

public interface HanPieceGenerator {

    List<Piece> generate(KnightElephantSetting knightElephantSetting);
}
