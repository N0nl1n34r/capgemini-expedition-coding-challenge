#!/bin/bash

# Path to python3
export dataaggr_python="/path/to/python3"

# Path to the rules file
export dataaggr_rules="recommendation-system/rules/capgemini_coding_challenge_example_rules"

# Pi communications port
export dataaggr_port=8080

# Refresh period in milliseconds
export dataaggr_period=1000

java -jar bin/serv-data-aggr.jar
