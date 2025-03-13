import os
import csv
import sys
sys.path.append(os.environ.get('AUTOMATION_HOME'))

def load_csv(data_dir):
    data = {}
    for (path, dir, files) in os.walk(data_dir):
        for filename in files:
            ext = os.path.splitext(filename)[-1]
            if ext == '.csv':
                data_list = []
                var_name = os.path.splitext(filename)[0]
                with open(f'{path}/{filename}', encoding='utf-8') as f:
                    reader = csv.DictReader(f)
                    for row in reader:
                        data_list.append(row)
                data[var_name] = data_list
    return data
'''
def get_variables(data_dir, file_ext = "csv"):
    return globals()[f'load_{file_ext}'](data_dir)
    '''
def get_variables(data_dir):
    return load_csv(data_dir)

