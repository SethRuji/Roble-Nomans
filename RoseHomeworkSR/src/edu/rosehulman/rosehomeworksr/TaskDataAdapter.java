package edu.rosehulman.rosehomeworksr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TaskDataAdapter {
	// Becomes the filename of the database
	private static final String DATABASE_NAME = "tasks.db";
	// Only one table in this database
	private static final String TABLE_NAME = "tasks";
	// We increment this every time we change the database schema which will
	// kick off an automatic upgrade
	private static final int DATABASE_VERSION = 1;
	
	public static final String KEY_ID = "_id"; // Android naming convention for IDs
	public static final String KEY_NAME = "name";
	public static final String KEY_COURSE = "course";
	public static final String KEY_YEAR = "year";
	public static final String KEY_MONTH = "month";
	public static final String KEY_DATE = "date";
	
	private static String DROP_STATEMENT = "DROP TABLE IF EXISTS " + TABLE_NAME;
	private static String CREATE_STATEMENT;
	static {
	 	StringBuilder sb = new StringBuilder();
	 	sb.append("CREATE TABLE " + TABLE_NAME + " (");
	 	sb.append(KEY_ID + " integer primary key autoincrement, ");
	 	sb.append(KEY_NAME + " text, ");
	 	sb.append(KEY_COURSE+ " text, ");
	 	sb.append(KEY_YEAR + " integer, ");
	 	sb.append(KEY_MONTH + " integer, ");
	 	sb.append(KEY_DATE + " integer");	 	
	 	sb.append(")");
	 	CREATE_STATEMENT = sb.toString();
	}

	private TaskDbHelper mOpenHelper;
	private SQLiteDatabase mDatabase;
	
	/*code*/
	public TaskDataAdapter(Context context){
		mOpenHelper= new TaskDbHelper(context);
	}
	
	public void open(){
		mDatabase= mOpenHelper.getWritableDatabase();
	}
	
	public void close(){
		mDatabase.close();
	}
	private ContentValues getContentValuesFromTask(Task task) {
	 	ContentValues row = new ContentValues();
	 	row.put(KEY_NAME, task.getName());
	 	row.put(KEY_COURSE, task.getCourse());
	 	row.put(KEY_DATE, task.getDayOfMonthDue());
	 	row.put(KEY_MONTH, task.getMonthDue());
	 	row.put(KEY_YEAR, task.getYearDue());
	 	return row;
	}
	
	public void deleteTask(long id){
		mDatabase.delete(TABLE_NAME, KEY_ID + " = " + id, null);
	}
	
	public long addTask(Task task){
		long rowInserted= mDatabase.insert(TABLE_NAME, null, getContentValuesFromTask(task));
		task.setId(rowInserted);
		return rowInserted;
	}
	
	private class TaskDbHelper extends SQLiteOpenHelper{

		public TaskDbHelper(Context context){
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_STATEMENT);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.d("TASKS", "Updating from version " + oldVersion + " to " + newVersion + ", which will destroy old table(s).");
			db.execSQL(DROP_STATEMENT);
			onCreate(db);
		}
		
	}

	public void setAllTasks(ArrayList<Task> mTasks) {
		String[] columns = null;
		Cursor cursor = mDatabase.query(TABLE_NAME, columns,null, null, null, null, null);
		if(cursor==null || !cursor.moveToFirst()){
			return;
		}
		mTasks.clear();
		do{
			mTasks.add(getTaskFromCursor(cursor));			
		}while(cursor.moveToNext());
		Collections.sort(mTasks);
	}

	private Task getTaskFromCursor(Cursor cursor) {
		String name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME));
		String course = cursor.getString(cursor.getColumnIndexOrThrow(KEY_COURSE));
		int year= cursor.getInt(cursor.getColumnIndexOrThrow(KEY_YEAR));
		int month = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_MONTH));
		int day = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_DATE));
		GregorianCalendar dueDate = new GregorianCalendar(year, month, day);
		Task task = new Task(name, course, dueDate);
		task.setId(cursor.getLong(cursor.getColumnIndexOrThrow(KEY_ID)));
		return task;
	}
}
