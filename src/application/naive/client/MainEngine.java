package application.naive.client;
import filtering.FilteringEngine;
import metadata.NaiveFileMetadataManager;

import java.io.*;
import java.util.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainEngine {


    public static void main(String args[]) throws FileNotFoundException {

        ApplicationController applicationController = new ApplicationController();

        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the path of the file: ");
        String filePath = input.nextLine();
        System.out.println("Please enter the symbolic name of the file: ");
        String fileAlias = input.nextLine();
        System.out.println("Please enter the seperator of the file: ");
        String fileSeperator = input.nextLine();
        File returnedFile = applicationController.regFile(fileAlias, filePath, fileSeperator);
        String[] fieldNames = applicationController.fieldNames();
        System.out.println(Arrays.toString(fieldNames).replace("[","").replace("]",""));
        HashMap<String, List<String>> filterMap = new HashMap<>();
        while (true) {
            System.out.println("Type the name of the field you want to set a filter for or type 'exit' to finish adding filters: ");
            String field = input.nextLine();
            if (field.equals("exit")) {
                break;
            }
            System.out.println("Add comma (,) seperated values to check");
            String[] filterValues = input.nextLine().split(",");

            filterMap.put(field, Arrays.asList(filterValues));

        }

        List<String[]> resultAfterFilters = applicationController.filterApplied(fileAlias, filterMap);


        HashMap<String, List<String[]>> history = new HashMap<>();
        System.out.println("Give a name to the result of the filter: ");
        String resultName = input.nextLine();
        history.put(resultName, resultAfterFilters);

        System.out.println("Do you want to print the result? (yes/no) ");
        String tempAnswer = input.nextLine();
        if ((tempAnswer).equals("yes")) {
            PrintStream out;
            System.out.println("Do you want to save output to a file?: (yes/no) ");
            tempAnswer = input.nextLine();
            if (tempAnswer.equals("yes")) {
                System.out.println("Enter the name of the file: ");
                tempAnswer = input.nextLine();
                out = new PrintStream(new File("./resources/Output/" + tempAnswer));
            } else {
                out = new PrintStream(System.out);
            }
            List<String[]> tempValue = history.get(resultName);
            applicationController.printResults(tempValue, out);

            System.out.println("Select the type of chart you want: (line/bar) ");
            String type = input.nextLine();

            System.out.println(Arrays.toString(fieldNames).replace("[","").replace("]",""));

            System.out.println("Choose name for x axis: ");
            String pXAxisName = input.nextLine();
            System.out.println("Choose name for y axis: ");
            String pYAxisName = input.nextLine();

            System.out.println("Enter number of field for x axis: (starts from 0)  ");
            int pXPosition = input.nextInt();
            System.out.println("Enter number of field for y axis: (starts from 0)  ");
            int pYPosition = input.nextInt();

            System.out.print("Enter output file name or blank (press enter) to not save:");
            String outputFileName = input.next();

            if(!outputFileName.isEmpty()) {
                outputFileName = "./resources/Output/" + outputFileName;
            }
            applicationController.visualize(fileAlias, tempValue, pXAxisName, pYAxisName, outputFileName, pXPosition, pYPosition, type);

        }
    }
}