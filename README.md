## This project contains examples of how to use JWebAssembly
I found it difficult to get this working the first time, so this is 
the simple HelloWorld example (other examples to come?). 

## Tools Used
- Intellij
- Java 8 (jdk1.8.0_261)

## Steps
- Open Project in Intellij
- Note the build.gradle settings. It took some fiddling to get that right
- Set Java SDK to Java 8 (jdk1.0.0_261)
  - **IMPORTANT**: It MUST be Java 8. I tried newer versions, and they fail.
  - File &rarr; Project_Structure &rarr; Project &rarr; Project SDK &rarr; 1.8 java version "1.0.0_261"
  - Also, Check that the "Project language level" is "SDK default (8 - Lambdas, type annotations etc.)
- If Gradle pane is not open, do View &rarr; Tool Windows &rarr; Gradle
- In Gradle pane
  - Hit Refresh
  - Under Tasks &rarr; Build, select "clean"
  - If clean is successful, then do "build"
- After building, check for build/distributions directory with your wasm files. There should be the same three in the "resources" directory
- To use, reference the index.html in resources/, and the official documentation


## Next Steps
If you can get this working, then just edit HelloWorld.java to suit your needs. Just recall that you need to use
@Export to export your method for use in javascript (in the index.html). 