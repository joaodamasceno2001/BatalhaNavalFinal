/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batalhanavalfinal;

import static batalhanavalfinal.BatalhaNavalFinal.tabuleiro;
import java.io.File;
import java.util.Scanner;

/**
 *
 * @author joao_
 */
public class Tabuleiro {

    char casa; // 0, X, S, D, C, P
    int iEmbarcacao; // índice da embarcação no vetor embarcações
    boolean secreto = true;

    Tabuleiro(char casa, int iEmbarcacao) {
        this.casa = casa;
        this.iEmbarcacao = iEmbarcacao;
    }

    Tabuleiro() {
    }

    public static void inicializarTabuleiro(char casa) {

        for (int L = 0; L < 10; L++) {
            for (int C = 0; C < 10; C++) {
                tabuleiro[L][C] = new Tabuleiro();
                tabuleiro[L][C].casa = casa;
            }
        }

    }

    public static void loadTabuleiro(File arquivo, int[] embarcacoes) {
//        percorrer todas as linhas do arquivo

        try {
            Scanner scan = new Scanner(arquivo);
            while (scan.hasNextLine()) {
                String linha = scan.nextLine();
                String[] val = new String[4];
                val = linha.split(" ");
                tabuleiro[Integer.parseInt(val[0])][Integer.parseInt(val[1])].casa = val[2].charAt(0);
                tabuleiro[Integer.parseInt(val[0])][Integer.parseInt(val[1])].iEmbarcacao = Integer.parseInt(val[3]);
                embarcacoes[Integer.parseInt(val[3])]++;
            }
        } catch (Exception e) {

        }
        
        
    }

   public static int verificarTabuleiro(char tabuleiro[], int L, int C, char embarcacao) {
   //   verifica o tabuleiro volta 1 se certo  se0 errado
   /*
   TODO:
   a. ultrapassa os limites do tabuleiro;
   b. utiliza uma casa já ocupada;
   c. encosta em uma outra embarcação.

         */
           
        
   
          
   
        return 1;
    }

    public static void imprimirTabuleiro() {

        System.out.println("  0 1 2 3 4 5 6 7 8 9");
        for (int C = 0; C < 10; C++) {
            System.out.print(C);
            for (int L = 0; L < 10; L++) {
                if (tabuleiro[C][L].secreto) {
                    System.out.print(" #");

                } else {
                    System.out.print(" " + tabuleiro[C][L].casa);

                }
            }
            System.out.println();
        }
    }

}
