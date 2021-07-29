@echo off

rem Path to python3
set dataaggr_python="\path\to\python3"

rem Path to the rules file
set dataaggr_rules="recommendation-system\rules\capgemini_coding_challenge_example_rules"

rem Pi communications port
set dataaggr_port=8080

rem Refresh period in milliseconds
set dataaggr_period=1000

java -jar bin\serv-data-aggr.jar
