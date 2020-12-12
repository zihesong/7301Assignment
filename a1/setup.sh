#!/bin/bash

#run source setup.sh

export CLASSPATH="/usr/local/lib/antlr-4.7-complete.jar:$CLASSPATH"

alias antlr4='java -jar /usr/local/lib/antlr-4.7-complete.jar'
alias grun='java org.antlr.v4.gui.TestRig'

echo "Set up successfully"