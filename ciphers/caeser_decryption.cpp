//caeser_decryption.cpp
#include <iostream>
using namespace std;

int main() {
    //int key;
    //cout << "Enter number of shift positions : ";
    //cin >> key;
    string plainText, encryptedText;
    cout << "Enter Encrypted Text : ";
    cin >> encryptedText;
    plainText = encryptedText;
    for(int i=0; i<plainText.length(); i++){
        //plainText[i] = (plainText[i]-97+26-key)%26 + 97;
        plainText[i] = (plainText[i]-97+26-3)%26 + 97;
    }
    cout << "Plain Text is " <<plainText << endl;
    return 0;
}
