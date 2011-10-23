package org.sqldroid;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class SqldroidPreparedStatement implements PreparedStatement {

    SQLiteDatabase db;
    SqldroidConnection sqldroidConnection;
    SqldroidResultSet rs = null;
    String sql;
    ContentValues cv = new ContentValues();
    ArrayList<Object> l = new ArrayList<Object>();

    //ResultSet rs = null;

    public SqldroidPreparedStatement(String sql, SqldroidConnection sqldroid) {
        Log.i("SQLDRoid", "new SqlDRoid prepared statement from " + sqldroid);
        this.sqldroidConnection = sqldroid;
        this.db = sqldroid.getDb();
        this.sql = sql;
    }


    private void ensureCap(int n) {
    }


    private void setObj(int n, Object obj) {

        // prapred statments count from 1, we count from 0 (in array)
        n--;

        // if arraylist is too small we add till it's grand enough
        // btw, why ain't there a l.setSize(n)?


        int additions = n - l.size() + 1;
        System.out.println("adding " + additions + " elements");
        for (int i = 0; i < additions; i++) {
            System.out.println("ADD NULL");
            l.add(null);
        }

        System.out.println("n = " + n + " size now " + l.size() + " we @ " + n);
        l.set(n, obj);
        System.out.println("POST set n = " + n + " size now " + l.size() + " we @ " + n);
    }


    @Override
    public void addBatch(String sql) throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void cancel() throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void clearBatch() throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void clearWarnings() throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void close() throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());
    }


    private String[] makeArgListQueryString() {
        // convert our parameter list objects to strings
        List<String> strList = new ArrayList<String>();

        for (Object o : l) {
            strList.add(o.toString());
        }

        return strList.toArray(new String[0]);
    }

    private Object[] makeArgListQueryObject() {
        return l.toArray();
    }

    @Override
    public boolean execute() throws SQLException {
        boolean ok = false;
        try {
            System.out.print("Executing \"" + sql + "\" on " + db + " with args ");
            //nielinjie
            if (sql.toLowerCase().startsWith("select ")) {
                this.rs = (SqldroidResultSet)executeQuery();
            } else {
                db.execSQL(sql, makeArgListQueryObject());
            }
            //
            ok = true;
        } catch (android.database.SQLException e) {
            System.out.println("SqlDRoid exception: ");
            e.printStackTrace();
            //nielinjie
            throw new SQLException(e);
        }

        return ok;
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        Log.d("sqldroid", "executeQuery " + sql);
        // when querying, all ? values must be converted to Strings for some reason
        Cursor c = db.rawQuery(sql, makeArgListQueryString());
        Log.d("sqldroid", "executeQuery " + 2);
        rs = new SqldroidResultSet(c);
        Log.d("sqldroid", "executeQuery " + 3);
        return rs;
    }

    @Override
    public int executeUpdate() throws SQLException {
        // TODO we can't count the actual number of updates .... :S
        return execute() ? 1 : 0;
    }

    @Override
    public boolean execute(String sql) throws SQLException {

        this.sql = sql;

        return execute();
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys)
            throws SQLException {

        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

        return false;
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {

        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

        return false;
    }

    @Override
    public boolean execute(String sql, String[] columnNames)
            throws SQLException {

        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

        return false;
    }

    @Override
    public int[] executeBatch() throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());
        return null;
    }


    @Override
    public ResultSet executeQuery(String sql) throws SQLException {

        this.sql = sql;
        return executeQuery();
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        db.execSQL(sql);

        return 0;
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys)
            throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());
        return 0;
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes)
            throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());
        return 0;
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames)
            throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());
        return 0;
    }


    @Override
    public Connection getConnection() throws SQLException {
        return sqldroidConnection;
    }

    @Override
    public int getFetchDirection() throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());
        return 0;
    }

    @Override
    public int getFetchSize() throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());
        return 0;
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());
        return null;
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());
        return 0;
    }

    @Override
    public int getMaxRows() throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());
        return 0;
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());
        return false;
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());
        return false;
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());
        return 0;
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        System.err.println(" ********************* implemented by nie@ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());
        return this.rs;
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());
        return 0;
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());
        return 0;
    }

    @Override
    public int getResultSetType() throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());
        return 0;
    }

    @Override
    public int getUpdateCount() throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());
        return 0;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());
        return null;
    }

    @Override
    public void setCursorName(String name) throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void setMaxRows(int max) throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void addBatch() throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void clearParameters() throws SQLException {
        l = new ArrayList<Object>();
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());
        return null;
    }

    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());
        return null;
    }

    @Override
    public void setArray(int parameterIndex, Array theArray)
            throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream theInputStream,
                               int length) throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal theBigDecimal)
            throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream theInputStream,
                                int length) throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void setBlob(int parameterIndex, Blob theBlob) throws SQLException {

        ensureCap(parameterIndex);
        setObj(parameterIndex, theBlob.getBytes(0, (int) theBlob.length()));

    }

    @Override
    public void setBoolean(int parameterIndex, boolean theBoolean)
            throws SQLException {

        ensureCap(parameterIndex);
        setObj(parameterIndex, theBoolean);

    }

    @Override
    public void setByte(int parameterIndex, byte theByte) throws SQLException {

        ensureCap(parameterIndex);
        setObj(parameterIndex, theByte);

    }

    @Override
    public void setBytes(int parameterIndex, byte[] theBytes)
            throws SQLException {
        ensureCap(parameterIndex);
        setObj(parameterIndex, theBytes);
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length)
            throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void setClob(int parameterIndex, Clob theClob) throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void setDate(int parameterIndex, Date theDate) throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());
    }

    @Override
    public void setDate(int parameterIndex, Date theDate, Calendar cal)
            throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void setDouble(int parameterIndex, double theDouble)
            throws SQLException {
        ensureCap(parameterIndex);
        setObj(parameterIndex, new Double(theDouble));
    }

    @Override
    public void setFloat(int parameterIndex, float theFloat)
            throws SQLException {
        ensureCap(parameterIndex);
        setObj(parameterIndex, new Double(theFloat));


    }

    @Override
    public void setInt(int parameterIndex, int theInt) throws SQLException {
        ensureCap(parameterIndex);
        setObj(parameterIndex, new Long(theInt));


    }

    @Override
    public void setLong(int parameterIndex, long theLong) throws SQLException {
        ensureCap(parameterIndex);
        setObj(parameterIndex, new Long(theLong));


    }

    @Override
    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        ensureCap(parameterIndex);
        setObj(parameterIndex, null);


    }

    @Override
    public void setNull(int paramIndex, int sqlType, String typeName)
            throws SQLException {
        ensureCap(paramIndex);
        setObj(paramIndex, null);

    }

    @Override
    public void setObject(int parameterIndex, Object theObject)
            throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void setObject(int parameterIndex, Object theObject,
                          int targetSqlType) throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void setObject(int parameterIndex, Object theObject,
                          int targetSqlType, int scale) throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void setRef(int parameterIndex, Ref theRef) throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void setShort(int parameterIndex, short theShort)
            throws SQLException {
        ensureCap(parameterIndex);
        setObj(parameterIndex, new Long(theShort));


    }

    @Override
    public void setString(int parameterIndex, String theString) {
        ensureCap(parameterIndex);
        setObj(parameterIndex, theString);


    }

    @Override
    public void setTime(int parameterIndex, Time theTime) throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void setTime(int parameterIndex, Time theTime, Calendar cal)
            throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp theTimestamp)
            throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp theTimestamp,
                             Calendar cal) throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void setURL(int parameterIndex, URL theURL) throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }

    @Override
    public void setUnicodeStream(int parameterIndex,
                                 InputStream theInputStream, int length) throws SQLException {
        System.err.println(" ********************* not implemented @ "
                + DebugPrinter.getFileName() + " line "
                + DebugPrinter.getLineNumber());

    }


    @Override
    public boolean isClosed() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public boolean isPoolable() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public void setPoolable(boolean poolable) throws SQLException {
        // TODO Auto-generated method stub

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
    public void setAsciiStream(int parameterIndex, InputStream inputStream)
            throws SQLException {
        // TODO Auto-generated method stub

    }


    @Override
    public void setAsciiStream(int parameterIndex, InputStream inputStream,
                               long length) throws SQLException {
        // TODO Auto-generated method stub

    }


    @Override
    public void setBinaryStream(int parameterIndex, InputStream inputStream)
            throws SQLException {
        // TODO Auto-generated method stub

    }


    @Override
    public void setBinaryStream(int parameterIndex, InputStream inputStream,
                                long length) throws SQLException {
        // TODO Auto-generated method stub

    }


    @Override
    public void setBlob(int parameterIndex, InputStream inputStream)
            throws SQLException {
        // TODO Auto-generated method stub

    }


    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length)
            throws SQLException {
        // TODO Auto-generated method stub

    }


    @Override
    public void setCharacterStream(int parameterIndex, Reader reader)
            throws SQLException {
        // TODO Auto-generated method stub

    }


    @Override
    public void setCharacterStream(int parameterIndex, Reader reader,
                                   long length) throws SQLException {
        // TODO Auto-generated method stub

    }


    @Override
    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        // TODO Auto-generated method stub

    }


    @Override
    public void setClob(int parameterIndex, Reader reader, long length)
            throws SQLException {
        // TODO Auto-generated method stub

    }


    @Override
    public void setNCharacterStream(int parameterIndex, Reader reader)
            throws SQLException {
        // TODO Auto-generated method stub

    }


    @Override
    public void setNCharacterStream(int parameterIndex, Reader reader,
                                    long length) throws SQLException {
        // TODO Auto-generated method stub

    }


    @Override
    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        // TODO Auto-generated method stub

    }


    @Override
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        // TODO Auto-generated method stub

    }


    @Override
    public void setNClob(int parameterIndex, Reader reader, long length)
            throws SQLException {
        // TODO Auto-generated method stub

    }


    @Override
    public void setNString(int parameterIndex, String theString)
            throws SQLException {
        // TODO Auto-generated method stub

    }


    @Override
    public void setRowId(int parameterIndex, RowId theRowId)
            throws SQLException {
        // TODO Auto-generated method stub

    }


    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject)
            throws SQLException {
        // TODO Auto-generated method stub

    }

}
