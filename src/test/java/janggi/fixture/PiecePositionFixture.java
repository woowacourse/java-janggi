package janggi.fixture;

import janggi.domain.value.JanggiPosition;
import java.util.List;

public class PiecePositionFixture {

    public final static List<JanggiPosition> GUNG_JANGGI_POSITIONS = List.of(new JanggiPosition(4, 8));
    public final static List<JanggiPosition> CHA_JANGGI_POSITIONS = List.of(new JanggiPosition(0, 9), new JanggiPosition(8, 9));
    public final static List<JanggiPosition> SA_JANGGI_POSITIONS = List.of(new JanggiPosition(3, 9), new JanggiPosition(5, 9));
    public final static List<JanggiPosition> PO_JANGGI_POSITIONS = List.of(new JanggiPosition(1, 7), new JanggiPosition(7, 7));
    public final static List<JanggiPosition> JOL_JANGGI_POSITIONS = List.of(new JanggiPosition(0, 6), new JanggiPosition(2, 6),
            new JanggiPosition(4, 6), new JanggiPosition(6, 6), new JanggiPosition(8, 6));
}
