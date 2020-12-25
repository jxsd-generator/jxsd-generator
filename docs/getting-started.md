# How to use

1. Checkout the project
2. Change the value of `dir.in` and `dir.out` params in the `src/main/resources/application.properties` file
3. Run `mvn install` command, it will build a fat jar
4. Push the CSV files and the `version.txt` file in the `dir.in` directory
5. Run the fat jar
6. Get your XSD in the `dir.out` directory

Here is the format of the version file:
* Name : `version.txt`
* content: the number of version

Here is the format of the CSV file:
* Name: the name of root element with `.csv`
* Example of content:

```
﻿Name;Description;Min;Max;Format;Length;Nullable;Values
Id;Identifier;1;1;N;8;;
SUBOBJECT;Sub object;0;1;;;;
```
