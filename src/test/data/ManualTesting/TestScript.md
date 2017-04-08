**Command:**<br>
(Pre-condition: src\test\data\ManualTesting\SampleData.xml exists)

    open src\test\data\ManualTesting\SampleData.xml

**Expected Result:**

* `Loaded SampleData.xml` will be displayed on the ResultDisplayBar.
* Load tasks from SampleData.xml to myPotato.

**Command:**

    help

**Expected Result:**

* A new window displaying User Guide will be displayed.
* A message "Opened hep window" will also be shown on the result display.

**Command:**

    add Buy dinner
    add Software engineering project submit end/11:59 10 apr
    add Visit grandpa start/15 apr
    add Meeting c/rehearse OP2 start/1pm end/4pm 22 Mar #CS2101

**Expected result:**

* Floating task buy dinner will be added at the back of the list.
* Deadline task software engineering will be added after Floating task buy dinner.
* Planning task visit grandpa will be added after Deadline task software engineering.
* Event meeting will be added after Planning task visit grandpa.
* Tasks details will be reflected on the right panel after a task is added.

**Command:**

    mark 52

**Expected result:**

* Task number 52 in the current list will change from black to green to indicate task as completed and the task will be added into the completed list.
* Task details will be shown on the right panel.

**Command:**

    list all
    list today
    list completed

**Expected result:**

* The list containing all the completed tasks and uncompleted tasks are shown on the left panel. "Listed all tasks" message will also be shown on the result display.
* The list containing today's date tasks will be shown on the left panel. "Listed all today tasks" message will also be shown on the result display.
* The list containing completed tasks will be shown on the left panel. "Listed all completed tasks" message will also be shown on the result display.

**Command:**

    list all
    select 52

**Expected result:**

* Auto scroll to the task number 52 in the current list. The task details and content will be displayed on the right panel.
* A message "Selected Task:52" will be shown on the result display.

**Command:**

    edit 52 c/new content
    edit 53 start/3 apr 4pm
    edit 54 end/4 apr 6:30pm #mrt

**Expected result:**

* edits the content of task number 52 to "new content".
* edits the start date time of task number 53 to 4pm of 3 April.
* edits the end date time of task number 54 to 6:30pm of 4 April and add a tag, mrt.
* After editing a task, the right panel should also reflect the changes.

**Command:**

    undo

**Expected result:**

* The previous edit should be undone and the result display should shows a "Previous command has been undo" message.

**Command:**

    unmark 52

**Expected result:**

* Task number 52 in the current list(all list) will change color from green to black to indicate task as uncompleted and the task will be removed from completed task.

**Command:**

    find meeting

**Expected result:**

* Task Meeting will be shown in the left panel list.
* A message "1 tasks listed" should be shown on the result display.

**Command:**

    find content/new

**Expected result:**

* Task buy dinner will be shown in the left panel list.
* A message "1 tasks listed" should be shown on the result display.

**Command:**

    list all
    delete 52

**Expected result:**

* Task 52 under the current list will be deleted

**Command:**<br>
(Pre-condition: task number 54, event meeting is created with a date and at index 54)

    delete 54 deadline

**Expected result:**

* Task 54 deadline will be deleted.
* A message "Deadline deleted for Meeting!" will be shown on the result display.

**Command:**

    undo

**Expected result:**

* The deadline for task 54 will be restored and the result display should shows a "Previous command has been undo" message.

**Command:**

    add Set alarm
    undo

**Expected result:**

* The floating task Set alarm which was added to the end of the list should now be deleted after the undo command and the result           display should shows a "Previous command has been undo" message.

**Command:**

    clear

**Expected result:**

* The task list should now be emptied.
* "myPotato has been cleared!" message should be shown on the result display.

**Command:**

    undo

**Expected result:**

* The previous task list should now be restored and the result display should shows a "Previous command has been undo" message

**Command:**

    save data/SampleData
    save data/SampleData.xml

**Expected Result:**

* A file will be created in the specified path.
* Tasks saved to `src\test\data\ManualTesting\SampleData.xml` will be displayed on the ResultDisplayBar.
* StatusBarFooter displays the path it is saved to.
