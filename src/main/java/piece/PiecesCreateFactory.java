package piece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import strategy.MoveStrategy;
import strategy.MoveStrategyFactory;

public class PiecesCreateFactory {

    public static Map<Team,Pieces> generate(List<String> pieces) {
        Map<Team,List<Piece>> teamPieces = new HashMap<>();

        for (String inputPiece : pieces) {
            var createdPiece = createPiece(inputPiece);
            var team = parseTeam(inputPiece);
            List<Piece> previousPieces = teamPieces.computeIfAbsent(team,k -> new ArrayList<>());
            previousPieces.add(createdPiece);
            teamPieces.put(team,previousPieces);
        }

        return teamPieces.entrySet()
                .stream()
                .collect(Collectors.toMap(Entry::getKey, entry -> new Pieces(entry.getValue())));
    }

    private static Piece createPiece(String inputPiece) {
        String[] perPiece = inputPiece.split(" ");
        Position position = parsePosition(perPiece[0]);
        PieceType pieceType = parsePieceType(perPiece[1]);
        MoveStrategy moveStrategy = MoveStrategyFactory.create(pieceType);
        return new Piece(position, moveStrategy, pieceType);
    }

    private static Team parseTeam(String inputPiece) {
        String[] perPiece = inputPiece.split(" ");
        return Team.from(perPiece[2]);
    }

    private static PieceType parsePieceType(String s) {
        return PieceType.from(s);
    }

    private static Position parsePosition(String s) {
        String[] positions = s.split(",");
        int r = Integer.parseInt(positions[0]);
        int c = Integer.parseInt(positions[1]);
        return new Position(r,c);
    }
}

