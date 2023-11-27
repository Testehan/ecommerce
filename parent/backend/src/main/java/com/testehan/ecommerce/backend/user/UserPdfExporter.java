package com.testehan.ecommerce.backend.user;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.testehan.ecommerce.common.entity.User;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class UserPdfExporter extends AbstractExporter{
    public void export(List<User> users, HttpServletResponse response) throws IOException {
        setupResponseHeader(response,"pdf","application/pdf");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(Color.RED);
        Paragraph documentTitle = new Paragraph("List of users", font);
        documentTitle.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(documentTitle);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(15);
        table.setWidths(new float[] {1.2f, 3.5f, 3.0f, 3.0f, 3.0f, 1.7f});
        
        writeTableHeader(table);
        writeTableData(table,users);
        
        document.add(table);
        
        document.close();

    }
    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.blue);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Email",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("First Name",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Last Name",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Roles",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Enabled",font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table, List<User> users) {
        for (User user : users){
            table.addCell(String.valueOf(user.getId()));
            table.addCell(user.getEmail());
            table.addCell(user.getFirstName());
            table.addCell(user.getLastName());
            table.addCell(user.getRoles().toString());
            table.addCell(String.valueOf(user.isEnabled()));
        }
    }

}
