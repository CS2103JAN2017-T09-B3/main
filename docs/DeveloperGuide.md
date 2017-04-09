# myPotato - Developer Guide

By : `Team myPotato`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Feb 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---
## Table Of Contents
1. [Introduction](#1-introduction)
2. [Setting Up](#2-setting-up)
3. [Design](#3-design)<br>
 3.1 [Architecture](#31-architecture)<br>
 3.2 [UI component](#32-ui-component)<br>
 3.3 [Logic component](#33-logic-component)<br>
 3.4 [Model component](#34-model-component)<br>
 3.5 [Storage component](#35-storage-component)<br>
4. [Implementation](#4-implementation)
5. [Testing](#5-testing)
6. [Dev Ops](#6-dev-ops)

## Appendix
* [Appendix A: User Stories](#appendix-a--user-stories)
* [Appendix B: Use Cases](#appendix-b--use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c--non-functional-requirements)
* [Appendix D: Glossary](#appendix-d--glossary)
* [Appendix E : Product Survey](#appendix-e--product-survey)

## 1. Introduction

MyPotato is a task manager which provides a platform for users to organize their tasks efficiently. Our objective is to create a user interface that allows the user to manage their tasks conveniently with minimal use of a mouse.

The purpose of this guide is to aid the developer in enhancing the application and creating extensions. The Developer Guide provides an overview of the major components and how information flows from user input to respond with a feedback message informing the user whether the command is executed successfully.

## 2. Setting up

### 2.1. Prerequisites

1. Ensure that your system runs on **JDK `1.8.0_60`**  or later<br>
2. Ensure your system has **Eclipse** IDE pre-installed
3. For steps 4-6, download the plugins given in [this page](http://www.eclipse.org/efxclipse/install.html#for-the-ambitious)
4. **e(fx)clipse** plugin for Eclipse
5. **Buildship Gradle Integration** plugin from the Eclipse Marketplace
6. **Checkstyle Plug-in** plugin from the Eclipse Marketplace


### 2.2. Importing the project into Eclipse

1. Fork this repo, and clone the fork to your computer
2. Open Eclipse (Note: Ensure you have installed the **e(fx)clipse** and **buildship** plugins as given
   in the prerequisites above)
3. Click `File` > `Import`
4. Click `Gradle` > `Existing Gradle Project` > `Next` > `Next`

<img src="images/ImportGradle.png" width="600"><br>
_Figure 2.2.1 : Architecture Diagram_

5. Click `Browse`, then locate the project's directory
6. Click `Finish`


### 2.3. Configuring Checkstyle
1. Click `Project` -> `Properties` -> `Checkstyle` -> `Local Check Configurations` -> `New...`
2. Choose `External Configuration File` under `Type`
3. Enter an arbitrary configuration name e.g. myPotato
4. Import checkstyle configuration file found at `config/checkstyle/checkstyle.xml`
5. Click OK once, go to the `Main` tab, use the newly imported check configuration.
6. Tick and select `files from packages`, click `Change...`, and select the `resources` package
7. Click OK twice. Rebuild project if prompted


Note:<br>
In step 6, click on `files from packages` in order to enable the `Change...` button<br>
Right click on the project (in Eclipse package explorer) to activate Checkstyle,<br>
choose Checkstyle > Activate Checkstyle

### 2.4. Troubleshooting project setup

**Problem: Eclipse reports compile errors after new commits are pulled from Git**

* Reason: Eclipse fails to recognize new files that appeared due to the Git pull
* Solution: Refresh the project in Eclipse:<br>
  Right click on the project (in Eclipse package explorer), choose `Gradle` -> `Refresh Gradle Project`

**Problem: Eclipse reports some required libraries missing**

* Reason: Required libraries may not have been downloaded during the project import
* Solution: [Run tests using Gradle](UsingGradle.md) once (to refresh the libraries)

## 3. Design

### 3.1. Architecture

<img src="images/Architecture.png" width="600"><br>
_Figure 3.1.1 : Architecture Diagram_

Tip:
The `.pptx` files used to create diagrams in this document can be found in the [diagrams](diagrams/) folder.
To update a diagram, modify the diagram in the pptx file, select the objects of the diagram, and choose `Save as picture`.

The **_Architecture Diagram_** given in Figure 3.1.1 explains the high-level design of myPotato.
Given below is a quick overview of each component.

`Main` has only one class called [`MainApp`](../src/main/java/seedu/mypotato/MainApp.java). It is responsible for

* Initializing the components in the correct sequence, and connects them up with each other at launch.
* Shutting down the components and invokes cleanup method where necessary.

[**`Commons`**](#36-common-classes) represents a collection of classes used by multiple other components.
Two of those classes play important roles at the architecture level.

* **`EventsCenter`** : This class (written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained))
  is used by components to communicate with other components using events (i.e. a form of _Event Driven_ design)
* **`LogsCenter`** : Used by many classes to write log messages to the [App](#app) log file.

Let's dive into the 4 main components and observe how the classes are connected and the work flow of various events.

* The [**`UI`**](#32-ui-component) provides support for the user to interact with our application.
* The [**`Logic`**](#33-logic-component) parses the user input to execute the right command before returning a success message.
* The [**`Model`**](#34-model-component)  manages the in-memory data and is independent from the 3 other main components.
* The [**`Storage`**](#35-storage-component) writes and store data to your local drive. The data stored will be initialized upon re-opening of the application.

For each of the main component:

* The [API](#api) in an `interface` is defined with the same name as the Component.
* The functionality is managed by using a `{Component Name}Manager` class.

For example, the `Logic` component (see the class diagram given below) defines its [API](#api) as `Logic.java`
interface and exposes its functionality using the `LogicManager.java` class.<br>

#### Events-Driven nature of the design

The _Sequence Diagram_ below shows how the components interact for the scenario where the user issues the
command `delete 1`.

<img src="images/SDforDeletePerson.png" width="800"><br>
_Figure 3.1.3a : Component interactions for `delete 1` command (part 1)_

Note how the `Model` simply raises a `TaskManagerChangedEvent` when the Task Manager data is changed,
instead of asking the `Storage` to save the updates to the hard disk.

The diagram below shows how the `EventsCenter` reacts to that event, which eventually results in the updates
being saved to the hard disk and the status bar of the UI being updated to reflect the 'Last Updated' time. <br>

<img src="images/SDforDeletePersonEventHandling.png" width="800"><br>
_Figure 3.1.3b : Component interactions for `delete 1` command (part 2)_

Note how the event is propagated through the `EventsCenter` to the `Storage` and `UI` without `Model` having
to be coupled to either of them. This is an example of how this Event Driven approach helps us reduce direct
coupling between components.

The sections below provide more details of each component.

### 3.2. UI component

Author: Long & Di Feng

<img src="images/UIClassDiagram.png" width="800"><br>
_Figure 3.2.1 : Structure of the UI Component_

[**API**](#api) : [`Ui.java`](../src/main/java/seedu/mypotato/ui/Ui.java) in `/src/main/java/seedu/mypotato/ui`

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `TaskListPanel`,
`StatusBarFooter`, `TaskDescription` , `TabList` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files
 that are in the `src/main/resources/view` folder.<br>
 For example, the layout of the [`MainWindow`](../src/main/java/seedu/mypotato/ui/MainWindow.java) is specified in
 [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

The `UI` component:

* Executes user commands using the `Logic` component.
* Binds itself to some data in the `Model` so that the UI can auto-update when data in the `Model` change.
* Responds to events raised from various parts of the [App](#app) and updates the UI accordingly.

### 3.3. Logic component

Author: Long, Ivan, Di Feng, Yan Hao

<img src="images/LogicClassDiagram.png" width="800"><br>
_Figure 3.3.1 : Structure of the Logic Component_

Logic component parses user input and determine which command to execute. When there are arguments for parsing, a Command Parser Object will be created instead before creating a Command object for execution. This process follows the command design pattern. 

[**API**](#api) : [`Logic.java`](../src/main/java/seedu/mypotato/logic/Logic.java) in `src/main/java/seedu/mypotato/logic`

1. `Logic` uses the `Parser` class to parse the user command.
2. This results in a `Command` object which is executed by the `LogicManager`.
3. The command execution can affect the `Model` (e.g. adding a task) and/or raise events.
4. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui` reassuring the user that the command is executed.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")`

[API](#api) call.<br>

<img src="images/DeletePersonSdForLogic.png" width="800"><br>
_Figure 3.3.2 : Interactions Inside the Logic Component for the `delete 1` Command_

### 3.4. Model component

Author: Ivan Koh, Yan Hao, Long

<img src="images/ModelClassDiagram.png" width="800"><br>
_Figure 3.4.1 : Structure of the Model Component_

[**API**](#api) : [`Model.java`](../src/main/java/seedu/mypotato/model/Model.java) in `src/main/java/seedu/mypotato/model`

Model component contains task class and tag class, and it ensures that myPotato can give the correct output according to the user input.

The `Model`:

* stores a `UserPref` object that represents the user's preferences.
* stores the Task Manager data.
* exposes a `UnmodifiableObservableList<ReadOnlyTask>` that can be 'observed' <br>
   e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.
* calls functions from `ListFilter` to filter data based on different criteria.

#### Handling undo commands

The model component contains 7 stacks specially designed to keep track of both previous commands and tasks. These stacks will be implemented by the undo command in the logic component to undo the most recent command input by the user.

* getUndoStack() stores all the previous commands from the user input.
* getAddedStackOfTasks() stores all the tasks which was previously added by the users.
* getDeletedStackOfTasks() stores all the tasks which was previously added by the users.
* getDeletedStackOfTasksIndex() stores the index of the task which was deleted by the users.
* stackOfOldTask() stores the previous unedited task.
* getCurrentTask() stores the new edited task
* stackOfTasks() stores the previous list of tasks which had been cleared.

Based on the previous command popped from the getUndoStack(), the undo command will call one of the following commandResult undoAdd(), undoDelete(), or undoClear() in the undoCommand class which will then restore the current task list to the previous state before the most recent command.

#### Design date and time

<img src="images/TaskDateTimeClassDiagram.png" width="800"><br>
_Figure 3.5.1 : Structure of the TaskDateTime class_

TaskDateTime contains two DateValue objects: one is for start datetime, the other is for end datetime.

* Floating task: both start and end datetime are null
* Deadline task: start datetime is null, end datetime is specified
* Planning task: start datetime is specified, end datetime is null
* Event: both start and end datetime are specified

DateMaker class is designed specially for convert input string to DateValue object.

### 3.5. Storage component

Author: Di Feng

<img src="images/StorageClassDiagram.png" width="800"><br>
_Figure 3.5.1 : Structure of the Storage Component_

[**API**](#api) : [`Storage.java`](../src/main/java/seedu/mypotato/storage/Storage.java) in `/src/main/java/seedu/mypotato/storage`

From Figure 3.5.1, the `Storage` component consists of a StorageManager that calls `Save` and `Read` methods from UserPrefsStorage and JsonUserPrefsStorage Interface.

* UserPrefsStorage can save `UserPref` objects in json format and read it upon opening myPotato.
* TaskManagerStorage can save the in-memory data in xml format and read it upon opening myPotato.

Note: XmlSerializableTaskManager is a class to convert the in-memory UniqueTaskList and UniqueTagList to a .xml storage file and vice versa while XmlAdaptedTag and XmlAdaptedTask are classes to convert the respective UniqueTaskList and UniqueTagList to xml format.

### 3.6. Common classes

Classes used by multiple components are in the `seedu.mypotato.commons` package.


## 4. Implementation

### 4.1. Logging

java.util.logging package is used for logging. The `LogsCenter` class is used to manage the logging levels
and logging destinations.

* The logging level can be controlled using the `logLevel` setting in the configuration file
  (See [Configuration](#42-configuration))
* The `Logger` for a class can be obtained using `LogsCenter.getLogger(Class)` which will log messages according to
  the specified logging level
* Currently log messages are output through: `Console` and to a `.log` file.

**Logging Levels**

* `SEVERE` : Critical problem detected which may possibly cause the termination of the application
* `WARNING` : Can continue, but with caution
* `INFO` : Information showing the noteworthy actions by the [App](#app)
* `FINE` : Details that is not usually noteworthy but may be useful in debugging
  e.g. print the actual list instead of just its size

### 4.2. Configuration

Certain properties of the application can be controlled (e.g App name, logging level) through the configuration file
(default: `config.json`):


## 5. Testing

Tests can be found in the `./src/test/java` folder.

**In Eclipse**:

* To run all tests, right-click on the `src/test/java` folder and choose
  `Run as` > `JUnit Test`
* To run a subset of tests, you can right-click on a test package, test class, or a test and choose
  to run as a JUnit test.

**Using Gradle**:

* See [UsingGradle.md](UsingGradle.md) for how to run tests using Gradle.

There are two types of tests:

1. **GUI Tests** - These are _System Tests_ that test the entire [App](#app) by simulating user actions on the GUI.
   These are in the `guitests` package.

2. **Non-GUI Tests** - These are tests not involving the GUI. They include,
   * _Unit tests_ targeting the lowest level methods/classes. <br>
      e.g. `seedu.mypotato.commons.UrlUtilTest`
   * _Integration tests_ that are checking the integration of multiple code units
     (those code units are assumed to be working).<br>
      e.g. `seedu.mypotato.storage.StorageManagerTest`
   * Hybrids of unit and integration tests. These test are checking multiple code units as well as
      how they are connected together.<br>
      e.g. `seedu.mypotato.logic.LogicManagerTest`

#### Headless GUI Testing
Thanks to the [TestFX](https://github.com/TestFX/TestFX) library we use,
 our GUI tests can be run in the _headless_ mode.
 In the headless mode, GUI tests do not show up on the screen.
 That means the developer can do other things on the Computer while the tests are running.<br>
 See [UsingGradle.md](UsingGradle.md#running-tests) to learn how to run tests in headless mode.

### 5.1. Troubleshooting tests

 **Problem: Tests fail because NullPointException when AssertionError is expected**

 * Reason: Assertions are not enabled for JUnit tests.
   This can happen if you are not using a recent Eclipse version (i.e. _Neon_ or later)
 * Solution: Enable assertions in JUnit tests as described
   [here](http://stackoverflow.com/questions/2522897/eclipse-junit-ea-vm-option). <br>
   Delete run configurations created when you ran tests earlier.


## 6. Dev Ops

### 6.1. Build Automation

See [UsingGradle.md](UsingGradle.md) to learn how to use Gradle for build automation.

### 6.2. Continuous Integration

We use [Travis CI](https://travis-ci.org/) and [AppVeyor](https://www.appveyor.com/) to perform _Continuous Integration_ on our projects.
See [UsingTravis.md](UsingTravis.md) and [UsingAppVeyor.md](UsingAppVeyor.md) for more details.

### 6.3. Publishing Documentation

See [UsingGithubPages.md](UsingGithubPages.md) to learn how to use GitHub Pages to publish documentation to the
project site.

### 6.4. Making a Release

Here are the steps to create a new release.

 1. Generate a JAR file [using Gradle](UsingGradle.md#creating-the-jar-file).
 2. Tag the repo with the version number. e.g. `v0.1`
 3. [Create a new release using GitHub](https://help.github.com/articles/creating-releases/)
    and upload the JAR file you created.

### 6.5. Converting Documentation to PDF format

We use [Google Chrome](https://www.google.com/chrome/browser/desktop/) for converting documentation to PDF format,
as Chrome's PDF engine preserves hyperlinks used in webpages.

Here are the steps to convert the project documentation files to PDF format.

 1. Make sure you have set up GitHub Pages as described in [UsingGithubPages.md](UsingGithubPages.md#setting-up).
 2. Using Chrome, go to the [GitHub Pages version](UsingGithubPages.md#viewing-the-project-site) of the
    documentation file. <br>
    e.g. For [UserGuide.md](UserGuide.md), the URL will be `https://<your-username-or-organization-name>.github.io/myPotato-level4/docs/UserGuide.html`.
 3. Click on the `Print` option in Chrome's menu.
 4. Set the destination to `Save as PDF`, then click `Save` to save a copy of the file in PDF format. <br>
    For best results, use the settings indicated in the screenshot below. <br>
    <img src="images/chrome_save_as_pdf.png" width="300"><br>
    _Figure 5.4.1 : Saving documentation as PDF files in Chrome_

### 6.6. Managing Dependencies

A project often depends on third-party libraries. For example, myPotato depends on the
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>
a. Include those libraries in the repo (this bloats the repo size)<br>
b. Require developers to download those libraries manually (this creates extra work for developers)<br>


## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | user | edit a task | make changes if necessary
`* * *` | user | add a task | know what I need to do
`* * *` | user | delete a task | remove entries that I no longer need
`* * *` | user | see the list of tasks | track what I have done and not done
`* * *` | user | add a priority to a task | know what needs to be done first
`* * *` | user | sort a task by its date |  find the latest task easily
`* * *` | user | have a task reminder | know when the deadline for a task is
`* * *` | user | have a back up of my tasks | retrieve tasks which I accidentally deleted
`* * *` | user | have a command list | know what command format to use and follow
`* * *` | user | have a list of overdue tasks | track tasks that I have yet to complete.
`* * *` | user | undo a change | undo previous add/ delete/ clear command
`* *` | user | see a list of tasks I have done in a certain day | track the progress
`* *` | user | duplicate a task | duplicate a task conveniently
`* *` | user | delete many tasks at once | save time
`* *` | user | pin my tasks | take note of the most important tasks
`* *` | user | categorize my task | find my task conveniently
`* *` | user | search task by task name| find a specific task without scrolling
`* *` | user | advanced command suggestion | know what format to follow without referring to the command list
`* *` | user | launch the application using keyboard shortcuts | access the application without using the mouse.
`* *` | user | sync with google calendar | use a calendar to schedule my tasks.
`* *` | user | have a login function | have privacy
`* *` | user | have an auto complete feature | save my time typing
`* *` | user | use the task manager anywhere | access it as and when I want.
`* *` | user | add a tag to a task | group my tasks
`*` | user | export the list of my tasks | send it to another user
`*` | user | set my profile picture | feel more personal
`*` | user | option to turn off the task reminder | choose not be prompted again after I have accomplished my task
`*` | user | customize my task manager | feel more personal
`*` | user | share my task with other colleagues | collaborate with our colleagues
`*` | user | set different frequency for my task reminder | prepare for the tasks.
`*` | user |  open my task list offline | open my task list without Internet access


## Appendix B : Use Cases

(For all use cases below, the **System** is the `myPotato` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: Add a task

**MSS**

1. User request to add a task
2. myPotato shows the list with added task

**Extension**

1a. The command is invalid

> 1a1. myPotato shows the list of command help
> Use case ends

#### Use case: Delete a task

**MSS**

1. User requests to list all tasks
2. myPotato shows a list of tasks
3. User requests to delete a specific task in the list
4. myPotato deletes the task <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. myPotato shows an error message <br>
  Use case resumes at step 2

#### Use Case: Edit tasks

**MSS**

1. User requests to list tasks
2. myPotato shows a list of tasks
3. User requests to change a specific task
4. myPotato shows the specific task
5. User makes the changes
6. myPotato saves and update changes <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given name is invalid

> 3a1. myPotato shows an error message<br>
  Use case resumes at Step 2

#### Use case: Undo previous command

**MSS**

1. User requests to show the list of all tasks
2. myPotato shows the list of tasks
3. User requests to undo a previous add/ delete/ edit/ clear command
4. myPotato update the list back to the previous version before the most recent command
Use case ends.

**Extension**

2a. No previous command found

> 2a1. myPotato shows an "No previous command found" message
  Use case resumes at step 2

#### Use case: Save TaskList

**MSS**
1. User requests to show the list of all tasks
2. myPotato shows the list of all tasks
1. User requests to save TaskList
2. myPotato validate the filename and creates a file.
Use case ends.

**Extension**

2a. The given filename is invalid

> 2a1. myPotato shows an invalid filename message
  Use case resumes at step 2

#### Use case: Open TaskList

**MSS**
1. User requests to open storage file.
2. myPotato validates, read from file before showing the list of all tasks
Use case ends.

**Extension**

2a. The given storage file is invalid

> 2a1. myPotato shows an invalid file message


## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 tasks without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands)
   should be able to accomplish most of the tasks faster using commands than using the mouse.

{More to be added}

## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

##### Private contact detail

> A contact detail that is not meant to be shared with others

#### APP
> Application, myPotato

#### API
> Application Programming Interface

## Appendix E : Product Survey

**Microsoft Outlook**

Author: Tang Di Feng

Pros:

* Email and Calendar features.
* Sync Email directly into Calendar. Jim will be able to schedule his tasks.
* Integrated search function for finding emails, contacts, date. Jim will be able to find a specific task.
* Portable
* Retrieve others' calendars for references

Cons:

* Cost. Jim may not be able to afford solely for his personal use.
* Little support to run on Linux-based system
* Too much functionalities and may be complex for users to use. Confusing for Jim.

**Wunderlist**

Author: My Duy Hoang Long

Pros:

* Subtasks can be created for each task
* Tags can be added to each task using hashtag
* Allow natural language input
* Simple and friendly UI
* Support multiple platforms
* Share list with other people. Since Jim work alone, this function might not be useful for Jim.

Cons:

* Sync process slow down the application
* Sync with Google Calendar and Outlook may not work properly

**Google Calendar**

Author : Ivan Koh

Pros

* Can be used in both online and offline mode
* can use color code to dictate the priority of each task
* Can share your tasks with others by simply inviting them
* Support multiple platforms
* Can view tasks in either daily, monthly, or even yearly mode
* Can setup reminder in Google Calender, which will be especially useful to Jim
* Allows you to repeat some events automatically
* Allows natural language input
* Allows you to jump to a specific date easily
* Can sync with other task manager inlcuding Microsoft Outlook

Cons

* Google Calender doesn't allows you to use tags to categorize your task

**HiTask**

Author: Zhang Yan Hao

Pros:

* HiTask has data backup, I think this feature is suitable for Jim,
* since Jim is a forgetful person, he might delete some important task accidentally.
* HiTask can sync with google calendar, I think this is useful since Jim may have plenty tasks,
* he might need to rely heavily on google calendar to help him organize his schedule.
* HiTask is easy to use, available in office, at home and on the road. I think this is useful for Jim,
* Jim is a busy man, so he might use his free time like on his way to work or back home to check his schedule.

Cons:

* HiTask is a team project management and task collaboration, I think this feature is not suitable for Jim,
  since Jim usually work alone.
* From the analysis, HiTask is suitable for Jim beside that this is a collaboration tool for group work.
