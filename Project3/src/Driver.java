import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;

public class Driver {

	public static void main(String[] args) throws Exception {
		College c = new College();
		
		Menu menu = new Menu();
	
		System.out.println("\t\t\t\t\t\tWelcome to my Personal Management Program\n");
		System.out.println("Choose one of the options:");
		
		while (true) {
			String option = menu.MainMenu();
			
			 switch(option) {
			case "1":
				c.addFal();		
				break;
			case "2":
				c.addStd();
				break;
			case "3":
				c.printTuition();
				break;
			case "4":
				c.printFal();
				break;
			case "5":
				c.addStaff();
				break;
			case "6":
				c.printStaff();
				break;
			case "7":
				System.out.print("Would you like to create the report? (Y/N): ");
				String suboption = (new Scanner(System.in)).next();
				if (suboption.toLowerCase().equals("y")){
					c.report();
					System.out.println("Report created!");
				}
				System.out.println("Goodbye!");
				System.exit(0);
			default:
				System.out.println("Invalid entry - please try again");
				break;
				
			}			
		}

	}

}

//_________________________________________________________________________________________________________________________________

abstract class Person {
	
	protected String fullname;
	protected String ID;
	
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	
	public Person(String fullname, String iD) {
		this.fullname = fullname;
		ID = iD;
	}
	
	public Person() {
		fullname = "Not set yet";
		ID = "Not set yet";
	}
	
	@Override
	public abstract String toString();
	
	public abstract void print();
	
}

//_________________________________________________________________________________________________________________________________

class Student extends Person{
	
	protected double gpa;
	protected int creHrs;
	
	public double getGpa() {
		return gpa;
	}

	public void setGpa(double gpa) {
		this.gpa = gpa;
	}

	public int getCreHrs() {
		return creHrs;
	}

	public void setCreHrs(int creHrs) {
		this.creHrs = creHrs;
	}

	public Student(String fullname, String iD, double gpa, int creHrs) {
		super(fullname, iD);
		this.gpa = gpa;
		this.creHrs = creHrs;
	}

	public Student() {
		super("Not set yet", "Not set yet");
		gpa = 0.0;
		creHrs = 0;
	}
	
	public void print() {
		final double tuitionRate = 236.45;
		final double fees = 52.0;
		double tuition = 0.0;
		double discount = 0;
		
		System.out.println("Here is the tuition invoice for " + fullname + ":\n");
		System.out.println("-----------------------------------------------------------------------");
			
		System.out.println(fullname + "\t\t" + ID);
		System.out.print("Credit Hours: " + creHrs);
		
		tuition = (creHrs * tuitionRate) + fees;
		
		if(gpa >= 3.85) {
			discount = tuition * 0.25;
			tuition -= discount;
		}
		
		System.out.printf(" ($%.2f/credit hour)\n", tuitionRate);
		
		System.out.println("Fees: $" + fees);
		System.out.println();
		
		System.out.printf("Total payment (after discount): $%.2f", tuition);
		System.out.printf("\t($%.2f discount applied)\n", discount);
		System.out.println("-----------------------------------------------------------------------");
	}
	
	public String toString() {
		return super.getFullname() + "\n\tID: " + super.getID() + "\n\tGPA: " + getGpa() + "\n\tCredit hours: " + getCreHrs();
	}
}

//_________________________________________________________________________________________________________________________________

abstract class Employee extends Person{
	protected String dpt;
	
	public String getDpt() {
		return dpt;
	}

	public void setDpt(String dpt) {
		this.dpt = dpt;
	}
	
	public Employee(String fullname, String iD, String dpt) {
		super(fullname, iD);
		this.dpt = dpt;
	}
	
	public Employee() {
		super("Not set yet", "Not set yet");
		dpt = "Not set yet";
	}
	
	public abstract void print();
}

//_________________________________________________________________________________________________________________________________

class Faculty extends Employee{
	protected String rank;

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public Faculty(String fullname, String iD, String dpt, String rank) {
		super(fullname, iD, dpt);
		this.rank = rank;
	}
	
	public Faculty() {
		super("Not set yet", "Not set yet", "Not set yet");
		rank = "Not set yet";
	}

	public void print() {
		System.out.println("-----------------------------------------------------------------------");
		System.out.println(fullname + "\t\t" + ID);
		System.out.println(dpt + " Department, " + rank);
		System.out.println("-----------------------------------------------------------------------");
	}
	
	public String toString() {
		return super.getFullname() + "\n\tID: " + super.getID() + "\n\t" + getRank() + ", " + super.getDpt();
	}
}

//_________________________________________________________________________________________________________________________________

