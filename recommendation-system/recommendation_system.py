# this cli tool gives recommendations according to given rules.
# First argument is the json from which you want to give recommendations.
# Second argument is a dictionary where the key is a jsonata query and the value is a message, 
# which gets printed, if the jsonata query evaluates to true.
from pyjsonata import jsonata
import json


def get_recommended_actions(json_data, rules):
    recommended_actions = []
    for premise, action in rules.items():
        if jsonata(premise, json_data) == 'true':
            recommended_actions.append(jsonata(action, json_data).strip('"'))
    return recommended_actions

if __name__ == '__main__':
    import sys

    json_data = sys.argv[1]
    rules = json.loads(sys.argv[2]) 

    print(get_recommended_actions(json_data, rules))