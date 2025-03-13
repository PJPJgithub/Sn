*** Settings ***
Library    AppiumLibrary

*** Keywords ***
Wait
    [Arguments]    ${seconds}
    Sleep    ${seconds}