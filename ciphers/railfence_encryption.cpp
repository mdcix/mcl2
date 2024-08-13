#include <iostream>
#include <string>
#include <vector>

using namespace std;

string railFenceEncrypt(const string& plaintext, int rows) {
    if (rows <= 1) return plaintext;

    vector<string> rails(rows);
    int rail = 0;
    bool down = false;

    for (char c : plaintext) {
        rails[rail] += c;
        
        if (rail == 0 || rail == rows - 1)
            down = !down;
        
        rail += down ? 1 : -1;
    }

    string ciphertext;
    for (const string& rail : rails)
        ciphertext += rail;

    return ciphertext;
}

int main() {
    string plaintext;
    int rows;

    cout << "Enter the plaintext: ";
    getline(cin, plaintext);

    cout << "Enter the number of rows: ";
    cin >> rows;

    if (rows <= 0) {
        cout << "Number of rows must be positive." << endl;
        return 1;
    }

    string ciphertext = railFenceEncrypt(plaintext, rows);
    cout << "Encrypted text: " << ciphertext << endl;

    return 0;
}