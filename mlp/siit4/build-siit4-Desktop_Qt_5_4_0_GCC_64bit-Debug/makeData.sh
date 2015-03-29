#!/bin/bash

for i in 7
do
rm -r target
mkdir target
./siit4
mv target $i
done