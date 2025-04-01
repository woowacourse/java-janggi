package dao;

import static org.assertj.core.api.Assertions.assertThat;

import domain.chessPiece.ChessPiece;
import domain.chessPiece.Pawn;
import domain.fake.InMemoryPieceDao;
import domain.position.ChessPosition;
import domain.type.ChessTeam;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceDaoImplTest {


    @Test
    @DisplayName("save 테스트")
    void test1() {
        //given
        final List<ChessPiece> chessPieces = List.of(
                new Pawn(new ChessPosition(1, 1), ChessTeam.RED),
                new Pawn(new ChessPosition(0, 0), ChessTeam.RED)
        );

        //when
        final InMemoryPieceDao inMemoryPieceDao = new InMemoryPieceDao();
        inMemoryPieceDao.saveAll(chessPieces);
        final List<ChessPiece> dataSource = inMemoryPieceDao.findAll();

        //then
        assertThat(dataSource).hasSize(chessPieces.size());
    }

    @Test
    @DisplayName("clear 테스트")
    void test2() {
        //given
        final List<ChessPiece> chessPieces = List.of(
                new Pawn(new ChessPosition(1, 1), ChessTeam.RED),
                new Pawn(new ChessPosition(0, 0), ChessTeam.RED)
        );

        //when
        final InMemoryPieceDao inMemoryPieceDao = new InMemoryPieceDao();
        inMemoryPieceDao.saveAll(chessPieces);
        inMemoryPieceDao.clear();
        final List<ChessPiece> dataSource = inMemoryPieceDao.findAll();

        //then
        assertThat(dataSource).isEmpty();

    }

}
