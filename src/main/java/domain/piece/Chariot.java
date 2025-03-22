package domain.piece;

import domain.position.Column;
import domain.position.Direction;
import domain.Path;
import domain.position.Position;
import domain.position.Row;
import domain.TeamType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chariot extends Piece {

    private static final List<Path> PATHS;

    static {
        PATHS = List.of(
                new Path(new ArrayList<>(Collections.nCopies(Row.MAX_ROW, Direction.DOWN))),
                new Path(new ArrayList<>(Collections.nCopies(Row.MAX_ROW, Direction.UP))),
                new Path(new ArrayList<>(Collections.nCopies(Column.MAX_COLUMN, Direction.LEFT))),
                new Path(new ArrayList<>(Collections.nCopies(Column.MAX_COLUMN, Direction.RIGHT)))
        );
    }

    public Chariot(Position position, TeamType teamType) {
        super(position, teamType);
    }

    private Chariot(Chariot chariot){
        super(chariot);
    }

    @Override
    public PieceType getType() {
        return PieceType.CHARIOT;
    }

    @Override
    public Piece newInstance() {
        return new Chariot(this);
    }

    @Override
    protected List<Path> getPaths() {
        return PATHS;
    }
}
