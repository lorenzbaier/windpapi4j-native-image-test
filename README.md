# windpapi4j in graal native image example test project

## install
- install build tools `winget install --id=Microsoft.VisualStudio.2022.BuildTools -e`
- download and install graal vm https://www.graalvm.org/downloads/ (set env GRAALVM_HOME)

## run
- `.\gradlew run` -> runs normally on jvm all fine
- `.\gradlew nativeRun` -> fails because of:
  ```
  Caused by: org.graalvm.nativeimage.MissingReflectionRegistrationError: The program tried to reflectively access the proxy class inheriting [com.github.windpapi4j.Crypt32] without it being registered for runtime reflection. Add [com.github.windpapi4j.Crypt32] to the dynamic-proxy metadata to solve this probl
  em. Note: The order of interfaces used to create proxies matters. See https://www.graalvm.org/latest/reference-manual/native-image/metadata/#dynamic-proxy for help.
  at org.graalvm.nativeimage.builder/com.oracle.svm.core.reflect.MissingReflectionRegistrationUtils.forProxy(MissingReflectionRegistrationUtils.java:100)
  at org.graalvm.nativeimage.builder/com.oracle.svm.core.reflect.proxy.DynamicProxySupport.getProxyClass(DynamicProxySupport.java:176)
  at java.base@21.0.6/java.lang.reflect.Proxy.getProxyConstructor(Proxy.java:47)
  at java.base@21.0.6/java.lang.reflect.Proxy.newProxyInstance(Proxy.java:1034)
  at com.sun.jna.Native.load(Native.java:620)
  at com.github.windpapi4j.Crypt32.<clinit>(Crypt32.java:71)
  at com.github.windpapi4j.WinDPAPI.<init>(WinDPAPI.java:180)
  at com.github.windpapi4j.WinDPAPI.newInstance(WinDPAPI.java:243)
  ... 2 more
  ```
  ```
  Caused by: java.lang.Error: Structure.getFieldOrder() on class com.github.windpapi4j.WinCrypt$DATA_BLOB returns names ([cbData, pbData]) which do not match declared field names ([])
  at com.sun.jna.Structure.getFields(Structure.java:1161)
  at com.sun.jna.Structure.deriveLayout(Structure.java:1340)
  at com.sun.jna.Structure.calculateSize(Structure.java:1237)
  at com.sun.jna.Structure.calculateSize(Structure.java:1183)
  at com.sun.jna.Structure.allocateMemory(Structure.java:433)
  at com.sun.jna.Structure.<init>(Structure.java:223)
  at com.sun.jna.Structure.<init>(Structure.java:211)
  at com.sun.jna.Structure.<init>(Structure.java:198)
  at com.sun.jna.Structure.<init>(Structure.java:190)
  at com.github.windpapi4j.WinCrypt$DATA_BLOB.<init>(WinCrypt.java:93)
  at com.github.windpapi4j.WinDPAPI.doProtectData(WinDPAPI.java:438)
  at com.github.windpapi4j.WinDPAPI.protectData(WinDPAPI.java:426)
  ... 4 more
  
  ```

- `.\gradlew nativeRun -PincludeReflectConfig` -> works again because correct reflect config is included
  note that `src/main/resources/META-INF/native-image/proxy-config.json`
  and `src/main/resources/META-INF/native-image/reflect-config.json` are only included when this flag is set
  ()