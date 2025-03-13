*** Settings ***
Library           AppiumLibrary
Library           Process
Library    %{AUTOMATION_HOME}/project/keywords/appium/start_appium_server.py

*** Keywords ***
Check Appium Server
    Start Appium Server
    Wait Until Keyword Succeeds    1 min    2 sec    Check
Check
    ${result}=    Run Process    curl http://127.0.0.1:4723/wd/hub/status    shell=True
    ${output}=    Convert To String    ${result.stdout}
    Should Contain    ${output}    "The server is ready to accept new connections"