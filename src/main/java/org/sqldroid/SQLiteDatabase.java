package org.sqldroid;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;

import android.database.Cursor;

public interface SQLiteDatabase {
    SQLiteDatabase getDb();
    void beginTransaction();
    void clearWarnings() throws SQLException;
    void close() throws SQLException;
    void commit() throws SQLException;
    Statement createStatement() throws SQLException;
    Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException;
    Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
            throws SQLException;
    void endTransaction();
    void execSQL(String sql);
    void execSQL(String sql, Object[] makeArgListQueryObject);
    boolean getAutoCommit() throws SQLException;
    String getCatalog() throws SQLException;
    int getHoldability() throws SQLException;
    DatabaseMetaData getMetaData() throws SQLException;
    int getTransactionIsolation() throws SQLException;
    Map<String, Class<?>> getTypeMap() throws SQLException;
    int getVersion();
    SQLWarning getWarnings() throws SQLException;
    boolean isClosed() throws SQLException;
    boolean isOpen();
    boolean isReadOnly() throws SQLException;
    String nativeSQL(String sql) throws SQLException;
    CallableStatement prepareCall(String sql) throws SQLException;
    CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException;
    CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability)
            throws SQLException;
    PreparedStatement prepareStatement(String sql) throws SQLException;
    PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException;
    PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException;
    PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException;
    PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException;
    PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability)
            throws SQLException;
    Cursor rawQuery(String sql, String[] strings);
    void releaseSavepoint(Savepoint savepoint) throws SQLException;
    void rollback() throws SQLException;
    void rollback(Savepoint savepoint) throws SQLException;
    void setAutoCommit(boolean autoCommit) throws SQLException;
    void setCatalog(String catalog) throws SQLException;
    void setHoldability(int holdability) throws SQLException;
    void setReadOnly(boolean readOnly) throws SQLException;
    Savepoint setSavepoint() throws SQLException;
    Savepoint setSavepoint(String name) throws SQLException;
    void setTransactionSuccessful();
    void setTransactionIsolation(int level) throws SQLException;
    void setTypeMap(Map<String, Class<?>> arg0) throws SQLException;
    boolean isWrapperFor(Class<?> arg0) throws SQLException;
    <T> T unwrap(Class<T> arg0) throws SQLException;
    Array createArrayOf(String typeName, Object[] elements) throws SQLException;
    Blob createBlob() throws SQLException;
    Clob createClob() throws SQLException;
    NClob createNClob() throws SQLException;
    SQLXML createSQLXML() throws SQLException;
    Struct createStruct(String typeName, Object[] attributes) throws SQLException;
    Properties getClientInfo() throws SQLException;
    String getClientInfo(String name) throws SQLException;
    boolean isValid(int timeout) throws SQLException;
    void setClientInfo(Properties properties) throws SQLClientInfoException;
    void setClientInfo(String name, String value) throws SQLClientInfoException;
}
