#include <iostream>
#include <string>

using namespace std;

int modInverse(int a, int m) {
    a = a % m;
    for (int x = 1; x < m; x++) {
        if ((a * x) % m == 1) {
            return x;
        }
    }
    return -1;
}

string affineDecrypt(const string& ciphertext, int a, int b) {
    string plaintext = "";
    int modInvA = modInverse(a, 26);
    if (modInvA == -1) {
        cout << "Error: 'a' has no modular inverse under modulo 26. Decryption is not possible." << endl;
        return "";
    }

    for (char ch : ciphertext) {
        if (isalpha(ch)) {
            char base = isupper(ch) ? 'A' : 'a';
            plaintext += (char)(((modInvA * (ch - base - b + 26)) % 26) + base);
        } else {
            plaintext += ch;
        }
    }
    return plaintext;
}

int main() {
    string ciphertext;
    int a, b;

    cout << "Enter the ciphertext: ";
    getline(cin, ciphertext);

    cout << "Enter the value of 'a': ";
    cin >> a;

    cout << "Enter the value of 'b': ";
    cin >> b;

    string plaintext = affineDecrypt(ciphertext, a, b);
    if (!plaintext.empty()) {
        cout << "Decrypted text: " << plaintext << endl;
    }

    return 0;
}
