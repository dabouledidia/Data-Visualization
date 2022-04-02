package metadata;

import file.manager.StructuredFileManager;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class NaiveFileMetadataManager implements MetadataManagerInterface {

    private File file;
    private String alias;
    private String separator;
    private Map<String, Integer> fieldPos = new HashMap<>();


    public NaiveFileMetadataManager(String pAlias, File pFile, String pSeparator){
            this.separator = pSeparator;
            this.file = pFile;
            this.alias = pAlias;
            if(!file.exists()){
                throw new NullPointerException();
            }
    }
    public String getAlias(){

        return alias;
    }
    @Override
    public Map<String, Integer> getFieldPositions() {
        String[] cols = getColumnNames();
        for(int i = 0; i<cols.length;i++){
            fieldPos.put(cols[i],i);
        }
        return fieldPos;
    }

    @Override
    public File getDataFile() {
        return  file;
    }

    @Override
    public String getSeparator() {

        return separator;
    }

    @Override
    public String[] getColumnNames() {
        String line;
        String[] colNames = null;
        Scanner br = null;
        try {
            br = new Scanner(new File(file.getPath()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        br.useDelimiter(separator);
        while(br.hasNext()){
            line = br.nextLine();
            colNames = line.split(separator);
            break;
        }
        br.close();
        return colNames;
    }
}

