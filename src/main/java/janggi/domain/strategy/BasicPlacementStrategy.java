package janggi.domain.strategy;

import janggi.domain.Piece;
import janggi.domain.PieceType;
import janggi.domain.Position;
import janggi.domain.Space;
import janggi.domain.Team;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

public class BasicPlacementStrategy implements InitializeStrategy {

    @Override
    public void basicSetting(Map<Position, Space> blankBoard) {
        try {
            File file = new File("resources\\data\\BasicPlacement.csv");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine(); // header skip
            while(line != null) {
                line = br.readLine();

                List<String> parts = List.of(line.split(","));
                int x = Integer.parseInt(parts.get(0));
                int y = Integer.parseInt(parts.get(1));
                Team team = Team.from(parts.get(2));
                PieceType pieceType = PieceType.from(parts.get(3));

                blankBoard.put(new Position(x,y), new Piece(team, pieceType));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("기물 초기화 실패");
        }
    }
}
