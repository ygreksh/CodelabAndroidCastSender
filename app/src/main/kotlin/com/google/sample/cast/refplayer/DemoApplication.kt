package com.google.sample.cast.refplayer

import androidx.multidex.MultiDexApplication


// Note: Multidex is enabled in code not AndroidManifest.xml because the internal build system
// doesn't dejetify MultiDexApplication in AndroidManifest.xml.
// Note: Multidex is enabled in code not AndroidManifest.xml because the internal build system
// doesn't dejetify MultiDexApplication in AndroidManifest.xml.
/** Application for multidex support.  */
class DemoApplication : MultiDexApplication()