class Staff extends Employee{
	protected String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Staff(String fullname, String iD, String dpt, String status) {
		super(fullname, iD, dpt);
		this.status = status;
	}
	
	public Staff() {
		super("Not set yet", "Not set yet", "Not set yet");
		status = "Not set yet";
	}

	public void print() {
		System.out.println("-----------------------------------------------------------------------");
		System.out.println(fullname + "\t\t" + ID);
		System.out.println(dpt + " Department, " + getStatus());
		System.out.println("-----------------------------------------------------------------------");
	}
	
	public String toString() {
		return super.getFullname() + "\n\tID: " + super.getID() + "\n\t" + super.getDpt() + ", " + getStatus();
		
	}
}

//_________________________________________________________________________________________________________________________________

class IdException extends Exception{
	public IdException() {
		System.out.println("\t\tInvalid ID format. Must be LetterLetterDigitDigitDigitDigit");	
	}
}

//_________________________________________________________________________________________________________________________________

class College {
	
	private Person [] employee;
	
	public College() {
		employee = new Person[100];
		
		for(int i = 0; i < 100; i++) {
			employee[i] = null;
		}
	}
	
	public void addStd() throws IdException{
		boolean check1 = true;
		System.out.println("Enter  the student info:");
		
		System.out.print("\t\tName of Student: ");
		String name = new Scanner(System.in).nextLine();

		String ID = "";
		boolean check = true;
		boolean tryAgain = true;
		while(tryAgain) {
			try {
				tryAgain = false;
				System.out.print("\t\tID: ");
				ID = (new Scanner (System.in)).next();
			
				check = Pattern.matches("[a-zA-Z][a-zA-Z][0-9][0-9][0-9][0-9]", ID);

				if (check == false)
					throw new IdException();
				}catch(IdException c) {
					tryAgain = true;
				}
		}
		
		tryAgain = true;
		double gpa = 0;
		while(tryAgain) {
			try {
				tryAgain = false;
				System.out.print("\t\tGPA: ");
				gpa = (new Scanner (System.in)).nextDouble();
			}
			catch (InputMismatchException excpt){
				tryAgain = true;
				System.out.println("\t\tWrong input. Please try again!");
			}
		}
		
		System.out.print("\t\tCredit Hours: ");
		int creHrs = (new Scanner (System.in)).nextInt();
		
		for(int index = 0; index < 100; index++) {
			if(employee[index] == null){
				employee[index] = new Student(name, ID, gpa, creHrs);
				break;
			}
		}
		
		System.out.println();
		System.out.println("\tStudents added!");
	}
		
	
	
	public void addFal() throws IdException{		
		System.out.println("Enter faculty info:");
		
		System.out.print("\t\tName of the faculty: ");
		String name = (new Scanner(System.in)).nextLine();
		
		String ID = "";
		boolean check = true;
		boolean tryAgain = true;
		while(tryAgain) {
			try {
				tryAgain = false;
				System.out.print("\t\tID: ");
				ID = (new Scanner (System.in)).next();
			
				check = Pattern.matches("[a-zA-Z][a-zA-Z][0-9][0-9][0-9][0-9]", ID);

				if (check == false)
					throw new IdException();
				}catch(IdException c) {
					tryAgain = true;
				}
		}
		
		System.out.print("\t\tRank: ");
		String rank = (new Scanner(System.in)).nextLine();
		
		while (true) {
			if (rank.equalsIgnoreCase("professor") || rank.equalsIgnoreCase("adjunct") ) {
				break;
			}
			else {
				System.out.println("\t\t     \"" + rank + "\" is invalid");
				System.out.print("\t\tRank: ");
				rank = (new Scanner(System.in)).nextLine();
			}
		}
		
		System.out.print("\t\tDepartment: ");
		String dpt = (new Scanner(System.in)).nextLine();
		
		while (true) {
			if(dpt.equalsIgnoreCase("mathematics") || 
			   dpt.equalsIgnoreCase("engineering") || 
			   dpt.equalsIgnoreCase("english"))
				break;
			else {
				System.out.println("\t\t     \"" + dpt + "\" is invalid");
				System.out.print("\t\tDepartment: ");
				dpt = (new Scanner(System.in)).nextLine();
			}
			
		}
		
		for(int index = 0; index < 100; index++) {
			if(employee[index] == null){
				employee[index] = new Faculty(name, ID, dpt, rank);
				break;
			}
		}
		
		System.out.println();
		System.out.println("\tFalculty successfully added");
	}
	
