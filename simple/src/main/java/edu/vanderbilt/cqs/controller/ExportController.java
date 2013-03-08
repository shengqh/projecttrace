package edu.vanderbilt.cqs.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.vanderbilt.cqs.bean.Project;
import edu.vanderbilt.cqs.bean.ProjectComment;
import edu.vanderbilt.cqs.bean.ProjectFile;
import edu.vanderbilt.cqs.bean.ProjectTechnology;
import edu.vanderbilt.cqs.bean.ProjectTechnologyModule;
import edu.vanderbilt.cqs.bean.Role;
import edu.vanderbilt.cqs.service.ProjectService;

@Controller
public class ExportController extends RootController {
	@Autowired
	private ProjectService service;

	@Autowired
	private Validator validator;

	@RequestMapping("/export")
	@Secured({ Role.ROLE_ADMIN, Role.ROLE_VANGARD_ADSTAFF })
	public void export(HttpServletResponse response) throws IOException {
		List<Project> projects = service.listProject();
		String daystr = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String filename = "VANGARD_Project_To_" + daystr + ".xls";
		String loginfo = "export project until " + daystr;

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline; filename="
				+ filename);
		response.setHeader("extension", "xls");

		OutputStream out = response.getOutputStream();

		Workbook workbook = new HSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();

		Font headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFont(headerFont);

		// Create cell style for the body
		Font bodyFont = workbook.createFont();
		bodyFont.setFontHeightInPoints((short) 10);

		CellStyle bodyStyle = getStringStyle(workbook, bodyFont);
		CellStyle wrapStringStyle = getWrapStringStyle(workbook, bodyFont);
		CellStyle dateStyle = getDateStyle(workbook, bodyFont);
		CellStyle moneyStyle = getMoneyStyle(workbook, bodyFont);

		Sheet sheet = workbook.createSheet();

		Row row = sheet.createRow(0);
		createCell(createHelper, headerStyle, row, 0, "ID");
		createCell(createHelper, headerStyle, row, 1, "Study");
		createCell(createHelper, headerStyle, row, 2, "Study PI");
		createCell(createHelper, headerStyle, row, 3, "Faculty");
		createCell(createHelper, headerStyle, row, 4, "Staff");
		createCell(createHelper, headerStyle, row, 5, "Completed");
		createCell(createHelper, headerStyle, row, 6, "BV-data?");
		createCell(createHelper, headerStyle, row, 7, "BV-data to PI");
		createCell(createHelper, headerStyle, row, 8, "BV-sample?");
		createCell(createHelper, headerStyle, row, 9, "BV-redeposit");
		createCell(createHelper, headerStyle, row, 10, "Granted?");
		createCell(createHelper, headerStyle, row, 11, "Billed");
		createCell(createHelper, headerStyle, row, 12, "Status");
		createCell(createHelper, headerStyle, row, 13, "Budgeted");
		createCell(createHelper, headerStyle, row, 14, "Billed");

		int rowNo = 1;
		for (Project project : projects) {
			row = sheet.createRow(rowNo);
			row.setHeight((short) -1);

			createCell(createHelper, bodyStyle, row, 0,
					project.getProjectName());

			createCell(createHelper, bodyStyle, row, 1, project.getName());
			createCell(createHelper, wrapStringStyle, row, 2, project.getStudyPI());
			createCell(createHelper, wrapStringStyle, row, 3,
					project.getFacultyName());
			createCell(createHelper, wrapStringStyle, row, 4, project.getStaffName());
			createCell(createHelper, dateStyle, row, 5,
					project.getWorkCompleted());
			createCell(createHelper, bodyStyle, row, 6,
					project.getIsBioVUDataRequest());
			createCell(createHelper, dateStyle, row, 7,
					project.getBioVUDataDeliveryDate());
			createCell(createHelper, bodyStyle, row, 8,
					project.getIsBioVUSampleRequest());
			createCell(createHelper, dateStyle, row, 9,
					project.getBioVURedepositDate());
			createCell(createHelper, bodyStyle, row, 10, project.getIsGranted());
			createCell(createHelper, dateStyle, row, 11,
					project.getBilledInCORES());
			createCell(createHelper, bodyStyle, row, 12, project.getStatus());
			createCell(createHelper, moneyStyle, row, 13, project.getQuoteAmount());
			createCell(createHelper, moneyStyle, row, 14, project.getBilledAmount());
			rowNo++;
		}
		sheet.autoSizeColumn(0);

