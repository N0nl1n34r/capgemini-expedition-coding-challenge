#!/bin/bash

# Path to python3
dataaggr_python="/path/to/python3"

# Path to the rules file
dataaggr_rules="recommendation-system/rules/capgemini_coding_challenge_example_rules"

# Pi communications port
dataaggr_port=8080

# Refresh period in milliseconds
dataaggr_period=1000

java -jar serv-data-aggr.jar
