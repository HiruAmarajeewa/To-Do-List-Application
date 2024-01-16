import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class Task {
    String name;
    String dueDate;
    int priority;

    public Task(String name, String dueDate, int priority) {
        this.name = name;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return String.format("Task: %-20s Due Date: %-15s Priority: %d", name, dueDate, priority);
    }
}

class Node {
    Task task;
    Node next;

    public Node(Task task) {
        this.task = task;
        this.next = null;
    }
}

class ToDoList {
    Node head;

    public ToDoList() {
        this.head = null;
    }

    public void addTask(Task task) {
        Node newNode = new Node(task);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public void displayTasks() {
        List<Task> tasks = new ArrayList<>();

        Node current = head;
        while (current != null) {
            tasks.add(current.task);
            current = current.next;
        }

        Collections.sort(tasks, (task1, task2) -> Integer.compare(task1.priority, task2.priority));

        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public void displayHeader(String header) {
        System.out.println("\n=== " + header + " ===");
    }

    public void displayTasksWithHeader(String header) {
        displayHeader(header);
        displayTasks();
    }

    public void removeTaskByPriority(int priority) {
        Node current = head;
        Node prev = null;

        while (current != null && current.task.priority != priority) {
            prev = current;
            current = current.next;
        }

        if (current != null) {
            if (prev != null) {
                prev.next = current.next;
            } else {
                head = current.next;
            }
            System.out.println("Task with priority " + priority + " removed successfully!");
        } else {
            System.out.println("No task with priority " + priority + " found!");
        }
    }

    public void markTaskAsCompletedByPriority(int priority) {
        Node current = head;

        while (current != null && current.task.priority != priority) {
            current = current.next;
        }

        if (current != null) {
            current.task.name = "[Completed] " + current.task.name;
            System.out.println("Task with priority " + priority + " marked as completed!");
        } else {
            System.out.println("No task with priority " + priority + " found!");
        }
    }

    public void displayTasksByPriority(int priority) {
        Node current = head;
        boolean found = false;

        while (current != null) {
            if (current.task.priority == priority) {
                System.out.println(current.task);
                found = true;
            }
            current = current.next;
        }

        if (!found) {
            System.out.println("No tasks with priority " + priority + " found.");
        }
    }

    public void displayMenu() {
        System.out.println("\n===== To-Do List Menu =====");
        System.out.println("1. Add Task");
        System.out.println("2. Display Tasks");
        System.out.println("3. Remove Task by Priority");
        System.out.println("4. Mark Task as Completed by Priority");
        System.out.println("5. Display Tasks by Priority");
        System.out.println("6. Exit");
    }
}

public class ToDoListApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ToDoList toDoList = new ToDoList();

        while (true) {
            toDoList.displayMenu();
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter task name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter due date: ");
                        String dueDate = scanner.next();
                        System.out.print("Enter priority: ");
                        int priority = scanner.nextInt();
                        Task newTask = new Task(name, dueDate, priority);
                        toDoList.addTask(newTask);
                        System.out.println("Task added successfully!");
                        break;
                    case 2:
                        toDoList.displayTasksWithHeader("Tasks in the To-Do List");
                        break;
                    case 3:
                        System.out.print("Enter priority to remove task: ");
                        int priorityToRemove = scanner.nextInt();
                        toDoList.removeTaskByPriority(priorityToRemove);
                        break;
                    case 4:
                        System.out.print("Enter priority to mark task as completed: ");
                        int priorityToComplete = scanner.nextInt();
                        toDoList.markTaskAsCompletedByPriority(priorityToComplete);
                        break;
                    case 5:
                        System.out.print("Enter priority to display tasks: ");
                        int priorityToDisplay = scanner.nextInt();
                        toDoList.displayTasksByPriority(priorityToDisplay);
                        break;
                    case 6:
                        System.out.println("Exiting the application. Goodbye!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
    }
}
