package men.brakh.migrator;

import men.brakh.migrator.custommapper.DateCustomMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Application {
    private static final Scanner scanner = new Scanner(System.in);

    private static List<File> getSchemas() {
        final File folder = new File(Application.class.getResource("/schemas").getPath());

        return Arrays.asList(folder.listFiles());
    }

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        System.out.println("Please, enter the json db basic directory");
        final String baseDbPath = scanner.nextLine();
        final List<File> schemas = getSchemas();

        for (final File schemaFile : schemas) {
            final String name = schemaFile.getName();
            final File jsonDbFile = new File(baseDbPath + "/" + name);

            final String dbTableName = name
                .replaceAll(".json", "")
                .replaceAll("-", "_");

            final JSONObject rawSchema = new JSONObject(new JSONTokener(new FileInputStream(schemaFile)));

            Migrator migration = new Migrator(rawSchema,
                dbTableName,
                getConnection(),
                new DateCustomMapper("birthDate", "enrollee"),
                new DateCustomMapper("date", "university_application")
            );

            migration.migrate(new JSONArray(new String(Files.readAllBytes(jsonDbFile.toPath()))));

        }
    }

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/enrollee","wt","123321");
    }
}
