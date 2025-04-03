package database.repository.piece;

import static org.assertj.core.api.Assertions.assertThat;

import database.connector.MySQLTestConnector;
import java.util.Map;
import java.util.Map.Entry;
import object.coordinate.Coordinate;
import object.piece.Piece;
import object.piece.PieceType;
import object.team.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceRepositoryTest {

    private PieceRepository pieceRepository;

    @BeforeEach
    void beforeEach() {
        MySQLTestConnector connector = new MySQLTestConnector();
        pieceRepository = new PieceRepository(connector);
        pieceRepository.initializeTable();
    }

    @Test
    @DisplayName("기물을 저장하고 불러온다.")
    void test1() {
        // given
        Coordinate hanCoordinate = new Coordinate(1, 1);
        Piece hanCha = new Piece(Country.HAN, PieceType.CHA);
        Coordinate choCoordinate = new Coordinate(1, 10);
        Piece choCha = new Piece(Country.CHO, PieceType.CHA);

        Entry<Coordinate, Piece> hanEntry = Map.entry(hanCoordinate, hanCha);
        Entry<Coordinate, Piece> choEntry = Map.entry(choCoordinate, choCha);
        Map<Coordinate, Piece> pieces = Map.ofEntries(hanEntry, choEntry);

        // when
        pieceRepository.savePieces(pieces);
        PieceDto loadedPieces = pieceRepository.loadPieces();

        // then
        assertThat(loadedPieces.hanPieces().get(hanCoordinate).getPieceType()).isEqualTo(PieceType.CHA);
        assertThat(loadedPieces.hanPieces().get(hanCoordinate).getCountry()).isEqualTo(Country.HAN);
        assertThat(loadedPieces.choPieces().get(choCoordinate).getPieceType()).isEqualTo(PieceType.CHA);
        assertThat(loadedPieces.choPieces().get(choCoordinate).getCountry()).isEqualTo(Country.CHO);
    }
}
