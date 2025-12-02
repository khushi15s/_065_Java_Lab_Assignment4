import java.io.*;
import java.util.*;

class Student {
    int roll;
    String name;
    String email;
    String course;
    double marks;

    Student(int r, String n, String e, String c, double m) {
        roll = r;
        name = n;
        email = e;
        course = c;
        marks = m;
    }

    public String toString() {
        return roll + "," + name + "," + email + "," + course + "," + marks;
    }
}

class FileUtil {
    public static ArrayList<Student> readStudents(String file) {
        ArrayList<Student> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] a = line.split(",");
                int r = Integer.parseInt(a[0]);
                String n = a[1];
                String e = a[2];
                String c = a[3];
                double m = Double.parseDouble(a[4]);
                list.add(new Student(r, n, e, c, m));
            }
        } catch (Exception e) {}
        return list;
    }

    public static void writeStudents(String file, ArrayList<Student> list) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Student s : list) {
                bw.write(s.toString());
                bw.newLine();
            }
        } catch (Exception e) {}
    }
}

class StudentManager {
    ArrayList<Student> students;

    StudentManager(ArrayList<Student> list) {
        students = list;
    }

    void addStudent() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Roll No: ");
        int r = Integer.parseInt(sc.nextLine());
        System.out.print("Enter Name: ");
        String n = sc.nextLine();
        System.out.print("Enter Email: ");
        String e = sc.nextLine();
        System.out.print("Enter Course: ");
        String c = sc.nextLine();
        System.out.print("Enter Marks: ");
        double m = Double.parseDouble(sc.nextLine());
        students.add(new Student(r, n, e, c, m));
    }

    void viewStudents() {
        Iterator<Student> it = students.iterator();
        while (it.hasNext()) {
            Student s = it.next();
            System.out.println("Roll No: " + s.roll);
            System.out.println("Name: " + s.name);
            System.out.println("Email: " + s.email);
            System.out.println("Course: " + s.course);
            System.out.println("Marks: " + s.marks);
        }
    }

    void searchByName() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Name to search: ");
        String name = sc.nextLine();
        boolean found = false;
        for (Student s : students) {
            if (s.name.equalsIgnoreCase(name)) {
                System.out.println("Roll No: " + s.roll);
                System.out.println("Name: " + s.name);
                System.out.println("Email: " + s.email);
                System.out.println("Course: " + s.course);
                System.out.println("Marks: " + s.marks);
                found = true;
            }
        }
        if (!found) System.out.println("Student not found");
    }

    void deleteByName() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Name to delete: ");
        String name = sc.nextLine();
        students.removeIf(s -> s.name.equalsIgnoreCase(name));
    }

    void sortByMarks() {
        students.sort((a, b) -> Double.compare(b.marks, a.marks));
        System.out.println("Sorted Student List by Marks:");
        viewStudents();
    }

    void randomAccessRead() {
        try {
            RandomAccessFile raf = new RandomAccessFile("students.txt", "r");
            raf.seek(0);
            System.out.println(raf.readLine());
            raf.close();
        } catch (Exception e) {}
    }
}

public class Management {
    public static void main(String[] args) {
        String file = "students.txt";
        ArrayList<Student> list = FileUtil.readStudents(file);

        System.out.println("Loaded students from file:");
        for (Student s : list) {
            System.out.println("Roll No: " + s.roll);
            System.out.println("Name: " + s.name);
            System.out.println("Email: " + s.email);
            System.out.println("Course: " + s.course);
            System.out.println("Marks: " + s.marks);
        }

        StudentManager sm = new StudentManager(list);
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("===== Capstone Student Menu =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search by Name");
            System.out.println("4. Delete by Name");
            System.out.println("5. Sort by Marks");
            System.out.println("6. Save and Exit");
            System.out.print("Enter choice: ");
            int ch = Integer.parseInt(sc.nextLine());

            if (ch == 1) sm.addStudent();
            else if (ch == 2) sm.viewStudents();
            else if (ch == 3) sm.searchByName();
            else if (ch == 4) sm.deleteByName();
            else if (ch == 5) sm.sortByMarks();
            else if (ch == 6) {
                FileUtil.writeStudents(file, list);
                break;
            }
        }
    }
}