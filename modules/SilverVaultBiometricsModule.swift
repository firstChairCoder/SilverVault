import ExpoModulesCore
import LocalAuthentication

public class SilverVaultBiometricsModule: Module {
  public func definition() -> ModuleDefinition {
    Name("SilverVaultBiometrics")

    AsyncFunction("isBiometricAvailable") { () -> Bool in
      let context = LAContext()
      return context.canEvaluatePolicy(.deviceOwnerAuthenticationWithBiometrics, error: nil)
    }

    AsyncFunction("authenticate") { (options: AuthOptions) in
      let context = LAContext()
      let reason = options.reason ?? "Authenticate to continue"
      
      try await context.evaluatePolicy(.deviceOwnerAuthenticationWithBiometrics, localizedReason: reason)
    }

    AsyncFunction("encrypt") { (plaintext: String, options: AuthOptions) -> String in
      // 1. Create Access Control (Biometric required)
      let access = SecAccessControlCreateWithFlags(nil,
                                                   kSecAttrAccessibleWhenPasscodeSetThisDeviceOnly,
                                                   .biometryCurrentSet,
                                                   nil)!
      
      // 2. Perform Encryption logic (simplified for brevity)
      // In production, we use the Public Key from Secure Enclave
      return try KeychainProvider.encrypt(plaintext, access: access)
    }

    AsyncFunction("decrypt") { (ciphertext: String, options: AuthOptions) -> String in
      // This will automatically trigger the FaceID/TouchID prompt 
      // because of the .biometryCurrentSet flag on the key
      return try KeychainProvider.decrypt(ciphertext)
    }
  }
}
