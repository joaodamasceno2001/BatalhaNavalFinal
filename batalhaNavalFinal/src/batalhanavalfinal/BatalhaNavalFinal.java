/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batalhanavalfinal;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author joao_
 */
public class BatalhaNavalFinal {

    /**
     * @param args the command line arguments
     */
    static float qtdBalas = 0;
    static float qtdBalasAgua = 0;
    static int qtdBalasNavio = 0;
    static float qtdBalasRepetidas = 0;
    static float qtdBalasSubmarino = 0;
    static float qtdBalasDestroyer = 0;
    static float qtdBalasCruzador = 0;
    static float qtdBalasPortaAviao = 0;

    static final Tabuleiro[][] tabuleiro = new Tabuleiro[10][10];

    public static void main(String[] args) {

        File arquivo = new File("tabuleiroRes.txt");
        int[] embarcacoes = new int[11];

        inicializarTabuleiro('#');
//       load lendo o arquivo
        loadTabuleiro(arquivo, embarcacoes);

//um while dentro dentro do outro para loopar o atirar até todas as
//enbarcacoes dentro do vetor ser 0
//gerar log
//encenrrar aplicativo após gerado o log ou seja finalizar o primeiro while
        boolean op = true;

        Scanner tiro = new Scanner(System.in);

        while (op) {
            if (qtdBalasNavio == 24) {
                System.out.println(("Parabéns você ganhou!" + "\nVocê gastou: " + qtdBalas + "\nTiros na agua: " + Math.round((qtdBalasAgua * 100) / qtdBalas) + "% " + "\nTiros repetidos: " + Math.round((qtdBalasRepetidas * 100) / qtdBalas) + "% " + "\nTiros acertados: " + Math.round((qtdBalasNavio * 100) / qtdBalas) + "% "));
                op = false;
            }

            imprimirTabuleiro();
            System.out.println(qtdBalasNavio);
            System.out.println("Informe a Linha: ");
            int L = tiro.nextInt();
            System.out.println("Informe a Coluna: ");
            int C = tiro.nextInt();

            LimpaTela.limpaTela();
            atirar(L, C, embarcacoes);

        }

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
//        verifica o tabuleiro volta 1 se certo  se0 errado
/*
TODO:
a. ultrapassa os limites do tabuleiro;
b. utiliza uma casa já ocupada;
c. encosta em uma outra embarcação.

         */
        return 1;
    }

