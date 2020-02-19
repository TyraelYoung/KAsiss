# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\Administrator\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


-keep class com.shuitianyun.kassistant.json.** {*;}

#sharesdk
	-keep class cn.sharesdk.**{*;}
	-keep class com.sina.**{*;}
	-keep class **.R$* {*;}
	-keep class **.R{*;}
	-keep class com.mob.**{*;}
	-dontwarn com.mob.**
	-dontwarn cn.sharesdk.**
	-dontwarn **.R$*

#http://stackoverflow.com/questions/33047806/proguard-duplicate-definition-of-library-class
-dontnote android.net.http.*
-dontnote org.apache.commons.codec.**
-dontnote org.apache.http.**

#电子邮件
-dontwarn java.awt.**
-dontwarn java.beans.Beans
-dontwarn javax.security.**

-keep class javamail.** {*;}
-keep class javax.mail.** {*;}
-keep class javax.activation.** {*;}

-keep class com.sun.mail.dsn.** {*;}
-keep class com.sun.mail.handlers.** {*;}
-keep class com.sun.mail.smtp.** {*;}
-keep class com.sun.mail.util.** {*;}
-keep class mailcap.** {*;}
-keep class mimetypes.** {*;}
-keep class myjava.awt.datatransfer.** {*;}
-keep class org.apache.harmony.awt.** {*;}
-keep class org.apache.harmony.misc.** {*;}

#okio
-dontwarn okio.**

#防止行号信息丢失
#http://bugly.qq.com/bbs/forum.php?mod=viewthread&tid=244
#http://bugly.qq.com/bbs/forum.php?mod=viewthread&tid=26
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
-ignorewarnings

#腾讯crash监控
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

#bos
-keep public class com.baidubce.**{*;}