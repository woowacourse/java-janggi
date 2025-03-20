package janggi.domain.board;

import janggi.domain.piece.BoardPiece;
import java.util.Set;

//TODO SetUp이 초기화 위치까지 아는것에 대한 리팩터링
public interface BoardSetUp {
    Set<BoardPiece> getPiecePositions();
}
