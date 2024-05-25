package com.example.diplomproject.report;

import com.example.diplomproject.model.entity.*;
import com.example.diplomproject.model.entity.enumStatus.StatusApplication;
import com.example.diplomproject.model.entity.enumStatus.StatusApplicationForRelease;
import com.example.diplomproject.model.entity.marking.TypeMarking;
import com.example.diplomproject.repository.*;
import com.example.diplomproject.service.ApplicationForReleaseService;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GenerateReport {

    private final MarkingInfoRepository markingInfoRepository;
    private final ApplicationForStorageRepository applicationForStorageRepository;
    private final DeliveryProductRepository deliveryProductRepository;
    private final OtpuskRepository otpuskRepository;


    @SneakyThrows
    public byte[] generatePDF(Path docFilePath, String name){
        try (InputStream docxInputStream = new FileInputStream(String.valueOf(docFilePath));
             OutputStream pdfOutputStream = new FileOutputStream(name)) {
            Document pdfDocument = new Document();
            PdfWriter.getInstance(pdfDocument, pdfOutputStream);
            pdfDocument.open();

            // Используем Apache POI для чтения документа Word
            XWPFDocument doc = new XWPFDocument(docxInputStream);
            for (XWPFParagraph para : doc.getParagraphs()) {
                Font font = new Font(Font.TIMES_ROMAN, 14, Font.NORMAL);
                if (para.getRuns().size() > 0) {
                    XWPFRun run = para.getRuns().get(0);
                    if (run.isBold()) {
                        font.setStyle(Font.BOLD);
                    }
                    font.setFamily(run.getFontFamily());
                    font.setSize(run.getFontSize() != -1 ? run.getFontSize() : 12);
                }

                Paragraph paragraph = new Paragraph(para.getText(), font);
                switch (para.getAlignment()) {
                    case CENTER:
                        paragraph.setAlignment(Element.ALIGN_CENTER);
                        break;
                    case RIGHT:
                        paragraph.setAlignment(Element.ALIGN_RIGHT);
                        break;
                    default:
                        paragraph.setAlignment(Element.ALIGN_LEFT);
                        break;
                }
                pdfDocument.add(paragraph);
            }
            pdfDocument.add(new Paragraph("\n"));

            for (XWPFTable tbl : doc.getTables()) {
                PdfPTable pdfTable = new PdfPTable(6);
                float pageWidth = pdfDocument.getPageSize().getWidth() - pdfDocument.leftMargin() - pdfDocument.rightMargin();
                pdfTable.setTotalWidth(pageWidth);
                pdfTable.setLockedWidth(true);

                for (XWPFTableRow row : tbl.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        PdfPCell pdfCell = new PdfPCell(new Paragraph(cell.getText()));
                        pdfTable.addCell(pdfCell);
                    }
                }
                pdfDocument.add(pdfTable);
            }

            pdfDocument.close();
        }
        return Files.readAllBytes(new File(name).toPath());
    }
    @SneakyThrows
    public byte[] generateMarkingProd() {
        XWPFDocument document = new XWPFDocument();

        XWPFParagraph heading = document.createParagraph();
        heading.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun headingRun = heading.createRun();
        headingRun.setText("Отчет о работе склада");
        headingRun.setBold(true);
        headingRun.setFontFamily("Times New Roman");
        headingRun.setFontSize(14);


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();

        XWPFParagraph dateheading = document.createParagraph();
        dateheading.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun dateHeadingRun = dateheading.createRun();
        dateHeadingRun.setText("Подготовлено " + dateFormat.format(date));
        dateHeadingRun.setBold(true);
        dateHeadingRun.setFontFamily("Times New Roman");
        dateHeadingRun.setFontSize(14);

        XWPFParagraph subheading = document.createParagraph();
        subheading.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun subheadingRun = subheading.createRun();
        subheadingRun.setText("Общая информация:");
        subheadingRun.setBold(true);
        subheadingRun.setFontFamily("Times New Roman");
        subheadingRun.setFontSize(14);
        List<MarkingInfo> markingInfo = markingInfoRepository.findAll();
        XWPFTable table = document.createTable(markingInfo.size(), 6);

        XWPFTableRow headerRow = table.getRow(0);
        headerRow.getCell(0).setText("Наименование");
        headerRow.getCell(1).setText("Код продукта");
        headerRow.getCell(2).setText("Вес брутто");
        headerRow.getCell(3).setText("Срок годности");
        headerRow.getCell(4).setText("Тип маркировки");
        headerRow.getCell(5).setText("Дата нанесения");

        markingInfo.forEach(p -> {
            XWPFTableRow dataRow = table.createRow();
            dataRow.getCell(0).setText(p.getProduct().getNameProduct());
            dataRow.getCell(1).setText(p.getProduct().getProductCode());
            dataRow.getCell(2).setText(String.valueOf(p.getProduct().getGrossWeight()));
            dataRow.getCell(3).setText(String.valueOf(p.getProduct().getFinalDate()));
            dataRow.getCell(4).setText(TypeMarking.getRussianName().get(p.getTypeMarking()));
            dataRow.getCell(5).setText(String.valueOf(p.getDatePut()));

        });
        File tempDocxFile = File.createTempFile("temp", ".docx");
        FileOutputStream docxOutputStream = new FileOutputStream(tempDocxFile);
        document.write(docxOutputStream);
        document.close();

        return generatePDF(tempDocxFile.toPath(), "reportMark.pdf");
    }


    @SneakyThrows
    public byte[] generateActiveWord() {
        XWPFDocument document = new XWPFDocument();

        XWPFParagraph heading = document.createParagraph();
        heading.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun headingRun = heading.createRun();
        headingRun.setText("Отчет о работе склада");
        headingRun.setBold(true);
        headingRun.setFontFamily("Times New Roman");
        headingRun.setFontSize(14);


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();

        XWPFParagraph dateheading = document.createParagraph();
        dateheading.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun dateHeadingRun = dateheading.createRun();
        dateHeadingRun.setText("Подготовлено " + dateFormat.format(date));
        dateHeadingRun.setBold(true);
        dateHeadingRun.setFontFamily("Times New Roman");
        dateHeadingRun.setFontSize(14);

        XWPFParagraph subheading = document.createParagraph();
        subheading.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun subheadingRun = subheading.createRun();
        subheadingRun.setText("Общая информация:");
        subheadingRun.setBold(true);
        subheadingRun.setFontFamily("Times New Roman");
        subheadingRun.setFontSize(14);
        List<ApplicationForStorage> application = applicationForStorageRepository.findAllByStatusApplication(StatusApplication.PENDING);
        XWPFTable table = document.createTable(application.size(), 6);

        XWPFTableRow headerRows = table.getRow(0);
        headerRows.getCell(0).setText("Дата поставки");
        headerRows.getCell(1).setText("Количество продуктов");
        headerRows.getCell(2).setText("Дата заявления");
        headerRows.getCell(3).setText("Номер декларации");
        headerRows.getCell(4).setText("Номер CRM");
        headerRows.getCell(5).setText("Номер ТТН");

        application.forEach(p -> {
            XWPFTableRow dataRow = table.createRow();
            dataRow.getCell(0).setText(String.valueOf(p.getDatePost()));
            dataRow.getCell(1).setText(String.valueOf(p.getCountPositionProducts()));
            dataRow.getCell(2).setText(String.valueOf(p.getDateZav()));
            dataRow.getCell(3).setText(String.valueOf(p.getDeclarationTD().getDeclarationNumber()));
            dataRow.getCell(4).setText(p.getCrm().getNumbers());
            dataRow.getCell(5).setText(p.getGoodTransportDocument().getGoodsTransportDocumentNumbers());

        });
        File tempDocxFile = File.createTempFile("active", ".docx");
        FileOutputStream docxOutputStream = new FileOutputStream(tempDocxFile);
        document.write(docxOutputStream);
        document.close();

        return generatePDF(tempDocxFile.toPath(), "active.pdf");
    }

    @SneakyThrows
    public byte[] generateHistoryWord() {
        XWPFDocument document = new XWPFDocument();

        XWPFParagraph heading = document.createParagraph();
        heading.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun headingRun = heading.createRun();
        headingRun.setText("Отчет о работе склада");
        headingRun.setBold(true);
        headingRun.setFontFamily("Times New Roman");
        headingRun.setFontSize(14);


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();

        XWPFParagraph dateheading = document.createParagraph();
        dateheading.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun dateHeadingRun = dateheading.createRun();
        dateHeadingRun.setText("Подготовлено " + dateFormat.format(date));
        dateHeadingRun.setBold(true);
        dateHeadingRun.setFontFamily("Times New Roman");
        dateHeadingRun.setFontSize(14);

        XWPFParagraph subheading = document.createParagraph();
        subheading.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun subheadingRun = subheading.createRun();
        subheadingRun.setText("Общая информация:");
        subheadingRun.setBold(true);
        subheadingRun.setFontFamily("Times New Roman");
        subheadingRun.setFontSize(14);
        List<ApplicationForStorage> application = applicationForStorageRepository.findAllByStatusApplication(StatusApplication.COMPLETED);
        XWPFTable table = document.createTable(application.size(), 6);

        XWPFTableRow headerRows = table.getRow(0);
        headerRows.getCell(0).setText("Дата поставки");
        headerRows.getCell(1).setText("Количество продуктов");
        headerRows.getCell(2).setText("Дата заявления");
        headerRows.getCell(3).setText("Номер декларации");
        headerRows.getCell(4).setText("Номер CRM");
        headerRows.getCell(5).setText("Номер ТТН");

        application.forEach(p -> {
            XWPFTableRow dataRow = table.createRow();
            dataRow.getCell(0).setText(String.valueOf(p.getDatePost()));
            dataRow.getCell(1).setText(String.valueOf(p.getCountPositionProducts()));
            dataRow.getCell(2).setText(String.valueOf(p.getDateZav()));
            dataRow.getCell(3).setText(String.valueOf(p.getDeclarationTD().getDeclarationNumber()));
            dataRow.getCell(4).setText(p.getCrm().getNumbers());
            dataRow.getCell(5).setText(p.getGoodTransportDocument().getGoodsTransportDocumentNumbers());

        });
        File tempDocxFile = File.createTempFile("history", ".docx");
        FileOutputStream docxOutputStream = new FileOutputStream(tempDocxFile);
        document.write(docxOutputStream);
        document.close();

        return generatePDF(tempDocxFile.toPath(), "history.pdf");
    }

    @SneakyThrows
    public byte[] generateDeliveryWord() {
        XWPFDocument document = new XWPFDocument();

        XWPFParagraph heading = document.createParagraph();
        heading.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun headingRun = heading.createRun();
        headingRun.setText("Отчет о работе склада");
        headingRun.setBold(true);
        headingRun.setFontFamily("Times New Roman");
        headingRun.setFontSize(14);


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();

        XWPFParagraph dateheading = document.createParagraph();
        dateheading.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun dateHeadingRun = dateheading.createRun();
        dateHeadingRun.setText("Подготовлено " + dateFormat.format(date));
        dateHeadingRun.setBold(true);
        dateHeadingRun.setFontFamily("Times New Roman");
        dateHeadingRun.setFontSize(14);

        XWPFParagraph subheading = document.createParagraph();
        subheading.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun subheadingRun = subheading.createRun();
        subheadingRun.setText("Общая информация:");
        subheadingRun.setBold(true);
        subheadingRun.setFontFamily("Times New Roman");
        subheadingRun.setFontSize(14);
        List<DeliveryProduct> deliveryProductList = deliveryProductRepository.findAll();
        XWPFTable table = document.createTable(deliveryProductList.size(), 6);

        XWPFTableRow headerRows = table.getRow(0);
        headerRows.getCell(0).setText("Вес товара");
        headerRows.getCell(1).setText("Дата доставки");
        headerRows.getCell(2).setText("Состояние товара");
        headerRows.getCell(3).setText("Товар");
        headerRows.getCell(4).setText("Номер заявки");
        headerRows.getCell(5).setText("Дата заявки");

        deliveryProductList.forEach(p -> {
            XWPFTableRow dataRow = table.createRow();
            dataRow.getCell(0).setText(String.valueOf(p.getWeightProduct()));
            dataRow.getCell(1).setText(String.valueOf(p.getArrangeDate()));
            dataRow.getCell(2).setText(String.valueOf(p.getProdCondition()));
            dataRow.getCell(3).setText(String.valueOf(p.getProductList()
                    .stream().map(Product::getNameProduct).collect(Collectors.joining(","))));
            dataRow.getCell(4).setText(String.valueOf(p.getApplicationForStorage().getIdApplication()));
            dataRow.getCell(5).setText(String.valueOf(p.getApplicationForStorage().getDateZav()));

        });
        File tempDocxFile = File.createTempFile("delivery", ".docx");
        FileOutputStream docxOutputStream = new FileOutputStream(tempDocxFile);
        document.write(docxOutputStream);
        document.close();

        return generatePDF(tempDocxFile.toPath(), "delivery.pdf");
    }

    @SneakyThrows
    public byte[] generateOtpuskWord() {
        XWPFDocument document = new XWPFDocument();

        XWPFParagraph heading = document.createParagraph();
        heading.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun headingRun = heading.createRun();
        headingRun.setText("Отчет о работе склада");
        headingRun.setBold(true);
        headingRun.setFontFamily("Times New Roman");
        headingRun.setFontSize(14);


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();

        XWPFParagraph dateheading = document.createParagraph();
        dateheading.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun dateHeadingRun = dateheading.createRun();
        dateHeadingRun.setText("Подготовлено " + dateFormat.format(date));
        dateHeadingRun.setBold(true);
        dateHeadingRun.setFontFamily("Times New Roman");
        dateHeadingRun.setFontSize(14);

        XWPFParagraph subheading = document.createParagraph();
        subheading.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun subheadingRun = subheading.createRun();
        subheadingRun.setText("Общая информация:");
        subheadingRun.setBold(true);
        subheadingRun.setFontFamily("Times New Roman");
        subheadingRun.setFontSize(14);
        List<Otpusk> deliveryProductList = otpuskRepository.findAll();
        XWPFTable table = document.createTable(deliveryProductList.size(), 6);

        XWPFTableRow headerRows = table.getRow(0);
        headerRows.getCell(0).setText("Номер заявления " + "\n" + "на отпуск");
        headerRows.getCell(1).setText("Сумма содержания");
        headerRows.getCell(2).setText("Дата отпуска");
        headerRows.getCell(3).setText("Статус отпуска");
        headerRows.getCell(4).setText("Товары");
        headerRows.getCell(5).setText("Номер отпуска");

        deliveryProductList.forEach(p -> {
            XWPFTableRow dataRow = table.createRow();
            dataRow.getCell(0).setText(String.valueOf(p.getApplicationForRelease().getIdApplicationForRelease()));
            dataRow.getCell(1).setText(String.valueOf(p.getSumForStorage()));
            dataRow.getCell(2).setText(String.valueOf(p.getApplicationForRelease().getDate()));
            dataRow.getCell(3).setText(String.valueOf(StatusApplicationForRelease.getRussianName().get(p.getApplicationForRelease().getStatusApplicationForRelease())));
            dataRow.getCell(4).setText(String.valueOf(p.getApplicationForRelease().getProduct().getNameProduct()));
            dataRow.getCell(5).setText(String.valueOf(p.getId()));

        });
        File tempDocxFile = File.createTempFile("otpusk", ".docx");
        FileOutputStream docxOutputStream = new FileOutputStream(tempDocxFile);
        document.write(docxOutputStream);
        document.close();

        return generatePDF(tempDocxFile.toPath(), "otpusk.pdf");
    }

}
