*** Settings ***
Library      AppiumLibrary
Resource     %{AUTOMATION_HOME}/project/keywords/open_activity.resource
Variables    %{AUTOMATION_HOME}/project/variables/data_loader.py    %{AUTOMATION_HOME}/project/test_data/click
 
 
*** Test Cases ***
Click Setting Main Menu Test
    [Tags]    feature:Click    sub-feature:Function Verification
    [Template]    Click Setting Main Menu
    FOR    ${ele}    IN    @{click_main_button}
        ${ele}[btn_name]
    END
 
 
*** Keywords ***
Click Setting Main Menu
    [Arguments]    ${btn_name}
    Open Main Activity
    #Wait Until Element Is Visible    android=UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text("${btn_name}").instance(0))
    Click Element    android=UiSelector().resourceId("com.gm.carcontrolsim:id/${btn_name}")
    Close Application

