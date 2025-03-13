# Robotframework Chapter 02 Study  

## 2.1 Test data syntax  

Structure words that used in RobotFramework  
- Suite file  
Files where test cases are located.     
- Test Suite  
Suite of tests that automatically created by RobotFramework  
- Suite Directory  
Directory containing multiple suite files that form a higher level test suite.  
- Test library    
Structured with low-level keywords created with Python or custom keyword implementation.  
- Resource file  
Files containing custom user keywords or variables.   
- Variable file  
Flexible way to create variables instead of variable sections in resource files.  

Pre-existing data sections in RobotFramework   
- Settings  
To import files and define metadata    
- Variables  
Define variables that can be used in test cases.  
- Test Cases  
Test cases should be placed in this section   
- Tasks  
Similar to the Test Cases section, a single file can only contain either Tests or Tasks.  
- Keywords  
Custom keywords created with existing subkeywords  
- Comments  
Additional comments or data   

- Suite file  
```.robot```

- Suite file (reStructuredText format)
```.robot.rst```

- Resource file  
```.resource```

RobotFramework supports the **reStructuredText** format.  
An example is shown below.  
```
reStructuredText example
------------------------

This text is outside code blocks and thus ignored.

.. code:: robotframework

   *** Settings ***
   Documentation    Example using the reStructuredText format.
   Library          OperatingSystem

   *** Variables ***
   ${MESSAGE}       Hello, world!

   *** Test Cases ***
   My Test
       [Documentation]    Example test.
       Log    ${MESSAGE}
       My Keyword    ${CURDIR}

   Another Test
       Should Be Equal    ${MESSAGE}    Hello, world!

Also this text is outside code blocks and ignored. 
Code blocks not containing Robot Framework data are ignored as well.

.. code:: robotframework

   # Both space and pipe separated formats are supported.

   | *** Keywords ***  |                        |         |
   | My Keyword        | [Arguments]            | ${path} |
   |                   | Directory Should Exist | ${path} |
```

If you want to separate two words or values in RobotFramework, you must insert at least two spaces or tabs.   
Or you can use ```|``` as shown below.  
```
| *** Test Cases *** |                 |               |
| My Test            | [Documentation] | Example test. |
|                    | Log             | ${MESSAGE}    |
|                    | My Keyword      | ${CURDIR}     |
| Another Test       | Should Be Equal | ${MESSAGE}    | Hello, world!
```
When using the pipe-separated format, blank values only need to be escaped if they are at the end of the line.  

