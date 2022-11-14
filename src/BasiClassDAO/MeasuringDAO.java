package BasiClassDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Aplication.ResourceLoader;
import BasicClassAccessDbase.Laboratory;
import BasicClassAccessDbase.Measuring;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.TypeMeasur;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.conectToAccessDB;

public class MeasuringDAO {

	public static void setValueMeasuring(
			Person person,
			Date date,
			double doze,
			double gGP,
			Laboratory lab,
			UsersWBC user,
			TypeMeasur typeMeasur) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO Measuring (Person_ID, Date, Doze, GGP, Lab_ID, UsersWBC_ID, TypeMeasur_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, person.getId_Person());
			preparedStatement.setObject(2, date);
			preparedStatement.setObject(3, doze);
			preparedStatement.setObject(4, gGP);
			preparedStatement.setObject(5, lab.getLab_ID());
			preparedStatement.setObject(6, user.getId_Users());
			preparedStatement.setObject(7, typeMeasur.getId_TypeMeasur());
			
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
	}
	
	public static void setObjectMeasuringToTable(Measuring measuring) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sql = "INSERT INTO Measuring (Person_ID, Date, Doze, GGP, Lab_ID, UsersWBC_ID, TypeMeasur_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setObject(1, measuring.getMeasuring_ID());
			preparedStatement.setObject(2, measuring.getDate());
			preparedStatement.setObject(3, measuring.getDoze());
			preparedStatement.setObject(4, measuring.getgGP());
			preparedStatement.setObject(5, measuring.getLab().getLab_ID());
			preparedStatement.setObject(6, measuring.getUser().getId_Users());
			preparedStatement.setObject(7, measuring.getMeasuring_ID());
			
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
	}
	
	public static void updateValueMeasuring(Measuring measuring, int id_Measuring) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "Update Measuring SET Person_ID = ?, Date = ?, Doze = ?, GGP = ?, Lab_ID = ?, UsersWBC_ID = ?,"
				+ " TypeMeasur_ID = ?,  where Measuring_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			
			preparedStatement.setObject(1, measuring.getDate());
			preparedStatement.setObject(2, measuring.getDoze());
			preparedStatement.setObject(3, measuring.getgGP());
			preparedStatement.setObject(4, measuring.getLab().getLab_ID());
			preparedStatement.setObject(5, measuring.getUser().getId_Users());
			preparedStatement.setObject(6, measuring.getTypeMeasur().getId_TypeMeasur());
			
			preparedStatement.setObject(7, id_Measuring);

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
		
		
	}

	public static void deleteValueMeasuring(int id_Measuring) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();

		String sqlUpdate = "delete from Measuring where Measuring_ID = ? ";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setInt(1, id_Measuring);

			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			ResourceLoader.appendToFile( e);
			e.printStackTrace();
		}
	}
	
	public static List<Measuring> getAllValueMeasuring() {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Measuring";
		List<Measuring> list = new ArrayList<Measuring>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				Measuring object = new Measuring();
				object.setMeasuring_ID(result.getInt("Measuring_ID"));
				Person person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
				object.setPerson(person);
				object.setDate(result.getDate("Date"));
				object.setDoze(result.getDouble("Doze"));
				object.setgGP(result.getDouble("GGP"));
				Laboratory lab = LaboratoryDAO.getValueLaboratoryByID(result.getInt("Lab_ID"));
				object.setLab(lab);
				UsersWBC user = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				object.setUser(user);
				TypeMeasur type = TypeMeasurDAO.getValueTypeMeasurByID(result.getInt("TypeMeasur_ID"));
				object.setTypeMeasur(type);
								
				list.add(object);
			}
			
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
		return list;
	}
		
	public static List<Measuring> getValueMeasuringSortByColumnName(String sortColumnName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Measuring  ORDER BY " + sortColumnName + " ASC";
		List<Measuring> list = new ArrayList<Measuring>();

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				Measuring object = new Measuring();
				object.setMeasuring_ID(result.getInt("Measuring_ID"));
				Person person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
				object.setPerson(person);
				object.setDate(result.getDate("Date"));
				object.setDoze(result.getDouble("Doze"));
				object.setgGP(result.getDouble("GGP"));
				Laboratory lab = LaboratoryDAO.getValueLaboratoryByID(result.getInt("Lab_ID"));
				object.setLab(lab);
				UsersWBC user = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				object.setUser(user);
				TypeMeasur type = TypeMeasurDAO.getValueTypeMeasurByID(result.getInt("TypeMeasur_ID"));
				object.setTypeMeasur(type);
				list.add(object);
			}
			
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
		return list;
	}
	
	public static List<Measuring> getValueMeasuringByObject(String columnName, Object object) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Measuring  where " + columnName + " = ? ";
		
		List<Measuring> list = new ArrayList<Measuring>();
		

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			switch (columnName) {
			case "Person_ID": {
				preparedStatement.setObject(1, ((Person) object).getId_Person());
			}
			case "Lab_ID": {
				preparedStatement.setObject(1, ((Laboratory) object).getLab_ID());
			}
			case "UsersWBC_ID": {
				preparedStatement.setObject(1, ((UsersWBC) object).getId_Users());
			}
			case "TypeMeasur_ID": {
				preparedStatement.setObject(1, ((TypeMeasur) object).getId_TypeMeasur());
			}
			default:
				preparedStatement.setObject(1, object);
			}
			
			
			ResultSet result = preparedStatement.executeQuery();


			while (result.next()) {
				Measuring resultObject = new Measuring();
				resultObject.setMeasuring_ID(result.getInt("Measuring_ID"));
				Person person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
				resultObject.setPerson(person);
				resultObject.setDate(result.getDate("Date"));
				resultObject.setDoze(result.getDouble("Doze"));
				resultObject.setgGP(result.getDouble("GGP"));
				Laboratory lab = LaboratoryDAO.getValueLaboratoryByID(result.getInt("Lab_ID"));
				resultObject.setLab(lab);
				UsersWBC user = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				resultObject.setUser(user);
				TypeMeasur type = TypeMeasurDAO.getValueTypeMeasurByID(result.getInt("TypeMeasur_ID"));
				resultObject.setTypeMeasur(type);
				list.add(resultObject);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
		return list;
	}
	
	public static List<Measuring> getValueMeasuringByObjectSortByColumnName(String columnName, Object object, String sortColumnName) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Measuring  where " + columnName + " = ?  ORDER BY " + sortColumnName + " ASC";
		
		List<Measuring> list = new ArrayList<Measuring>();
		

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, object);
			ResultSet result = preparedStatement.executeQuery();


			while (result.next()) {
				Measuring resultObject = new Measuring();
				
				resultObject.setMeasuring_ID(result.getInt("Measuring_ID"));
				Person person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
				resultObject.setPerson(person);
				resultObject.setDate(result.getDate("Date"));
				resultObject.setDoze(result.getDouble("Doze"));
				resultObject.setgGP(result.getDouble("GGP"));
				Laboratory lab = LaboratoryDAO.getValueLaboratoryByID(result.getInt("Lab_ID"));
				resultObject.setLab(lab);
				UsersWBC user = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				resultObject.setUser(user);
				TypeMeasur type = TypeMeasurDAO.getValueTypeMeasurByID(result.getInt("TypeMeasur_ID"));
				resultObject.setTypeMeasur(type);
				
				list.add(resultObject);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
		return list;
	}
	
	public static Measuring getValueMeasuringByID(int id) {

		Connection connection = conectToAccessDB.conectionBDtoAccess();
		String sql = "SELECT * FROM Measuring  where Measuring_ID = ? ";
		
		List<Measuring> list = new ArrayList<Measuring>();
		

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, id);
			ResultSet result = preparedStatement.executeQuery();


			while (result.next()) {
				Measuring resultObject = new Measuring();
				resultObject.setMeasuring_ID(result.getInt("Measuring_ID"));
				Person person = PersonDAO.getValuePersonByID(result.getInt("Person_ID"));
				resultObject.setPerson(person);
				resultObject.setDate(result.getDate("Date"));
				resultObject.setDoze(result.getDouble("Doze"));
				resultObject.setgGP(result.getDouble("GGP"));
				Laboratory lab = LaboratoryDAO.getValueLaboratoryByID(result.getInt("Lab_ID"));
				resultObject.setLab(lab);
				UsersWBC user = UsersWBCDAO.getValueUsersWBCByID(result.getInt("UsersWBC_ID"));
				resultObject.setUser(user);
				TypeMeasur type = TypeMeasurDAO.getValueTypeMeasurByID(result.getInt("TypeMeasur_ID"));
				resultObject.setTypeMeasur(type);
				list.add(resultObject);
			}
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			ResourceLoader.appendToFile( e);
		}
		return list.get(0);
	}
	
	
}
