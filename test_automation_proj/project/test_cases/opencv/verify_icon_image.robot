*** Settings ***
Library    AppiumLibrary
Resource   %{AUTOMATION_HOME}/project/keywords/click/click_button.resource
Resource   %{AUTOMATION_HOME}/project/keywords/open_activity.resource
Resource   %{AUTOMATION_HOME}/project/keywords/verify/verify_news_image.resource

*** Test Cases ***
Click Main Activity button
    [Documentation]    Launch the application and test button clicks n times
    Open Main Activity
    Button Display With Opencv
    Close Application