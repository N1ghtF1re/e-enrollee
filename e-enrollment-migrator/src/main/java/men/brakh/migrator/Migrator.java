package men.brakh.migrator;

import men.brakh.migrator.custommapper.CustomMapper;
import men.brakh.migrator.types.IntSqlValue;
import men.brakh.migrator.types.SqlValue;
import men.brakh.migrator.types.SqlValuesFactory;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

public class Migrator {
    private final Schema schema;
    private final JSONObject rawSchema;
    private final String dbTableName;
    private final Connection connection;
    private final List<CustomMapper> customMappers;

    public Migrator(final JSONObject rawSchema,
                    final String dbTableName,
                    final Connection connection,
                    final CustomMapper... customMappers) {
        this.schema = SchemaLoader.load(rawSchema);
        this.rawSchema = rawSchema;
        this.dbTableName = dbTableName;
        this.connection = connection;
        this.customMappers = Arrays.asList(customMappers);
    }

    private void validate(final JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            schema.validate(array.getJSONObject(i));
        }
    }

    private Set<String> getRequiredFields() {
        final Set<String> requiredFieldsSet = new HashSet<>();
        final JSONArray requiredFields = rawSchema.getJSONArray("required");
        for (int i = 0; i < requiredFields.length(); i++) {
            requiredFieldsSet.add(requiredFields.getString(i));
        }
        return requiredFieldsSet;
    }



    private List<String> getFieldNames() {
        final JSONObject jsonFields = rawSchema.getJSONObject("properties");
        Set<String> keys = jsonFields.keySet();
        return new ArrayList<>(keys);
    }

    private void generateTable(final List<SqlValue> sqlValues, final String tableName) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS " + tableName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        final StringBuilder sqlRequest = new StringBuilder();
        sqlRequest.append("CREATE TABLE ")
            .append(tableName)
            .append("(\n");

        List<String> sqlColumnsDeclarations = sqlValues.stream()
            .map(sqlValue -> "`"
                + sqlValue.getFieldName()
                + "`"
                + " "
                + sqlValue.getSqlTypeName()
            ).collect(Collectors.toList());


        sqlRequest.append(String.join(", \n", sqlColumnsDeclarations))
            .append(")");

        try {
            Statement statement = connection.createStatement();
            statement.execute(sqlRequest.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void generateTable(final List<SqlValue> sqlValues) {
        generateTable(sqlValues, dbTableName);

    }


    private void insertValues(final List<List<SqlValue>> sqlTablesValues, final String tableName) {
        sqlTablesValues.forEach(sqlValues -> {
            final StringBuilder sqlScript = new StringBuilder("INSERT INTO " + tableName);

            final List<String> fields = sqlValues
                .stream()
                .map(SqlValue::getFieldName)
                .collect(Collectors.toList());

            final List<String> questions = sqlValues
                .stream()
                .map(any -> "?")
                .collect(Collectors.toList());

            sqlScript.append(" (")
                .append(String.join(", ", fields))
                .append(") VALUES (")
                .append(String.join(", ", questions))
                .append(");");

            final PreparedStatement preparedStatement;
            try {
                preparedStatement = connection.prepareStatement(sqlScript.toString());

                for (int i = 0; i < sqlValues.size(); i++) {
                    sqlValues.get(i).setValue(preparedStatement, i + 1);
                }
                preparedStatement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void insertValues(final List<List<SqlValue>> sqlTablesValues) {
        insertValues(sqlTablesValues, dbTableName);
    }

    public void migrate(final JSONArray array) {
        validate(array);


        final List<List<SqlValue>> values = new ArrayList<>();
        final JSONObject jsonFields = rawSchema.getJSONObject("properties");
        final Set<String> requiredFields = getRequiredFields();


        for (int i = 0; i < array.length(); i++) {
            final int finalI = i;
            values.add(
                getFieldNames().stream()
                    .map(fieldName -> {
                        final JSONObject fieldDefinition = jsonFields.getJSONObject(fieldName);

                        if (isArray(fieldDefinition)) {
                            fillArrayTable(array, finalI, fieldName, fieldDefinition);
                            return null;
                        }
                        return SqlValuesFactory.create(
                            array.getJSONObject(finalI),
                            fieldName,
                            fieldDefinition,
                            requiredFields
                        );
                    })
                    .filter(Objects::nonNull)
                    .map(this::customMapFields)
                .collect(Collectors.toList())
            );
        }

        generateTable(values.get(0));
        insertValues(values);
    }

    private boolean isArray(final JSONObject fieldDefinition) {
        return fieldDefinition.getString("type").equals("array");
    }

    private void fillArrayTable(final JSONArray array,
                                final int finalI,
                                final String fieldName,
                                final JSONObject fieldDefinition) {
        final IntSqlValue intSqlValue = new IntSqlValue(
            array.getJSONObject(finalI).getInt("id"),
            dbTableName + "_id",
            true
        );

        final JSONArray arr = array.getJSONObject(finalI).getJSONArray(fieldName);

        final List<List<SqlValue>> sqlValues = new ArrayList<>();

        for (int i = 0; i < arr.length(); i++) {
            sqlValues.add(Arrays.asList(
                intSqlValue,
                SqlValuesFactory.create(arr.get(i), fieldName,
                    fieldDefinition.getJSONObject("items"), getRequiredFields())
            ));
        }

        if (sqlValues.size() > 0) {
            generateTable(
                sqlValues.get(0),
                dbTableName + "_" + fieldName
            );

            insertValues(sqlValues, dbTableName + "_" + fieldName);
        }

    }

    private SqlValue customMapFields(final SqlValue sqlValue) {
        Optional<CustomMapper> customMapper = customMappers.stream()
        .filter(mapper ->
            mapper.isNeedToCustomMap(sqlValue.getFieldName(), dbTableName)
        ).findFirst();

        if (customMapper.isPresent()) {
            return customMapper.get().map(sqlValue);
        } else {
            return sqlValue;
        }
    }
}
