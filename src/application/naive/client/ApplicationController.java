package application.naive.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import application.chart.management.VisualizationEngine;
import application.jtable.management.JTableViewer;
import file.manager.StructuredFileManagerFactory;
import file.manager.StructuredFileManagerInterface;
import metadata.NaiveFileMetadataManager;

public class ApplicationController {

    private final StructuredFileManagerInterface fileManager;
    private final VisualizationEngine visualizationEngine;
    public String alias;
    public ApplicationController(){

        StructuredFileManagerFactory engineFactory = new StructuredFileManagerFactory();
        this.fileManager = engineFactory.createStructuredFileManager();
        this.visualizationEngine = new VisualizationEngine();
    }

    public File regFile(String pAlias, String pPath, String pSeparator){
        alias = pAlias;
        File resultFile = null;
        try {
            resultFile = this.fileManager.registerFile(pAlias, pPath, pSeparator);
        } catch (NullPointerException e) {
            System.err.println("ApplicationController::regFile NullPointerException");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("ApplicationController::regFile IOException");
            e.printStackTrace();
        }
        return resultFile;
    }

    public String[] fieldNames(){
        String[] names = fileManager.getFileColumnNames(alias);
        return names;
    }

    public List<String[]> filterApplied(String pAlias, Map<String, List<String>> pAtomicFilters){
        List <String[]> worksFilters = fileManager.filterStructuredFile(pAlias,pAtomicFilters);
        return worksFilters;
    }

    public int printResults(List<String[]> recordList, PrintStream pOut){
        int numOfRec = fileManager.printResultsToPrintStream(recordList,pOut);
        return numOfRec;
    }

    public void visualize(String pAlias, List<String[]> series, String pXAxisName, String pYAxisName,
                          String outputFileName, int pXPosition, int pYPosition, String type){
        if(type.equals("line")){
        visualizationEngine.showSingleSeriesLineChart(pAlias,series,pXAxisName,pYAxisName,outputFileName,pXPosition,pYPosition);
        }
        else if(type.equals("bar")) {
            visualizationEngine.showSingleSeriesBarChart(pAlias,series,pXAxisName,pYAxisName,outputFileName,pXPosition,pYPosition);
        }
    }


}
