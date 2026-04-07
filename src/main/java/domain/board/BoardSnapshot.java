package domain.board;

import domain.country.CountryType;
import domain.piece.PieceInfos;
import java.util.Objects;

public record BoardSnapshot(PieceInfos pieceInfos, CountryType turn) {
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BoardSnapshot that = (BoardSnapshot) o;
        return Objects.equals(pieceInfos, that.pieceInfos) && turn == that.turn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceInfos, turn);
    }
}
