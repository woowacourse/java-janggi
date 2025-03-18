package piece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.swing.text.Position;
import strategy.MoveStrategy;
import strategy.MoveStrategyMapper;

public class PiecesCreateFactory {

    public static Map<Team,Pieces> generate(List<String> pieces) {
        Map<Team,List<Piece>> teamPieces = new HashMap<>();

        for (String piece : pieces) {
            String[] perPiece = piece.split(" ");
            Position position = parsePosition(perPiece[0]);
            PieceType pieceType = parsePieceType(perPiece[1]);
            MoveStrategy moveStrategy = MoveStrategyMapper.from(pieceType);
            Team team = parseTeam(perPiece[2]);
            List<Piece> pieceList = teamPieces.getOrDefault(team, new ArrayList<>());
            pieceList.add(new Piece(position,moveStrategy));
            teamPieces.put(team,pieceList);
        }
        return teamPieces.entrySet()
                .stream()
                .collect(Collectors.toMap(Entry::getKey, entry -> new Pieces(entry.getValue())));
    }

    private static Team parseTeam(String s) {
        return Team.from(s);

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

