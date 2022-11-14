package BasicClassAccessDbase;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


public class Measuring implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int measuring_ID;
	private Date date;
	private double doze;
	private double gGP;
	@ManyToOne
	private Person person;
	@ManyToOne
	private Laboratory lab;
	@ManyToOne
	private UsersWBC user;
	@ManyToOne
	private TypeMeasur typeMeasur;
	
	
	public Measuring(
			Person person,
			Date date,
			double doze,
			double gGP,
			Laboratory lab,
			UsersWBC user,
			TypeMeasur typeMeasur) {
		
		this.person = person;
		this.date = date;
		this.doze = doze;
		this.gGP = gGP;
		this.lab = lab;
		this.user = user;
		this.typeMeasur = typeMeasur;
		
	}
	
	public Measuring() {
		super();
	}

	public int getMeasuring_ID() {
		return measuring_ID;
	}

	public void setMeasuring_ID(int measuring_ID) {
		this.measuring_ID = measuring_ID;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getDoze() {
		return doze;
	}

	public void setDoze(double doze) {
		this.doze = doze;
	}

	public double getgGP() {
		return gGP;
	}

	public void setgGP(double gGP) {
		this.gGP = gGP;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Laboratory getLab() {
		return lab;
	}

	public void setLab(Laboratory lab) {
		this.lab = lab;
	}

	public UsersWBC getUser() {
		return user;
	}

	public void setUser(UsersWBC user) {
		this.user = user;
	}

	public TypeMeasur getTypeMeasur() {
		return typeMeasur;
	}

	public void setTypeMeasur(TypeMeasur typeMeasur) {
		this.typeMeasur = typeMeasur;
	}
	
	
	
}
