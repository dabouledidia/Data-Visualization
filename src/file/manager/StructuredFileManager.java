package file.manager;
import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import filtering.FilteringEngine;
import metadata.NaiveFileMetadataManager;

public class StructuredFileManager implements StructuredFileManagerInterface {

    public StructuredFileManager(){}

    private NaiveFileMetadataManager naiveFileMetadataManager;
    private FilteringEngine filteringEngine;
    private String separator;
    private String alias; //Path
    private String name;
    private File res;

    @Override
    public File registerFile(String pAlias, String pPath, String pSeparator) throws IOException, NullPointerException {
        name = pAlias;
        res = new File(pPath);
        alias = res.getPath();
        separator = pSeparator;
        if(res == null){
            throw new NullPointerException();
        }
        else if(!res.exists()){
            throw new IOException();
        }
        return res;
    }


    @Override
    public String[] getFileColumnNames(String pAlias) {
        boolean check = name.equals(pAlias);
        pAlias = alias;
        String line;
        String[] colNames = null;
        String[] empty = {};
        if(!check){
            return empty;
        }
        else{
            naiveFileMetadataManager = new NaiveFileMetadataManager(name, res, separator);
            colNames = naiveFileMetadataManager.getColumnNames();
        }
        return colNames;
    }
    @Override
    public List<String[]> filterStructuredFile(String pAlias, Map<String, List<String>> pAtomicFilters) {
        List<String[]> filters = new ArrayList<>();
        naiveFileMetadataManager = new NaiveFileMetadataManager(name, res, separator);
        filteringEngine = new FilteringEngine(pAtomicFilters, naiveFileMetadataManager);
        filters = filteringEngine.workWithFile();
        return filters;
    }

    @Override
    public int printResultsToPrintStream(List<String[]> recordList, PrintStream pOut) {
        int numOfRecords = recordList.size();
        if(pOut == null){
            return -1;
        }
        for(int i = 0; i < recordList.size(); i++){
            pOut.println(Arrays.toString(recordList.get(i)).replace("[","").replace("]",""));
        }
        return numOfRecords;
    }

}
