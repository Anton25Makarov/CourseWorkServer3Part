package by.bsuir.course.database;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileReaderWriter {

    public DataBaseData getDbData() {

        DataBaseData dataBaseData = null;

        if (!Files.exists(Paths.get("C:\\Users\\Acer\\eclipse-workspace\\" +
                "CourseWork\\Try\\CourseWorkServerFinal\\out" +
                "\\artifacts\\CourseWorkServer_jar\\data.txt"))) {
            return null;
        }
        try {
            if (Files.size(Paths.get("C:\\Users\\Acer\\eclipse-workspace\\" +
                    "CourseWork\\Try\\CourseWorkServerFinal\\out" +
                    "\\artifacts\\CourseWorkServer_jar\\data.txt")) == 0) {
                dataBaseData = new DataBaseData();
                dataBaseData.setReferees(new ArrayList<>());
                dataBaseData.setSportsmen(new ArrayList<>());
                return dataBaseData;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try (FileInputStream fileIn = new FileInputStream("C:\\Users\\Acer\\eclipse-workspace\\" +
                "CourseWork\\Try\\CourseWorkServerFinal\\out" +
                "\\artifacts\\CourseWorkServer_jar\\data.txt");
             ObjectInputStream oi = new ObjectInputStream(fileIn)) {

            dataBaseData = (DataBaseData) oi.readObject();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dataBaseData;
    }

    public void setDbData(DataBaseData dbData) {

        try {
            File file = new File("C:\\Users\\Acer\\eclipse-workspace" +
                    "\\CourseWork\\Try\\CourseWorkServerFinal\\" +
                    "out\\artifacts\\CourseWorkServer_jar\\data.txt");
            try (FileOutputStream fos = new FileOutputStream(file);
                 ObjectOutputStream o = new ObjectOutputStream(fos)) {
                o.writeObject(dbData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
