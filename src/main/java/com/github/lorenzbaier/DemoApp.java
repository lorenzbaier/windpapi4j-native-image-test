package com.github.lorenzbaier;

import com.github.windpapi4j.InitializationFailedException;
import com.github.windpapi4j.WinDPAPI;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DemoApp {
  public static void main(String[] args) throws Exception {
    if(WinDPAPI.isPlatformSupported()) {
      WinDPAPI winDPAPI = WinDPAPI.newInstance(WinDPAPI.CryptProtectFlag.CRYPTPROTECT_UI_FORBIDDEN);

      String message = "Hello World!";
      String charsetName = "UTF-8";

      byte[] clearTextBytes = message.getBytes(charsetName);

      byte[] cipherTextBytes = winDPAPI.protectData(clearTextBytes);

      System.out.println("cypher text b64: " + Base64.getEncoder().encodeToString(cipherTextBytes));

      byte[] decryptedBytes = winDPAPI.unprotectData(cipherTextBytes);

      String decryptedMessage = new String(decryptedBytes, charsetName);

      if(! message.equals(decryptedMessage) ) {
        // should not happen
        throw new IllegalStateException(message + " != " + decryptedMessage);
      }

      System.out.println("decrypted message: " + decryptedMessage);
    } else {
      System.err.println("ERROR: platform not supported");
    }
  }
}
