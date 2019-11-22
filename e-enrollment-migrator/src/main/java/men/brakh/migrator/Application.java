package men.brakh.migrator;

import men.brakh.migrator.custommapper.DateCustomMapper;
import org.everit.json.schema.ValidationException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Application {
    private static final Properties properties = new Properties();

    private static List<File> getSchemas() {
        final File folder = new File(Application.class.getResource("/schemas").getPath());

        return Arrays.asList(folder.listFiles());
    }

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        loadProperties();


        final List<File> schemas = getSchemas();

        for (final File schemaFile : schemas) {
            final String name = schemaFile.getName();
            final File jsonDbFile = new File(properties.getProperty("jsondb.path") + "/" + name);

            final String dbTableName = properties.getProperty("db.prefix") + name
                .replaceAll(".json", "")
                .replaceAll("-", "_");

            final JSONObject rawSchema = new JSONObject(new JSONTokener(new FileInputStream(schemaFile)));

            Migrator migration = new Migrator(rawSchema,
                dbTableName,
                getConnection(),
                new DateCustomMapper("birthDate", "enrollee"),
                new DateCustomMapper("date", "university_application")
            );

            try {
                migration.migrate(new JSONArray(new String(Files.readAllBytes(jsonDbFile.toPath()))));
            } catch (ValidationException e) {
                System.out.println("VALIDATION ERROR: " + e.getMessage());
            }

        }
    }

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(properties.getProperty("db.driver"));
        return DriverManager.getConnection(
            properties.getProperty("db.url"),
            properties.getProperty("db.user"),
            properties.getProperty("db.password")
        );
    }

    private static void loadProperties() throws IOException {
        properties.load(Application.class.getResourceAsStream("/migration.properties"));
    }
}
