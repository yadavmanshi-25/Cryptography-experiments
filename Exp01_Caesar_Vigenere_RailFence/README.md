# Experiment 1: Implementation of Caesar, Vigenère, and Rail Fence Ciphers

## 1. Aim

To implement Caesar, Vigenère, and Rail Fence ciphers to encrypt and decrypt the given plaintext `HELLO WORLD` and compare their working and effectiveness.

## 2. Software/Tool Required

- CrypTool 2.1 – For Caesar Cipher and Vigenère Cipher.
- CrypTool Online – For Rail Fence Cipher.
- Web Browser – To access CrypTool Online.

## 3. Algorithms

### A. Caesar Cipher

1. Take the plaintext `HELLO WORLD`.
2. Choose a shift value of 3.
3. Replace each letter with the letter three positions ahead in the alphabet.
4. Keep the space between the words.
5. To decrypt, shift each ciphertext letter three positions backward.

**Plaintext:** `HELLO WORLD`  
**Key:** `3`  
**Ciphertext:** `KHOOR ZRUOG`

### B. Vigenère Cipher
1. Take the plaintext `HELLO WORLD`.
2. Use the key `KEY`.
3. Repeat the key over the plaintext letters, ignoring spaces when applying the key.
4. Shift each plaintext letter according to the corresponding key letter.
5. Combine the resulting letters to obtain the ciphertext.
6. To decrypt, reverse the shifts using the same key.

**Plaintext:** `HELLO WORLD`  
**Key:** `KEY`  
**Ciphertext:** `RIJVS UYVJN`


## 4. Working Procedure

### A. Caesar Cipher

1. Open CrypTool 2.1.
2. Select the Caesar Cipher component.
3. Enter the plaintext `HELLO WORLD`.
4. Set the shift value to 3.
5. Perform encryption and record the ciphertext.
6. Decrypt the ciphertext using the same shift value.
7. Verify that the original plaintext is recovered.

### B. Vigenère Cipher

1. Open CrypTool 2.1.
2. Select the Vigenère Cipher component.
3. Enter the plaintext `HELLO WORLD`.
4. Enter the key `KEY`.
5. Perform encryption and record the ciphertext.
6. Decrypt the ciphertext using the same key.
7. Verify that the original plaintext is recovered.

## 5. Screenshots

### A. Caesar Cipher

#### Encryption Output

![Caesar Encryption](Screenshots/Caesar_Encryption.png)

#### Decryption Output

![Caesar Decryption](Screenshots/Caesar_Decryption.png)

### B. Vigenère Cipher

#### Encryption Output

![Vigenere Encryption](Screenshots/Vigenere_Encryption.png)

#### Decryption Output

![Vigenere Decryption](Screenshots/Vigenere_Decryption.png)

## 6. Comparison of Working and Effectiveness

| Cipher | Working Method | Effectiveness |
|---|---|---|
| Caesar Cipher | Shifts each letter by a fixed number of positions. | Easy to use, but easily broken because of its small key space. |
| Vigenère Cipher | Uses a repeating keyword to determine the shift for each letter. | More resistant than Caesar to simple frequency analysis, but vulnerable to attacks on repeated-key patterns. |


### Explanation

- **Caesar Cipher:** Changes each letter according to a fixed shift.
- **Vigenère Cipher:** Uses a keyword to apply different shifts to letters.

These classical ciphers demonstrate basic encryption techniques but are not suitable for modern secure communication.

## 7. Result

Caesar and Vigenère ciphers both are implemented for encryption and decryption. Their working methods and effectiveness were compared.

Caesar uses a fixed shift, Vigenère uses a repeating keyword.
## 8. Conclusion

The experiment demonstrated substitution and transposition techniques in classical cryptography. The results show that these ciphers are useful for understanding encryption fundamentals but have significant security limitations.
