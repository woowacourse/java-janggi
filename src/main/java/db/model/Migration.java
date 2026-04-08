package db.model;

@Deprecated
@SuppressWarnings("unused")
public record Migration(int version, String description, String resourcePath) {
}
