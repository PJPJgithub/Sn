*** Settings ***
Library    AppiumLibrary

*** Keywords ***
Press Button
    [Arguments]    ${text}
    Sleep    3seconds
    Clicking Button    ${text}
    Sleep    1second
    
Clicking Button
    [Arguments]    ${button_id}
    Sleep    1 second
    Click Element    id=${button_id}