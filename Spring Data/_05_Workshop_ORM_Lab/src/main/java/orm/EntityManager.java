package orm;

import annotations.Column;
import annotations.Entity;
import annotations.Id;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class EntityManager<E> implements DbContext<E> {

    private Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }


    public void createTable(Class<?> entityClass) throws SQLException {
        String tableName = getTableName(entityClass);
        String fieldsWithTypes = getSQLFieldsWithTypes(entityClass);

        String createQuery = String.format("CREATE TABLE %s (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT, %s)",
                tableName,
                fieldsWithTypes);
        PreparedStatement statement = connection.prepareStatement(createQuery);
        statement.execute();
    }

    private String getTableName(Class<?> clazz) {
        Entity[] annotationsByType = clazz.getAnnotationsByType(Entity.class);
        if (annotationsByType.length == 0) {
            throw new UnsupportedOperationException("Class must be Entity");
        }
        return annotationsByType[0].name();
    }

    private String getSQLFieldsWithTypes(Class<?> entityClass) {
        return getEntityColumnFieldsWithoutId(entityClass.getDeclaredFields())
                .stream()
                .map(field -> {
                    String fieldName = getSQLColumnName(field.getAnnotationsByType(Column.class));
                    String sqlType = getSQLType(field.getType());
                    return fieldName + " " + sqlType;
                })
                .collect(Collectors.joining(", "));
    }

    private String getSQLColumnName(Column[] idColumn) {
        return idColumn[0].name();
    }


    @Override
    public boolean persist(E entity) throws IllegalAccessException, SQLException {
        Field idColumn = getIdColumn(entity.getClass());
        idColumn.setAccessible(true);
        Object idValue = idColumn.get(entity);

        if (idValue == null || (long) idValue <= 0) {
            return doInsert(entity);
        }
        return doUpdate(entity, (long)idValue);
    }

    private Field getIdColumn(Class<?> clazz) {
//        return Arrays.stream(clazz.getDeclaredFields())
//                .filter(field -> field.isAnnotationPresent(Id.class))
//                .findFirst()
//                .orElseThrow(() -> new UnsupportedOperationException("Entity missing an Id column"));

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            boolean annotationPresent = declaredField.isAnnotationPresent(Id.class);
            if (annotationPresent) {
                return declaredField;
            }
        }
        throw new UnsupportedOperationException("Entity missing an Id column");
    }

    private boolean doInsert(E entity) throws SQLException, IllegalAccessException {
        String tableName = getTableName(entity.getClass());
        List<String> tableFields = getColumnsWithoutId(entity.getClass());
        List<String> tableValues = getColumnsValuesWithoutId(entity);

        String insertQuery = String.format("INSERT INTO %s (%s) VALUES (%s)",
                tableName,
                String.join(", ", tableFields),
                String.join(", ", tableValues));

        return connection.prepareStatement(insertQuery).execute();
    }

    private List<String> getColumnsWithoutId(Class<?> aClass) {
        return getEntityColumnFieldsWithoutId(aClass.getDeclaredFields())
                .stream()
                .map(f -> f.getAnnotationsByType(Column.class))
                .map(a -> getSQLColumnName(a))
                .collect(Collectors.toList());
    }

    private List<String> getColumnsValuesWithoutId(E entity) throws IllegalAccessException {
        Class<?> clazz = entity.getClass();
        List<Field> fields = getEntityColumnFieldsWithoutId(clazz.getDeclaredFields());

        List<String> values = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            Object object = field.get(entity);

            if (object instanceof String || object instanceof LocalDate) {
                values.add("'" + object + "'");
            } else {
                values.add(object.toString());
            }
        }

        return values;
    }

    private List<Field> getEntityColumnFieldsWithoutId(Field[] clazz) {
        return Arrays.stream(clazz)
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .filter(f -> f.isAnnotationPresent(Column.class))
                .collect(Collectors.toList());
    }

    private boolean doUpdate(E entity, long idValue) throws IllegalAccessException, SQLException {
        String tableName = getTableName(entity.getClass());
        List<String> tableFields = getColumnsWithoutId(entity.getClass());
        List<String> tableValues = getColumnsValuesWithoutId(entity);

        List<String> setStatements = new ArrayList<>();
        for (int i = 0; i < tableFields.size(); i++) {
            String statement = tableFields.get(i) + " = " + tableValues.get(i);
            setStatements.add(statement);
        }
        String updateQuery = String.format("UPDATE %s" +
                        " SET %s" +
                        " WHERE id = %d",
                tableName,
                String.join(", ", setStatements),
                idValue);
        PreparedStatement statement = connection.prepareStatement(updateQuery);
        return statement.execute();
    }

    @Override
    public void update(E toUpdate, String where) throws IllegalAccessException, SQLException {
        String tableName = getTableName(toUpdate.getClass());
        Field idColumn = getIdColumn(toUpdate.getClass());
        String idColumnName = getSQLColumnName(idColumn.getAnnotationsByType(Column.class));
        idColumn.setAccessible(true);
        Object idColumnValue = idColumn.get(toUpdate);

        String query = String.format("UPDATE %s" +
                        " SET age = %s" +
                        " WHERE %s = %s",
                tableName,
                where,
                idColumnName,
                idColumnValue);
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();
        System.out.println("UPDATE WAS SUCCESSFUL");
    }

    public void alterTable(Class<?> entityClass) throws SQLException {
        String tableName = getTableName(entityClass);
        String addColumnStatements = getAddColumnsStatementsForNewFields(entityClass);
        String alterQuery = String.format("ALTER TABLE %s %s", tableName, addColumnStatements);

        PreparedStatement statement = connection.prepareStatement(alterQuery);
        statement.execute();
    }

    private String getAddColumnsStatementsForNewFields(Class<?> entityClass) throws SQLException {
        Set<String> sqlColumns = getSQLColumnNames(entityClass);
        List<Field> fields = getEntityColumnFieldsWithoutId(entityClass.getDeclaredFields());

        List<String> allAddStatements = new ArrayList<>();
        for (Field field : fields) {
            String fieldName = getSQLColumnName(field.getAnnotationsByType(Column.class));
            if (sqlColumns.contains(fieldName)) {
                continue;
            }
            String sqlType = getSQLType(field.getType());
            String addStatement = String.format("ADD COLUMN %s %s", fieldName, sqlType);
            allAddStatements.add(addStatement);
        }
        return String.join(", ", allAddStatements);
    }

    @Override
    public boolean delete(E toDelete) throws IllegalAccessException, SQLException {
        String tableName = getTableName(toDelete.getClass());
        Field idColumn = getIdColumn(toDelete.getClass());
        String idColumnName = getSQLColumnName(idColumn.getAnnotationsByType(Column.class));
        idColumn.setAccessible(true);
        Object idColumnValue = idColumn.get(toDelete);

        String query = String.format("DELETE FROM %s WHERE %s = %s",
                tableName,
                idColumnName,
                idColumnValue);
        PreparedStatement statement = connection.prepareStatement(query);
        return statement.execute();
    }

    @Override
    public E findFirst(Class<E> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return findFirst(table, null);
    }

    @Override
    public List<E> find(Class<E> table, String where) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return baseFind(table, where, null);
    }

    @Override
    public E findFirst(Class<E> table, String where) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<E> result = baseFind(table, where, "LIMIT 1");
        return result.get(0);
    }

    private List<E> baseFind(Class<E> table, String where, String limit) throws SQLException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        String tableName = getTableName(table);
        String selectQuery = String.format("SELECT * FROM %s %s %s",
                tableName,
                where != null ? "WHERE " + where : "",
                limit != null ? limit : "");

        PreparedStatement statement = connection.prepareStatement(selectQuery);
        ResultSet resultSet = statement.executeQuery();

        List<E> result = new ArrayList<>();
        while (resultSet.next()) {
            E entity = table.getDeclaredConstructor().newInstance();
            fillEntity(table, resultSet, entity);
            result.add(entity);
        }

        return result;
    }

    @Override
    public List<E> find(Class<E> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return find(table, null);
    }

    private String getSQLType(Class<?> type) {
        String sqlType = "";
        if (type == Integer.class || type == int.class) {
            sqlType = "INT";
        } else if (type == String.class) {
            sqlType = "VARCHAR(200)";
        } else if (type == LocalDate.class) {
            sqlType = "DATE";
        }
        return sqlType;
    }

    private Set<String> getSQLColumnNames(Class<?> entityClass) throws SQLException {
//        String tableName = getTableName(entityClass);
//        Field idColumn = getIdColumn(entityClass);
//        String idColumnName = getSQLColumnName(idColumn);

        String schemaQuery = "SELECT COLUMN_NAME FROM information_schema.`COLUMNS` c" +
                " WHERE c.TABLE_SCHEMA = 'custom_orm'" +
                " AND COLUMN_NAME != 'id'" +
                " AND TABLE_NAME = 'users'";

        PreparedStatement statement = connection.prepareStatement(schemaQuery);
        ResultSet resultSet = statement.executeQuery();
        Set<String> result = new HashSet<>();
        while (resultSet.next()) {
            String columnName = resultSet.getString("COLUMN_NAME");
            result.add(columnName);
        }
        return result;
    }

    private void fillEntity(Class<E> table, ResultSet resultSet, E entity) throws SQLException, IllegalAccessException {
        Field[] declaredFields = table.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            fillFiled(declaredField, resultSet, entity);
        }
    }

    private void fillFiled(Field declaredField, ResultSet resultSet, E entity) throws SQLException, IllegalAccessException {
        Class<?> fieldType = declaredField.getType();
        String fieldName = getSQLColumnName(declaredField.getAnnotationsByType(Column.class));
        if (fieldType == int.class || fieldType == Integer.class) {
            int value = resultSet.getInt(fieldName);
            declaredField.set(entity, value);
        } else if (fieldType == long.class || fieldType == Long.class) {
            long value = resultSet.getLong(fieldName);
            declaredField.set(entity, value);
        } else if (fieldType == LocalDate.class) {
            LocalDate value = LocalDate.parse(resultSet.getString(fieldName));
            declaredField.set(entity, value);
        } else {
            String value = resultSet.getString(fieldName);
            declaredField.set(entity, value);
        }
    }


}