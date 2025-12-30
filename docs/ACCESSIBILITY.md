# ♿ SilverVault Accessibility (Elderly-First)

## 🎯 Philosophy
SilverVault is built for users who may experience declining motor skills, visual acuity, or cognitive fatigue. We treat accessibility not as a feature, but as a hard system constraint.

---

## 📏 Interaction & Motor Control

### Target Sizing
- **Constraint:** All interactive elements (buttons, inputs, toggles) must have a minimum hit area of **56x56dp**.
- **Rationale:** Accommodates tremors, arthritis, or reduced fine motor precision.
- **Implementation:** ```tsx
  <TouchableOpacity hitSlop={{ top: 10, bottom: 10, left: 10, right: 10 }}>
```

1. Module Package Configuration (package.json)
This file defines the module's identity and ensures that the expo-module.config.json is recognized by the Expo CLI during the build process.

JSON

{
  "name": "expo-silversafe-biometrics",
  "version": "1.0.0",
  "description": "Hardware-backed biometric security module for SilverSafe.",
  "main": "index.ts",
  "types": "index.ts",
  "expoProperty": "expo-silversafe-biometrics",
  "keywords": [
    "expo",
    "react-native",
    "biometrics",
    "security",
    "keychain",
    "keystore"
  ],
  "peerDependencies": {
    "expo": "*",
    "expo-modules-core": "*"
  },
  "devDependencies": {
    "expo-module-scripts": "^3.0.0",
    "typescript": "^5.0.0"
  }
}
2. Accessibility Guidelines (docs/ACCESSIBILITY.md)
This document serves as the "Source of Truth" for the Elderly-First design philosophy. It is intended for both developers and auditors to understand why specific UI constraints exist.

Markdown

# ♿ SilverSafe Accessibility (Elderly-First)

## 🎯 Philosophy
SilverSafe is built for users who may experience declining motor skills, visual acuity, or cognitive fatigue. We treat accessibility not as a feature, but as a hard system constraint.

---

## 📏 Interaction & Motor Control

### Target Sizing
- **Constraint:** All interactive elements (buttons, inputs, toggles) must have a minimum hit area of **56x56dp**.
- **Rationale:** Accommodates tremors, arthritis, or reduced fine motor precision.
- **Implementation:** ```tsx
  <TouchableOpacity hitSlop={{ top: 10, bottom: 10, left: 10, right: 10 }}>
Gesture Independence
Strict Rule: No "hidden" gestures.

Guideline: Every action performable via a swipe or long-press must have a visible, single-tap button equivalent.

👁️ Visual Requirements
Contrast & Color
Standard: All text must meet WCAG AAA (7:1) contrast ratios where possible, and AA (4.5:1) at a minimum.

Anti-Pattern: Do not use color alone to convey meaning (e.g., a red border for error).

Requirement: Always include an icon or explicit text label (e.g., "Error: Invalid Code").

Typography
Dynamic Type: The app MUST respond to system-level font scaling.

Overflow Policy: Layouts must use Flexbox with flex-wrap to ensure text doesn't truncate when scaled to 200%.

Font Weight: Prefer Medium or Semi-Bold for body text to improve legibility on low-brightness screens.

🔊 Screen Readers (VoiceOver / TalkBack)
Semantic Labeling
Every component must provide a clear "What" and "Why":

Label: What the element is ("Unlock Vault Button").

Hint: What happens if they click it ("Triggers FaceID to show your passwords").

Role: Defines the component type ("Button", "Header", "Adjustable").

Announcement Management
Use AccessibilityInfo.announceForAccessibility() for temporary status changes (e.g., "Scanning fingerprint...") so the user isn't left in silence during async native processes.

🧪 Testing Checklist
[ ] Can I navigate the entire app using only a Bluetooth Switch?

[ ] Is the app usable at 200% system font scaling?

[ ] Does every screen have a clear, focused heading for screen readers?

[ ] Are there any "timed" interactions that might expire too quickly? (No auto-dismissing alerts).
