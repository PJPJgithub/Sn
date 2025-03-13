*** Settings ***
Library    AppiumLibrary

*** Variables ***
${REMOTE_URL}    http://127.0.0.1:4723/wd/hub
${PLATFORM_NAME}    Android
${DEVICE_NAME}    34inch_MY23S_API_32
${APP}    C:/Users/TZ47HH/AndroidStudioProjects/Snapnews_project1/Snapnews/release/GM-CarcontrolSim-0.1.0-release.apk
${AUTOMATION_NAME}    UiAutomator2

*** Test Cases ***
Launch App
    [Documentation]    Launch the application and test button clicks
    Open Application    ${REMOTE_URL}    platformName=${PLATFORM_NAME}    deviceName=${DEVICE_NAME}    app=${APP}    automationName=${AUTOMATION_NAME}
    Sleep    5 seconds
    Click Button    prev_button
    Sleep    1 second
    Click Button    next_button
    Sleep    1 second
    Click Button    reset_button
    Sleep    1 second
    Click Button    voice_button
    Sleep    1 second
    Click Button    list_button
    Sleep    1 second
    Capture Page Screenshot
    Close Application

*** Keywords ***
Click Button
    [Arguments]    ${button_id}
    Click Element    id=${button_id}