		sheet.setColumnWidth(1, 30 * 256);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);

		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);
		sheet.autoSizeColumn(6);
		sheet.autoSizeColumn(7);
		sheet.autoSizeColumn(8);
		sheet.autoSizeColumn(9);
		sheet.autoSizeColumn(10);

		sheet.setColumnWidth(11, 13 * 256);

		sheet.autoSizeColumn(12);
		sheet.autoSizeColumn(13);
		sheet.autoSizeColumn(14);

		workbook.write(out);

		out.close();

		addUserLogInfo(loginfo);
	}

	@RequestMapping("/exportproject")
	@Secured({ Role.ROLE_ADMIN, Role.ROLE_VANGARD_ADSTAFF })
	public void exportProject(@RequestParam(value = "id") Long projectid,
			HttpServletResponse response) throws IOException {
		Project project = service.findProject(projectid);
		if (project == null) {
			return;
		}

		String daystr = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String filename = project.getProjectName() + "_" + daystr + ".xls";
		String loginfo = "export " + filename;

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline; filename="
				+ filename);
		response.setHeader("extension", "xls");

		OutputStream out = response.getOutputStream();

		Workbook workbook = new HSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();

		Font headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFont(headerFont);

		// Create cell style for the body
		Font bodyFont = workbook.createFont();
		bodyFont.setFontHeightInPoints((short) 10);

		CellStyle stringStyle = getStringStyle(workbook, bodyFont);
		CellStyle wrapStringStyle = getWrapStringStyle(workbook, bodyFont);
		CellStyle leftMoneyStyle = getLeftAlignMoneyStyle(workbook, bodyFont);
		CellStyle moneyStyle = getMoneyStyle(workbook, bodyFont);
		CellStyle intStyle = getIntegerStyle(workbook, bodyFont);
		CellStyle dateStyle = getDateStyle(workbook, bodyFont);
		CellStyle dateTimeStyle = getDateTimeStyle(workbook, bodyFont);

		Sheet sheet = workbook.createSheet("Overview");
		int rowNo = 0;
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle, "Project",
				stringStyle, project.getProjectName());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle, "Contact date",
				dateStyle, project.getContactDate());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle, "Contact",
				wrapStringStyle, project.getContact());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"BioVU data request?", stringStyle,
				project.getIsBioVUDataRequest());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"BioVU sample request?", stringStyle,
				project.getIsBioVUSampleRequest());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"VANTAGE project?", stringStyle, project.getIsVantage());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"Outside project?", stringStyle, project.getIsOutside());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle, "Granted?",
				stringStyle, project.getIsGranted());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"Study descriptive name", stringStyle, project.getName());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle, "Study PI",
				wrapStringStyle, project.getStudyPI());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle, "Quote amount",
				leftMoneyStyle, project.getQuoteAmount());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"Contract date", dateStyle, project.getContractDate());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"Assigned to (faculty)", wrapStringStyle, project.getFacultyName());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle, "Study status",
				stringStyle, project.getStatus());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"Assigned to (staff)", wrapStringStyle, project.getStaffName());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle, "Work started",
				dateStyle, project.getWorkStarted());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"Work completed", dateStyle, project.getWorkCompleted());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"BioVU data delivery to investigator (date)", dateStyle,
				project.getBioVUDataDeliveryDate());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"BioVU redeposit (date)", dateStyle,
				project.getBioVURedepositDate());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"Cost center to bill", wrapStringStyle,
				project.getCostCenterToBillList());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"Request cost center setup in CORES (date)", dateStyle,
				project.getRequestCostCenterSetupInCORES());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"Requested by (name)", stringStyle, project.getRequestedBy());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"Billed in CORES (date)", dateStyle, project.getBilledInCORES());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"Billed by (name)", stringStyle, project.getBilledBy());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"Billed amount", leftMoneyStyle, project.getBilledAmount());

		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);

		sheet = workbook.createSheet("Details");
		Row row = sheet.createRow(0);

		int colNo = 0;
		colNo = createCell(createHelper, headerStyle, row, colNo, "Technology");
		colNo = createCell(createHelper, headerStyle, row, colNo, "Platform");
		colNo = createCell(createHelper, headerStyle, row, colNo,
				"Sample number");
		colNo = createCell(createHelper, headerStyle, row, colNo, "Other unit");
		colNo = createCell(createHelper, headerStyle, row, colNo, "Module");
		colNo = createCell(createHelper, headerStyle, row, colNo, "Per project");
		colNo = createCell(createHelper, headerStyle, row, colNo, "Per unit");
		colNo = createCell(createHelper, headerStyle, row, colNo, "Module type");
		colNo = createCell(createHelper, headerStyle, row, colNo,
				"Project setup fee");
		colNo = createCell(createHelper, headerStyle, row, colNo, "Unit fee");
		colNo = createCell(createHelper, headerStyle, row, colNo, "Total fee");
		colNo = createCell(createHelper, headerStyle, row, colNo, "Notes");

		int totalColCount = colNo - 1;

		rowNo = 1;
		for (ProjectTechnology tec : project.getTechnologies()) {
			row = sheet.createRow(rowNo);
			row.setHeight((short) -1);

			colNo = 0;
			colNo = createCell(createHelper, stringStyle, row, colNo,
					tec.getTechnology());
			colNo = createCell(createHelper, stringStyle, row, colNo,
					tec.getPlatform());

			boolean bFirst = true;
			for (ProjectTechnologyModule mod : tec.getModules()) {
				int lastColNo = colNo;
				if (!bFirst) {
					rowNo++;
					row = sheet.createRow(rowNo);
				} else {
					bFirst = false;
				}
				lastColNo = createCell(createHelper, intStyle, row, lastColNo,
						mod.getSampleNumber());
				lastColNo = createCell(createHelper, intStyle, row, lastColNo,
						mod.getOtherUnit());
				lastColNo = createCell(createHelper, stringStyle, row,
						lastColNo, mod.getName());
				lastColNo = createCell(createHelper, moneyStyle, row,
						lastColNo, mod.getPricePerProject());
				lastColNo = createCell(createHelper, moneyStyle, row,
						lastColNo, mod.getPricePerUnit());
				lastColNo = createCell(createHelper, stringStyle, row,
						lastColNo, mod.getModuleTypeString());
				lastColNo = createCell(createHelper, moneyStyle, row,
						lastColNo, mod.getProjectSetupFee());
				lastColNo = createCell(createHelper, moneyStyle, row,
						lastColNo, mod.getUnitFee());
				lastColNo = createCell(createHelper, moneyStyle, row,
						lastColNo, mod.getTotalFee());
				lastColNo = createCell(createHelper, stringStyle, row,
						lastColNo, mod.getDescription());
			}
			rowNo++;
		}

		row = sheet.createRow(rowNo);
		row.setHeight((short) -1);
		colNo = createCell(createHelper, stringStyle, row, 0,
				"Amount estimated");
		Cell c = doCreateCell(moneyStyle, row, totalColCount - 3);
		c.setCellFormula("SUM(I2:I" + String.valueOf(rowNo) + ")");
		c = doCreateCell(moneyStyle, row, totalColCount - 2);
		c.setCellFormula("SUM(J2:J" + String.valueOf(rowNo) + ")");
		c = doCreateCell(moneyStyle, row, totalColCount - 1);
		c.setCellFormula("SUM(K2:K" + String.valueOf(rowNo) + ")");

		for (int i = 0; i <= totalColCount; i++) {
			sheet.autoSizeColumn(i);
		}

		// Export files
		sheet = workbook.createSheet("Files");
		row = sheet.createRow(0);
		colNo = 0;
		colNo = createCell(createHelper, headerStyle, row, colNo, "Upload date");
		colNo = createCell(createHelper, headerStyle, row, colNo, "Upload user");
		colNo = createCell(createHelper, headerStyle, row, colNo, "File name");
		colNo = createCell(createHelper, headerStyle, row, colNo, "File size");
		rowNo = 1;
		for (ProjectFile file : project.getFiles()) {
			row = sheet.createRow(rowNo);
			row.setHeight((short) -1);
			colNo = 0;
			colNo = createCell(createHelper, dateTimeStyle, row, colNo,
					file.getCreateDate());
			colNo = createCell(createHelper, stringStyle, row, colNo,
					file.getCreateUser());
			colNo = createCell(createHelper, stringStyle, row, colNo,
					file.getFileName());
			colNo = createCell(createHelper, stringStyle, row, colNo,
					file.getFileSizeString());
			rowNo++;
		}

		for (int i = 0; i < 4; i++) {
			sheet.autoSizeColumn(i);
		}

		// Export comments
		sheet = workbook.createSheet("Comments");
		row = sheet.createRow(0);
		colNo = 0;
		colNo = createCell(createHelper, headerStyle, row, colNo, "Date");
		colNo = createCell(createHelper, headerStyle, row, colNo,
				"User/Comment");
		rowNo = 1;
		for (ProjectComment comment : project.getComments()) {
			row = sheet.createRow(rowNo);
			row.setHeight((short) -1);
			colNo = 0;
			colNo = createCell(createHelper, dateTimeStyle, row, colNo,
					comment.getCommentDate());
			colNo = createCell(
					createHelper,
					wrapStringStyle,
					row,
					colNo,
					comment.getCommentUser() + "wrote:\n"
							+ comment.getComment());
			rowNo++;
		}

		for (int i = 0; i < 2; i++) {
			sheet.autoSizeColumn(i);
		}

		workbook.write(out);

		out.close();

		addUserLogInfo(loginfo);
	}

	private CellStyle getDateTimeStyle(Workbook workbook, Font bodyFont) {
		CellStyle result = workbook.createCellStyle();
		DataFormat df = workbook.createDataFormat();
		result.setFont(bodyFont);
		result.setAlignment(CellStyle.ALIGN_LEFT);
		result.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		result.setWrapText(false);
		result.setDataFormat(df.getFormat("yyyy-MM-dd hh:mm:ss"));
		return result;
	}

	private CellStyle getStringStyle(Workbook workbook, Font bodyFont) {
		CellStyle result = workbook.createCellStyle();
		result.setFont(bodyFont);
		result.setAlignment(CellStyle.ALIGN_LEFT);
		result.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		result.setWrapText(false);
		return result;
	}

	private CellStyle getWrapStringStyle(Workbook workbook, Font bodyFont) {
		CellStyle result = workbook.createCellStyle();
		result.setFont(bodyFont);
		result.setAlignment(CellStyle.ALIGN_LEFT);
		result.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		result.setWrapText(true);
		return result;
	}

	private CellStyle getIntegerStyle(Workbook workbook, Font bodyFont) {
		CellStyle result = getStringStyle(workbook, bodyFont);
		result.setAlignment(CellStyle.ALIGN_RIGHT);
		result.setDataFormat((short) 1);
		return result;
	}

	private CellStyle getLeftAlignMoneyStyle(Workbook workbook, Font bodyFont) {
		CellStyle result = getStringStyle(workbook, bodyFont);
		result.setDataFormat((short) 4);
		return result;
	}

	private CellStyle getMoneyStyle(Workbook workbook, Font bodyFont) {
		CellStyle result = getStringStyle(workbook, bodyFont);
		result.setAlignment(CellStyle.ALIGN_RIGHT);
		result.setDataFormat((short) 4);
		return result;
	}

	private CellStyle getDateStyle(Workbook workbook, Font bodyFont) {
		CellStyle result = workbook.createCellStyle();
		DataFormat df = workbook.createDataFormat();
		result.setFont(bodyFont);
		result.setAlignment(CellStyle.ALIGN_LEFT);
		result.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		result.setWrapText(false);
		result.setDataFormat(df.getFormat("yyyy-MM-dd"));
		return result;
	}

	private int addRow(CreationHelper createHelper, Sheet sheet, int rowNo,
			CellStyle headerStyle, String name, CellStyle bodyStyle,
			List<String> values) {
		return addRow(createHelper, sheet, rowNo, headerStyle, name, bodyStyle,
				convertList(values));
	}

	private int addRow(CreationHelper createHelper, Sheet sheet, int rowNo,
			CellStyle headerStyle, String name, CellStyle bodyStyle,
			Double value) {
		Row row = sheet.createRow(rowNo);
		createCell(createHelper, headerStyle, row, 0, name);
		createCell(createHelper, bodyStyle, row, 1, value);
		return rowNo + 1;
	}

	private int addRow(CreationHelper createHelper, Sheet sheet, int rowNo,
			CellStyle headerStyle, String name, CellStyle bodyStyle, Date value) {
		Row row = sheet.createRow(rowNo);
		createCell(createHelper, headerStyle, row, 0, name);
		createCell(createHelper, bodyStyle, row, 1, value);
		return rowNo + 1;
	}

	private int addRow(CreationHelper createHelper, Sheet sheet, int rowNo,
			CellStyle headerStyle, String name, CellStyle bodyStyle,
			Boolean value) {
		return addRow(createHelper, sheet, rowNo, headerStyle, name, bodyStyle,
				getBoolString(value));
	}

	private int addRow(CreationHelper createHelper, Sheet sheet, int rowNo,
			CellStyle headerStyle, String name, CellStyle bodyStyle,
			String value) {
		Row row = sheet.createRow(rowNo);
		createCell(createHelper, headerStyle, row, 0, name);
		createCell(createHelper, bodyStyle, row, 1, value);
		return rowNo + 1;
	}

	private String convertList(List<String> aList) {
		StringBuilder sb = new StringBuilder();
		if (aList != null) {
			boolean isFirst = true;
			for (String name : aList) {
				if (isFirst) {
					sb.append(name);
					isFirst = false;
				} else {
					sb.append("\n").append(name);
				}
			}
		}
		String value = sb.toString();
		return value;
	}

	private Cell doCreateCell(CellStyle bodyStyle, Row row, int colNo) {
		Cell result = row.createCell(colNo);
		result.setCellStyle(bodyStyle);
		return result;
	}

	private int createCell(CreationHelper createHelper, CellStyle bodyStyle,
			Row row, int colNo, String value) {
		Cell c = doCreateCell(bodyStyle, row, colNo);
		if (value != null) {
			c.setCellValue(createHelper.createRichTextString(value));
		}
		return colNo + 1;
	}

	private int createCell(CreationHelper createHelper, CellStyle bodyStyle,
			Row row, int colNo, Double value) {
		Cell c = doCreateCell(bodyStyle, row, colNo);
		if (value != null) {
			c.setCellValue(value);
		}
		return colNo + 1;
	}

	private int createCell(CreationHelper createHelper, CellStyle bodyStyle,
			Row row, int colNo, Integer value) {
		Cell c = doCreateCell(bodyStyle, row, colNo);
		if (value != null) {
			c.setCellValue(value);
		}
		return colNo + 1;
	}

	private int createCell(CreationHelper createHelper, CellStyle bodyStyle,
			Row row, int colNo, Boolean value) {
		return createCell(createHelper, bodyStyle, row, colNo,
				getBoolString(value));
	}

	private String getBoolString(Boolean isBioVUDataRequest) {
		return (isBioVUDataRequest != null) && isBioVUDataRequest ? "yes"
				: "no";
	}

	private int createCell(CreationHelper createHelper, CellStyle bodyStyle,
			Row row, int colNo, Date value) {
		Cell c = doCreateCell(bodyStyle, row, colNo);
		if (value != null) {
			c.setCellValue(value);
		}
		return colNo + 1;
	}

	private int createCell(CreationHelper createHelper, CellStyle bodyStyle,
			Row row, int colNo, List<String> aList) {
		return createCell(createHelper, bodyStyle, row, colNo,
				convertList(aList));
	}
}