package corey.cpitzoapp.todolist;

import corey.cpitzoapp.todolist.R;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
public class MainActivity extends SwipeListViewActivity{

	
	private ItemDB itemDatabase = null;
	private Cursor items = null;
	private static SimpleCursorAdapter adapter = null;
	private final static String[] columnNames = {"Name","Due","Class", BaseColumns._ID};
	private ListView lvItems = null;
	protected static int REQUEST_UPDATE = 1;	
	protected static int REQUEST_INSERT = 2;
	protected static int REQUEST_DELETE = 3;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		CursorFactory factory = null;
		itemDatabase = new ItemDB(this, "itemDB", factory);
		String[] columnNames = {"Name", "Due", "Class", BaseColumns._ID};
		lvItems = (ListView)findViewById(R.id.lvItems);
		int[] targetLayoutIDs = {R.id.tvName,R.id.tvDue,R.id.tvClass};
		SQLiteDatabase db = itemDatabase.getReadableDatabase();
		items = db.query("myTodo", columnNames,null,null,null,null,null);
		adapter = new SimpleCursorAdapter(this, R.layout.items,
				items, columnNames, targetLayoutIDs, 0);
		lvItems.setAdapter(adapter);
		db.close();
		
		lvItems.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				TextView tvName=(TextView)view.findViewById(R.id.tvName);
				TextView tvDue=(TextView)view.findViewById(R.id.tvDue);
				TextView tvClass=(TextView)view.findViewById(R.id.tvClass);
				Intent updateIntent = new Intent(MainActivity.this, UpdateActivity.class);
				String[] itemContents = {
						tvName.getText().toString(),
						tvDue.getText().toString(),
						tvClass.getText().toString()
				};
				updateIntent.putExtra("itemContents",itemContents);
				startActivityForResult(updateIntent,REQUEST_UPDATE);
			}
		});
		
	}
	@Override
	 public ListView getListView() {
	 return lvItems;
	 }

	@Override
	 public void getSwipeItem(boolean isRight, int position) {
	if(isRight == true){
		
		String delName =((Cursor)adapter.getItem(position)).getString(((Cursor)adapter.getItem(position)).getColumnIndex("Name"));
		String delDue =((Cursor)adapter.getItem(position)).getString(((Cursor)adapter.getItem(position)).getColumnIndex("Due"));
		String delClass =((Cursor)adapter.getItem(position)).getString(((Cursor)adapter.getItem(position)).getColumnIndex("Class"));
		SQLiteDatabase db = itemDatabase.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("Due",delDue );
		cv.put("Class",delClass );
		db.update("Deleted", cv , "Name= '"+delName+"';", null);
		db.delete("myTodo", "Name= '"+delName+"';",null);
		items = db.query("myTodo", columnNames, null,null,null,null,null);
		adapter.changeCursor(items);
		db.close();
	}
	 }

	
	@Override
	protected void onActivityResult(int reqCode, int resCode, Intent data){
		if(reqCode==REQUEST_UPDATE && data!=null){
			if(resCode==RESULT_OK){
				String[] results = data.getExtras().getStringArray("results");
				SQLiteDatabase db = itemDatabase.getWritableDatabase();
				ContentValues cv = new ContentValues();
				cv.put("Due", results[1]);
				cv.put("Class",results[2]);
				db.update("myTodo",cv,"Name= '"+results[0]+"';",null);
				items = db.query("myTodo", columnNames, null,null,null,null,null);
				adapter.changeCursor(items);
				db.close();
				
			}
		}
		else if(reqCode==REQUEST_INSERT && data!=null){
			if(resCode==RESULT_OK){
				String[] results = data.getExtras().getStringArray("results");
				SQLiteDatabase db = itemDatabase.getWritableDatabase();
				ContentValues cv = new ContentValues();
				cv.put("Name", results[0]);
				cv.put("Due", results[1]);
				cv.put("Class",results[2]);
				db.insert("myTodo",null,cv);
				items = db.query("myTodo", columnNames, null,null,null,null,null);
				adapter.changeCursor(items);
				db.close();
			}

		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override 
	public boolean onOptionsItemSelected(MenuItem item){
		boolean returnValue = true;
		switch(item.getItemId()){
		case R.id.menu_itemInsert:
			Intent insertIntent = new Intent(MainActivity.this, InsertActivity.class);
			startActivityForResult(insertIntent,REQUEST_INSERT);
				break;
		case R.id.menu_itemSortA_Z:
			SQLiteDatabase db = itemDatabase.getWritableDatabase();
			items = db.query("myTodo", columnNames, null,null,null,null,"Name ASC");
			adapter.changeCursor(items);
			db.close();
				break;
		case R.id.menu_itemSortDate:
			SQLiteDatabase db1 = itemDatabase.getWritableDatabase();
			items = db1.query("myTodo", columnNames, null,null,null,null,"Due DESC");
			adapter.changeCursor(items);
			db1.close();
				break;
		case R.id.menu_itemsortSubj:
			SQLiteDatabase db2 = itemDatabase.getWritableDatabase();
			items = db2.query("myTodo", columnNames, null,null,null,null,"Class ASC");
			adapter.changeCursor(items);
			db2.close();
				break;
		case R.id.menu_itemUnorder:
			SQLiteDatabase db3 = itemDatabase.getWritableDatabase();
			items = db3.query("myTodo", columnNames, null,null,null,null,null);
			adapter.changeCursor(items);
			db3.close();
			break;
		case R.id.menu_itemDeleted:
			Intent deleteIntent = new Intent(MainActivity.this, DeletedActivity.class);
			break;
		default:
			returnValue = super.onOptionsItemSelected(item);
			break;
		}
		return returnValue;
	}
	
		
}


