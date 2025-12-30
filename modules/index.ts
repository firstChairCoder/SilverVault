import {
  EventEmitter,
  NativeModule,
  registerWebModule,
} from "expo-modules-core";

export enum SilverVaultError {
  BIOMETRIC_NOT_AVAILABLE = "BIOMETRIC_NOT_AVAILABLE",
  AUTH_CANCELLED = "AUTH_CANCELLED",
  AUTH_FAILED = "AUTH_FAILED",
  KEY_NOT_FOUND = "KEY_NOT_FOUND",
  ENCRYPTION_FAILED = "ENCRYPTION_FAILED",
  DECRYPTION_FAILED = "DECRYPTION_FAILED",
}

export type AuthOptions = {
  reason?: string;
};

export declare class SilverVaultBiometricsModule extends NativeModule {
  isBiometricAvailable(): Promise<boolean>;
  authenticate(options?: AuthOptions): Promise<void>;
  encrypt(plaintext: string, options?: AuthOptions): Promise<string>;
  decrypt(ciphertext: string, options?: AuthOptions): Promise<string>;
}

import SilverVaultBiometrics from "./SilverVaultBiometricsModule";
export { SilverVaultBiometrics };
