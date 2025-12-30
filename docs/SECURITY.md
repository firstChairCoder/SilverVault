# 🔐 SilverSafe Security Overview

This document describes the security architecture, cryptographic decisions, and threat model for **SilverSafe**, an elderly-first password manager.

Security in SilverSafe is treated as a **system-level concern**, not a UI feature.

---

## 🎯 Security Goals

1. Protect sensitive credentials at rest and in memory
2. Prevent unauthorized access even if local storage is compromised
3. Enforce OS-level user presence verification
4. Minimize JavaScript exposure to secrets
5. Favor platform security primitives over custom crypto

---

## 🔑 Key Management Strategy

### Master Encryption Key
- A single **master encryption key** is generated per app installation
- Key size: **256 bits**
- Generated using platform-secure random generators
- Stored exclusively in secure OS storage

| Platform | Secure Store |
|--------|-------------|
| iOS    | Keychain    |
| Android| Android Keystore |

📌 **Critical Rule:**  
The master key is **never persisted, logged, or exposed** in JavaScript.

---

### Key Lifecycle

1. **Generation**
   - Occurs on first successful biometric enrollment
   - Generated natively
2. **Storage**
   - Keychain / Keystore only
3. **Access**
   - Gated behind biometric authentication
4. **Destruction**
   - Automatically removed on app uninstall
   - Optional future support for manual key reset

---

## 🔐 Encryption Design

### Algorithm
- **AES-256-GCM** (preferred)
  - Confidentiality
  - Integrity
  - Authentication

### Initialization Vector (IV)
- Generated per encryption operation
- Cryptographically secure random
- Stored alongside ciphertext

### Data Flow
1. User authenticates via biometrics
2. Native module retrieves master key
3. Data encrypted natively
4. Encrypted payload returned to JS
5. JS persists encrypted data only

---

## 🚫 JavaScript Security Boundaries

JavaScript is treated as a **potentially compromised environment**.

### JS Layer Limitations
- ❌ Cannot generate encryption keys
- ❌ Cannot store plaintext secrets
- ❌ Cannot retrieve raw encryption keys
- ❌ Cannot bypass biometric checks

### JS Layer Permissions
- ✔ Request encryption/decryption
- ✔ Store encrypted payloads
- ✔ Display decrypted data temporarily in memory

---

## 🧠 Memory Handling

- Plaintext secrets exist **only in volatile memory**
- Cleared immediately after use where possible
- No logging of sensitive data
- No background persistence of decrypted content

---

## 🔍 Threat Model

### Considered Threats

| Threat | Mitigation |
|------|-----------|
| Lost or stolen device | Biometric-gated key access |
| Malicious app reading storage | Encrypted payloads only |
| Screenshot attacks | OS-level protections |
| JS runtime compromise | Native-only key handling |
| Replay attacks | Authenticated encryption |

### Non-Goals
- Defense against fully compromised OS
- Defense against hardware-level attacks

---

## 🔄 Future Hardening (Planned)

- Key rotation support
- Secure Enclave-backed keys (iOS)
- Hardware-backed Keystore enforcement (Android)
- Encrypted backups with user-controlled passphrase

---

## 📌 Summary

SilverSafe’s security model prioritizes:
- **Least privilege**
- **OS-enforced trust boundaries**
- **Explicit threat awareness**
- **Fail-safe defaults**

This architecture mirrors security expectations for financial, healthcare, and identity-critical mobile applications.
