*** Settings ***
Library    AppiumLibrary
Library    Collections
Resource   %{AUTOMATION_HOME}/project/keywords/click/click_button.resource
Resource   %{AUTOMATION_HOME}/project/keywords/open_activity.resource
Resource   %{AUTOMATION_HOME}/project/keywords/tts/voice_command.resource
Resource   %{AUTOMATION_HOME}/project/keywords/log_check.resource
Resource   %{AUTOMATION_HOME}/project/keywords/click/click_n_times.resource
Variables  %{AUTOMATION_HOME}/project/variables/voice_speed.py

*** Test Cases ***
Click Main Activity button
    [Documentation]    Launch the application and test button clicks n times
    Open Main Activity
    Provide Voice Command    삼성전자 뉴스 알려줘    ${voice_speed}
    Click N times    100    next_button
    Close Application