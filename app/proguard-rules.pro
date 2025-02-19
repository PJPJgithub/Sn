# Copyright (C) GM Global Technology Operations LLC 2022.
# All Rights Reserved.
# GM Confidential Restricted.

# Keep common dependency classes
-keep class android.** {*;}
-keep class android.**$** {*;}
-keep class androidx.** {*;}
-keep class androidx.**$** {*;}
-keep class kotlin.** {*;}
-keep class kotlin.**$** {*;}
-keep class com.google.** {*;}
-keep class com.google.**$** {*;}
-keep class com.gm.uisdk.** {*;}
-keep class gm.common.**$** {*;}

# Dagger/Hilt
-keep class dagger.** { *; }