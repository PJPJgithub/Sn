*** Settings ***
Library    AppiumLibrary
Library    OperatingSystem
Resource   %{AUTOMATION_HOME}/project/keywords/tts/voice_command.resource
Resource   %{AUTOMATION_HOME}/project/keywords/click/click_button.resource
Resource   %{AUTOMATION_HOME}/project/keywords/log_check.resource

*** Variables ***
${LOG_FILE}    %{AUTOMATION_HOME}/reports/voice_speed_result.txt

*** Keywords ***
Voice Speed Test
    [Arguments]    ${top}    ${bottom}
    Log    Starting Voice Speed Test with range: ${top} to ${bottom}
    ${speed}    Evaluate Voice Speed    ${top}    ${bottom}
    Log    Suitable voice speed found: ${speed}
    Write Suitable Speed To File    ${speed}
    Log    Voice Speed Test completed


Evaluate Voice Speed
    [Documentation]    Evaluate voice speed from 200 to 100, decreasing by 10, until 5 consecutive successful commands
    [Arguments]    ${top}    ${bottom}
    Log    Evaluating voice speed from ${top} to ${bottom}
    Sleep    5seconds
    FOR    ${i}    IN RANGE    ${top}    ${bottom}    -10
        Log    Testing speed: ${i}
        FOR    ${j}    IN RANGE    5
            Log    Attempt ${j} at speed ${i}
            Press Button    voice_button
            Provide Voice Command    다음    ${i}
            ${result}    Run Keyword And Return Status    Log check    No more news articles
            Run Keyword If    not ${result}    Exit For Loop
            #Press Button    voice_button
            #Provide Voice Command    GM 뉴스 알려 줘    ${i}
            #Sleep    1second
            #${result}    Run Keyword And Return Status    Log check    News fetched successfully
            #Run Keyword If    not ${result}    Exit For Loop
        END
        Run Keyword If    ${j} == 4   Exit For Loop
    END
    Log    Suitable speed determined: ${i}
    RETURN    ${i}

Write Suitable Speed To File
    [Arguments]    ${speed}
    Log    Writing suitable speed to file: ${speed}
    Create File    ${LOG_FILE}
    Append To File    ${LOG_FILE}    Suitable voice speed: ${speed}
    Log    Suitable speed written to file: ${LOG_FILE}
