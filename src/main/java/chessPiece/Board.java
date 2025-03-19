package chessPiece;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {

    private final Map<BoardPosition, ChessPiece> janggiPan = new HashMap<>();

    public Board(List<ChessPiece> han, List<ChessPiece> cho) {
        janggiPan.putAll(
                Stream.concat(han.stream(), cho.stream())
                        .collect(Collectors.toMap(ChessPiece::getBoardPosition, piece -> piece))
        );
    }

    public Map<BoardPosition, ChessPiece> getJanggiPan() {
        return janggiPan;
    }
}
