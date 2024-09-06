# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

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

-keep class android.support.** { *; }

-keep interface android.support.** { *; }
-keepattributes *Annotation*


-keep class com.google.android.gms.** { *; }


#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
  *** rewind();
}


-keep public class com.aksstore.storily.model.Story
-keep public class com.aksstore.storily.model.StoryModel

-keep class com.aksstore.storily.model.** { *; }

-keep class com.aksstore.storily.model.dictionary.** { *; }
-keep class com.aksstore.storily.model.home.** { *; }

-keep public class com.aksstore.storily.model.dictionary.DictionaryResponseItem
-keep public class com.aksstore.storily.model.dictionary.DictionaryResponse
-keep public class com.aksstore.storily.model.dictionary.Definition
-keep public class com.aksstore.storily.model.dictionary.License
-keep public class com.aksstore.storily.model.dictionary.Meaning
-keep public class com.aksstore.storily.model.dictionary.Phonetic

-keep class com.aksstore.storily.base.** { *; }

-keep public class com.aksstore.storily.adapter.StoryAdapter

# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
# Prevent R8 from leaving Data object members always null
-keepclasseswithmembers class * {
    <init>(...);
    @com.google.gson.annotations.SerializedName <fields>;
}
# Retain generic signatures of TypeToken and its subclasses with R8 version 3.0 and higher.
-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken

## Rules for Retrofit2
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

# Retrofit and Gson
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }
-keep class com.google.gson.** { *; }

# Keep @SerializedName annotations
-keepattributes *Annotation*

# Prevent issues with reflection
-keep class **.R$* { *; }
-keep class **.databinding.* { *; }

# Keep classes implementing ParameterizedType (to fix ClassCastException with generics)
-keep class ** implements java.lang.reflect.ParameterizedType
-keepclassmembers class * {
    *;
}

# Gson specific rules (if using Gson)
-keep class com.google.gson.** { *; }

# Retrofit specific rules (if using Retrofit)
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }
-dontwarn retrofit2.**

# Keep necessary attributes and methods for reflection
-keepclassmembers class ** {
    @java.lang.reflect.ParameterizedType <fields>;
}

# Keep generic signature of Call, Response (R8 full mode strips signatures from non-kept items).
 -keep,allowobfuscation,allowshrinking interface retrofit2.Call
 -keep,allowobfuscation,allowshrinking class retrofit2.Response

 # With R8 full mode generic signatures are stripped for classes that are not
 # kept. Suspend functions are wrapped in continuations where the type argument
 # is used.
 -keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation