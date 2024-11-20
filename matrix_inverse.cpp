#include <iostream>
#include <iomanip>
using namespace std;

// Function to calculate the determinant of a 2x2 matrix
double determinant2x2(double matrix[2][2]) {
    return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
}

// Function to calculate the determinant of a 3x3 matrix
double determinant3x3(double matrix[3][3]) {
    double det = 0;
    
    // Using the first row for expansion
    for(int i = 0; i < 3; i++) {
        double temp[2][2];
        int tempRow = 0, tempCol = 0;
        
        // Creating the 2x2 sub-matrix
        for(int row = 1; row < 3; row++) {
            tempCol = 0;
            for(int col = 0; col < 3; col++) {
                if(col != i) {
                    temp[tempRow][tempCol] = matrix[row][col];
                    tempCol++;
                }
            }
            tempRow++;
        }
        
        // Adding the terms with appropriate signs
        det += matrix[0][i] * determinant2x2(temp) * ((i % 2 == 0) ? 1 : -1);
    }
    
    return det;
}

// Function to find the adjoint matrix
void adjoint(double matrix[3][3], double adj[3][3]) {
    for(int i = 0; i < 3; i++) {
        for(int j = 0; j < 3; j++) {
            // Creating the 2x2 sub-matrix for cofactor
            double temp[2][2];
            int tempRow = 0, tempCol = 0;
            
            for(int row = 0; row < 3; row++) {
                if(row != i) {
                    tempCol = 0;
                    for(int col = 0; col < 3; col++) {
                        if(col != j) {
                            temp[tempRow][tempCol] = matrix[row][col];
                            tempCol++;
                        }
                    }
                    tempRow++;
                }
            }
            
            // Calculate cofactor with appropriate sign
            adj[j][i] = determinant2x2(temp) * ((i + j) % 2 == 0 ? 1 : -1);
        }
    }
}

// Function to find the inverse of the matrix
bool inverse(double matrix[3][3], double result[3][3]) {
    // Calculate determinant
    double det = determinant3x3(matrix);
    
    // Check if matrix is invertible
    if(det == 0) {
        cout << "Matrix is not invertible (determinant = 0)" << endl;
        return false;
    }
    
    // Calculate adjoint matrix
    double adj[3][3];
    adjoint(matrix, adj);
    
    // Calculate inverse using adjoint and determinant
    for(int i = 0; i < 3; i++) {
        for(int j = 0; j < 3; j++) {
            result[i][j] = adj[i][j] / det;
        }
    }
    
    return true;
}

// Function to display matrix
void displayMatrix(double matrix[3][3]) {
    for(int i = 0; i < 3; i++) {
        for(int j = 0; j < 3; j++) {
            cout << setw(10) << fixed << setprecision(4) << matrix[i][j] << " ";
        }
        cout << endl;
    }
}

int main() {
    double matrix[3][3];
    double inverse_matrix[3][3];
    
    cout << "Enter the elements of 3x3 matrix:" << endl;
    for(int i = 0; i < 3; i++) {
        for(int j = 0; j < 3; j++) {
            cout << "Enter element [" << i + 1 << "][" << j + 1 << "]: ";
            cin >> matrix[i][j];
        }
    }
    
    cout << "\nInput Matrix:" << endl;
    displayMatrix(matrix);
    
    if(inverse(matrix, inverse_matrix)) {
        cout << "\nInverse Matrix:" << endl;
        displayMatrix(inverse_matrix);
        
        // Verification: multiply original matrix with inverse
        cout << "\nVerification (Original × Inverse should be Identity Matrix):" << endl;
        double verify[3][3] = {0};
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                for(int k = 0; k < 3; k++) {
                    verify[i][j] += matrix[i][k] * inverse_matrix[k][j];
                }
            }
        }
        displayMatrix(verify);
    }
    
    return 0;
}