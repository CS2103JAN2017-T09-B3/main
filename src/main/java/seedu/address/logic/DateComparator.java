package seedu.address.logic;

import java.util.Comparator;

import seedu.address.model.task.ReadOnlyTask;
//This is a date comparator to be used to sort the task list according to their deadline
public class DateComparator implements Comparator<ReadOnlyTask>{
	//a method to compare the year of the the two task and assign 2099 to float task
	public int compareYear(ReadOnlyTask task1, ReadOnlyTask task2){
		int year1 = 0, year2 = 0;
		if (task1.getDateTime().date == null){
			year1 = 2099;
		}
		else if (task2.getDateTime().date == null){
			year2 = 2099;
		}
		else {
		year1 = task1.getDateTime().date.getYear();
		year2 = task2.getDateTime().date.getYear();
		}
		return Integer.compare(year1, year2);
	}
	//method to compare the month of the two task and assign 12 to float task
	public int compareMonth(ReadOnlyTask task1, ReadOnlyTask task2){
		int month1 = 0,month2 = 0;
		if (task1.getDateTime().date.equals(null)){
			month1 = 12;
		}
		else if (task2.getDateTime().date.equals(null)){
			month2 = 12;
		}
		else{
		month1 = task1.getDateTime().date.getMonth();
		month2 = task2.getDateTime().date.getMonth();
		}
		return Integer.compare(month1, month2);
	}
	//method to compare the day of the two task and assign 31 to float task
	public int compareDay(ReadOnlyTask task1, ReadOnlyTask task2){
		int day1 = 0, day2 = 0;
		if (task1.getDateTime().date.equals(null)){
			day1 = 31;
		}
		else if (task2.getDateTime().date.equals(null)){
			day2 = 31;
		}
		else{
		day1 = task1.getDateTime().date.getDay();
		day2 = task2.getDateTime().date.getDay();
		}
		return Integer.compare(day1, day2);
	}
	//method to compare the hour of the two task and assign 23 to float task
	public int compareHour(ReadOnlyTask task1, ReadOnlyTask task2){
		int hour1 = 0, hour2 = 0;
		if (task1.getDateTime().date.equals(null)){
			hour1 = 23;
		}
		if (task2.getDateTime().date.equals(null)){
			hour2 = 23;
		}
		else{
		hour1 = task1.getDateTime().date.getHours();
		hour2 = task2.getDateTime().date.getHours();
		}
		return Integer.compare(hour1, hour2);
	}
	//method to compare the minute of the two task and assign 59 to float task
	public int compareMinute(ReadOnlyTask task1, ReadOnlyTask task2){
		int minute1 = 0, minute2 = 0;
		if (task1.getDateTime().date.equals(null)){
			minute1 = 59;
		}
		if (task2.getDateTime().date.equals(null)){
			minute2 = 59;
		}
		else{
		minute1 = task1.getDateTime().date.getMinutes();
		minute2 = task2.getDateTime().date.getMinutes();
		}
		return Integer.compare(minute1, minute2);
	}
	//this is the compare method that compare the each value of the
	//deadline of the tasks according to their time priority.
	//For example, year>month>day>hour>minute.
	@Override
	public int compare(ReadOnlyTask task1,ReadOnlyTask task2) {
		if (compareYear(task1,task2) == 0){
			if (compareMonth(task1,task2) == 0){
				if (compareDay(task1,task2)==0){
					if (compareHour(task1,task2)==0){
						return compareMinute(task1,task2);
					}else{return compareHour(task1,task2);}
				}else {return compareDay(task1,task2);}
			}else {return compareMonth(task1,task2);}
		}else {return compareYear(task1,task2);}
	}
}
