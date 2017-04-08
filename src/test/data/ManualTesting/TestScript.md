Command: (Pre-condition: src\test\data\ManualTesting\SampleData.xml exists)
open src\test\data\ManualTesting\SampleData.xml

Expected Result:
¡°Loaded src\test\data\ManualTesting\SampleData.xml¡± will be displayed on the ResultDisplayBar.
Load tasks from SampleData.xml to myPotato.

Command:
help

Expected Result:
MyPotato user guide will be shown

Command:
add Buy dinner
add Software engineering project submit end/11:59 10 apr
add Visit grandpa start/15 apr
add Meeting c/rehearse OP2 start/1pm end/4pm 22 Mar #CS2101

Expected result:
Floating task buy dinner will be added at the back of the list
Deadline task software engineering will be added after buy dinner
Planning task visit grandpa will be added after software engineering
Event meeting will be added after visit grandpa

Command:
mark 52

Expected result:
Task number 52 in the current list will change color from black to green and the task will be added into the completed list

Command:
list all
list today
list completed

Expected result:
The list containing all the completed tasks and uncompleted tasks is shown
The list containing today¡¯s date tasks will be shown
The list containing completed tasks will be shown

Command:
select 52

Expected result:
Select the second task in the current list

Command: 
edit 52 c/new content
edit 53 start/3 apr 4pm
edit 54 end/4 apr 6:30pm #mrt

Expected result:
edits the content of task number 52 to "new content"
edits the start date time of task number 53 to 4pm of 3 April
edits the end date time of task number 54 to 6:30pm of 4 April and add a tag

Command:
Undo

Expected result:
The previous edit should be undone and the result display should shows a   "Previous command has been undo" message.

Command:
unmark 52

Expected result:
Task number 52 in the current list(all list) will change color from green to black and the task will be removed from completed task.

Command:
find meeting

Expected result:
Task Meeting will be shown in the list

Command:
find content/new

Expected result:
Task buy dinner will be shown

Command:
delete 52

Expected result:
Task 52 under the current list will be deleted

Command: (Pre-condition: task number 55 is created)
delete 55 deadline

Expected result:
Task 55 deadline will be deleted

Command: Undo

Expected result:
The deadline for task 55 which be restored and the result display should  shows a   "Previous command has been undo" message

Command:
 add Set alarm
 Undo

Expected result:
The floating task Set alarm which was added to the end of the list should now  be deleted after the undo command and the result display should shows a "Previous command has been undo" message

Command: Clear

Expected result: 
The task list should now be emptied 

Command: Undo

Expected result:
The previous task list should now be restored and the result display should     shows a   "Previous command has been undo" message

Command: 
save data/SampleData
save data/SampleData.xml

Expected Result: 
A file will be created in the specified path.
¡°Tasks saved to src\test\data\ManualTesting\SampleData.xml¡± will be displayed on the ResultDisplayBar.
StatusBarFooter displays the path it is saved to.
