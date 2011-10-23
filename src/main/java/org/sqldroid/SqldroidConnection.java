package org.sqldroid;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
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

import android.database.sqlite.SQLiteDatabaseLockedException;
import android.util.Log;

public class SqldroidConnection implements Connection {
    private org.sqldroid.SQLiteDatabase sqlitedb;
    private boolean autoCommit = true;

    public SqldroidConnection(String url, Properties info) throws SQLException {
        Log.i("Sqldroid", "new sqlite jdbc from url '" + url + "', " + "'" + info + "'");

        // Make a filename from url
        String dbQname = url.substring(SqldroidDriver.sqldroidPrefix.length());
        Log.i("Sqldroid", "opening database " + dbQname);

        long timeout = 30000;
        long start = System.currentTimeMillis();
        android.database.sqlite.SQLiteDatabase sqlitedb_native = null;
        SQLiteDatabaseLockedException databaseLockedException = null;
        while (System.currentTimeMillis() - start < timeout) {
            try {
                sqlitedb_native = android.database.sqlite.SQLiteDatabase.openDatabase(dbQname, null,
                        android.database.sqlite.SQLiteDatabase.CREATE_IF_NECESSARY
                                | android.database.sqlite.SQLiteDatabase.OPEN_READWRITE);
                databaseLockedException = null;
                break;
            } catch (SQLiteDatabaseLockedException e) {
                if (databaseLockedException == null) {
                    databaseLockedException = e;
                }
                System.out.println("Exception opening SQLiteDatabase: " + e.getClass() + " " + e.getMessage());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        if (sqlitedb_native == null) {
            throw new SQLException("Timeout opening database", databaseLockedException);
        }

        Log.i("Sqldroid", "Adding retry with timeout");
        sqlitedb = addRetryWithTimeout(sqlitedb_native);
    }

    /**
     * Adds trace logging to the given object using a proxy decorator.
     */
    public static SQLiteDatabase addRetryWithTimeout(final android.database.sqlite.SQLiteDatabase object) {
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws SQLException {
                // System.out.println("With retry: " + method + "(" +
                // args.length + ")");
                long timeout = 30000;
                long start = System.currentTimeMillis();
                Method target_method;
                try {
                    target_method = object.getClass().getMethod(method.getName(), method.getParameterTypes());
                } catch (NoSuchMethodException e1) {
                    throw new SQLException(e1);
                }
                while (System.currentTimeMillis() - start < timeout) {
                    try {
                        return target_method.invoke(object, args);
                    } catch (IllegalAccessException e) {
                        throw new SQLException(e);
                    } catch (InvocationTargetException e) {
                        if (e.getCause() instanceof SQLiteDatabaseLockedException) {
                            Log.w("Sqldroid", "Detected lock in SQLiteDatabase.  Retrying.");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e1) {
                                Thread.currentThread().interrupt();
                            }
                        } else {
                            throw new SQLException("Timeout writing to SQLiteDatabase", e.getCause());
                        }
                    }
                }
                return null;
            }
        };

        return (SQLiteDatabase) Proxy.newProxyInstance(SqldroidConnection.class.getClassLoader(),
                new Class[]{SQLiteDatabase.class}, handler);
    }

    public SQLiteDatabase getDb() {
        return sqlitedb;
    }

    @Override
    public void clearWarnings() throws SQLException {
    }

    @Override
    public void close() throws SQLException {
        if (sqlitedb != null)
            sqlitedb.close();

        sqlitedb = null;
    }

    @Override
    public void commit() throws SQLException {
        if (autoCommit)
            throw new SQLException("database in auto-commit mode");
        sqlitedb.setTransactionSuccessful();
        sqlitedb.endTransaction();
        sqlitedb.beginTransaction();
    }

    @Override
    public Statement createStatement() throws SQLException {
        return new SqldroidStatement(this);
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        System.err.println(" ********************* implemented by nie @ " + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());
        return new SqldroidStatement(this);
        //return null;
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
            throws SQLException {
        System.err.println(" ********************* implemented by nie @ " + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());
        return new SqldroidStatement(this);
        //return null;
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return autoCommit;
    }

    @Override
    public String getCatalog() throws SQLException {
        System.err.println(" ********************* not implemented @ " + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

        return null;
    }

    @Override
    public int getHoldability() throws SQLException {
        System.err.println(" ********************* not implemented @ " + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

        return 0;
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {

        return new SqldroidDatabaseMetaData(this);
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        System.err.println(" ********************* not implemented @ " + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

        return 0;
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        System.err.println(" ********************* not implemented @ " + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

        return null;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        System.err.println(" ********************* not implemented @ " + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

        return null;
    }

    @Override
    public boolean isClosed() throws SQLException {
        return sqlitedb == null || !sqlitedb.isOpen();
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        System.err.println(" ********************* not implemented @ " + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

        return false;
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        System.err.println(" ********************* not implemented @ " + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());
        sqlitedb.execSQL(sql);
        return "Sqldroid: no return info available from sqlite";
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        System.err.println(" ********************* not implemented @ " + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

        return null;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        System.err.println(" ********************* not implemented @ " + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

        return null;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
                                         int resultSetHoldability) throws SQLException {
        System.err.println(" ********************* not implemented @ " + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return new SqldroidPreparedStatement(sql, this);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        System.err.println(" ********************* not implemented @ " + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        System.err.println(" ********************* not implemented @ " + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        System.err.println(" ********************* not implemented @ " + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
            throws SQLException {
        System.err.println(" ********************* implemented by nie @ " + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());
        return new SqldroidPreparedStatement(sql, this);
        // return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
                                              int resultSetHoldability) throws SQLException {
        System.err.println(" ********************* implemented by nie @ " + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());
        return new SqldroidPreparedStatement(sql, this);
        //return null;
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        System.err.println(" ********************* not implemented @ " + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void rollback() throws SQLException {
        if (autoCommit)
            throw new SQLException("database in auto-commit mode");
        sqlitedb.endTransaction();
        sqlitedb.beginTransaction();
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        System.err.println(" ********************* not implemented @ " + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        if (this.autoCommit == autoCommit)
            return;
        this.autoCommit = autoCommit;
        if (autoCommit) {
            sqlitedb.setTransactionSuccessful();
            sqlitedb.endTransaction();
        } else {
            sqlitedb.beginTransaction();
        }
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
        System.err.println(" ********************* not implemented @ " + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
        System.err.println(" ********************* not implemented @ " + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        System.err.println(" ********************* not implemented @ " + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        System.err.println(" ********************* not implemented @ " + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

        return null;
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        System.err.println(" ********************* not implemented @ " + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

        return null;
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
        System.err.println(" ********************* not implemented @ " + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void setTypeMap(Map<String, Class<?>> arg0) throws SQLException {
        System.err.println(" ********************* not implemented @ " + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    protected void finalize() throws Throwable {
        Log.i("Sqldroid", " --- Finalize Sqldroid, closing db.");
        if (sqlitedb != null)
            sqlitedb.close();

        sqlitedb = null;
        super.finalize();
    }

    @Override
    public boolean isWrapperFor(Class<?> arg0) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Blob createBlob() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Clob createClob() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NClob createNClob() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        // TODO Auto-generated method stub

    }
}
