//caeser_encryption.cpp
#include <iostream>
using namespace std;

int main() {
    //int key;
    //cout << "Enter number of shift positions : ";
    //cin >> key;
    string plainText, encryptedText;
    cout << "Enter your Message : ";
    cin >> plainText;
    encryptedText = plainText;
    for(int i=0; i<encryptedText.length(); i++){
        //encryptedText[i] = (encryptedText[i]-97+key)%26 + 97;
        encryptedText[i] = (encryptedText[i]-97+3)%26 + 97;
        //encryptedText[i] = encryptedText[i]+97;
    }
    cout << "Encrypted Text is " <<encryptedText << endl;
    return 0;
}
