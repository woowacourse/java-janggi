package domain.dao;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Board;
import domain.piece.Piece;
import domain.piece.PieceFactory;
import domain.piece.strategy.LeftElephantStrategy;
import domain.piece.strategy.RightElephantStrategy;
import domain.position.Position;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardDaoTest {

    @Test
    @DisplayName("보드 상태 저장 테스트")
    void saveTest() {
        PieceFactory pieceFactory = new PieceFactory();
        Map<Position, Piece> allPieces = pieceFactory.createAllPieces(new RightElephantStrategy(),
                new LeftElephantStrategy());
        Board board = new Board(allPieces);

        BoardDao boardDao = new BoardDao();
        boardDao.save(board);
    }

    @Test
    @DisplayName("보드 조회 기능 테스트")
    void getBoard() {
        BoardDao boardDao = new BoardDao();
        Optional<Board> optionalBoard = boardDao.findBoard();
        assertThat(optionalBoard).isPresent();
        Board board = optionalBoard.get();

        Map<Position, Piece> alivePieces = board.getAlivePieces();
        PieceFactory pieceFactory = new PieceFactory();
        Map<Position, Piece> allPieces = pieceFactory.createAllPieces(new RightElephantStrategy(),
                new LeftElephantStrategy());

        assertThat(alivePieces.size()).isEqualTo(allPieces.size());
    }

    @Test
    @DisplayName("보드 상태 업데이트")
    void updateBoard() {
        BoardDao boardDao = new BoardDao();
        boardDao.updateBoard(Position.of(1, 1), Position.of(0, 0
        ));
    }

}