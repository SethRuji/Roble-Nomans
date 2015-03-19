package edu.rosehulman.rosehomeworksr;

import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Task implements Comparable<Task> {
	private long mTaskID;
	private String mName;
	private String mCourse;
	private GregorianCalendar mDueDate;
	public static final String[] MONTHS= {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
 
 	public Task() { /* no-op */ }
 
 	public Task(String name, String course, GregorianCalendar dueDate) {
       	mName = name;
       	mCourse = course;
       	mDueDate = dueDate;
       	mTaskID = -1;
 	}
  	public long getId() { return mTaskID; }
	
  	public void setId(long id) { this.mTaskID = id; }
	 
 	public String getName() { return mName; }
 
 	public void setName(String name) { this.mName = name; }
 
 	public String getCourse() { return mCourse; }
 
 	public void setCourse(String course) { this.mCourse = course; }
 	public GregorianCalendar getDueDate() { return mDueDate; }
 	 
 	public int getYearDue() { return mDueDate.get(Calendar.YEAR); }
 
 	public int getMonthDue() { return mDueDate.get(Calendar.MONTH); }
 
 	public int getDayOfMonthDue() { return mDueDate.get(Calendar.DAY_OF_MONTH); }
 	
 	public void setDueDate(GregorianCalendar dueDate) { this.mDueDate = dueDate; }
 	 
 	public void setDueDate(int year, int month, int dayOfMonth) {
       	mDueDate = new GregorianCalendar();
       	mDueDate.set(Calendar.HOUR, 0);
       	mDueDate.set(Calendar.MINUTE, 0);
       	mDueDate.set(Calendar.SECOND, 0);
       	mDueDate.set(Calendar.MILLISECOND, 0);
       	mDueDate.set(Calendar.YEAR, year);
       	mDueDate.set(Calendar.MONTH, month);
       	mDueDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
 	}

 	@Override
 	public int compareTo(Task another) {
       	GregorianCalendar anotherDueDate = another.getDueDate();
       	int comp = mDueDate.compareTo(anotherDueDate);
       	return (comp == 0 ? mName.compareTo(another.getName()) : comp);
 	}
 
 	     @Override
 	public String toString() {
       	return MONTHS[this.mDueDate.get(Calendar.MONTH)] + " "
                 	+ this.mDueDate.get(Calendar.DAY_OF_MONTH) + ", "
                 	+ this.mDueDate.get(Calendar.YEAR) + " " + mCourse + " "
                 	+ mName;
 	}

}
