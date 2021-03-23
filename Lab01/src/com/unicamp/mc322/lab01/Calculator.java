package com.unicamp.mc322.lab01;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        start();
    }

    private static void start() {
        System.out.println(
            "1) Digite 1 para somar;\n"
                .concat("2) Digite 2 para subtrair;\n")
                .concat("3) Digite 3 para multiplicar;\n")
                .concat("4) Digite 4 para dividir;\n")
                .concat("5) Digite 5 para calcular fatorial;\n")
                .concat("6) Digite 6 para verificar se um número é primo;\n")
                .concat("7) Digite qualquer outro valor para sair;\n")
        );

        Scanner scan = new Scanner(System.in);
        System.out.print("Opção: ");
        int opt;
        try {
            opt = scan.nextInt();
        } catch (InputMismatchException e) {
            // Se não for um número inteiro,
            // é um "qualquer outro valor", então terminamos
            scan.close();
            return;
        }
        System.out.print("\n");

        int[] args;

        if (opt >= 1 && opt <= 4) {
            // Se for soma, subtração, multiplicação ou divisão,
            // precisamos de 2 números
            args = getArguments(2);
        } else if (opt >= 5 && opt <= 6) {
            // Caso contrário, as outras opções só precisam
            // de 1 número
            args = getArguments(1);
        } else {
            // Qualquer outro valor, terminamos
            scan.close();
            return;
        }

        String answer;

        switch (opt) {
        case 1: // Soma
            answer = String.valueOf(args[0] + args[1]);
            break;
        case 2: // Subtração
            answer = String.valueOf(args[0] - args[1]);
            break;
        case 3: // Multiplicação
            answer = String.valueOf(args[0] * args[1]);
            break;
        case 4: // Divisão
            answer = String.valueOf(args[0] / args[1]);
            break;
        case 5: // Fatorial
            int f = args[0];
            if (f < 0) {
                answer = "Não existe fatorial de número negativo.";
            } else {
                // Enquanto f não for 0, multiplicamos o resultado por f e diminuímos f.
                int res = 1;
                while (f > 0) {
                    res *= f;
                    f--;
                }

                answer = String.valueOf(res);
            }
            break;
        case 6: // Verificar se é primo
            answer = args[0] + " é primo.";
            int n = args[0];
            for (int i = 2; i <= n / 2; i++) {
                // Se n for divisível por qualquer outro valor que não for 1 (o loop não chega
                // em 1)
                // ou ele mesmo (o loop começa em n/2), ele não é primo.
                if (n % i == 0) {
                    answer = args[0] + " não é primo.";
                    break;
                }
            }
            break;
        default:
            scan.close();
            return;
        }

        scan = new Scanner(System.in);
        System.out.printf("Resultado: %s\nAperte qualquer botão para continuar.\n", answer);
        scan.nextLine(); // Aguardamos qualquer ação
        start(); // e reiniciamos o programa
    }

    /**
     * Captura e retorna um vetor com uma certa quantidade de números inteiros.
     * 
     * @param amount Quantidade de números inteiros pra capturar.
     * @return Vetor com os números inteiros capturados.
     */
    private static int[] getArguments(int amount) {
        System.out.printf("Digite %d número(s) inteiro(s): ", amount);

        int[] args = new int[amount];
        boolean fail = false;

        Scanner scan = new Scanner(System.in);
        for (int i = 0; i < amount; i++) {
            try {
                args[i] = scan.nextInt();
            } catch (InputMismatchException e) {
                // Se não for um número, tentamos novamente.
                System.out.println("Argumento inserido não é um número inteiro válido.");
                fail = true;
                break;
            }
        }

        if (fail) {
            return getArguments(amount);
        }

        System.out.print("\n");
        return args;
    }
}
