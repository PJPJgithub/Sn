*** Settings ***
Library           AppiumLibrary
Library           Process
Suite Setup       Setup Test Environment
Suite Teardown    Teardown Test Environment
Resource   %{AUTOMATION_HOME}/project/keywords/appium/check_appium_server.resource
Library    %{AUTOMATION_HOME}/project/keywords/appium/close_appium_server.py

*** Keywords ***
Setup Test Environment
    [Documentation]    The keywords used inside them are performed before the start of every test case.
    ...    So it's a good idea to put in logic like starting the Appium server.
    Check Appium Server

Teardown Test Environment
    [Documentation]    The keywords used inside them are performed after all test cases are finished.
    ...    Keywords like turn off appium server are good to have in there.
    Close Appium Server

