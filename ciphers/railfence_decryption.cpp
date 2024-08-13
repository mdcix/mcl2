#include <iostream>
#include <string>
#include <vector>

using namespace std;

string railFenceDecrypt(const string& ciphertext, int rows) {
    if (rows <= 1) return ciphertext;

    vector<vector<int>> rail(rows, vector<int>(ciphertext.length(), -1));
    
    int row = 0, col = 0;
    bool down = false;

    for (int i = 0; i < ciphertext.length(); i++) {
        rail[row][col++] = 0;
        if (row == 0 || row == rows - 1)
            down = !down;
        row += down ? 1 : -1;
    }

    // Fill the rail matrix with ciphertext characters
    int index = 0;
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < ciphertext.length(); j++) {
            if (rail[i][j] == 0) {
                rail[i][j] = ciphertext[index++];
            }
        }
    }

    string plaintext;
    row = 0, col = 0;
    down = false;
    for (int i = 0; i < ciphertext.length(); i++) {
        plaintext += rail[row][col++];
        if (row == 0 || row == rows - 1)
            down = !down;
        row += down ? 1 : -1;
    }

    return plaintext;
}

int main() {
    string ciphertext;
    int rows;

    cout << "Enter the ciphertext: ";
    getline(cin, ciphertext);

    cout << "Enter the number of rows: ";
    cin >> rows;

    if (rows <= 0) {
        cout << "Number of rows must be positive." << endl;
        return 1;
    }

    string plaintext = railFenceDecrypt(ciphertext, rows);
    cout << "Decrypted text: " << plaintext << endl;

    return 0;
}