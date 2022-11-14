package Aplication;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import BasiClassDAO.KodeStatusDAO;
import BasiClassDAO.PersonDAO;
import BasiClassDAO.PersonStatusDAO;
import BasiClassDAO.Spisak_PrilogeniaDAO;
import BasiClassDAO.UsersWBCDAO;
import BasiClassDAO.ZoneDAO;
import BasicClassAccessDbase.KodeStatus;
import BasicClassAccessDbase.Person;
import BasicClassAccessDbase.PersonStatus;
import BasicClassAccessDbase.Spisak_Prilogenia;
import BasicClassAccessDbase.UsersWBC;
import BasicClassAccessDbase.Workplace;
import FrameViewClass.ObrabDublicateSpisak_Prilogenie;

//четене на ПерсонСтатус от годишния ексел фаил
public class ReadPersonStatusFromExcelFile {

	static String pathFile = "D:\\EXTERNAL_2021.xls"; // пътя и името до ексел файла
	static String firmName = "Външни организации"; // името на фирмата за обекта списък-приложения "АЕЦ Козлодуй"
													// "Външни организации"
	static int year = 2021; // годината за списък-приложения

	public static void ReadPersonStatusFromExcelFile() {

		updatePersonFromExcelFile(pathFile);
		
//		List<PersonStatus> list = getListPersonStatusFromExcelFile(pathFile, firmName, year);
//		System.out.println("55555555555555555555555  " + list.size());
//		setToBDateListPersonStatus(list);

	}

	public static void updatePersonFromExcelFile(String pathFile) {
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
		String EGN = "", FirstName = "", SecondName = "", LastName = "";
		Sheet sheet = workbook.getSheetAt(3);
		Cell cell, cell1;
		List<Person> listPerson = PersonDAO.getAllValuePerson();
		for (int row = 5; row <= sheet.getLastRowNum(); row += 1) {
			
			if (sheet.getRow(row) != null) {
				cell = sheet.getRow(row).getCell(5);
				cell1 = sheet.getRow(row).getCell(6);

				if (ReadExcelFileWBC.CellNOEmpty(cell)) {

					EGN = ReadExcelFileWBC.getStringfromCell(cell);
					boolean fl= true;
					while (listPerson.next()) {
						if(person.getEgn().equals(EGN)) {
							fl=false;
						}
					}
					if (listPerson.size() == 0) {
						FirstName = ReadExcelFileWBC.getStringfromCell(cell1);
						String[] names = ReadExcelFileWBC.splitAllName(FirstName);
						FirstName = names[0];
						SecondName = names[1];
						LastName = names[2];
						System.out.println("1 "+FirstName +" 2 "+SecondName+" 3 "+ LastName);
						PersonDAO.setObjectPersonToTable(new Person(EGN, FirstName, SecondName, LastName));

					}
				}

			}
		}

	}