Ignored data when RobotFramework parses suite file  
- All data before the first test data section.  
- Data in the Comments section.  
- All empty rows.  
- All empty cells at the end of rows when using the pipe separated format.  
- All single backslashes (\) when not used for escaping.  
- All characters following the hash character (#), when it is the first character of a cell. This means that hash marks can be used to - enter comments in the test data.  

You can use special characters as literal values with ```\n```, ```\r```, ```\t```, ```\$```, ```\@```, ```\&```, ```\%```, ```\#```, ```\=```, ```\|```, ```\\``` these syntex.  
Hex value can be used with ```\xhh``` syntex.  
Empty value can be used with ```${EMPTY}``` syntex.  
Spaces can be used with ```${SPACE}``` syntex.  
Multiple spaces are expressed as ```${SPACE * 3}```.  

You can split multiple lines with the keyword ```...``` as shown below.  
```
*** Variables ***
${STRING}          This is a long string.
...                It has multiple sentences.
...                It does not have newlines.
```
An actual ```${STRING}``` value would be ```This is a long string.It has multiple sentences.It does not have newlines.```.  

## 2.2 Creating test cases  

Basic syntax of Test case is shown below.  
```
*** Test Cases ***
    Keyword Name    keyword_arg
```

Keywords can take zero or more arguments, and some arguments can have default values.  
Suppose there is a keyword named ``Create File`` as shown below.  
```
*** Keywords ***
Create File
    [Argument]    ${path}    ${content}    ${encoding}=UTF-8
    Log    ${path}    ${content}    ${encoding}
```

Arguments should be strictly located to one's own position.  
```
*** Test Cases ***
Example
    Create File    ${CURDIR}/file.txt    hello world    UTF-8
```

If there is a default value in keyword, you can use keyword as shown below.  
```
*** Test Cases ***
Example
    Create File    ${CURDIR}/file.txt
```

Suppose there is a keyword named ``Create Files`` as shown below.  
```
*** Keywords ***
Create Files
    [Argument]    *paths
    FOR    ${path}    IN    @{paths}
        Log    ${path}
    END
```
```*paths``` is array argument.  

You can pass an undefined number of arguments to this function, as shown below.    
```
*** Test Cases ***
Example
    Create File    ${CURDIR}/file01.txt    ${CURDIR}/file02.txt    ${CURDIR}/file03.txt    ${CURDIR}/file04.txt
```

RobotFramework also supports the argument of the ``**kwargs`` style.  
Suppose there is a keyword named ``Create Files`` as shown below.  
```
*** Keywords ***
Create Files
    [Argument]    group_name    *path    **envs
    Log    ${group_name}
    FOR    ${path}    IN    @{paths}
        Log    ${path}
    END
    Log    ${envs}[env]
    Log    ${envs}[option]
```

You can use this keyword as shown below.  
```
*** Test Cases ***
Example
    Create Files    Sample Grouop    ${CURDIR}/file01.txt    ${CURDIR}/file02.txt    env=${SOMEENV}    option=${REPLACEMODE}
```

The error message associated with a failed test-case is taken directly from the failed keyword.  
The default error messages are plain text, but if you want HTML error messages you can enable HTML syntex by using the ``*HTML*`` keyword as shown below.  
```
*** Test Cases ***
Normal Error
    Fail    This is a normal error message.

HTML Error
    Fail    *HTML* This is a HTML error message.
```

You can set the documentation on the test cases you have written as shown below.  
```
*** Test Cases ***
Formatting
    [Documentation]
    ...    This list has:
    ...    - *bold*
    ...    - _italics_
    ...    - link: http://robotframework.org
    Custom Keyword
```

Tag is used to filter a test case or test suite to be executed.  

You can set the tags of the suite file as shown below.  
```
*** Settings ***
Test Tags    requirement: 42    smoke
```

You can also write the tags on your test cases.  
```
*** Test Cases ***
Custom Test
    [Documentation]    Test has tags 'requirement: 42', 'smoke' and 'not ready'.
    [Tags]    sample tag
    Log    Test Log
```  

If your tags are constructed as shown below, tag will be ```requirement: 42    smoke    sample tag```.  
```
*** Settings ***
Test Tags    requirement: 42    smoke

*** Test Cases ***
Custom Test
    [Tags]    sample tag
    Log    Test Log
```

There are a few more tag options as shown below.  
```
*** Settings ***
Test Tags       requirement: 42    smoke

*** Variables ***
${HOST}         10.0.1.42

*** Test Cases ***
# The tag of the following test case should be "requirement: 42    smoke    host: 10.0.1.42"
Own tags with variable
    [Documentation]    Test has tags 'requirement: 42', 'smoke' and 'host: 10.0.1.42'.
    [Tags]    host: ${HOST}
    No Operation

# The tag of the following test case should be "requirement: 42"
Remove common tag
    [Documentation]    Test has only tag 'requirement: 42'.
    [Tags]    -smoke
    No Operation

# The tag of the following test case should be "smoke"
Remove common tag using a pattern
    [Documentation]    Test has only tag 'smoke'.
    [Tags]    -requirement: *
    No Operation

# The tag of the following test case should be "smoke    example    another"
Set Tags and Remove Tags keywords
    [Documentation]    This test has tags 'smoke', 'example' and 'another'.
    Set Tags    example    another
    Remove Tags    requirement: *
```
You can use the value on the tag, also you can remove some tag. (you can even set some condition with regex like simple pattern)  

One thing users should avoid when using the tag feature is ```robot:``` prefix tag.  
There are some predefined tags on the robot framework, so when you make tags, avoid ``robot:`` word.  

```Test Setup``` is something that is run before a test Case.  
```Test Teardown``` is executed after a test case. (It will be executed even if the test fails)  

How they are used is shown below.  
```
*** Settings ***
Test Setup       Open Application    SampleApp
Test Teardown    Close Application    SampleApp

*** Test Cases ***
Default values
    [Documentation]    Setup and teardown from setting section
    Do Something

Overridden setup
    [Documentation]    Own setup, teardown from setting section
    [Setup]    Open Application    App B
    Do Something

No teardown
    [Documentation]    Default setup, no teardown at all
    Do Something
    [Teardown]    NONE

Using variables
    [Documentation]    Setup and teardown specified using variables
    [Setup]    ${SETUPAPP}
    Do Something
    [Teardown]    ${TEARDOWNAPP}
```

Template is used for data-driven test cases.  
It is really good for iterating the same test repeatedly.  
The basic use of the test template is shown below.  
```
*** Test Cases ***
Templated test case
    [Template]    Some Sample Template
    First argument    Second argument
    hello    world
    Epic    High
    1    2
    VOICE    ASSISTANCE

*** Keywords ***
Some Sample Template
    [Arguments]    ${logData01}    ${logData02}
    Log    There are two log data, one is ${logData01}, second is ${logData02}
```
When you run the ```Templated test case``` keyword, ```Some Sample Template``` will be called five times with listed arguments.  

You can also use the test template globally, as shown below.  
```
*** Settings ***
Test Template    Some Sample Template

*** Test Cases ***
Strings Test
    First argument    Second argument
    hello    world
    Epic    High
    VOICE    ASSISTANCE

Number Test
    1    2
    3    4
    123    456

*** Keywords ***
Some Sample Template
    [Arguments]    ${logData01}    ${logData02}
    Log    There are two log data, one is ${logData01}, second is ${logData02}
```
As you can see above, you can structure test cases with arguments without using keywords.  

You can use test template with ```FOR``` loop.  
```
*** Test Cases ***
Template with FOR loop
    [Template]    Some Sample Template
    FOR    ${index}    IN RANGE    42
        ${index}    ${index}+1
    END

*** Keywords ***
Some Sample Template
    [Arguments]    ${logData01}    ${logData02}
    Log    There are two log data, one is ${logData01}, second is ${logData02}
```

The keyword-driven style is shown below.  
```
*** Test Cases ***
    Open Application    Sample App
    Do Some Task 01
    Do Some Task 02
    Close Application
```

The data-driven style can be written using the Test Template feature.  
```
*** Test Cases ***
Templated test case
    [Template]    Some Sample Template
    First argument    Second argument
    hello    world
    Epic    High
    1    2
    VOICE    ASSISTANCE

*** Keywords ***
Some Sample Template
    [Arguments]    ${logData01}    ${logData02}
    Log    There are two log data, one is ${logData01}, second is ${logData02}
```
In this case, a single keyword is at the top of everything.  
and that keyword is used repeatedly with different values.  

The behaviour-driven style can be written as shown below.  
```
*** Test Cases ***
Login With Admin
    [Documentation]    This test case is to test login with BDD syntax
    Given I am on the login page
    When I login with username "admin" and password "admin"
    Then I should see the welcome page

Login With Invalid User
    [Documentation]    This test case is to test login with BDD syntax
    Given I am on the login page
    When I login with username "invalid" and password "invalid"
    Then I should see the error message
    And I should be able to login again

*** Keywords ***
I am on the login page
    Log To Console    I am on the login page

I login with username "${username}" and password "${password}"
    Log To Console    I login with username "${username}" and password "${password}"

I should see the welcome page
    Log To Console    I should see the welcome page

I should see the error message
    Log To Console    I should see the error message

I should be able to login again
    Log To Console    I should be able to login again
    Log To Console    The Login page is displayed
```
```Given/When/Then/And/But``` prefixes are ignored.  

## 2.3 Creating test suites  

Files and directories containing various test cases create a hierarchical test suite structure.  
The number of test cases is recommended to be less than ``10`` on each suite file.  

The underscore is converted to a space and the extension is ignored in the names of robot files and directories.  
So ```01_test.robot``` will be converted to ```01 test```.   

The names of files and directories are important in the order in which tests are run.  
If there are ``01_test.robot`` and ``02_test.robot`` files in the directory, and the user executes that directory, ``01_test.robot`` will be executed first.  

You can also specify the name in the robot file inside.  
```
*** Settings ***
Name            Custom suite name
```

Metadata and documentation are displayed in reports and logs.   
```
*** Settings ***
Documentation    An example suite documentation with *some* _formatting_.
...              Long documentation can be split into multiple lines.
Metadata        Version            2.0
Metadata        Robot Framework    http://robotframework.org
Metadata        Platform           ${PLATFORM}
Metadata        Longer Value
...             Longer metadata values can be split into multiple
...             rows. Also *simple* _formatting_ is supported.
```

### Suite initialization files  

The Initialization file is used to set some test options globally to the files in the directory (even recursively to the child directory) where the Initialization file is located.  
An initialization file name must always be of the format ```__init__.robot```.  
Example of ```__init__.robot``` is shown below.  
```
*** Settings ***
Documentation    Example suite
Suite Setup      Do Something    ${MESSAGE}
Test Tags        example
Library          SomeLibrary

*** Variables ***
${MESSAGE}       Hello, world!

*** Keywords ***
Do Something
    [Arguments]    ${args}
    Some Keyword    ${arg}
    Another Keyword
```
The syntex is almost the same as the test suite file, but you can't use the keyword from the initialization file.  
And also you can't define test cases in here.  

#### Suite setup and teardown  

Bound of setup and teardown in test suite is more wider than test cases setup and teardown.  
Assume that there is some file structure as shown below.  
```md
automation_project
│ 
├── test_dir
│   │ 
│   ├── child_test_dir
│   │   └── child_test.robot
│   │ 
│   ├── __init__.robot
│   ├── test_01.robot
│   └── test_02.robot
│ 
└── settings.json
```

And suppose ```__init__.robot``` is same as below.  
```
*** Settings ***
Suite Setup       Open Application    SampleApp
Suite Teardown    Close Application    SampleApp
```
In this case, when the tester executes the ``test_cases`` directory, ```Open Application``` will be executed first, and then ``test_01.robot``, ``test_02.robot``, ```child_test.robot``` will be executed.  
Finally, ``Close Application`` is executed.  

## 2.5 Using test libraries  

As shown below, there are many ways to import libraries.  
```
*** Settings ***
Library    OperatingSystem
Library    my.package.TestLibrary
Library    MyLibrary    arg1    arg2
Library    relative/path/PythonDirLib/    possible    arguments
Library    ${LIBRARY}

*** Variables ***
${LIBRARY}    AppiumLibrary
```
Suite files, resource files, initialization files could be a library so we can import those.  
If you want to import directory, you have to include ```/``` letter at the end.  

You can also import libraries dynamically using the ``Import Library`` keyword.  
This is useful in cases where the library is not available at the start of the test execution and only some other keywords make it available.  
```
*** Test Cases ***
Example
    Do Something
    Import Library    OperatingSystem
    Run Process    msedge.exe
```
After you run the ``Import Library OperatingSystem`` line, you can use the ``Run Process`` keyword that came from the ```OperatingSystem``` library.  

You can use a custom name on the imported library as shown below with the keyword ```AS```.   
```
*** Settings ***
Library    AppiumLibrary    AS    Appium
# AS usage With argument
Library    SomeLibrary    localhost    1234    AS    LocalLib

*** Test Cases ***
Example
    Appium.Swipe Up    100    200    300    400
    LocalLib.SomeFunction    200
```

## 2.6 Variables  

- ```${SCALAR}```  
Used for scalar variable like string, integer, etc...  
- ```@{LIST}```  
Used for list variable.  
- ```&{DICT}```  
Used for dictionary.  
-  ```%{ENV_VAR}```  
Used to reference the environment variable.   

As you have already seen, you can set the variable as shown below.  
```
*** Variables ***
${MESSAGE}       Hello, world!
```

One thing you should be aware of is the use of objects.  
```
class MyObj:
    def __str__():
        return "Hi, terra!"
```
Suppose there is a class like the one above.  

In this case, ```KW 1``` keyword will get the object itself.  
But ```KW 2``` keyword will get the ``You said Hi, terra!`` string.  
```
*** Test Cases ***
Objects
    KW 1    ${OBJ}
    KW 2    You said "${OBJ}"
```

As shown below, you can use a nested array.  
```
*** Variables ***
@{NESTEDARRAY}    [['a', 'b', 'c'], {'key': ['x', 'y']}]
```

Using array is almost the same as using syntex in Python.  
```
*** Test Cases ***
Nested container
    Log Many    @{NESTEDARRAY}[0]         # Logs 'a', 'b' and 'c'.
    Log Many    @{NESTEDARRAY}[1][key]    # Logs 'x' and 'y'.
    Log Many    @{NESTEDARRAY}[0][1:]     # Logs 'b' and  'c'.
```

When importing a library with a specific argument, you should separate the library name from the library argument.  
This also applies when using setup and teardown.  
```
*** Settings ***
Library         ExampleLibrary      @{LIB ARGS}    # This works
Library         ${LIBRARY}          @{LIB ARGS}    # This works
Library         @{LIBRARY AND ARGS}                # This does not work
Suite Setup     Some Keyword        @{KW ARGS}     # This works
Suite Setup     ${KEYWORD}          @{KW ARGS}     # This works
Suite Setup     @{KEYWORD AND ARGS}                # This does not work
```

You can set the dictionary variable as shown below.  
```
*** Variables ***
${PW}    password
&{USER}    name=tongstar    password=password of tongstar

*** Test Cases ***
Nested container
    Log Many    &{USER}[name]         # Logs 'tongstar'.
    Log Many    &{USER}[${PW}]        # Logs 'password of tongstar'.
```

When it comes to using dictionary variables in the ``Settings'' section, only imports, setup/teardown can use them.  
```
*** Settings ***
Library        ExampleLibrary    &{LIB ARGS}
Suite Setup    Some Keyword      &{KW ARGS}     named=arg
```

If you have the environment variable ``JAVA_HOME`` on your PC, you can use it on RobotFramework.  
```
*** Test Cases ***
Some Test Using Java
    Run    %{JAVA_HOME}    test.jar

# If you don't have taget env var, the default value is used.  
Android Test
    Log    %{ANDROID_HOME=~/android_sdk/}   
```

There are several ways to create variables.  
```
*** Variables ***
${NORMALWAY}    Some Value
${EQUALSIGN} =  Equal Sign Way
@{NAMES}        Matti       Teppo
@{MANY}         one         two      three      four
...             five        six      seven
```

In multi-line mode, you can set the separator option.  
```
*** Variables ***
${NOTMULTILINE}    First line.
...             Second line.
...             Third line.

${MULTILINE}    First line.
...             Second line.
...             Third line.
...             separator=\n
```
Without specifying the separator option, the default value of the separator is space, so the value of ```${NOTMULTILINE}``` will be ```First line. Second line. Third line.```.   
On the other hand, ```${MULTILINE}``` value with the separator will be as shown below.  
```
First line.
Second line.
Third line.
```

The creation of a dictionary value is shown below.  
```
&{MANY}         first=1       second=2
&{EVEN MORE}    &{MANY}       first=override      empty=      key\=here=some value
                phone=456     =empty              
```
In the above case, ```&{EVEN MORE}[key=here]``` is the same as ```some value```.   
```&{EVEN MORE}[]``` is equivalent to ```empty```, ```&{EVEN MORE}[empty]``` is equivalent to an empty value.   

Dynamic value creation supported by RobotFramework 7.0   
```
*** Variables ***
${X}        Y
${${X}}     Z
```
In the above case, ```${Y}``` is the same as ```Z```.  

You can set more than one variable at a time.   
```
*** Variables ***
@{NAMES}    1    2    3

*** Test Cases ***
Assign multiple
    ${a}    ${b}    ${c} =    @{NAMES}
```  

You can set the variable from the command line as shown below.   
```
./test.robot --variable HOST:localhost:7272 --variable USER:robot
```
The ```${HOST}``` value in the ```test.robot``` file is set to ``localhost:7272`` and the ```${USER}``` value is also set to ```robot```.  
Variables set on the command line have the highest priority of all variables that can be set before the actual test execution starts.  

There are many built-in variables in RobotFramework.  
- ```${CURDIR}```  
An absolute path to the directory where the test data file is located. This variable is case-sensitive.  
- ```${TEMPDIR}```  
An absolute path to the system temporary directory. In UNIX-like systems this is typically /tmp, and in Windows c:\Documents and Settings\<user>\Local Settings\Temp.  
- ```${EXECDIR}```  
An absolute path to the directory where test execution was started from.  
- ```${/}```  
The system directory path separator. / in UNIX-like systems and \ in Windows.  
- ```${:}```  
The system path element separator. : in UNIX-like systems and ; in Windows.  
- ```${\n}```  
The system line separator. \n in UNIX-like systems and \r\n in Windows.  
- ```${Some Number}```  
``80`` is treated like string on RobotFramework, but ``${80}`` this one is treated as integer.  
- ```${true}``` / ```${false}```  
Built-In boolean.  
- ```${SPACE}``` / ```${SPACE * rep}```  
Built-In space. ```${SPACE * 4}``` means four space in a row.  
- ```${EMPTY}``` / ```@{EMPTY}``` / ```&{EMPTY}```  
Built-In void value.  

Apart from these, there are still many pre-defined variables in the RobotFramework.   
You can check these out at https://robotframework.org/robotframework/latest/RobotFrameworkUserGuide.html#automatic-variables.   

You can use a Python object as a robot variable.   
```
class MyObject:

    def __init__(self, name):
        self.name = name

    def eat(self, what):
        return '%s eats %s' % (self.name, what)

    def __str__(self):
        return self.name

OBJECT = MyObject('Robot')
DICTIONARY = {1: 'one', 2: 'two', 3: 'three'}
```

Use is as follows.  
```
*** Test Cases ***
Example
    KW 1    ${OBJECT.name}
    KW 2    ${OBJECT.eat('Cucumber')}
    KW 3    ${DICTIONARY[2]}
```

You can also use native Python functions on the robot framework.   
```
*** Test Cases ***
String
    ${string} =    Set Variable    abc
    Log    ${string.upper()}      # Logs 'ABC'
    Log    ${string * 2}          # Logs 'abcabc'

Number
    ${number} =    Set Variable    ${-2}
    Log    ${number * 10}         # Logs -20
    Log    ${number.__abs__()}    # Logs 2
```
Since you can't use ```${abs(number)}```, you have to use ```${number.__abs__()}```.  

You can use ```${{abs(${number})}}``.  
If you want to use native Python syntex freely, use ```${{}}`` syntex.  
Examples are given below.  
```
*** Variables ***
${Some Len}    ${{len('${var}') > 3}}
${Some Date}    ${{datetime.date(2019, 11, 5)}}
${Some List}    ${{[1, 2, 3, 4]}}
```

## 2.7 Creating user keywords  

The method of defining keywords is shown below. 
```
*** Keywords ***
Open Login Page
    Open Browser    http://host/login.html
    Title Should Be    Login Page

Title Should Start With
    [Arguments]    ${expected}
    ${title} =    Get Title
    Should Start With    ${title}    ${expected}

Two Arguments With Defaults
    [Arguments]    ${arg1}=default 1    ${arg2}=${VARIABLE}
    [Documentation]    This keyword takes 0-2 arguments
    Log    1st argument ${arg1}
    Log    2nd argument ${arg2}

Any Number Of Arguments
    [Arguments]    @{varargs}
    Log Many    @{varargs}

Positional And Free Named
    [Arguments]    ${required}    &{extra}
    Log Many    ${required}    &{extra}
    RETURN    ${required}

*** Test Cases ***
Test 01
    Any Number Of Arguments    Hello    World   Hi    There!
    ${return_val}    Positional And Free Named    profile    name=kyungjoonlee    nickname=tongstar
```
There are many options such as ```[Documentation]```, ```[Tags]```, ```[Arguments]```, ```[Setup]```, ```[Teardown]```, ```[Timeout]```, ```[Return]```.

You can also embed the argument in a custom keyword, as shown below.   
```
*** Keywords ***
Select ${animal} from list
    Open Page    Pet Selection
    Select Item From List    animal_list    ${animal}

Number of ${animals} should be
    [Arguments]    ${count}
    Open Page    Pet Selection
    Select Items From List    animal_list    ${animals}
    Number of Selected List Items Should Be    ${count}

Select ${city} ${team:\S+}
    Log    Selected ${team} from ${city}.
```
As you can see in the ```Select ${city} ${team:\S+}``` keyword, you can use regex in an embedded argument.    
This is to prevent collisions between keywords with the same name.  

## 2.8 Resource and variable files  

Resource file is used to define keywords other than test case or task.  
Resource file supports ``reStructuredText``, ``json`` format.  
```
*** Settings ***
Resource    example.resource
Resource    example.rst
Resource    example.json
```

An example of ``example.rst`` is shown below.  
```
Resource file using reStructuredText
------------------------------------

This text is outside code blocks and thus ignored.

.. code:: robotframework

   *** Settings ***
   Library          OperatingSystem

   *** Variables ***
   ${MESSAGE}       Hello, world!

Also this text is outside code blocks and ignored. Code blocks not
containing Robot Framework data are ignored as well.

.. code:: robotframework

   # Both space and pipe separated formats are supported.

   | *** Keywords ***  |                        |         |
   | My Keyword        | [Arguments]            | ${path} |
   |                   | Directory Should Exist | ${path} |
```

Variable file is used to define variables.  
Variable file supports ```py```, ```yaml```, ```json``` format.  
```
*** Settings ***
Variables    myvariables.py
Variables    myvariables.yaml
Variables    myvariables.json
```

In the Python file, you can simply write a variable file as shown below.  
```
VARIABLE = "An example string"
ANOTHER_VARIABLE = "This is pretty easy!"
INTEGER = 42
STRINGS = ["one", "two", "kolme", "four"]
NUMBERS = [1, INTEGER, 3.14]
MAPPING = {"one": 1, "two": 2, "three": 3}
```

The above syntex is the same as the following RobotFramework syntex.
```
*** Variables ***
${VARIABLE}            An example string
${ANOTHER VARIABLE}    This is pretty easy!
${INTEGER}             ${42}
@{STRINGS}             one          two           kolme         four
@{NUMBERS}             ${1}         ${INTEGER}    ${3.14}
&{MAPPING}             one=${1}     two=${2}      three=${3}
```

One thing you should be aware of is that when Robot Framework processes variable files, all their attributes that do not start with an underscore are expected to be variables.
So if there is any function in a variable file, you need to follow the syntax as shown below.  
```
import math

__all__ = ['AREA1', 'AREA2']

def get_area(diameter):
    radius = diameter / 2.0
    area = math.pi * radius * radius
    return area

AREA1 = get_area(1)
AREA2 = get_area(2)
```

Or you can write as below.  
```
def get_variables():
    variables = {"VARIABLE ": "An example string",
                 "ANOTHER VARIABLE": "This is pretty easy!",
                 "INTEGER": 42,
                 "STRINGS": ["one", "two", "kolme", "four"],
                 "NUMBERS": [1, 42, 3.14],
                 "MAPPING": {"one": 1, "two": 2, "three": 3}}
    return variables
```

Or like this.  
```
variables1 = {'scalar': 'Scalar variable',
              'LIST__list': ['List','variable']}
variables2 = {'scalar' : 'Some other value',
              'LIST__list': ['Some','other','value'],
              'extra': 'variables1 does not have this at all'}

def get_variables(arg):
    if arg == 'one':
        return variables1
    else:
        return variables2
```
As you can see above, you can write proper arguments to variable files.  

You can also include the target variable from the command line.   
```
--variablefile myvariables.py
--variablefile path/variables.py
--variablefile /absolute/path/common.py
--variablefile variablemodule
--variablefile arguments.py:arg1:arg2
--variablefile rootmodule.Variables:arg1:arg2
```
As you can see above, you can even pass an argument to a variable file.  

You can use the yaml format as shown below.   
```
string:   Hello, world!
integer:  42
list:
  - one
  - two
dict:
  one: yksi
  two: kaksi
  with spaces: kolme
```

You can use the json format as shown below.   
```
{
    "string": "Hello, world!",
    "integer": 42,
    "list": [
        "one",
        "two"
    ],
    "dict": {
        "one": "yksi",
        "two": "kaksi",
        "with spaces": "kolme"
    }
}
```

## 2.9 Control structures  

The ``FOR`` loop in RobotFramework is shown below.  
```
*** Test Cases ***
Example 01
    FOR    ${animal}    IN    cat    dog
        Log    ${animal}
        Log    2nd keyword
    END
    Log    Outside loop

Example 02
    FOR    ${animal}    IN    @{animal_ary}
        Log    ${animal}
        Log    2nd keyword
    END
    Log    Outside loop

Example 03
    FOR    ${index}    IN RANGE    1    11
        Log    ${index}
    END

# Get item with index
Example 04
    FOR    ${index}    ${item}    IN ENUMERATE    @{LIST}    start=0
        My Keyword    ${index}    ${item}
    END
```

The ```WHILE``` loop in RobotFramework is shown below.  
```
*** Test Cases ***
Example 01
    VAR    ${rc}   1
    WHILE    ${rc} != 0
        ${rc} =    Keyword that returns zero on success
    END

# Raise exception when while loop exceed 100 loop.
Example 02
    WHILE    True    limit=100
        Log    This is run 100 times.
    END
```

The ```IF``` condition in RobotFramework is shown below.  
```
*** Test Cases ***
Normal IF
    IF    $rc > 0
        Positive keyword
    ELSE IF    $rc < 0
        Negative keyword
    ELSE IF    $rc == 0
        Zero keyword
    ELSE
        Fail    Unexpected rc: ${rc}
    END
```

The ```TRY/EXCEPT``` syntex in RobotFramework is shown below.  
```
*** Test Cases ***
First example
    TRY
        Some Keyword
    EXCEPT    Error message
        Error Handler Keyword
    ELSE
        Log    No error occurred!
    FINALLY
        Log    Always executed.
    END
``` 

You can use regex to match error messages as shown below.  
```
*** Test Cases ***
Regular expression
    TRY
        Some Keyword    
    EXCEPT    [Ee]rror \\d+ occurred    type=Regexp    # Backslash needs to be escaped.
        Error Handler 1
    END

Capture Error Msg
    TRY
        Some Keyword
    EXCEPT    [Ee]rror \\d+    (Invalid|Bad) usage    type=REGEXP    AS    ${error}
        Error Handler 1    ${error}
    EXCEPT    AS    ${error}
        Error Handler 2    ${error}
    END
```

## 2.10 Advanced features  

The keyword priority of robotframework is shown below.  
- Highest  
keyword in the currently running suite file.  
- Middle  
keyword in a resource file.  
- Lowest  
keword from external libraries.  

You can set ```Timeout``` for a suite file, or for each test, or even for a keyword.  
```
*** Settings ***
Test Timeout       2 minutes

Override
    [Documentation]    Override default, use 10 seconds timeout.
    [Timeout]    10
    Some Keyword    argument

Hardcoded
    [Arguments]    ${arg}
    [Timeout]    1 minute 42 seconds
    Some Keyword    ${arg}
```

## Structure Proposal  

This is the proposed structure I wrote after learning the Robot Framework chapter 2 section.  
The directory structure of the test automation project is shown below.  
```
automation_test
│ 
├── common
│   │ 
│   ├── config  
│   │   ├── logging.json
│   │   └── settings.json
│   │  
│   ├── keyword  
│   │   ├── sample.py
│   │   └── sample.resource    
│   │ 
│   ├── variable  
│   │   ├── sample.py  
│   │   └── sample.json    
│   │ 
│   └── module  
│       │   
│       ├── log  
│       │   ├── lib_log.py
│       │   └── lib_stt.py
│       │ 
│       └── voice_analyzer  
│           └── tss_sample.py
│  
├── chromedriver
│   │
│   ├── linux
│   │   └── chromedriver
│   │
│   └── windows
│       └── chromedriver.exe
│ 
└── application
    │
    ├── resource
    │   └── sample.png
    │
    ├── keyword  
    │   ├── sample.py
    │   └── sample.resource    
    │ 
    ├── variable  
    │   ├── sample.py  
    │   └── sample.json    
    │
    ├── test_cases  
    │   ├── __init__.robot
    │   │  
    │   └── test_category
    │       │ 
    │       └── test_example.robot
    │
    └── test_output
        │  
        ├── allure_report    
        │   └── report.html
        │
        └── robot_report
            ├── report.html  
            ├── log.html  
            └── output.xml
```

- config  
This directory should contain configuration files for setting up the Appium server and running the application.  

- keyword  
This directory should contain keyword defining files such as the robot library written in Python or a ```.resource`` file.  

- variable   
This directory should contain variable files such as ```.py``` or ```json```.  
If you create a variable file in Python format, you need to follow the method below.  
    ```
    import math

    def get_area(diameter):
        radius = diameter / 2.0
        area = math.pi * radius * radius
        return area

    def get_variables(*arg):
        variables = {
                     "variable ": "An example string",
                     "another_variable": "This is pretty easy!",
                     "integer": 42,
                     "strings": ["one", "two", "kolme", "four"],
                     "numbers": [1, 42, 3.14],
                     "mapping": {"one": 1, "two": 2, "three": 3},
                     "some_area": get_area(arg[0])
                    }
        return variables
    ```
    Because the above method is flexible when injecting variables from the command line.  

- module  
This directory should contain Python modules.  
For example, wrapper functions of logging library can be placed here.  
Also these modules can often be used in making keword file, which is located in the keyword directory. 

- resource  
This directory should contain resource files such as an image, music file or icon.  

- test_cases  
This directory should contain ```.robot``` files which contains the implementation of the test cases.  
And these test suite files should be categorised by feature, such as radio control, map control, and so on.  
```test_example.robot``` can be scripted as shown below.  
    ```
    *** Settings ***
    Test Setup  Start Appium Server
    Test Teardown    Close Appium Server

    *** Test Cases ***
    Sample Test
        [Tags]    Big Feature    Detail Feature
        [Template]    Select Destination
        Hello    World
        Hi    There
        Oh    My
        1    2

    *** Keywords ***
    Sample Keyword
        [Documentation]    This is sample keyword
        [Arguments]    ${arg_01}    ${arg_02}
        Log To Console    ${arg_01} and ${arg_02}
    ```

- test_output  
This directory should contain test output.  
And the test output should be categorised by library.  