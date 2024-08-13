#include <iostream>
#include <string>
#include <cctype>

using namespace std;

int modInverse(int a, int m) {
    a = a % m;
    for (int x = 1; x < m; x++)
        if ((a * x) % m == 1)
            return x;
    return -1;
}

string affineEncrypt(const string& plaintext, int a, int b) {
    string ciphertext = "";
    
    for (char ch : plaintext) {
        if (isalpha(ch)) {
            char base = isupper(ch) ? 'A' : 'a';
            int x = ch - base;
            int encryptedX = (a * x + b) % 26;
            char encryptedChar = (encryptedX + base);
            ciphertext += encryptedChar;
        } else {
            ciphertext += ch;
        }
    }
    
    return ciphertext;
}

int main() {
    string plaintext;
    int a, b;

    cout << "Enter the plaintext: ";
    getline(cin, plaintext);

    cout << "Enter the value of 'a' (must be coprime with 26): ";
    cin >> a;

    if (modInverse(a, 26) == -1) {
        cout << "Error: 'a' is not coprime with 26. Encryption is not possible." << endl;
        return 1;
    }

    cout << "Enter the value of 'b': ";
    cin >> b;

    string ciphertext = affineEncrypt(plaintext, a, b);
    cout << "Encrypted text: " << ciphertext << endl;

    return 0;
}