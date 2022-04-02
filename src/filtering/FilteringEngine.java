package filtering;

import file.manager.StructuredFileManager;
import metadata.MetadataManagerInterface;

import java.io.*;
import java.util.*;

public class FilteringEngine implements FilteringEngineInterface{

    public Map<String, List<String>> pAtomicFilters;
    public MetadataManagerInterface pMetadata;
    public StructuredFileManager structuredFileManager;

    public File file;
    public FilteringEngine(Map<String, List<String>> atomicFilters, MetadataManagerInterface metadata){
        this.pAtomicFilters = atomicFilters;
        this.pMetadata = metadata;
    }
    @Override
    public int setupFilteringEngine(Map<String, List<String>> pAtomicFilters, MetadataManagerInterface pMetadataManager) {
        file = pMetadata.getDataFile();
        if(file.exists()) {
            return 0;
        }
        else{
            return -1;
        }
    }

    @Override
    public List<String[]> workWithFile() {
        List<String[]> filters = new ArrayList<>();
        String[] colsNames = pMetadata.getColumnNames();
        String[] colNamesLine;
        int x = setupFilteringEngine(pAtomicFilters, pMetadata);
        if (x == 0) {

            String line = "";
            ArrayList<Integer> colPosToCheck = new ArrayList<>();

            for (String i : pAtomicFilters.keySet()) {

                for (int j = 0; j < colsNames.length; j++) {
                    if (i.equals(colsNames[j])) {
                        colPosToCheck.add(j);
                    }
                }
            }

            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                while ((line = br.readLine()) != null) {
                    boolean isBoth = false;
                    colNamesLine = line.split(pMetadata.getSeparator());
                    int checker = 0;
                    for (int i = 0; i < colPosToCheck.size(); i++) {
                        if (pAtomicFilters.get(colsNames[colPosToCheck.get(i)]).contains(colNamesLine[colPosToCheck.get(i)])){
                            checker++;
                        }

                    }
                    if(checker == colPosToCheck.size()){

                        filters.add(colNamesLine);
                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }





        }
        return filters;
    }
}
