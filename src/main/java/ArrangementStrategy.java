import java.util.Map;

public interface ArrangementStrategy {
    Map<Dot, Piece> arrange(Dynasty dynasty);
}
