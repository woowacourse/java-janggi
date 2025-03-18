import java.util.Objects;

public class Space {
    private final LocationX locationX;
    private final LocationY locationY;
    private Piece piece = Piece.createShadow();

    public Space(LocationX locationX, LocationY locationY) {
        this.locationX = locationX;
        this.locationY = locationY;
    }

    public boolean isEmptySpace() {
        return piece.isShadow();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Space space = (Space) o;
        return Objects.equals(locationX, space.locationX) && Objects.equals(locationY, space.locationY);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationX, locationY);
    }
}
