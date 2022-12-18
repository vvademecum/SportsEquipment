package com.example.Acrobatum.service;

import com.example.Acrobatum.models.Characteristics;
import com.example.Acrobatum.models.Client;
import com.example.Acrobatum.models.Contacts;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ClientExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Client> listClients;

    public ClientExporter(List<Client> listClients) {
        this.listClients = listClients;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Clients");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Код клиента", style);
        createCell(row, 1, "ФИО клиента", style);
        createCell(row, 2, "Логин", style);
        createCell(row, 3, "Email", style);
        createCell(row, 4, "Номер телефона", style);
        createCell(row, 5, "Адрес", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Client client : listClients) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            String email = "Не указано";
            String phoneNum = "Не указано";
            String address = "Не указано";

            if (client.getContacts() != null) {
                Contacts contacts = client.getContacts();

                email = contacts.getEmail() != null ? contacts.getEmail() : "Не указано";
                phoneNum = contacts.getPhoneNumber() != null ? contacts.getPhoneNumber() : "Не указано";
                address = contacts.getPhoneNumber() != null ? contacts.getPhoneNumber() : "Не указано";
            }

            String ClientFIO = client.getSurname() + " " + client.getName() + " " + client.getPatronymic();

            createCell(row, columnCount++, client.getId().toString(), style);
            createCell(row, columnCount++, ClientFIO, style);
            createCell(row, columnCount++, client.getLogin().toString(), style);
            createCell(row, columnCount++, email, style);
            createCell(row, columnCount++, phoneNum, style);
            createCell(row, columnCount, address, style);
        }
    }

    public void export(String currentDateTime) throws IOException {
        writeHeaderLine();
        writeDataLines();

        String path = "load/clients_" + currentDateTime + ".xlsx";

        File excelFile = new File(path);

        OutputStream outputStream = Files.newOutputStream(Paths.get(path));
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