    //variaveis para obter informações sobre os tiros
    // dentro de cada case vai obtendo numeros sobre os tiros
    public static void atirar(int L, int C, int[] embarcacoes) {
        //        atirar em C e L
        //  verificar aonde o tiro está indo

        if (tabuleiro[L][C].secreto == false) {
            System.out.println("Tiro repetido");
            qtdBalasRepetidas++;

            if (tabuleiro[L][C].casa != '#') {

                tabuleiro[L][C].iEmbarcacao++;
            }

        }

        if (tabuleiro[L][C].casa == '#') {
            System.out.println("Você acertou a água! ");
            tabuleiro[L][C].secreto = false;
            qtdBalas++;
            qtdBalasAgua++;

        } else if (tabuleiro[L][C].casa == '#' && tabuleiro[L][C].casa == 'S') {

            System.out.println("Você acertou um Submarino! ");
            System.out.println("Vida antes do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao]);
            tabuleiro[L][C].secreto = false;
            embarcacoes[tabuleiro[L][C].iEmbarcacao]--;
            System.out.println("vida depois do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao]);
            qtdBalas++;
            qtdBalasNavio++;
            qtdBalasSubmarino++;

            if (qtdBalasSubmarino == 1) {
                System.out.println("Afundou um submarino");
                qtdBalasSubmarino = 0;
            }

        }

       else if (tabuleiro[L][C].casa != '#' && tabuleiro[L][C].casa == 'D') {

            System.out.println("Você acertou um Destroyer! ");
            System.out.println("vida antes do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao]);
            tabuleiro[L][C].secreto = false;
            embarcacoes[tabuleiro[L][C].iEmbarcacao]--;
            System.out.println("vida depois do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao]);
            qtdBalas++;
            qtdBalasNavio++;
            qtdBalasDestroyer++;

            if (qtdBalasDestroyer == 2) {
                System.out.println("Afundou um destroyer");
                qtdBalasDestroyer = 0;
            }

        }

       else if (tabuleiro[L][C].casa != '#' && tabuleiro[L][C].casa == 'C') {

            System.out.println("Você acertou um Cruzador! ");
            System.out.println("vida antes do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao]);
            tabuleiro[L][C].secreto = false;
            embarcacoes[tabuleiro[L][C].iEmbarcacao]--;
            System.out.println("vida depois do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao]);
            qtdBalas++;
            qtdBalasNavio++;
            qtdBalasCruzador++;

            if (qtdBalasCruzador == 4) {
                System.out.println("Afundou um Cruzador");
                qtdBalasCruzador = 0;
            }
        }

       else if (tabuleiro[L][C].casa != '#' && tabuleiro[L][C].casa == 'P') {

            System.out.println("Você acertou um Porta-Avião! ");
            System.out.println("vida antes do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao]);
            tabuleiro[L][C].secreto = false;
            embarcacoes[tabuleiro[L][C].iEmbarcacao]--;
            System.out.println("vida depois do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao]);
            qtdBalas++;
            qtdBalasNavio++;
            qtdBalasPortaAviao++;

            if (qtdBalasPortaAviao == 5) {
                System.out.println("Afundou um porta-avião");
                qtdBalasPortaAviao = 0;
            }
        }

        else if (tabuleiro[L][C].casa != '#' && tabuleiro[L][C].casa == 'S') {

            System.out.println("Você acertou um Submarino! ");
            System.out.println("Vida antes do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao]);
            tabuleiro[L][C].secreto = false;
            embarcacoes[tabuleiro[L][C].iEmbarcacao]--;
            System.out.println("vida depois do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao]);
            qtdBalas++;
            qtdBalasNavio++;
            qtdBalasSubmarino++;

            if (qtdBalasSubmarino == 1) {
                System.out.println("Afundou um submarino");
                qtdBalasSubmarino = 0;
            }

        }

       else if (tabuleiro[L][C].casa != '#' && tabuleiro[L][C].casa == 'D') {

            System.out.println("Você acertou um Destroyer! ");
            System.out.println("vida antes do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao]);
            tabuleiro[L][C].secreto = false;
            embarcacoes[tabuleiro[L][C].iEmbarcacao]--;
            System.out.println("vida depois do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao]);
            qtdBalas++;
            qtdBalasNavio++;
            qtdBalasDestroyer++;

            if (qtdBalasDestroyer == 2) {
                System.out.println("Afundou um destroyer");
                qtdBalasDestroyer = 0;
            }

        }

       else if (tabuleiro[L][C].casa != '#' && tabuleiro[L][C].casa == 'C') {

            System.out.println("Você acertou um Cruzador! ");
            System.out.println("vida antes do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao]);
            tabuleiro[L][C].secreto = false;
            embarcacoes[tabuleiro[L][C].iEmbarcacao]--;
            System.out.println("vida depois do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao]);
            qtdBalas++;
            qtdBalasNavio++;
            qtdBalasCruzador++;

            if (qtdBalasCruzador == 4) {
                System.out.println("Afundou um Cruzador");
                qtdBalasCruzador = 0;
            }
        }

       else if (tabuleiro[L][C].casa != '#' && tabuleiro[L][C].casa == 'P') {

            System.out.println("Você acertou um Porta-Avião! ");
            System.out.println("vida antes do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao]);
            tabuleiro[L][C].secreto = false;
            embarcacoes[tabuleiro[L][C].iEmbarcacao]--;
            System.out.println("vida depois do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao]);
            qtdBalas++;
            qtdBalasNavio++;
            qtdBalasPortaAviao++;

            if (qtdBalasPortaAviao == 5) {
                System.out.println("Afundou um porta-avião");
                qtdBalasPortaAviao = 0;
            }
        }
        
        
        
    }

    public static void gerarLog(File arquivo) {

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
