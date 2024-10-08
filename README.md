# ToDo-App

## 1. Introduction
- The TodoApp is a product designed for efficient personal task management, offering a range of features such as inserting, editing, deleting, sorting, and grouping tasks according to their status.
- Users can flexibly create custom categories tailored to their specific needs.
- Displaying tasks based on due dates enhances user convenience in managing time effectively.

## 2. Technologies
- Language: Kotlin
- Room Database
- MVVM Architecture
- ViewModel, LiveData, ViewBinding
- TabLayout, ViewPager2
- ...

## 3. Demo App
### a. Splash Screen
<table>
  <tr>
    <td>
      <img src="https://github.com/gxdumplingg/ReadMe.Image/blob/main/splash_screen.png" style="width:300px; height:680px;">
    </td>
  </tr>
</table>

### b. Home Screen
- Home screen contains: progress bar, category list, in progress task list:
<table>
  <tr>
    <td>
      <img src="https://github.com/gxdumplingg/ReadMe.Image/blob/main/home_screen.png" style="width:300px; height:680px;">
    </td>
  </tr>
</table>

- When choosing **View All** in Home Screen:
 <table>
  <tr>
    <td>
      <img src="https://github.com/gxdumplingg/ReadMe.Image/blob/main/view_all_categories.png" style="width:300px; height:680px;">
    </td>
  </tr>
</table>

- **Adding New Category** by selecting the Add button:
<table>
  <tr>
    <td> <img src="https://github.com/gxdumplingg/ReadMe.Image/blob/main/add_new_category.png" style="width:300px; height:680px;"></td>
  </tr>
</table>

- **View Task List grouped by Category** and **View Detailed Category** by selecting the More button and tapping on the category respectively:
<table>
  <tr>
    <td><img src="https://github.com/gxdumplingg/ReadMe.Image/blob/main/view_category_task_list.png" style="width:300px; height:680px;"></td>
    <td><img src="https://github.com/gxdumplingg/ReadMe.Image/blob/main/view_detailed_category.png" style="width:300px; height:680px;"></td>
  </tr>
</table>

### b. Calendar Screen
- When picking a date in the caledar, tasks with the appropriate due date will be displayed below:
<table>
  <tr>
    <td><img src="https://github.com/gxdumplingg/ReadMe.Image/blob/main/calendar_view.png" style="width:300px; height:680px;"></td>
  </tr>
</table>

### c. Task Screen
- Allow to view all tasks, sort tasks by due dates, or view tasks grouped by status. Tasks are divided by 3 statuses, including: **To Do**, **In Progress** and **Done**.
<table>
  <tr>
    <td><img src="https://github.com/gxdumplingg/ReadMe.Image/blob/main/view_all_tasks.png" ></td>
    <td><img src="https://github.com/gxdumplingg/ReadMe.Image/blob/main/view_to_do_tasks.png" ></td>
    <td><img src="https://github.com/gxdumplingg/ReadMe.Image/blob/main/view_in_progress_tasks.png" ></td>
    <td><img src="https://github.com/gxdumplingg/ReadMe.Image/blob/main/view_done_tasks.png" ></td>
  </tr>
</table>
- Tasks can also be displayed in grid layout.
<table>
  <tr>
    <td><img src="https://github.com/gxdumplingg/ReadMe.Image/blob/main/view_grid_layout.png" style="width:300px; height:680px;"></td>
  </tr>
</table>

- When tapping on a task, detailed information of a task will be displayed on screen. Additionally, editting and deleting task are also allowed.
<table>
  <tr>
    <td><img src="https://github.com/gxdumplingg/ReadMe.Image/blob/main/view_detailed_task.png" style="width:300px; height:680px;"></td>
  </tr>
</table>


### d. Recently Deleted Screen
- All deleted tasks will be moved into Recently Deleted Screen and can be restored.
<table>
  <tr>
    <td><img src="https://github.com/gxdumplingg/ReadMe.Image/blob/main/recently_deleted_no_task.png" ></td>
    <td><img src="https://github.com/gxdumplingg/ReadMe.Image/blob/main/view_recently_deleted.png" ></td>
    <td><img src="https://github.com/gxdumplingg/ReadMe.Image/blob/main/restore_task.png" ></td>
  </tr>
</table>