	public static List<PersonStatus> getListPersonStatusFromExcelFile(String pathFile, String firmName, int year) {
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
		List<PersonStatus> listPerStat = new ArrayList<>();
		java.sql.Date dateSet = java.sql.Date.valueOf(LocalDate.now());

		UsersWBC userSet = UsersWBCDAO.getValueUsersWBCByID(1);

		String kodeKZ1 = "", kodeKZ2 = "", kodeHOG = "", kodeT1 = "", kodeT2 = "";
		String EGN = "", FirstName = "", SecondName = "", LastName = "", zab = "";
		Date startDate;
		Date endDate;
		String otdelName = "", formulyarName = "";
		Workplace workplace = new Workplace();
		String[] masiveWorkplace = ReadExcelFileWBC.getMasiveString(firmName);
		Sheet sheet = workbook.getSheetAt(3);
		Cell cell, cell1;
		for (int row = 5; row <= sheet.getLastRowNum(); row += 1) {
			zab = "";
			if (sheet.getRow(row) != null) {
				cell = sheet.getRow(row).getCell(5);
				cell1 = sheet.getRow(row).getCell(6);
//				System.out.println("***************"+firmName+" "+ masiveWorkplace.length+" "+otdelName);
				if (!ReadExcelFileWBC.CellNOEmpty(cell) && ReadExcelFileWBC.CellNOEmpty(cell1)) {
					otdelName = cell1.getStringCellValue();

					if (!otdelName.contains("край")) {
						workplace = ReadExcelFileWBC.selectWorkplace(firmName, masiveWorkplace, otdelName);

//						System.out.println("???????????????"+workplace.getOtdel());	
					}
				}

				if (ReadExcelFileWBC.CellNOEmpty(cell) && workplace.getOtdel() != null) {

					EGN = ReadExcelFileWBC.getStringfromCell(cell);
					FirstName = ReadExcelFileWBC.getStringfromCell(cell1);
					if (cell1.getCellComment() != null) {
						zab = cell1.getCellComment().getString().getString();
					}
//					String[] names = ReadExcelFileWBC.splitAllName (FirstName);
//					FirstName = names[0];
//					SecondName = names[1];
//					LastName = names[2];
					Person person = null;
					List<Person> listPerson = PersonDAO.getValuePersonByObject("EGN", EGN);
					if (listPerson.size() > 0) {
						person = listPerson.get(0);
					} else {

						String sst = "egn: " + EGN + " FirstName: " + FirstName + " SecondName: " + SecondName
								+ " LastName: " + LastName;
						MessageDialog(sst);
					}
//						
//						person = new Person(EGN, FirstName, SecondName, LastName);
//						PersonDAO.setObjectPersonToTable(person);
//						System.out.println("EGN2 "+EGN);
//						List<Person> list = PersonDAO.getValuePersonByObject("EGN", EGN);
//						System.out.println("6666666666666    "+list.size());
//						if(list.size()>0) {
//						person = list.get(0);
//						}
//					}

					cell = sheet.getRow(row).getCell(0);
					kodeKZ1 = cell.getStringCellValue();

					cell = sheet.getRow(row).getCell(1);
					kodeKZ2 = cell.getStringCellValue();

					cell = sheet.getRow(row).getCell(2);
					kodeHOG = cell.getStringCellValue();

					cell = sheet.getRow(row).getCell(3);
					kodeT2 = cell.getStringCellValue();

					cell = sheet.getRow(row).getCell(4);
					kodeT1 = cell.getStringCellValue();

					if (pathFile.equals("EXTERNAL")) {
						kodeT1 = kodeT2;
						kodeT2 = "";
					}

					int k = 7;
					cell = sheet.getRow(row).getCell(k);
					while (ReadExcelFileWBC.CellNOEmpty(cell)) {

						formulyarName = cell.getStringCellValue();
						k++;
						cell = sheet.getRow(row).getCell(k);
						startDate = ReadExcelFileWBC.readCellToDate(cell, workbook);
						k++;
						cell = sheet.getRow(row).getCell(k);
						endDate = ReadExcelFileWBC.readCellToDate(cell, workbook);
						k++;
						cell = sheet.getRow(row).getCell(k);

//						System.out.println("\\\\\\\\\\\\\\"+person.getEgn().toString()+" "+zab+" "+workplace.getOtdel());

						Spisak_Prilogenia spPr = search_Spisak_Prilogenia(formulyarName, startDate, endDate, workplace);
						PersonStatus personStat = new PersonStatus(person, workplace, spPr, userSet, dateSet, "");
						PersonStatusDAO.setObjectPersonStatusToTable(personStat);

						PersonStatus perStat = PersonStatusDAO.getValuePersonStatusByPersonStatus(personStat);

						listPerStat.add(perStat);
					}
					setKodeStatusToPersonInDataDase(year, kodeKZ1, kodeKZ2, kodeHOG, kodeT1, kodeT2, person);
					listPerStat.get(listPerStat.size() - 1).setZabelejka(zab);
				}
			}

		}

		return listPerStat;

	}

