package gen;

import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import exception.CannotImportExcelException;

public class ExcelLoader{
    private String fileName;
    private ArrayList<Question> questions;
    private File file;

    public ExcelLoader(String fileName){
        this.fileName = fileName;
        this.questions = new ArrayList<Question>();
    }

    public ExcelLoader(File file){
        this.file = file;
        this.questions = new ArrayList<Question>();
    }

    public void loadExcel() throws CannotImportExcelException{
        XSSFWorkbook workbook = null;
        try {
            if(file==null){
                InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);
                workbook =  new XSSFWorkbook(is);
            }else{
                FileInputStream fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
            }

            XSSFSheet sheet = workbook.getSheetAt(0);
            retrieveData(sheet);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CannotImportExcelException(e.getMessage());
        }finally{
            try {
                workbook.close();
            } catch (Exception e) {}
        }
    }

    private void retrieveData(XSSFSheet sheet){
        DataFormatter dataFormatter = new DataFormatter();
        questions = new ArrayList<>();

        Iterator<Row> rowIter = sheet.iterator();
        
        while(rowIter.hasNext()){
            Row row = rowIter.next();
            Question question = new Question();

            Iterator<Cell> cellIter = row.cellIterator();
            int qCtr = 0;
            while(cellIter.hasNext()){
                Cell cell = cellIter.next();
                String cellVal = dataFormatter.formatCellValue(cell);
                if(qCtr==0)
                    question.setId(cellVal);
                else if(qCtr==1)
                    question.setCategory(cellVal);
                else if(qCtr==2)
                    question.setQuestion(cellVal);
                else if(qCtr>2 && qCtr<7)
                    question.addChoice(cellVal);
                else if(qCtr==7)
                    question.setAnswer(cellVal);
                else if(qCtr==8)
                    question.setImagePath(cellVal);
                qCtr++;
            }
            questions.add(question);
        }
    }

    public ArrayList<Question> getQuestions(){
        return this.questions;
    }

}