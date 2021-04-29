import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public abstract class Appointment {
	//Data Members for Appointment
	private Patient recipient;
	private String recipentEmail;
	private String vaccinationCenter;
	private String vaccinationDate;
	//No args constructor for Appointment
	
	public Appointment() {

	}
	//Constructor for Appointment with parameters that initializes all Data Members
	
	public Appointment(Patient rn, String re, String vc, String vd) {
		recipient = rn;
		recipentEmail = re;
		vaccinationCenter = vc;
		vaccinationDate = vd;

	}
	//Getters and Setters for Appointment
	
	public Patient getRecipientName() {
		return recipient;
	}


	public void setRecipientName(Patient recipient) {
		this.recipient = recipient;
	}


	public String getRecipentEmail() {
		return recipentEmail;
	}


	public void setRecipentEmail(String recipentEmail) {
		this.recipentEmail = recipentEmail;
	}


	public String getVaccinationCenter() {
		return vaccinationCenter;
	}


	public void setVaccinationCenter(String vaccinationCenter) {
		this.vaccinationCenter = vaccinationCenter;
	}


	public String getVaccinationDate() {
		return vaccinationDate;
	}


	public void setVaccinationDate(String vaccinationDate) {
		this.vaccinationDate = vaccinationDate;
	}
}




