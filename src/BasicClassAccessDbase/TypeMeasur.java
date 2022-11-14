package BasicClassAccessDbase;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class TypeMeasur implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_TypeMeasur;
	private String kodeType;
	private String nameType;

	public TypeMeasur(Integer id_TypeMeasur, String kodeType, String nameType) {

		this.id_TypeMeasur = id_TypeMeasur;
		this.kodeType = kodeType;
		this.nameType = nameType;
	}

	public TypeMeasur() {
		super();
	}

	public int getId_TypeMeasur() {
		return id_TypeMeasur;
	}

	public void setId_TypeMeasur(int id_TypeMeasur) {
		this.id_TypeMeasur = id_TypeMeasur;
	}

	public String getKodeType() {
		return kodeType;
	}

	public void setKodeType(String kodeType) {
		this.kodeType = kodeType;
	}

	public String getNameType() {
		return nameType;
	}

	public void setNameType(String nameType) {
		this.nameType = nameType;
	}

}
