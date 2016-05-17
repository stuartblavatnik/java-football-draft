del *.jar

del c:\dev\java\draft\jar\com\twoforboth\draft\*.class
del c:\dev\java\draft\jar\com\twoforboth\draft\event\*.class
del c:\dev\java\draft\jar\com\twoforboth\draft\misc\*.class
del c:\dev\java\draft\jar\com\twoforboth\draft\panel\*.class
REM del c:\dev\java\draft\jar\com\twoforboth\draft\servlet\*.class
del c:\dev\java\draft\jar\com\twoforboth\draft\thread\*.class
REM del c:\dev\java\draft\jar\com\twoforboth\draft\table\*.class

copy c:\dev\java\draft\out\com\twoforboth\draft\*.class c:\dev\java\draft\jar\com\twoforboth\draft
copy c:\dev\java\draft\out\com\twoforboth\draft\event\*.class c:\dev\java\draft\jar\com\twoforboth\draft\event
copy c:\dev\java\draft\out\com\twoforboth\draft\misc\*.class c:\dev\java\draft\jar\com\twoforboth\draft\misc
copy c:\dev\java\draft\out\com\twoforboth\draft\panel\*.class c:\dev\java\draft\jar\com\twoforboth\draft\panel
REM copy c:\dev\java\draft\out\com\twoforboth\draft\servlet\*.class c:\dev\java\draft\jar\com\twoforboth\draft\servlet
copy c:\dev\java\draft\out\com\twoforboth\draft\thread\*.class c:\dev\java\draft\jar\com\twoforboth\draft\thread
REM copy c:\dev\java\draft\out\com\twoforboth\draft\table\*.class c:\dev\java\draft\jar\com\twoforboth\draft\table

jar -cfv draft.jar com 
