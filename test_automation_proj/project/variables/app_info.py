import os
import sys
sys.path.append(os.environ.get('AUTOMATION_HOME'))

__all__ = [
    'package', 
    'activity',
    'app'
]

package = {
    "package_name": "com.gm.carcontrolsim"
}

activity = {
    "activity_name": {
        "main": ".activity.MainActivity",
        "list": ".activity.ArticleListActivity",
        "detail": ".activity.ArticleDetailActivity"
    }
}

app = "C:/Users/TZ47HH/AndroidStudioProjects/Snapnews_project1/Snapnews/release/GM-CarcontrolSim-0.1.0-release.apk"