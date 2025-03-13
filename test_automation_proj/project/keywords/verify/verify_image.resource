*** Settings ***
Library      AppiumLibrary
Library      %{AUTOMATION_HOME}/project/keywords/avd/screen_shot_emulator.py
Library      %{AUTOMATION_HOME}/project/keywords/openCV/template_match.py
Library      %{AUTOMATION_HOME}/project/keywords/openCV/icon_list.py
 
*** Keywords ***
Button Display Test With Opencv
    [Tags]    feature:news    sub-feature:VoiceRecognition
    Sleep    5s
    Verify Buttons Info
    Capture Emulator Screenshot
    Verify Icons 

Verify Buttons Info
    Element Should Be Visible    id=prev_button
    Element Should Be Visible    id=next_button
    Element Should Be Visible    id=reset_button
    Element Should Be Visible    id=voice_button
    Element Should Be Visible    id=list_button
 
Capture Emulator Screenshot
    Capture Screenshot
 
Verify Icons
    ${icons}=    Get Icon List
    Match Icons    ${icons}