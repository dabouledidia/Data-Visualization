package application.naive.client;

import static org.junit.Assert.*;

import file.manager.StructuredFileManager;
import filtering.FilteringEngine;
import metadata.MetadataManagerInterface;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import file.manager.StructuredFileManagerInterface;
import metadata.NaiveFileMetadataManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class ApplicationControllerTest {
    private static ApplicationController appController;
    private static List<String[]> result ;
    private static NaiveFileMetadataManager naiveFileMetadataManager;
    private static StructuredFileManager structuredFileManager;
    private static Map<String, List<String>> atomicFilters;
    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        appController = new ApplicationController();
        structuredFileManager = new StructuredFileManager();
        atomicFilters = new HashMap<String, List<String>>();
        List<String> countryFilter = new ArrayList<String>();
        countryFilter.add("AUS:Australia");
        atomicFilters.put("LOCATION:Country", countryFilter);
        //structuredFileManager = new FilteringEngine(atomicFilters, naiveFileMetadataManager);
        result = new ArrayList<String[]>();

        //String sAlias = "simple";
        //String sSeparator = ",";
        //appController.regFile(sAlias, "./test/resources/input/simple.csv", sSeparator);


        String[] result1 = {"HFTOT:All financing schemes", "HCTOT:Current expenditure on health (all functions)", "HPTOT:All providers", "AUT:Austria", "2010", "4261.055"};
        String[] result2 = {"HFTOT:All financing schemes", "HCTOT:Current expenditure on health (all functions)", "HPTOT:All providers", "AUT:Austria", "2011", "4345.16"};

        result.add(result1); result.add(result2);

    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testRegFileHappy(){
        String expPath = "./test/resources/input/simple.csv";
        File res = appController.regFile("simple", "./test/resources/input/simple.csv", ",");
        assertEquals(expPath, res.getPath());
    }

    @Test
    public void testFieldNamesHappy(){
        File res = appController.regFile("simple", "./test/resources/input/simple.csv", ",");
        naiveFileMetadataManager = new NaiveFileMetadataManager(res.getName(),res,",");
        String[] resColNames = naiveFileMetadataManager.getColumnNames();
        String[] expectedColNames= {"HF:Financing scheme","HC:Function","HP:Provider","LOCATION:Country","TIME:Year","MSR:Value"};
        for (int i =0; i< expectedColNames.length;i++) {
            if (!resColNames[i].equals(expectedColNames[i]))
                fail("Erroneous col name arrays");
        }
    }

    @Test
    public void testFilterAppliedHappy(){
        Map<String, List<String>> multiCriteriaAtomicFilters = new HashMap<String, List<String>>(atomicFilters);
        List<String> timeFilter = new ArrayList<String>();
        timeFilter.add("2010");
        timeFilter.add("2011");
        timeFilter.add("2012");
        multiCriteriaAtomicFilters.put("TIME:Year", timeFilter);
        File res = appController.regFile("simple", "./test/resources/input/simple.csv", ",");
        List <String[]> worksFilters = appController.filterApplied(res.getName(),multiCriteriaAtomicFilters);
        assertEquals(3, worksFilters.size());
    }

    @Test
    public void testPrintResults(){
        String outputFilePath = "./test/resources/output/testPrintStreamHappy.txt";
        String[] result1 = {"HFTOT:All financing schemes", "HCTOT:Current expenditure on health (all functions)", "HPTOT:All providers", "AUT:Austria", "2010", "4261.055"};
        String[] result2 = {"HFTOT:All financing schemes", "HCTOT:Current expenditure on health (all functions)", "HPTOT:All providers", "AUT:Austria", "2011", "4345.16"};
        List<String[]> result = new ArrayList<String[]>();
        result.add(result1);
        result.add(result2);

        int numRows = 0;
        FileOutputStream fOutStream = null;
        try {
            fOutStream = new FileOutputStream(outputFilePath);
        } catch (FileNotFoundException e) {
            System.err.println("structuredFileManagerTest::testPrintResultsHappyDay() failed to open fout stream");
            e.printStackTrace();
        }
        PrintStream pOutStream = new PrintStream(fOutStream);
        numRows = appController.printResults(result, pOutStream);
        assertEquals(2, numRows);

    }

    @Test
    public void testVisualize(){
        String outputFilePath = "./test/resources/output/naiveTestLine.png";
        File cleanUp = new File(outputFilePath);
        if(cleanUp.exists()) {
            boolean deletionStatus = cleanUp.delete();
            if (deletionStatus == false) {
                fail("NaiveAppControllerTest::testBarChartHappy(): could not cleanup");
            }
        }
        //"HF:Financing scheme","HC:Function","HP:Provider","LOCATION:Country","TIME:Year","MSR:Value"
        appController.visualize("simple", result, "TIME:Year", "MSR:Value", "./test/resources/output/naiveTestLine", 4,5,"line");
        File refFile = new File(outputFilePath);
        if (!refFile.exists())
            fail("NaiveAppControllerTest::testLineChartHappy(): output was not created");
        assertTrue(refFile.exists());
    }

}
