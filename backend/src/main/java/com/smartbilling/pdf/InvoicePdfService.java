package com.smartbilling.pdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.smartbilling.domain.Invoice;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.awt.Color;

@Service
public class InvoicePdfService {
    public byte[] generate(Invoice invoice, String businessName, String address, String gstin) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4, 36, 36, 36, 36);
            PdfWriter.getInstance(document, out);
            document.open();

            document.add(new Paragraph(businessName, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            document.add(new Paragraph(address));
            document.add(new Paragraph("GSTIN: " + gstin));
            document.add(new Paragraph("Invoice: " + invoice.getInvoiceNo()));
            document.add(new Paragraph("Customer: " + invoice.getCustomer().getName()));
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(new float[]{4, 1, 2, 2});
            table.setWidthPercentage(100);
            addHeader(table, "Item");addHeader(table, "Qty");addHeader(table, "Rate");addHeader(table, "Total");
            invoice.getItems().forEach(i -> {
                table.addCell(i.getProduct().getName());
                table.addCell(String.valueOf(i.getQuantity()));
                table.addCell(String.format("%.2f", i.getUnitPrice()));
                table.addCell(String.format("%.2f", i.getLineTotal()));
            });
            document.add(table);
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Grand Total: " + invoice.getGrandTotal()));
            document.add(new Paragraph("Paid: " + invoice.getPaidAmount()));
            document.add(new Paragraph("Due: " + (invoice.getGrandTotal() - invoice.getPaidAmount())));
            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to generate PDF");
        }
    }

    private void addHeader(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        table.addCell(cell);
    }
}
