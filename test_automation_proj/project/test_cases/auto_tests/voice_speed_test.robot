*** Settings ***
Library    AppiumLibrary
Resource   %{AUTOMATION_HOME}/project/keywords/open_activity.resource
Resource   %{AUTOMATION_HOME}/project/keywords/voice_speed/evalute_voice_speed.resource

*** Test Cases ***
Find Suitable Voice Speed
    [Documentation]    Launch the application and find suitable voice speed
    Open Main Activity
    Voice Speed Test    200    100
    Close Application