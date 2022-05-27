package gen;

import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook; 

public class ExcelLoader{
    private String fileName;
    private ArrayList<Question> questions;

    public ExcelLoader(String fileName){
        this.fileName = fileName;
        this.questions = new ArrayList<Question>();
    }

    public void loadExcel(){
        URL url = this.getClass().getClassLoader().getResource(fileName);
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodePath = (new File(path)).getParentFile().getPath();
        String xlsxFile = decodePath+"/imports/questions.xlsx";


        // try {
        //     decodePath = URLDecoder.decode(path, "UTF-8");
        // } catch (UnsupportedEncodingException e1) {
        //     // TODO Auto-generated catch block
        //     e1.printStackTrace();
        // }
        System.out.println(" pAHJT "+new File(xlsxFile).exists());

        try {
            FileInputStream fis = new FileInputStream(new File(url.toURI()));
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            retrieveData(sheet);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
            //report an error
        }
    }

    private void retrieveData(XSSFSheet sheet){
        DataFormatter dataFormatter = new DataFormatter();

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