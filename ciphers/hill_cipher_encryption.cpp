//hill_cipher_encryption.cpp

#include <iostream>
#include <string.h>
using namespace std;

void printMatrix(int matrix[][2]){
    for(int i=0; i<2; i++){
        for(int j=0; j<2; j++){
            cout << matrix[i][j] << " ";
        }
        cout << endl;
    }
}

int main(){
    string key, plainText, encryptedText;
    encryptedText = "";

    cout << endl << "NOTE: KINDLY USE UPPERCASE THROUGHOUT THE PROGRAM !!!" << endl << endl;
    int shift=65;

    // cout << "note: kindly use lowercase throughout the program !!!" << endl;
    // int shift=97;

    cout << "Enter 4 letter keyword : ";
    cin >> key;
    while(key.length()!=4){
        cout << "Enter 4 letter keyword : ";
        cin >> key;
    }
    //key = "DCDF";
    cout << "Enter secret message : ";
    cin >> plainText;

    for(int i=0; i<key.length(); i++){
        key[i]-=shift;
    }
    for(int i=0; i<plainText.length(); i++){
        plainText[i]-=shift;
    }

    int keyMatrix[2][2] = {{0,0},{0,0}};
    int ptr = 0;
    for(int i=0; i<2; i++){
        for(int j=0; j<2; j++){
            keyMatrix[j][i] = key[ptr++];
        }
    }

    if(plainText.length()%2==1) plainText = plainText+"X";
    for(int i=0; i<plainText.length(); i+=2){
        int temp[] = {plainText[i], plainText[i+1]};
        for(int i=0; i<2; i++){
            int sum = 0;
            for(int j=0; j<2; j++){
                sum+=keyMatrix[i][j]*temp[j];
                //cout << "Mult " << keyMatrix[i][j] << " and " << temp[j] << endl;
            }
            sum = sum%26;
            sum+=shift;
            //cout << sum << endl;
            encryptedText+=sum;
        }
    }

    //printMatrix(keyMatrix);
    //cout << plainText << " length is " << plainText.length();
    cout << "\nEncrypted Text : " << encryptedText << endl;

    return 0;
}