*** Settings ***
Library    AppiumLibrary
Resource   %{AUTOMATION_HOME}/project/keywords/click/click_button.resource
Resource   %{AUTOMATION_HOME}/project/keywords/open_activity.resource
Resource   %{AUTOMATION_HOME}/project/keywords/verify/verify_image.resource

*** Test Cases ***
Verify Icon Image
    [Documentation]    Launch the application and verify with opencv if icons are well presented.
    Open Main Activity
    Button Display Test With Opencv
    Close Application