import os
import sys
sys.path.append(os.environ.get('AUTOMATION_HOME'))

__all__ = [
    'proxy',
]

proxy = {
    "address": 'koreaproxies.apa.gm.com',
    "port": 80
}
proxy['url'] = f"http://{proxy['address']}:{proxy['port']}"

