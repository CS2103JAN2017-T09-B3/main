# myPotato - User Guide

By : `Team myPotato`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `FEB 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

### Quick Links

1. [Quick Start](#1-quick-start)
2. [Features](#2-features)
3. [FAQ](#3-faq)
4. [Command Summary](#4-command-summary)


### Welcome to myPotato

myPotato is a user-friendly task scheduler which helps users to better manage their daily tasks.<br>
This application allows input and editing of tasks using an easy to use command line interface suitable for users of all technical background.


## 1. Quick Start

1. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
2. Download and install the latest version of myPotato.
3. Double-click the icon to start myPotato. The GUI should appear in a few seconds.
   > <img src="images/MyPotato-1.PNG" width="600">
4. Type the command in the command line and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Refer to the [Features](#2-features) section below for details of each command.<br>

### Getting Started

In this guide, we will guide you through all the features by the following list.


| **Features** |
| ---------- |
| 1. [Help](#help--help) |
| 2. [Add Task](#add-task--add) |
| 3. [List Tasks](#list-tasks--list) |
| 4. [Select Task](#select-task--select) |
| 5. [Edit Task](#edit-task--edit) |
| 6. [Mark Task As Done](#mark-task-as-done--mark) |
| 7. [Mark Task As UnDone](#mark-task-as-undone--unmark) |
| 8. [Find Tasks](#find-tasks--find) |
| 9. [Delete Task](#delete-task--delete) |
| 10. [Clear Tasks](#clear-tasks--clear) |
| 11. [Undo](#undo--undo) |
| 12. [Save TaskList](#save-tasklist--save) |
| 13. [Open TaskList](#open-tasklist--open) |
| 14. [Exit Program](#exit-program--exit) |

### Launch


## 2. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

### Help : `help`

After Launching the application, if you need a cheatsheet of the command formats, myPotato has a help function that displays the list of the commands. Simply enter `help` command for more information.

    Format: help

> Help is also shown if you enter an incorrect command e.g. `abcd`


### Add Task : `add`
You can add a task using the following format. Upon adding a task, details of the task will be displayed on the right window for your verification. <br>

    Format: add TITLE c/[CONTENT] start/[DATE] [TIME] end/[DATE] [TIME] #[tags]

* TITLE is the name of a task. Only TITLE is compulsory when you want to add a task, other parts are optional<br>
* CONTENT is the description of a task <br>
* DATE can be in any of following formats

      dd/MM/yyyy (e.g 15/3/2017)
      dd/MM/yy (e.g 15/3/17)
      dd/MM: year will be specified as current year (e.g 15/3)
      dd-MM-yyyy (e.g 15-3-2017)
      dd-MM-yy (e.g 15-3-17)
      dd-MM: year will be specified as current year (e.g 15-3)
      dd MMM: year will be specified as current year (e.g 15 Mar)
      dd.MM.yy (e.g 15.03.17)

* TIME can be in any of following formats

      HH:mm (e.g 12:15)
      HH :mm am/pm (e.g 9:15pm)
      HH am/pm (e.g 10am)
      
* If only time is given in ending date time, date is automatically specified as today.
* If only time is given in starting date time, starting date is automatically specified as ending date. If there is ending date, starting date is automatically specified as today 
* After adding, the task is automatically highlighted
* Supported types of task:
      
      Floating task: a task without date time
      Deadline task: a task with an ending date time
      Planning task: a task with an starting date time
      Event: a task with starting and ending date time
      
**Examples:**

   * add Buy dinner
      >*Floating task*
   * add Software engineering project submit end/11:59 10 apr
      >*Deadline task*
   * add Visit granpa start/15 apr
      >*Planning task*
   * add Meeting c/rehearse OP2 start/1pm end/4pm 22 Mar #CS2101
      >*Event*


### List Tasks : `list`

After adding the tasks you need, you can track them using the list command.<br>

    Format: list all/today/completed

**Examples:**

   * list all
      >*list all the tasks*
   * list today
      >*list all tasks which start and end today*
   * list completed
      >*list all completed tasks*

### Select Task : `select`

When you need the content to a specific task, you can use the select command to select the task from the list.<br>

    Format: select INDEX

* Alternative: click to the task in the showing list 
* Select the task and display all details at the specified `INDEX`
* The INDEX refers to the index number shown in the most recent listing

**Examples:**

   * select 2
      >*select the second task in the current list*
    
### Edit Task : `edit`

You can update any part of a task using edit command. The formats of DATE and TIME are the same as add command.<br>

    Format: Edit INDEX [NEW_TITLE] c/[NEW_CONTENT] start/[NEW_TIME] [NEW_DATE] end/[NEW_TIME] [NEW_DATE] #[NEW_TAGS]

* Edit the task at the specified INDEX
* The index refers to the index number shown in the last task listing
* After editing, edited task is automatically highlighted
* Existing values will be updated to the new input values
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative
* You can remove all the task's tags by typing # without specifying any tags after it
* You can remove the task's starting date time by typing start/ without specifying any date time after it
* You can remove the task's ending date time by typing end/ without specifying any date time after it

**Examples:**

   * edit 1 c/new content
      >*edits the content of task number 1 to "new content"*

    * edit 2 start/3 apr 4pm
      >*edits the start date time of task number 2 to 4pm of 3 April*
    
    * edit 3 end/6:30pm 4 apr #mrt
      >*edits the end date time of task number 2 to 6:30pm of 4 April and add a tag mrt*

### Mark Task As Done : `mark`

### Mark Task As Undone : `unmark`

### Find Tasks : `find`

In addition, you can simply find tasks by entering the `find` command accompanied with keywords or numbers in their title, description and dates.

    Format: find KEYWORD [MORE_KEYWORDS]
    Format: find content/ KEYWORD [MORE_KEYWORDS]

* The keyword is not case sensitive

* The order of the keywords does not matter. <br>
   > e.g. `Meeting Project` will match `Project Meeting`<br>

* By default, finding is in title of task. With specifier "content/", finding is in both title and content of task<br>

* Partial word can be found<br>
   > e.g. `ject` will also match `Projects`<br>

* Tasks matching at least one keyword will be returned (i.e. `OR` search).<br>
   > e.g. `Project` will match `Project Meeting`

**Examples:**

   * find Meeting
      >*returns Project Meeting*

### Delete Task : `delete`

If you have completed the task or need to delete unwanted task, simply input the `delete` command.

    Format: delete INDEX [TASK_DETAIL]

* Task index can be found in the list on the left side of myPotato /**put a screenshot here**/<br>
* Input the corresponding index number to delete the targeted task.<br>
* TASK_DETAIL is part of task that you want to delete. For now, you only can delete deadline from a task. So, TASK_DETAIL is only "deadline". 

**Examples:**

   * delete 2
      >*deletes the task number 2*
   * delete 1 deadline
      >*deletes only the deadline of task number 1*

### Clear Tasks : `clear`

Need an efficient way to remove all tasks? Simply enter `clear` to remove the entire list of tasks.

    Format: clear

* This command allows you to clear your tasks when you do not need them anymore.

### Undo : `undo`

Accidentally removed your task? Fret not, simply enter the `undo` command to revert your changes.

    Format:  undo

* This command will undo the previous add/delete command which you had entered.
* Unless you exit the program, you should be able to undo all the previous add/delete command executed in the program.

### Save TaskList : `save`

Specify your file directory or file path to `save` a back-up copy of your tasklist to your location conveniently. Upon successful command execution, `Tasks saved to FILELOCATION` message will be displayed.<br>

    Format: save FILEPATH

* Future changes will be automatically saved to your specified FILELOCATION.<br>
* myPotato will load the most recent saved FILELOCATION on start-up.
    
 **Examples:**
 
    * save C:\CS2103\Project
    * save C:\CS2103\Project\myPotato
    * save C:\CS2103\Project\myPotato.xml

### Open TaskList : `open`

Specify a valid xml file to load into myPotato. Upon successful command execution, `Loaded FILENAME` message will be displayed.

    Format: open FILEPATH

**Examples:**

   * open C:\CS2103\Project\taskmanager.xml

### Exit program : `exit`

To exit the program, simply type `exit`.

    Format: exit

* This command will allow you to exit and save your previous changes.


## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous myPotato folder.


## 4. Command Summary

| **Command** | **Format** |
| ----------- | ---------- |
| [Help](#help--help) | help |
| | e.g. help |
| [Add](#add-task--add) | add TITLE c/[CONTENT] start/[DATE][TIME] end/[DATE][TIME] #[TAG] |
| | e.g. add CS2103 meeting c/bring computer start/2pm end/4pm 3 mar #Programming Lab 2 |
| [List](#list-tasks--list) | show a list of task list in the list task |
| | e.g. list all |
| | e.g. list today |
| | e.g. list completed |
| [Select](#select-task--select) | select INDEX |
| | e.g. select 2 |
| [Edit](#edit-task--edit) | edit INDEX [TITLE] c/[CONTENT] start/[DATE][TIME] end/[DATE][TIME] #[TAG] |
| | e.g. edit 1 CS2101 meeting end/04/03 #Progress Report |
| [Mark](#mark-task-as-done--mark) | mark INDEX |
| | e.g. mark 2 |
| [Unmark](#mark-task-as-undone--mark) | unmark INDEX |
| | e.g. unmark 2 |
| [Find](#find-tasks--find) | find KEYWORD [MORE_KEYWORDS] |
| | e.g. find CS2101 meeting |
| | e.g. find content/Programming Lab 2 |
| [Delete](#delete-task--delete)| delete INDEX [TASKDETAIL] |
| | e.g. delete 3|
| | e.g. delete 3 deadline |
| [Clear](#clear-tasks--clear)| clear |
| | e.g. clear |
| [Undo](#undo--undo)| undo |
| | e.g. `undo` |
| [Save](#save-tasklist--save)| save FILEPATH |
| | e.g. save C:\CS2103\Project\myPotato.xml |
| [Open](#open-tasklist--open) | open FILEPATH |
| | e.g. open C:\CS2103\Project\myPotato.xml |
| [Exit](#exit-program--exit)| exit |
| | e.g. exit |
