#include <iostream>
using namespace std;

#define SIZE 3

// Function to calculate the determinant of a 3x3 matrix
float determinant(float matrix[SIZE][SIZE]) {
    return matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1]) -
           matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0]) +
           matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);
}

// Function to calculate the inverse of a 3x3 matrix
int inverse(float matrix[SIZE][SIZE], float inverse[SIZE][SIZE]) {
    float det = determinant(matrix);
    if (det == 0) {
        cout << "Matrix is singular and cannot be inverted." << endl;
        return 0; // Indicates failure
    }

    // Calculate adjugate matrix
    float adjugate[SIZE][SIZE];
    adjugate[0][0] = matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1];
    adjugate[0][1] = -(matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0]);
    adjugate[0][2] = matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0];
    adjugate[1][0] = -(matrix[0][1] * matrix[2][2] - matrix[0][2] * matrix[2][1]);
    adjugate[1][1] = matrix[0][0] * matrix[2][2] - matrix[0][2] * matrix[2][0];
    adjugate[1][2] = -(matrix[0][0] * matrix[2][1] - matrix[0][1] * matrix[2][0]);
    adjugate[2][0] = matrix[0][1] * matrix[1][2] - matrix[0][2] * matrix[1][1];
    adjugate[2][1] = -(matrix[0][0] * matrix[1][2] - matrix[0][2] * matrix[1][0]);
    adjugate[2][2] = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

    // Calculate the inverse using the formula A^-1 = (1/det) * adj(A)
    for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE; j++) {
            inverse[i][j] = adjugate[i][j] / det;
        }
    }
    return 1; // Indicates success
}

// Function to print a matrix
void printMatrix(float matrix[SIZE][SIZE]) {
    for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE; j++) {
            cout << matrix[i][j] << " ";
        }
        cout << endl;
    }
}

int main() {
    float matrix[SIZE][SIZE] = {
        {4, 7, 2},
        {3, 6, 1},
        {2, 5, 3}
    };

    float inv[SIZE][SIZE];

    if (inverse(matrix, inv)) {
        cout << "Inverse of the matrix is:" << endl;
        printMatrix(inv);
    }

    return 0;
}
