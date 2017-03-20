# myPotato - User Guide

By : `Team myPotato`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `FEB 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

### Quick Links

1. [Quick Start](#1-quick-start)
2. [Features](#2-features)
3. [FAQ](#3-faq)
4. [Command Summary](#4-command-summary)


### Welcome to MyPotato

MyPotato is a user-friendly task scheduler which helps users to better manage their daily tasks.<br>
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
| 3. [List all Tasks](#list-all-tasks--list) |
| 4. [Select Task](#select-task--select) |
| 5. [Edit Task](#edit-task--edit) |
| 6. [Find Tasks](#find-tasks--find) |
| 7. [Delete Task](#delete-task--delete) |
| 8. [Clear all Tasks](#clear-all-tasks--clear) |
| 9. [Undo a Previous Command](#undo-a-previous-command--undo) |
| 10. [Save TaskList](#save-tasklist--save) |
| 11. [Open TaskList](#open-tasklist--open) |
| 12. [Exit Program](#exit-program--exit) |

### Launch

You can start the day by opening MyPotato to view the tasks to be completed for the current day.<br>

Note: Mypotato can help you to automatically sort your tasks according to their deadlines.<br>
The earliest deadline will appear at the top of the list and floating tasks at the bottom.


## 2. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

### Help : `help`

After Launching the application, if you need a cheatsheet of the command formats, MyPotato has a help function that displays the list of the commands. Simply enter “help” command for more information.

    Format: help

> Help is also shown if you enter an incorrect command e.g. `abcd`


### Add Task : `add`
You can add a task with or without deadline. Upon adding a task, the content of the task will be displayed on the right window for your verification. <br>

    Format: add TITLE d/[CONTENT] from/[DATE] [TIME] to/[DATE] [TIME] #[tags]
            add TITLE d/[CONTENT] by/[DATE] [TIME] #[tags]

However, do take note of the following:

> TITLE is the name of a task <br>
> DATE can be in different formats 
               
      dd/MM/yyyy (e.g 15/3/2017)
      dd/MM/yy (e.g 15/3/17)
      dd/MM: year will be specified as current year (e.g 15/3)
      dd-MM-yyyy (e.g 15-3-2017)
      dd-MM-yy (e.g 15-3-17)
      dd-MM: year will be specified as current year (e.g 15-3)
      dd MMM: year will be specified as current year (e.g 15 Mar)

> TIME can be in different formats 
      
      HH:mm (e.g 12:15)
      HH :mm am/pm (e.g 9:15pm)
      HH am/pm (e.g 10am)
    

      Examples:
      add Meeting c/rehearse OP2 start/1pm end/4pm 22 Mar #CS2101


#### List all Tasks : `list`

After adding the tasks you need, you can track them using the list command.<br>

    Format: list


#### Select Task : `select`

When you need the content to a specific task, you can use the select command to select the task from the list. <br>

    Format: select INDEX

> Alternative: click to the task in the showing list <br>
> Select the task and display all details at the specified `INDEX`<br>
> The INDEX refers to the index number shown in the most recent listing<br>

    Examples:
    
    list
    select 2
    Select the 2nd task
    
    find Project
    select 1
    Select the 1st task from the results returned from the find command

#### Edit Task : `edit`

You can update any part of a task using edit command. The formats of DATE and TIME are the same as add command <br>

    Format: Edit INDEX [NEW_TITLE] c/[NEW_CONTENT] start/[NEW_TIME] [NEW_DATE] end/[NEW_TIME] [NEW_DATE] #[NEW_TAGS]

> Edit the task at the specified INDEX. The index refers to the index number shown in the last task listing. Existing values will be updated to the input values. When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative. You can remove all the task's tags by typing t/ without specifying any tags after it.

    Examples:

    edit 1  d/22/03
    Edits the due date of the 1st task to be 22/03
    
    edit 2 Project meeting t/LT1
    Edits the 2nd task to Project meeting and add hashtag LT1

#### Find Tasks : `find`

In addition, you can simply find tasks by entering the `find` command accompanied with keywords or numbers in their title, description and dates.


    Format: find KEYWORD [MORE_KEYWORDS]

However, do take note of the following:

> The keyword is case sensitive. <br>
> e.g `project` will not match `Project`<br>

> The order of the keywords does not matter. <br>
> e.g. `Meeting Project` will match `Project Meeting`<br>

> Search can based on title, description or dates.<br>

> Only complete word will be matched  <br>
> e.g. `Project` will not match `Projects`<br>

> Tasks matching at least one keyword will be returned (i.e. `OR` search).<br>
> e.g. `Project` will match `Project Meeting`

    Examples:

    find Meeting
    Returns Project Meeting
    
    find 23/03
    Returns Any task due by 23/03

#### Delete Task : `delete`

If you have completed the task or need to delete unwanted task, simply input the `delete` command.

    Format: delete INDEX [TASKDETAIL]

> Alternative: choose the task showing in the list and type delete.<br>
> Task index can be found in the panel List on the left side of myPotato.<br>
> Input the corresponding index number to delete the targeted task.<br>
> If there is any deadline you need to remove from a task, simply key in “deadline” after the index.

    Examples:

    list
    delete 2
    Deletes the 2nd task
  
    find Project
    delete 1
    Deletes the 1st task from the results of the `find` command
  
#### Clear all Tasks : `clear`

Need an efficient way to remove all tasks? Simply enter `clear` to remove the entire list of tasks.

    Format: clear

> This command allows you to clear your tasks when you do not need them anymore.

#### Undo a Previous Command : `undo` 

Accidentally removed your task? Fret not, simply enter the `undo` command to revert your changes.

    Format:  undo

> This command will undo the previous add/delete command which you had entered. 
> Unless you exit the program, you should be able to undo all the previous add/delete command executed in the program.  

#### Save TaskList : `save`

Specify your file directory or file path to `save` a back-up copy of your tasklist to your location conveniently.

    Format: save FILEPATH

    Examples:

    save C:\CS2103\Project
    save C:\CS2103\Project\myPotato
    save C:\CS2103\Project\myPotato.xml

#### Open TaskList : `open`

Specify a valid xml file to load into myPotato.

    Format: open FILEPATH

    Examples:

    open C:\CS2103\Project\taskmanager.xml

#### Exit program : `exit`

To exit the program, simply type `exit`.

    Format: exit

> This command will allow you to exit and save your previous changes.


## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous myPotato folder.


## 4. Command Summary

| **Command** | **Format** |
| ----------- | ---------- |
| [Help](#help--help) | help |
| | e.g. help |
| [Add](#add-task--add) | add t/TASK [d/task] [#tags] |
| | e.g. add t/CS2103 meeting d/03/03 #Programming Lab 2 |
| [List](#list-all-tasks--list) | show a list of task list in the list task |
| | e.g. list Homework |
| [Select](#select-task--select) | select INDEX |
| | e.g. select 2 |
| [Edit](#edit-task--edit) | edit INDEX [t/TASK] [d/DATE] [#tags] |
| | e.g. edit 1 t/CS2101 meeting d/04/03 #Progress Report |
| [Find](#find-tasks--find) | find KEYWORD [MORE_KEYWORDS] |
| | e.g. find CS2101 meeting |
| | e.g. find #Programming Lab 2 |
| [Delete](#delete-task--delete)| delete INDEX |
| | e.g. ` delete 3` |
| [Clear](#clear-all-tasks--clear)| clear |
| | e.g. clear |
| [Undo](#undo-a-previous-command--undo)| undo |
| | e.g. `undo` |
| [Save](#save-tasklist--save)| save FILEPATH |
| | e.g. save C:\CS2103\Project\myPotato.xml |
| [Open](#open-tasklist--open) | open FILEPATH |
| | e.g. open C:\CS2103\Project\myPotato.xml |
| [Exit](#exit-program--exit)| exit |
| | e.g. exit |