	private static void setKodeStatusToPersonInDataDase(int year, String kodeKZ1, String kodeKZ2, String kodeHOG,
			String kodeT1, String kodeT2, Person person) {
		if (!kodeKZ1.equals("ЕП-2") && !kodeKZ1.trim().equals("")) {
			KodeStatus kodeStatus = new KodeStatus(person, kodeKZ1, ZoneDAO.getValueZoneByID(1), true, year);
			System.out.println(kodeStatus.getPerson().getEgn() + " " + kodeStatus.getKode() + " "
					+ kodeStatus.getZone().getNameTerritory());
			KodeStatusDAO.setObjectKodeStatusToTable(kodeStatus);
		}
		if (!kodeKZ2.equals("н") && !kodeKZ2.trim().equals("")) {
			KodeStatus kodeStatus = new KodeStatus(person, kodeKZ2, ZoneDAO.getValueZoneByID(2), true, year);
			System.out.println(kodeStatus.getPerson().getEgn() + " " + kodeStatus.getKode() + " "
					+ kodeStatus.getZone().getNameTerritory());
			KodeStatusDAO.setObjectKodeStatusToTable(kodeStatus);
		}
		if (!kodeHOG.equals("н") && !kodeHOG.trim().equals("")) {
			KodeStatus kodeStatus = new KodeStatus(person, kodeHOG, ZoneDAO.getValueZoneByID(3), true, year);
			System.out.println(kodeStatus.getPerson().getEgn() + " " + kodeStatus.getKode() + " "
					+ kodeStatus.getZone().getNameTerritory());
			KodeStatusDAO.setObjectKodeStatusToTable(kodeStatus);
		}
		if (!kodeT1.equals("н") && !kodeT1.trim().equals("")) {
			KodeStatus kodeStatus = new KodeStatus(person, kodeT1, ZoneDAO.getValueZoneByID(4), true, year);
			System.out.println(kodeStatus.getPerson().getEgn() + " " + kodeStatus.getKode() + " "
					+ kodeStatus.getZone().getNameTerritory());
			KodeStatusDAO.setObjectKodeStatusToTable(kodeStatus);
		}
		if (!kodeT2.equals("н") && !kodeT2.trim().equals("")) {
			KodeStatus kodeStatus = new KodeStatus(person, kodeT2, ZoneDAO.getValueZoneByID(5), true, year);
			System.out.println(kodeStatus.getPerson().getEgn() + " " + kodeStatus.getKode() + " "
					+ kodeStatus.getZone().getNameTerritory());
			KodeStatusDAO.setObjectKodeStatusToTable(kodeStatus);
		}
	}

