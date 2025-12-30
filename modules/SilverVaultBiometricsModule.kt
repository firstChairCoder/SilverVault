package expo.modules.silvervault

import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.biometric.BiometricPrompt
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator

class SilverVaultBiometricsModule : Module() {
  override fun definition() = ModuleDefinition {
    Name("SilverVaultBiometrics")

    AsyncFunction("isBiometricAvailable") {
      val biometricManager = BiometricManager.from(context)
      return@AsyncFunction biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS
    }

    AsyncFunction("encrypt") { plaintext: String, options: AuthOptions ->
      val cipher = getCipher()
      val key = getOrCreateKey()
      
      // Forces hardware to require biometric before cipher initialization
      cipher.init(Cipher.ENCRYPT_MODE, key)
      
      val encryptedBytes = cipher.doFinal(plaintext.toByteArray())
      return@AsyncFunction Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    }

    AsyncFunction("decrypt") { ciphertext: String, options: AuthOptions ->
      val cipher = getCipher()
      val key = getKey()
      
      // This call will fail with UserNotAuthenticatedException 
      // if authenticate() wasn't called or timed out
      cipher.init(Cipher.DECRYPT_MODE, key)
      
      val decodedBytes = Base64.decode(ciphertext, Base64.DEFAULT)
      return@AsyncFunction String(cipher.doFinal(decodedBytes))
    }
  }

  private fun getOrCreateKey(): SecretKey {
    val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
    if (keyStore.containsAlias("SilverVaultKey")) {
        return keyStore.getKey("SilverVaultKey", null) as SecretKey
    }

    val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
    keyGenerator.init(
        KeyGenParameterSpec.Builder("SilverVaultKey", 
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setUserAuthenticationRequired(true) // 📌 CRITICAL: Hardware-enforced
            .setUserAuthenticationParameters(0, KeyProperties.AUTH_BIOMETRIC_STRONG)
            .build()
    )
    return keyGenerator.generateKey()
  }
}
