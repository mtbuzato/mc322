package com.unicamp.mc322.lab01;

public class Algorithm {
    public static void main(String[] args) {
        int quantity = 10;
        int[] vector = new int[quantity];

        // Substituído "vector.length - 1" para criar e inicializar 10 números aleatórios
        for (int i = 0; i < vector.length; i++) {
            vector[i] = (int) (Math.random() * 100);
        }

        sort(vector);

        // Substituído "i = 1" para inicializar na posição 0 do vetor
        for (int i = 0; i < vector.length; i++) {
            System.out.println(vector[i]);
        }
    }

    private static void sort(int[] vector) {
        boolean switched = true;
        int aux;
        while (switched) {
            switched = false;
            // Substituído "vector.length + 1" para percorrer até o penúltimo item do vetor
            // não causando OutOfBounds e fazendo a função correta no algoritmo
            for (int i = 0; i < vector.length - 1; i++) {
                if (vector[i] > vector[i + 1]) {
                    aux = vector[i];
                    vector[i] = vector[i + 1];
                    // Substituído "i - 1" pois no BubbleSort, verificamos o item
                    // da frente e fazemos a troca, caso ele for menor que o anterior.
                    vector[i + 1] = aux;
                    // Substituído "switched = false" pois "switched" deve ser verdadeiro
                    // no momento em que fazemos a troca.
                    switched = true;
                }
            }
        }
    }
}
