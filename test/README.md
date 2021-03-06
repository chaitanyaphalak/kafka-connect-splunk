
# Prerequsite
* Python version must be > 3.x

# Testing Instructions
0. Use a virtual environment for the test  
    `virtualenv --python=python3.7 venv`  
    `source venv/bin/activate`
1. Install the dependencies  
    `pip install -r requirements.txt`  
2. Start the test with the required options configured  
    `python -m pytest <options>`  

    **Options are:**  
    --splunkd-url
    * Description: splunkd url used to send test data to. Eg: https://localhost:8089  
    * Default: https://localhost:8089

    --splunk-user
    * Description: splunk username  
    * Default: admin

    --splunk-password
    * Description: splunk user password  
    * Default: changeme

    --splunk-token
    * Description: splunk hec token  
    * Default: No default value

    --splunk-index
    * Description: splunk index   
    * Default: main
    
    --kafka-connect-url
    * Description: url used to interact with kafka connect  
    * Default: http://localhost:8083
    
    --kafka-topic
    * Description: kafka topic used to get data with kafka connect  
    * Default: No default value    
