package Aplication;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;

import BasiClassDAO.KodeGenerateDAO;
import BasiClassDAO.Spisak_PrilogeniaDAO;
import BasiClassDAO.WorkplaceDAO;
import BasiClassDAO.ZoneDAO;
import BasicClassAccessDbase.KodeGenerate;
import BasicClassAccessDbase.Spisak_Prilogenia;
import BasicClassAccessDbase.Workplace;
import BasicClassAccessDbase.Zone;
import net.ucanaccess.jdbc.UcanaccessSQLException;

public class ReadExcelFileWBC {

	public static Workbook openExcelFile(String FILE_PATH) {
		Workbook workbook = null;
		try {
			FileInputStream fis = new FileInputStream(FILE_PATH);
			workbook = new HSSFWorkbook(fis);

		} catch (FileNotFoundException | OldExcelFormatException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Excel файлът трябва да е версия 97/2000/XP/2003", "Грешни данни",
					JOptionPane.ERROR_MESSAGE);

		} catch (IOException e) {
			ResourceLoader.appendToFile(e);
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Не е избран excel файл", "Грешни данни", JOptionPane.ERROR_MESSAGE);
		}

		return workbook;
	}

	
	public static Date readCellToDate(Cell cell, Workbook workbook) {
		Date date = new Date();
		if(cell!=null) {
		String type = cell.getCellType().toString();
		switch (type) {
		case "STRING": {
			date =isLegalDate(cell.getStringCellValue(), cell);
		}
		break;
		case "DATA": 
		case "NUMERIC":{
			date = cell.getDateCellValue();
		}
		break;
		
		}
		}
		return date;
	}

	public static Cell formatCellToDate(Cell cell, Workbook workbook) {
	CellStyle cellStyle = workbook.createCellStyle();
	CreationHelper createHelper = workbook.getCreationHelper();
	cellStyle.setDataFormat(
	    createHelper.createDataFormat().getFormat("dd.MM.yy"));
	
	cell.setCellValue(new Date());
	cell.setCellStyle(cellStyle);
	return cell;
	
	}
	
	public static List<KodeGenerate> getKodeGenerate_ListFromExcelFile(Workbook workbook, String teritori,
			String firmName) {
		List<KodeGenerate> kodeGenerate_List = new ArrayList<KodeGenerate>();
		String textNulCell = "", letter_L = "", letter_R = "";
		int startCount = 0, endCount = 0;
		String[] masiveWorkplace = getMasiveString(firmName);
		Zone zone = ZoneDAO.getValueZoneByObject("NameTerritory", teritori).get(0);
		Workplace workplace = new Workplace();

		Sheet sheet = workbook.getSheetAt(0);

		Cell cell;
		for (int row = 0; row <= sheet.getLastRowNum(); row += 1) {
			System.out.println(row);
			if (sheet.getRow(row) != null) {
				cell = sheet.getRow(row).getCell(0);
				if (CellNOEmpty(cell)) {
					textNulCell = cell.getStringCellValue();
					System.out.println(row + "  -  " + textNulCell);

					if (!textNulCell.isEmpty()) {

						cell = sheet.getRow(row).getCell(0);
						String otdelName = cell.getStringCellValue();

						workplace = selectWorkplace(firmName, masiveWorkplace, otdelName);

						cell = sheet.getRow(row).getCell(1);
						System.out.println(cell.getStringCellValue());
						letter_L = cell.getStringCellValue();

						cell = sheet.getRow(row).getCell(2);
						System.out.println(cell.getStringCellValue());
						letter_R = cell.getStringCellValue();

						cell = sheet.getRow(row).getCell(3);
						System.out.println(cell.getNumericCellValue());
						startCount = (int) cell.getNumericCellValue();
						System.out.println(startCount);

						cell = sheet.getRow(row).getCell(4);
						System.out.println(cell.getNumericCellValue());
						endCount = (int) cell.getNumericCellValue();
						System.out.println(endCount);
					}
				}
			}

			kodeGenerate_List.add(new KodeGenerate(workplace, zone, letter_L, letter_R, startCount, endCount));
		}

		for (KodeGenerate kode : kodeGenerate_List) {
			System.out.println(kode.getZone().getNameTerritory() + " " + kode.getWorkplace().getFirmName() + " "
					+ kode.getWorkplace().getOtdel() + " " + kode.getLetter_L() + " " + kode.getLetter_R() + " "
					+ kode.getStartCount() + " " + kode.getEndCount());
		}

		return kodeGenerate_List;
	}

	static Workplace selectWorkplace(String firmMane, String[] masiveWorkplace, String otdelName) {
		Workplace workplace;
		List<Workplace> list = WorkplaceDAO.getValueWorkplaceByObject("Otdel", otdelName);
		workplace = new Workplace();
		if (list.size() > 0) {
			workplace = list.get(0);
		} else {
			String ss = InputDialog(masiveWorkplace, otdelName);
			if (ss == null) {
				workplace = new Workplace(0, firmMane, otdelName);
				WorkplaceDAO.setObjectWorkplaceToTable(workplace);
				workplace = WorkplaceDAO.getValueWorkplaceByObject("Otdel", otdelName).get(0);
			} else {
				list = WorkplaceDAO.getValueWorkplaceByObject("Otdel", ss);
				workplace = list.get(0);
			}
		}
		return workplace;
	}

	public static void setListKodeGeneratetoBData(List<KodeGenerate> kodeGenerateList) {
		for (KodeGenerate kodeGenerate : kodeGenerateList) {
			
			KodeGenerateDAO.setObjectKodeGenerateToTable(kodeGenerate);
		}
	}

	public static boolean CellNOEmpty(Cell cell) {
		return cell != null && cell.getCellType() != CellType.BLANK;
	}

	@SuppressWarnings("deprecation")
	public static String getStringfromCell(Cell cell) {
		String str="";
		
		String type = cell.getCellType().toString();
		switch (type) {
		case "STRING": {
			str =cell.getStringCellValue();
		}
		break;
		case "BLANG": {
			str = "";
		}
		break;
		case "DATA": 
		case "NUMERIC":{
			cell.setCellType(CellType.STRING);
			str =cell.getStringCellValue();
	 	
		}
		break;
		}
		return str;
	}
	
	public static String InputDialog(String[] options, String input) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);

		ImageIcon icon = new ImageIcon("src/images/turtle32.png");
		String n = (String) JOptionPane.showInputDialog(null, input, "I like turtles", JOptionPane.QUESTION_MESSAGE,
				icon, options, options[2]);
		System.out.println(n);
		return n;
	}
	
	public static Date isLegalDate(String strDate, Cell cell) {
		SimpleDateFormat sdfrmt = null;
		Date javaDate = null;
		
		
		if (strDate.trim().equals("")) {
			
		} else {
			strDate = strDate.replaceAll("г.", "");
			strDate = strDate.replaceAll("г", "");
			strDate = strDate.trim();
			try {
				if(strDate.length()==8) {
			 sdfrmt = new SimpleDateFormat("dd.MM.yy");
			sdfrmt.setLenient(false);
				}
				if(strDate.length()==9) {
					 sdfrmt = new SimpleDateFormat("dd.M.yyyy");	
					sdfrmt.setLenient(false);
				}
				if(strDate.length()==10) {
					 sdfrmt = new SimpleDateFormat("dd.MM.yyyy");	
					sdfrmt.setLenient(false);
				}
				if(strDate.length()==7) {
					 sdfrmt = new SimpleDateFormat("dd.M.yy");	
					sdfrmt.setLenient(false);
				}
			
				 javaDate = sdfrmt.parse(strDate); 
			} catch (ParseException e) {
				String cellSring = "Cell = " + CellReference.convertNumToColString(cell.getColumnIndex())+ (cell.getRowIndex()+1) +", val = "+strDate;
				String cel = CellReference.convertNumToColString(cell.getColumnIndex())+ (cell.getRowIndex()+1);
				String m = JOptionPane.showInputDialog(null, cellSring,cel);
				javaDate =isLegalDate(m,cell) ;
			}
			
		}
	
		return javaDate;
	}	

	public static String[] getMasiveString(String firmName) {
		List<Workplace> list = WorkplaceDAO.getValueWorkplaceByObjectSortByColumnName("FirmName", firmName, "Otdel");
		String[] str = new String[list.size()];
		int i = 0;
		for (Workplace workplace : list) {
			str[i] = workplace.getOtdel();
			i++;
		}
		return str;
	}

	public static String[] splitAllName (String firstName) {
		String[] names = new String[3];
		
		String[] names1 = firstName.split(" ");
		
		if(names1.length<4) {
		
		for (int i = 0; i < names1.length; i++) {
			names[i] = names1[i];
		}
		for (int i = names1.length; i < names.length; i++) {
			names[i] = "";
		}
		
		if(names[2].equals("")){
			names[2] = names[1];
			names[1] = "";
		}
		}else {
			InputDialog(names1, firstName);
		}
		return names;
		
	}
	
	
}
