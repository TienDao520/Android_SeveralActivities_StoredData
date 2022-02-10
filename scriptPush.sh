#!/bin/bash
commitMessage=$1
branchName=$2
if [[ $commitMessage == "" || $branchName == "" ]];
then
	echo "Please also enter the commit Message and branch's Name"
	echo "Format $0 <commitMessage> <branchName>"
else
	git add .
	git commit -m  "$1"
	git push origin "$2"
fi
