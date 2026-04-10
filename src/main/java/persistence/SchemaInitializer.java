package persistence;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import org.h2.tools.RunScript;

public final class SchemaInitializer {

    private SchemaInitializer() {}

    public static void apply(Connection connection) throws SQLException, IOException {
        try (InputStream stream = openSchemaStream()) {
            RunScript.execute(connection, new InputStreamReader(stream, StandardCharsets.UTF_8));
        }
    }

    private static InputStream openSchemaStream() throws IOException {
        InputStream stream = SchemaInitializer.class.getResourceAsStream("/schema.sql");
        if (stream == null) {
            throw new IOException("classpath:/schema.sql 을 찾을 수 없습니다.");
        }
        return stream;
    }
}
