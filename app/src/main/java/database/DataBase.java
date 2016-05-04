package database;

    import java.util.ArrayList;
    import java.util.List;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;
    import android.database.sqlite.SQLiteStatement;
    import android.provider.BaseColumns;

public class DataBase extends SQLiteOpenHelper implements BaseColumns{
        public static final String NAME_CITI = "name";
        public static final String NAME_COUNTRY = "country";
        public static final String DATA_DATE = "data_date";
        public static final String DATA_TIME = "data_time";
        public static final String CLOUDS_VALUE = "cloudsValue";
        public static final String DEGREE_VALUE = "degreeValue";
        public static final String HUMIDITY_VALUE = "humidityValue";
        private static final String DATABASE_NAME = "citydata.db";
        public static final String TABLE = "cities";
        private static final String DATABASE_CREATE_SCRIPT = "create table "
            + TABLE + " (" + BaseColumns._ID
            + " integer primary key autoincrement, " + NAME_CITI
            + " text not null, " + NAME_COUNTRY + " text, " +DATA_DATE + " text, " + DATA_TIME + " text, " + CLOUDS_VALUE + " text, " + DEGREE_VALUE + " text, " + HUMIDITY_VALUE
            + " text);";
        private static final int DATABASE_VERSION = 1;
        private Context context;
        private SQLiteDatabase db;
        private static final String INSERT = "insert into " + TABLE
                + "(name,country,data_date,data_time,cloudsValue,degreeValue,humidityValue) values(?,?,?,?,?,?,?)";
        private SQLiteStatement insertStmt;

        public DataBase(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            }

        public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory,
                                  int version) {
                super(context, name, factory, version);
            }
        public long insert(String name, String country,String data_date,String data_time,String cloudsValue,String degreeValue,String humidityValue) {
            this.insertStmt.bindString(1, name);
            this.insertStmt.bindString(2, country);
            this.insertStmt.bindString(3, data_date);
            this.insertStmt.bindString(4, data_time);
            this.insertStmt.bindString(5, cloudsValue);
            this.insertStmt.bindString(6, degreeValue);
            this.insertStmt.bindString(7, humidityValue);
            return this.insertStmt.executeInsert();
        }
         public void deleteAll() {
            this.db.delete(TABLE, null, null);
        }
        public String readOne(int id) {
            List<String> list = new ArrayList<String>();
            Cursor cursor = this.db.query(TABLE, new String[] { "name" },null, null, null, null, "name desc");

            if (cursor.moveToFirst()) {
                do {
                  list.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            return list.get(id);
         }
        public List<String> selectAll() {
            List<String> list = new ArrayList<String>();
            Cursor cursor = this.db.query(TABLE, new String[] { "name" },null, null, null, null, "name desc");

            if (cursor.moveToFirst()) {
                do {
                  list.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            return list;
        }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SCRIPT);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF IT EXISTS " + TABLE);
        onCreate(db);
    }
}