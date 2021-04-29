import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * CMS 270 Group Project
 * 
 * @author Matthew S
 * @author Ben Mann
 * @author Angelina K
 * @author Cruse S
 *
 * April 19 2021
 *
 */
public class App {
	public static void main(String[] args) {
		Patient user = login();
		System.out.println("Welcome, " + user.getPatientName() + "!");
		mainMenu(user);
	}
	//Initializes a newUser and returns it
	public static Patient login() {
		LoginDatabase loginMenu = LoginDatabase.createDatabase();
		Patient newUser = loginMenu.login();
		return newUser;
	}
	//Main Menu Class
	public static void mainMenu(Patient p) {
		System.out.println(printMenu());
		System.out.println("Please enter what you would like to do.");
		Scanner s = new Scanner(System.in);
		if (!s.hasNextInt()) {
			System.out.println("Please enter a number.");
			mainMenu(p);
		} else {
			int choice = s.nextInt();
			switch (choice) {
			case 1:
				p.appointment();
				mainMenu(p);
				break;
			case 2:
				cancel(p);
				mainMenu(p);
				break;
			case 3:
				Pfizer newVac = new Pfizer();
				newVac.vaccinate(p);
				mainMenu(p);
				break;
			case 4:
				createCharts();
				mainMenu(p);
				break;
			case 5:
				createOccupationGraph();
				mainMenu(p);
				break;
			case 6:
				printAppointmentGraph();
				mainMenu(p);
				break;
			case 7:
				sendReminders();
				mainMenu(p);
				break;
			case 8:
				System.exit(0);
			default:
				System.out.println(printMenu());
				mainMenu(p);
			}

		}
	}
	//Prints a  list of the menu options
	private static String printMenu() {
		String Print=" ";
		StringBuilder sb = new StringBuilder();
		sb.append("Please enter a number between 1 and 7");
		sb.append("\n");
		sb.append("    1: Schedule Appointments");
		sb.append("\n");
		sb.append("    2: Cancel Appointments");
		sb.append("\n");
		sb.append("    3: Vaccinate");
		sb.append("\n");
		sb.append("    4: Print Ocupation Charts");
		sb.append("\n");
		sb.append("    5: Print Ocupation Graph");
		sb.append("\n");
		sb.append("    6: Print Graph of Current Appointments");
		sb.append("\n");
		sb.append("    7: Send Reminders for Appointments");
		sb.append("\n");
		sb.append("    8: Close Program");
		Print=sb.toString();
		return Print;
	}
	//Intializes the database and Calls Methods to Create the Occupation Chart
	private static void createCharts() {
		LoginDatabase loginMenu = LoginDatabase.createDatabase();
		ArrayList<Key> patients = loginMenu.getDatabase();
		printInTable(patients);
	}
	//Initializes the database and Calls Methods to Create the Occupation Graph
	private static void createOccupationGraph() {
		LoginDatabase loginMenu = LoginDatabase.createDatabase();
		ArrayList<Key> patients = loginMenu.getDatabase();
		printOccupationGraph(patients);
	}
	//Initializes ArrayLists for the different occupations and calls the methods to create the table
	private static void printInTable(ArrayList<Key> patients) {
		ArrayList<Patient> hr = new ArrayList<Patient>();
		ArrayList<Patient> fr = new ArrayList<Patient>();
		ArrayList<Patient> dw = new ArrayList<Patient>();
		System.out.println("\nCurrent Applicable Occupations");
		createRiskTable(patients, hr);
		createFirstResponderTable(patients, fr);
		createDesignatedWorkerTable(patients, dw);
	}
	//Initializes ArrayLists for the different occupations and calls the methods to create the graph
	private static void printOccupationGraph(ArrayList<Key> patients) {
		ArrayList<Patient> hr = new ArrayList<Patient>();
		ArrayList<Patient> fr = new ArrayList<Patient>();
		ArrayList<Patient> dw = new ArrayList<Patient>();
		System.out.println("\nCurrent Applicable Occupations");
		createRiskGraph(patients, hr);
		createFirstResponderGraph(patients, fr);
		createDesignatedWorkerGraph(patients, dw);
		PrintNumbers();
	}
	//Creates High Risk Table Line
	private static void createRiskTable(ArrayList<Key> patients, ArrayList<Patient> hr) {
		for (int i = 0; i < patients.size(); i++) {
			if (patients.get(i).getValue().isHighRisk().equalsIgnoreCase("yes")) {
				hr.add(patients.get(i).getValue());
			}
		}
		System.out.print("  HighRisk          | ");
		if (hr.size() == 0) {
			System.out.print("There is no one here.");
		} else {
			for (int x = 0; x < hr.size(); x++) {
				System.out.print(hr.get(x).getPatientName() + ", ");
			};
		}
		System.out.println();
	}
	//Creates High Risk Graph Line
	private static void createRiskGraph(ArrayList<Key> patients, ArrayList<Patient> hr) {
		for (int i = 0; i < patients.size(); i++) {
			if (patients.get(i).getValue().isHighRisk().equalsIgnoreCase("yes")) {
				hr.add(patients.get(i).getValue());
			}
		}
		System.out.print("  HighRisk          | ");
		if (hr.size() == 0) {
			System.out.print("");
		} else {
			for (int x = 0; x < hr.size(); x++) {
				System.out.print("x");
			};
		}
		System.out.println();
	}
	//Creates First Responder Table Line
	private static void createFirstResponderTable(ArrayList<Key> patients, ArrayList<Patient> fr) {
		for (int i = 0; i < patients.size(); i++) {
			if (patients.get(i).getValue().isFirstResponder().equalsIgnoreCase("yes")) {
				fr.add(patients.get(i).getValue());
			}
		}
		System.out.print("  FirstResponder    | ");
		if (fr.size() == 0) {
			System.out.print("There is no one here.");
		} else {
			for (int x = 0; x < fr.size(); x++) {
				System.out.print(fr.get(x).getPatientName() + ", ");
			};
		}
		System.out.println();
	}
	//Creates First Responder Graph Line
	private static void createFirstResponderGraph(ArrayList<Key> patients, ArrayList<Patient> fr) {
		for (int i = 0; i < patients.size(); i++) {
			if (patients.get(i).getValue().isFirstResponder().equalsIgnoreCase("yes")) {
				fr.add(patients.get(i).getValue());
			}
		}
		System.out.print("  FirstResponder    | ");
		if (fr.size() == 0) {
			System.out.print("");
		} else {
			for (int x = 0; x < fr.size(); x++) {
				System.out.print("x");
			};
		}
		System.out.println();
	}
	//Creates Designated Worker Table Line
	private static void createDesignatedWorkerTable(ArrayList<Key> patients, ArrayList<Patient> dw) {
		for (int i = 0; i < patients.size(); i++) {
			if (patients.get(i).getValue().isDesignatedWorker().equalsIgnoreCase("yes")) {
				dw.add(patients.get(i).getValue());
			}
		}
		System.out.print("  DesignatedWorker  | ");
		if (dw.size() == 0) {
			System.out.print("There is no one here.");
		} else {
			for (int x = 0; x < dw.size(); x++) {
				System.out.print(dw.get(x).getPatientName() + ", ");
			};
		}
		System.out.println();
	}
	//Creates Designated Worker Graph Line
	private static void createDesignatedWorkerGraph(ArrayList<Key> patients, ArrayList<Patient> dw) {
		for (int i = 0; i < patients.size(); i++) {
			if (patients.get(i).getValue().isDesignatedWorker().equalsIgnoreCase("yes")) {
				dw.add(patients.get(i).getValue());
			}
		}
		System.out.print("  DesignatedWorker  | ");
		if (dw.size() == 0) {
			System.out.print("");
		} else {
			for (int x = 0; x < dw.size(); x++) {
				System.out.print("x");
			};
		}
		System.out.println();
	}
	//Reads data in firstappointment.txt and stores it in FirstAppointments
	public static ArrayList<String> readFirstAppointmentData() {
		ArrayList<String> FirstAppointments = new ArrayList<String>();
		try {
			File f1 = new File("firstappointment.txt");
			Scanner input = new Scanner(f1);
			while(input.hasNextLine()) {
				String s1= input.nextLine();
				String[] parts = s1.split("\n");
				FirstAppointments.add(parts[0]);	
			}
			input.close();
		}

		catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			e.printStackTrace();
		}
		return FirstAppointments;
	}
	//Reads data in secondappointment.txt and stores it in SecondAppointments
	public static ArrayList<String> readSecondAppointmentData() {
		ArrayList<String> SecondAppointments = new ArrayList<String>();
		try {
			File f1 = new File("secondappointment.txt");
			Scanner input = new Scanner(f1);
			while(input.hasNextLine()) {
				String s1= input.nextLine();
				String[] parts = s1.split("\n");
				SecondAppointments.add(parts[0]);	
			}
			input.close();
		}

		catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			e.printStackTrace();
		}
		return SecondAppointments;
	}
	//Simulates sending reminders for appointments
	public static void sendReminders() {
		ArrayList<String> printFirst =readFirstAppointmentData();
		ArrayList<String> printSecond =readSecondAppointmentData();
		for(int i =0; i<printFirst.size();i++){
			if(printFirst.get(i).equals("&")) {
				printFirst.add(i," ");
			}
			System.out.print("Sending Reminder to ");
			System.out.println(printFirst.get(i));
		}
		for(int i =0; i<printSecond.size();i++){
			if(printSecond.get(i).equals("&")) {
				printSecond.add(i," ");
			}
			System.out.print("Sending Reminder to ");
			System.out.println(printSecond.get(i));
		}

	}
	//Prints First Appointment Graph Line
	public static void PrintFirstAppointmentGraph() {
		System.out.println("          Type                Amount of First and Second Appointments");
		ArrayList<String> print =readFirstAppointmentData();
		System.out.print("  FirstAppointment  | ");
		if(print.size()==0) {
			System.out.print(" ");
		}else {
			for(int i =0; i<print.size();i++){
				System.out.print("x");
			}
		}System.out.println("");
	}
	//Prints Second Appointment Graph Line
	public static void PrintSecondAppointmentGraph() {
		ArrayList<String> print =readSecondAppointmentData();
		System.out.print("  SecondAppointment | ");
		if(print.size()==0) {
			System.out.print(" ");
		}else {
			for(int i =0; i<print.size();i++){
				System.out.print("x");
			}System.out.println("");
		}
	}
	//Prints Number axis of the graph
	public static void PrintNumbers() {
		System.out.print("                     ");
		for (int x = 0; x < 52; x++) {
			System.out.print("-");
		};
		System.out.print("\n"+"                     ");
		for (int x = 0; x < 31; x++) {
			System.out.print(x);
		};
		System.out.println("\n"+"                                           "+"Quantity");
		System.out.println();
	}
	//Call the methods to Print the Appointment Graph
	public static void printAppointmentGraph() {
		PrintFirstAppointmentGraph();
		PrintSecondAppointmentGraph();
		PrintNumbers();
	}
	//Prompts user to enter 1 or 2 then cancels first or second appointment
	public static void cancel(Patient p) {
		System.out.println("Please enter 1 if you want to cancel the first appointment and 2 if you would like cancel 2nd appointment.");
		Scanner s = new Scanner(System.in);
		if (!s.hasNextInt()) {
			cancel(p);
		} else {
			int choice = s.nextInt();
			if (choice == 1) {
				FirstAppointment temp = new FirstAppointment();
				temp.cancel(p);
			}
			else if (choice == 2) {
				SecondAppointment temp = new SecondAppointment();
				temp.cancel(p);
			}
		}
	}
}