	public void addStaff() throws IdException{
		System.out.print("\t\tName of the staff member: ");
		String name = (new Scanner(System.in)).nextLine();
		
		String ID = "";
		boolean check = false;
		boolean tryAgain = true;
		while(tryAgain) {
			try {
				tryAgain = false;
				System.out.print("\t\tID: ");
				ID = (new Scanner (System.in)).next();
			
				check = Pattern.matches("[a-zA-Z][a-zA-Z][0-9][0-9][0-9][0-9]", ID);

				if (check == false)
					throw new IdException();
				}catch(IdException c) {
					tryAgain = true;
				}
		}
		
		System.out.print("\t\tDepartment: ");
		String dpt = (new Scanner(System.in)).nextLine();
		
		while (true) {
			if(dpt.equalsIgnoreCase("mathematics") || 
			   dpt.equalsIgnoreCase("engineering") || 
			   dpt.equalsIgnoreCase("english")) 
				break;
			else {
				System.out.println("\t\t     \"" + dpt + "\" is invalid");
				System.out.print("\t\tDepartment: ");
				dpt = (new Scanner(System.in)).nextLine();
			}
			
		}
		
		System.out.print("\t\tStatus, Enter P for Part Time or Enter F for Full Time: ");
		String status = (new Scanner(System.in)).nextLine();
		if(status.toLowerCase().equals("p"))
			status = "Part time";
		if(status.toLowerCase().equals("f"))
			status = "Full time";
		
		for(int index = 0; index < 100; index++) {
			if(employee[index] == null){
				employee[index] = new Staff(name, ID, dpt, status);
				break;
			}
		}
		
		System.out.println();
		System.out.println("\tStaff successfully added");
	}
	
	private int search(String id) {
		int index;
		
		for(index = 0; index < 100; index++) {
			if(employee[index] != null) {
				if(employee[index].getID().equalsIgnoreCase(id))
					return index;
			}
		}
		
		return -1;
	}
	
	public void printTuition() {
		String id;

		System.out.print("Enter the Student's ID: ");
		
		id = new Scanner(System.in).nextLine();
		
		int index = search(id);
		
		if(index == -1 || !(employee[index] instanceof Student)) System.out.println("No student matched!");
		else employee[index].print();
	}
	
	public void printFal() {
		String id;

		System.out.print("Enter the Faculty's ID: ");
		
		id = new Scanner(System.in).nextLine();
		
		int index = search(id);
		
		if(index == -1 || !(employee[index] instanceof Faculty)) System.out.println("No faculty matched!");
		else employee[index].print();
	}
	
	public void printStaff() {
		String id;

		System.out.print("Enter the Staff's ID: ");
		
		id = new Scanner(System.in).nextLine();
		
		int index = search(id);
		
		if(index == -1 || !(employee[index] instanceof Staff)) System.out.println("No staff matched!");
		else employee[index].print();
	}
	
	public void report() throws Exception{
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		
		PrintStream outFile = new PrintStream (new File("report.txt"));
		
		outFile.println("\t\t\tReport created on " + formatter.format(date));
		outFile.println("\t\t\t****************************" + "\n");
		
		int count = 1;
		
		outFile.println("Faculty Members");
		outFile.println("---------------");
		for (int index = 0; index < 100; index++) {
			if(employee[index] != null && employee[index] instanceof Faculty) {
				outFile.println("\t" + (count++) + ". " + employee[index]);
				outFile.println();
				}
			}
		outFile.println();
		
		count = 1;
		
		outFile.println("Staff Members");
		outFile.println("-------------");
		for (int index = 0; index < 100; index++) {
			if(employee[index] != null && employee[index] instanceof Staff) {
				outFile.println("\t" + (count++) + ". " + employee[index]);
				outFile.println();
				}
			}
		outFile.println();
		
		count = 1;
		
		outFile.println("Students");
		outFile.println("--------");
		for (int index = 0; index < 100; index++) {
			
			if(employee[index] != null && employee[index] instanceof Student) {
				outFile.println("\t" + (count++) + ". " + employee[index]);
				outFile.println();
				}
		}
		
		outFile.close();
	}
}


//_________________________________________________________________________________________________________________________________

class Menu {

	public String MainMenu() {
		
		System.out.println("1- Enter the information of a faculty");
		System.out.println("2- Enter the information of a student");
		System.out.println("3- Print tuition invoice");
		System.out.println("4- Print faculty information");
		System.out.println("5- Enter the information of a staff member");
		System.out.println("6- Print the information of a staff member");
		System.out.println("7- Exit Program ");
		System.out.print("\t Enter your selection: ");
		
		String option = new Scanner(System.in).nextLine();
		
		return option;
	}
}

