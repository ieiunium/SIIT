#!/bin/bash
rm *.zip 
for i in 0 1 2 3 4 5 6 7 8 9 10 11
do
rm -r target
mkdir target
java -jar lab2-1.0-SNAPSHOT.jar
zip -r $i.zip ./target/
done
zip all.zip *.zip
exit 0 

#Группа АС-35 - II делает лабу по СИИТ=)