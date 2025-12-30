# 🔐 SilverVault — Elderly-First Password Manager

> A security-first, accessibility-driven password manager built with React Native, showcasing native biometric integration, OS-level key management, and production-grade cryptography.

SilverVaut is intentionally designed **for elderly users**, particularly my elderly parents :-), where usability failures and accessibility gaps have real-world consequences. This project demonstrates senior-level engineering judgment across **security, native mobile development, and inclusive UX**.

---

## 🎯 Why This Project Exists

Modern password managers often optimize for power users, not older adults.  
SilverVault flips that priority:

- **Accessibility is not an afterthought**
- **Security is enforced at the OS boundary**
- **Sensitive secrets never touch JavaScript**

This project was built to demonstrate readiness for **high-trust, high-impact mobile systems**

---

## 🧠 Core Engineering Signals

✔ Custom native modules (Android & iOS)  
✔ Biometric authentication via OS frameworks  
✔ AES-256 encryption with secure key lifecycle  
✔ Elderly-first accessibility design  
✔ Clear security & threat modeling documentation  

---

## 🏗 Architecture Overview

```text
┌─────────────────────────────────────────────────────────┐
│                 React Native UI Layer                   │
│         (Accessibility-First Styled Components)         │
└────────────────────────────┬────────────────────────────┘
                             │
                    ⚡ JS Bridge / JSI
                             ▼
┌─────────────────────────────────────────────────────────┐
│            Custom Native Module (C++ / Java)            │
│                 SilverVaultBiometrics                   │
├─────────────────────────────────────────────────────────┤
│  • Biometric Authentication (FaceID/Fingerprint)        │
│  • Cryptographic Key Retrieval                          │
│  • Hardware-Accelerated Encrypt / Decrypt               │
└────────────────────────────┬────────────────────────────┘
                             │
                 🔐 Secure Hardware Call
                             ▼
┌─────────────────────────────────────────────────────────┐
│                   Secure OS Storage                     │
├────────────────────────────┬────────────────────────────┤
│        iOS Keychain        │      Android Keystore      │
│      (Secure Enclave)      │     (TEE / StrongBox)      │
└────────────────────────────┴────────────────────────────┘
```

📌 **Design Principle:**  
> Encryption keys never cross the JavaScript bridge in plaintext.

---

## 🔐 Security Model

### Encryption
- **Algorithm:** AES-256 (GCM preferred)
- **IV:** Secure random IV per encryption operation
- **Integrity:** Authenticated encryption
- **Storage:** Only encrypted payloads are persisted

### Key Management
- A single **master encryption key** is generated on first launch
- Stored exclusively in:
  - **iOS:** Keychain
  - **Android:** Android Keystore
- Key access is gated behind biometric authentication
- No hardcoded keys, no JS-level key storage

### Threat Model (High-Level)
- Lost or stolen device
- Malicious app access to local storage
- Unauthorized UI interaction
- Memory inspection attempts

Mitigations are documented in detail in `/docs/THREAT_MODEL.md`.

---

## 🧬 Custom Native Module

### Module Name
`SilverVaultBiometrics`

### Responsibilities
- Trigger OS-level biometric prompt
- Verify user presence
- Securely retrieve encryption key
- Perform encryption and decryption natively

### JavaScript API
```ts
SilverVaultBiometrics.authenticate(): Promise<boolean>
SilverVaultBiometrics.encrypt(data: string): Promise<string>
SilverVaultBiometrics.decrypt(cipherText: string): Promise<string>
```

📌 Intentional Constraint:
Raw keys are never exposed to JavaScript.

♿ Accessibility (Elderly-First by Design)

SilverSafe treats accessibility as a core system requirement, not a UI polish step.

Interaction Design

Extra-large tap targets (56–64dp minimum)

Clear pressed / focused states

No gesture-only interactions

Visual Accessibility

High-contrast themes (WCAG AA/AAA)

Dark mode support

No color-only indicators

Text & Scaling

Dynamic font scaling using:

PixelRatio

Responsive layout calculations

Supports extreme text scaling (200%+) without layout breakage

Screen Readers

Full VoiceOver and TalkBack support

Proper use of:

accessibilityLabel

accessibilityHint

accessibilityRole

Accessibility decisions and testing notes are documented in /docs/ACCESSIBILITY.md.

🧰 Tech Stack
Frontend

React Native (TypeScript)

React Navigation

Minimal global state (Context / Zustand)

Native

Android: BiometricPrompt, Android Keystore

iOS: LocalAuthentication, Keychain

Storage

Encrypted local persistence only

No plaintext secrets at rest

