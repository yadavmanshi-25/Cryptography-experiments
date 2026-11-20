# Experiment 3: DES and AES Encryption and Decryption Using CrypTool

## 1. Aim
To perform encryption and decryption using DES (Data Encryption Standard) and AES (Advanced Encryption Standard) algorithms in CrypTool and understand the working of symmetric-key cryptography.

## 2. Tools Required
- Operating System: Windows
- CrypTool 2
- Computer or Laptop

## 3. Algorithm

### A. DES Encryption and Decryption
1. Open CrypTool 2.
2. Create a new workspace.
3. Add the Text Input component and enter the plaintext message.
4. Add the DES encryption component.
5. Configure the DES algorithm, encryption mode, and padding.
6. Connect the input message to the DES component.
7. Execute the workspace to encrypt the plaintext.
8. Display the encrypted ciphertext using the appropriate output component.
9. For decryption, provide the ciphertext as input to the DES component.
10. Select the decryption action and use the same secret key and compatible settings.
11. Execute the workspace and verify that the original plaintext is recovered.

### B. AES Encryption and Decryption
1. Open CrypTool 2 and create a workspace.
2. Add a Text Input component and enter the plaintext message.
3. Add the AES encryption component.
4. Configure the AES key size, encryption mode, and padding.
5. Connect the input message and key to the AES component.
6. Execute the workspace to generate the ciphertext.
7. Display the ciphertext using the appropriate output component.
8. For decryption, provide the ciphertext as input.
9. Select the decryption action and use the same secret key and compatible settings.
10. Execute the workspace and verify that the original plaintext is recovered.

## 4. Screenshot

The following screenshots demonstrate DES and AES encryption and decryption using CrypTool 2.

### 1. AES Encryption
![AES Encryption](screenshot/aes%20encryption.png)

### 2. AES Decryption
![AES Decryption](screenshot/aes%20decryption.png)

### 3. DES Encryption
![DES Encryption](screenshot/des%20encryption.jpeg)

### 4. DES Decryption
![DES Decryption](screenshot/des%20decryption.jpeg)


## 5. Result
DES and AES encryption and decryption were demonstrated using CrypTool 2. The encryption processes generated ciphertext from plaintext, and the decryption processes recovered the original messages when the correct key and compatible settings were used.

## 6. Conclusion
This experiment demonstrates the working of two symmetric-key encryption algorithms, DES and AES, using CrypTool 2. Both algorithms use a secret key for encryption and decryption. AES provides stronger security than DES and is preferred for modern cryptographic applications.