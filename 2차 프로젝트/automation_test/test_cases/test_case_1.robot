*** Settings ***
Library      %{AUTOMATION_HOME}/automation_test/tmap/keywords/tmap_main.py

*** Variables ***
${BASE_URL}    ${BASE_URL}
${AUTOMATION_HOME}    ${AUTOMATION_HOME}

*** Test Cases ***
Sample Test Case
    [Documentation]    This is a sample test case.
    Log    ${AUTOMATION_HOME}
    ${result}=    Call Some Function
    Should Be Equal    ${result}    Hello from module!