	private static Spisak_Prilogenia search_Spisak_Prilogenia(String formulyarName, Date startDate, Date endDate,
			Workplace workplace) {
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yy");

		List<Spisak_Prilogenia> listSpPr = Spisak_PrilogeniaDAO
				.getValueSpisak_PrilogeniaByObjectSortByColumnName("FormulyarName", formulyarName, "FormulyarName");
		System.out.println("Br SpPr ot Db " + listSpPr.size());
		Spisak_Prilogenia spPr = null, spisPril = null, spisPrilData = null;
		boolean fl_Data = false;
		for (Spisak_Prilogenia spisak_Prilogenia : listSpPr) {

			System.out.println("ot Bdata " + spisak_Prilogenia.getFormulyarName() + " "
					+ sdfrmt.format(spisak_Prilogenia.getStartDate()) + " "
					+ sdfrmt.format(spisak_Prilogenia.getEndDate()) + " "
					+ spisak_Prilogenia.getWorkplace().getOtdel());

			System.out.println("ot Excel " + formulyarName + " " + sdfrmt.format(startDate) + " "
					+ sdfrmt.format(endDate) + " " + workplace.getOtdel());

			if (sdfrmt.format(startDate).equals(sdfrmt.format(spisak_Prilogenia.getStartDate()))
					&& sdfrmt.format(endDate).equals(sdfrmt.format(spisak_Prilogenia.getEndDate()))) {
				fl_Data = true;
				spisPrilData = spisak_Prilogenia;
				if (workplace.getOtdel().equals(spisak_Prilogenia.getWorkplace().getOtdel())) {
					spPr = spisak_Prilogenia;
					System.out.println("------------------------" + spisak_Prilogenia.getFormulyarName());
				}

			}
			spisPril = spisak_Prilogenia;
		}

		System.out.println("=========" + formulyarName + " " + sdfrmt.format(startDate) + " " + sdfrmt.format(endDate)
				+ " " + workplace.getOtdel());
		if (spPr == null) {

			if (fl_Data) {
				spisPrilData.setWorkplace(workplace);
				Spisak_PrilogeniaDAO.setObjectSpisak_PrilogeniaToTable(spisPrilData);
				search_Spisak_Prilogenia(spisPrilData.getFormulyarName(), spisPrilData.getStartDate(),
						spisPrilData.getEndDate(), workplace);
			} else {
				if (spisPril != null) {
					String zaInsSpis = formulyarName + " " + sdfrmt.format(startDate) + " " + sdfrmt.format(endDate);

					String orignSpis = spisPril.getFormulyarName() + " " + sdfrmt.format(spisPril.getStartDate()) + " "
							+ sdfrmt.format(spisPril.getEndDate());
					final JFrame frame = new JFrame();

					ObrabDublicateSpisak_Prilogenie obrabDublicateSpisak_Prilogenie = new ObrabDublicateSpisak_Prilogenie(
							frame, orignSpis, sdfrmt.format(spisPril.getStartDate()),
							sdfrmt.format(spisPril.getEndDate()), zaInsSpis, sdfrmt.format(startDate),
							sdfrmt.format(endDate));

					obrabDublicateSpisak_Prilogenie.setVisible(true);
					String[] str = ObrabDublicateSpisak_Prilogenie.get_DublicatePole();

					if (str != null) {
						Date start = null, end = null;
						Spisak_Prilogenia spPr1 = null;
						try {
							start = sdfrmt.parse(str[0]);
							end = sdfrmt.parse(str[1]);
							spPr1 = new Spisak_Prilogenia(spisPril.getFormulyarName(), spisPril.getYear(), start, end,
									spisPril.getWorkplace(), "нов запис");
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						System.out.println(spPr1.getFormulyarName() + " " + sdfrmt.format(spPr1.getStartDate()) + " "
								+ sdfrmt.format(spPr1.getEndDate()));

						Spisak_PrilogeniaDAO.setObjectSpisak_PrilogeniaToTable(spPr1);

						search_Spisak_Prilogenia(spisPril.getFormulyarName(), start, end, workplace);
					}
				} else {
					spPr = new Spisak_Prilogenia(formulyarName, year + "", startDate, endDate, workplace,
							"нов, нов запис");
					Spisak_PrilogeniaDAO.setObjectSpisak_PrilogeniaToTable(spPr);

					search_Spisak_Prilogenia(spPr.getFormulyarName(), startDate, endDate, workplace);
				}
			}
		}

		return spPr;
	}

	private static String[] getMasiveStringFromSpisak_Prilogenia(List<Spisak_Prilogenia> listSpPr,
			Workplace workplace) {

		String[] masiveStr = new String[listSpPr.size()];
		for (int i = 0; i < masiveStr.length; i++) {
			if (listSpPr.get(i).getWorkplace().equals(workplace)) {
				masiveStr[i] = listSpPr.get(i).getSpisak_Prilogenia_ID() + ": " + listSpPr.get(i).getFormulyarName()
						+ " " + listSpPr.get(i).getStartDate() + " " + listSpPr.get(i).getEndDate();
			}
		}

		return masiveStr;
	}

	private static void setToBDateListPersonStatus(List<PersonStatus> list) {

		for (PersonStatus personStatus : list) {
			PersonStatusDAO.setObjectPersonStatusToTable(personStatus);
			System.out.println(personStatus.getPerson().getEgn() + " " + personStatus.getWorkplace().getOtdel() + " "
					+ personStatus.getSpisak_prilogenia().getFormulyarName() + " "
					+ personStatus.getUserWBC().getLastName() + " " + personStatus.getZabelejka().toString() + " "
					+ personStatus.getDateSet().toString());

		}

	}

	public static void MessageDialog(String text) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, text, "Грешка", JOptionPane.ERROR_MESSAGE);
	}
}
