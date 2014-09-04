package corey.cpitzoapp.todolist;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ItemDB extends SQLiteOpenHelper{
	private static final int DB_VERSION = 1;
	private static final String ITEM_TABLE_NAME = "myTodo";
	private static final String DELETED_TABLE_NAME = "Deleted";
	
	ItemDB(Context context, String name, SQLiteDatabase.CursorFactory factory)
	{
		super(context,name,factory,DB_VERSION);
	}
	
	public void onCreate(SQLiteDatabase dbItems)
	{
		createTable(dbItems);
	}
	
	public void onUpgrade(SQLiteDatabase dbItems, int oldVersion, int newVersion)
	{
		String dropSQL = "DROP TABLE IF EXISTS " + ITEM_TABLE_NAME+";";
		String dropSQL2 = "DROP TABLE IF EXISTS " + DELETED_TABLE_NAME+";";
		dbItems.execSQL(dropSQL);
		dbItems.execSQL(dropSQL2);
		createTable(dbItems);
	}
	
	private void createTable(SQLiteDatabase dbItems)
	{
		String createSQL = "CREATE TABLE " + ITEM_TABLE_NAME + "(" +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"Name TEXT," + "Due TEXT," + "Class TEXT);";
		dbItems.execSQL(createSQL);
		String insertSQL="INSERT INTO " + ITEM_TABLE_NAME +
				" (Name, Due, Class) " +
				" SELECT 'test' AS Name, '21' AS Due, 'math' AS Class "+
				" UNION SELECT 'test2','Sept 16','career';";
		dbItems.execSQL(insertSQL);
		String createSQL2 = "CREATE TABLE " + DELETED_TABLE_NAME + "(" +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"Name TEXT," + "Due TEXT," + "Class TEXT);";
		dbItems.execSQL(createSQL2);
		
	}
}
