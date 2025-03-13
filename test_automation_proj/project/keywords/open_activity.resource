*** Settings ***
Library      AppiumLibrary
Library      Process
Variables    %{AUTOMATION_HOME}/project/variables/appium_setup.py
Variables    %{AUTOMATION_HOME}/project/variables/proxy.py
Variables    %{AUTOMATION_HOME}/project/variables/app_info.py

*** Keywords ***
Open Main Activity
    Open Application    ${appium}[url]        
    ...    platformName=${platform_name}        
    ...    deviceName=${device_name}   
    ...    automationName=${automation_name}
    ...    app=${app}
    ...    appPackage=${package}[package_name]
    ...    appActivity=${activity}[activity_name][main]
    ...    chromedriverExecutable=${chromedriver_executable}
    ...    newCommandTimeout=3000
    ...    autoGrantPermissions=${True}

Open List Activity
    Open Application    ${appium}[url]        
    ...    platformName=${platform_name}        
    ...    deviceName=${device_name}   
    ...    automationName=${automation_name} 
    ...    appPackage=${package}[package_name]
    ...    appActivity=${activity}[activity_name][list]
    ...    chromedriverExecutable=${chromedriver_executable}
    ...    newCommandTimeout=3000
    ...    autoGrantPermissions=${True}

Open Detail Activity
    Open Application    ${appium}[url]        
    ...    platformName=${platform_name}        
    ...    deviceName=${device_name}   
    ...    automationName=${automation_name} 
    ...    appPackage=${package}[package_name]
    ...    appActivity=${activity}[activity_name][detail]
    ...    chromedriverExecutable=${chromedriver_executable}
    ...    newCommandTimeout=3000
    ...    autoGrantPermissions=${True}


