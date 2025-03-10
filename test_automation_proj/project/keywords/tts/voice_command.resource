*** Settings ***
Library    AppiumLibrary
Library    %{AUTOMATION_HOME}/project/keywords/tts/generate_voice_command.py

*** Keywords ***
Provide Voice Command
    [Arguments]    ${text}    ${rate}
    Generate Voice Command    ${text}    ${rate}    #%{AUTOMATION_HOME}/project/resources