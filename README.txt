Requirments:
1. Linux OS
2. junit4 for testing
3. xchart-3.8.1 for chart output.

Below is a sample run of the program where "Q: " is a question from the application
and  "A: " or "(A:)" is the input of the user.

Q: Please enter the path of the file:
A: ./test/resources/input/simple.csv
Q: Please enter the symbolic name of the file:
A: simple
Q: Please enter the separator of the file:
A: ,

//It will print the file column titles. For our file are the following.

Q: HF:Financing scheme, HC:Function, HP:Provider, LOCATION:Country, TIME:Year, MSR:Value
Q: Type the name of the field you want to set a filter for or type 'exit' to finish adding filters:
A: LOCATION:Country
Q: Add comma (,) separated values to check
A: AUS:Australia
Q: Type the name of the field you want to set a filter for or type 'exit' to finish adding filters:
A: exit
Q: Give a name to the result of the filter:
A: ausFilter
Q: Do you want to print the result? (yes/no)
A: yes
Q: Do you want to save output to a file?: (yes/no)
A: yes
Q: Enter the name of the file:
A: ausOutfile
Q: Select the type of chart you want: (line/bar)
A: bar
Q: HF:Financing scheme, HC:Function, HP:Provider, LOCATION:Country, TIME:Year, MSR:Value
Q: Choose name for x axis:
A: Year
Q: Choose name for y axis:
A: Value
Q: Enter number of field for x axis: (starts from 0)
A: 4
Q: Enter number of field for y axis: (starts from 0)
A: 5
Q: Enter output file name or blank (press enter) to not save:(A:)ausImage


For the above inputs the application creates a txt file named "ausOutfile" consisted of the rows
that pass the filter given by the user and a png image named "ausImage.png" of a bar chart
with the TIME:Year (4) in the x axis and the MSR:Value (5) in the y axis.