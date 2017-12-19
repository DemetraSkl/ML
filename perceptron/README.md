# Perceptron algorithm implementation

The primal, dual and kernel versions of the perceptron algorithm are implemented and evaluated. 

## Installing

To build the source code I used ant. All tasks are defined in the
build.xml file. The main code routine is in Main.java

To get the results for all experiments simply run ant.

The test script expects:

additionalTraining.arff, and
additionalTest.arff

The test script is a bash script that runs the compiled java
code with test arguments and should be readily executable.

## Author

Demetra Skl

## Acknowledgements

Data for these experiments has been obtained from [UCI's machine learning repository](https://archive.ics.uci.edu/ml/index.php).