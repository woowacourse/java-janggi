package piece;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;
import strategy.MoveStrategy;
import strategy.MoveStrategyFactory;

public class PiecesCreateFactory {

    public static Map<Team, Pieces> generate(List<String> pieces) {
        Map<Team, List<Piece>> teamPieces = new HashMap<>();

        for (String inputPiece : pieces) {
            var createdPiece = createPiece(inputPiece);
            Team team = createdPiece.team();
            List<Piece> previousPieces = teamPieces.computeIfAbsent(team, k -> new ArrayList<>());
            previousPieces.add(createdPiece);
            teamPieces.put(team, previousPieces);
        }

        return teamPieces.entrySet()
                .stream()
                .collect(Collectors.toMap(Entry::getKey, entry -> new Pieces(entry.getValue())));
    }

    public static Map<Team, Pieces> generate(String fileSrc) {
        File file = new File(fileSrc);
        final Scanner scanner = defaultFileScanner(file);
        List<String> piecesLines = new ArrayList<>();
        while (scanner.hasNext()) {
            piecesLines.add(scanner.nextLine());
        }
        return generate(piecesLines);
    }

    private static Scanner defaultFileScanner(File file) {
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return scanner;
    }

    private static Piece createPiece(String inputPiece) {
        String[] perPiece = inputPiece.split(" ");
        Position position = parsePosition(perPiece[0]);
        PieceType pieceType = parsePieceType(perPiece[1]);
        Team team = Team.from(perPiece[2]);
        MoveStrategy moveStrategy = MoveStrategyFactory.create(pieceType);
        return new Piece(position, moveStrategy, pieceType, team);
    }

    private static PieceType parsePieceType(String s) {
        return PieceType.from(s);
    }

    private static Position parsePosition(String s) {
        String[] positions = s.split(",");
        int r = Integer.parseInt(positions[0]);
        int c = Integer.parseInt(positions[1]);
        return new Position(r, c);
    }
}

