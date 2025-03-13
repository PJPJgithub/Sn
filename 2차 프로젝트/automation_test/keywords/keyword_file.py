from robot.api.deco import keyword
from modules.module_file import some_function
import os
import sys
sys.path.append(os.environ.get('AUTOMATION_HOME'))

@keyword('keyword_file')
def call_some_function():
    return some_function()