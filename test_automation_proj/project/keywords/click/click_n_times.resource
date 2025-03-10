*** Settings ***
Library    AppiumLibrary

*** Keywords ***
Click N times
    [Arguments]    ${times}    ${button}
    Log    Starting Click N times with ${times} clicks on button ${button}
    Sleep    5 seconds
    Repeat Keyword    ${times} times    Click Element    id=${button}
    Log    Completed Click N times with ${times} clicks on button ${button}