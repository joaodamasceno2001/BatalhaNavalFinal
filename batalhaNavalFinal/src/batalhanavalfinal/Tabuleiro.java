/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batalhanavalfinal;

import static batalhanavalfinal.BatalhaNavalFinal.tabuleiro;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author João Victor Damasceno, Lucas Farias Pacifico Albuquerque, Christian
 * Herculano, George Wanderson
 */
public class Tabuleiro {

    char casa; // 0, X, S, D, C, P
    int iEmbarcacao; // índice da embarcação no vetor embarcações
    boolean secreto = true; //valores reais (submarino...)

    Tabuleiro(char casa, int iEmbarcacao) { //atribuindo ao tabuleiro
        this.casa = casa;
        this.iEmbarcacao = iEmbarcacao;
    }

    Tabuleiro() {//construtor 
    }

    public static void inicializarTabuleiro(char casa) { // inicializa o tabuleiro (Com água)

        for (int L = 0; L < 10; L++) {
            for (int C = 0; C < 10; C++) {
                tabuleiro[L][C] = new Tabuleiro();
                tabuleiro[L][C].casa = casa;
                tabuleiro[L][C].iEmbarcacao = 69;
            }
        }

    }
    public static int maxPT = 0; //contador para cada embarcação que for carregada

    public static void loadTabuleiro(File arquivo, int[] embarcacoes) throws ArrayIndexOutOfBoundsException, FileNotFoundException {
//        carregar o arquivo TXT e atribuir as embarcações

        try {
            Scanner scan = new Scanner(arquivo);
            while (scan.hasNextLine()) {
                maxPT++;
                String linha = scan.nextLine();
                String[] LinhaTXT = new String[4]; //vetor de 4 posiçoes para cada linha do TXT
                LinhaTXT = linha.split(" ");//corta os espaços
                  
                //verificar tabuleiro, se retornar 1: tudo ok. retornar 0: Erro
                if (verificarTabuleiro(Integer.parseInt(LinhaTXT[0]), Integer.parseInt(LinhaTXT[1]), Integer.parseInt(LinhaTXT[3])) != 0) {//
                    tabuleiro[Integer.parseInt(LinhaTXT[0])][Integer.parseInt(LinhaTXT[1])].casa = LinhaTXT[2].charAt(0);                  // Atribuindo as embarcações ao tabuleiro
                    tabuleiro[Integer.parseInt(LinhaTXT[0])][Integer.parseInt(LinhaTXT[1])].iEmbarcacao = Integer.parseInt(LinhaTXT[3]);   //
                } else {
                    System.out.println("O arquivo do tabuleiro contem algum erro."); //vai dizer que existe Erro
                }
                embarcacoes[Integer.parseInt(LinhaTXT[3])]++; //atribuindo os indices de embarcaçoes
            }
        } catch (ArrayIndexOutOfBoundsException e) {  //quando tiver mais embarcaçoes do que deve, da erro!!!
            System.out.println("Erro: Quantidade de embarcações suportadas: " + BatalhaNavalFinal.tabuleiro.length + ". Quantidade de embarcações no arquivo:" + e.getMessage() + " ou mais");
        } catch (FileNotFoundException e) { //quando não encontrar o arquivo TXT
            System.out.println(e);
        }

    }

    public static int verificarTabuleiro(int x, int y, int iEmbarcacao) { //vericar cada x e y quando der load no tabuleiro
        //   verifica o tabuleiro volta 1 se certo  se0 errado
//   TODO:
//   a. ultrapassa os limites do tabuleiro;
        try {
            char casa = tabuleiro[x][y].casa;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("O arquivo utrapassou os limites do tabuleiro!");
            return 0;
        }
//   b. utiliza uma casa já ocupada;
        if (tabuleiro[x][y].casa != '#') {
            return 0;
        }
//   c. encosta em uma outra embarcação.
        int[] redor = new int[8];
//        superiores
        if (x != 0) {
            if (y != 0) {
                redor[0] = tabuleiro[x - 1][y - 1].iEmbarcacao;
            } else {
                redor[0] = 99;
            }
            redor[1] = tabuleiro[x - 1][y].iEmbarcacao;
            if (y != 9) {
                redor[2] = tabuleiro[x - 1][y + 1].iEmbarcacao;
            } else {
                redor[2] = 99;
            }
        } else {
            redor[0] = 99;
            redor[1] = 99;
            redor[2] = 99;
        }
//        Central
        if (y != 0) {
            redor[3] = tabuleiro[x][y - 1].iEmbarcacao;
        } else {
            redor[3] = 99;
        }
        if (y != 9) {
            redor[4] = tabuleiro[x][y + 1].iEmbarcacao;
        } else {
            redor[4] = 99;
        }
//        inferiores
        if (x != 9) {
            if (y != 0) {
                redor[5] = tabuleiro[x + 1][y - 1].iEmbarcacao;
            } else {
                redor[5] = 99;
            }
            redor[6] = tabuleiro[x + 1][y].iEmbarcacao;
            if (y != 9) {
                redor[7] = tabuleiro[x + 1][y + 1].iEmbarcacao;
            } else {
                redor[7] = 99;
            }
        } else {
            redor[5] = 99;
            redor[6] = 99;
            redor[7] = 99;
        }
        for (int i = 0; i < 8; i++) { //quando encontrar uma embarcação checar em qual indice vai estar
            if (redor[i] != 69 && redor[i] != 99) {
                if (redor[i] != iEmbarcacao) {
                    System.out.println("Index da posição = " + "[" + i + "]" + redor[i]);
                    System.out.println("Embarcacao em " + "[" + x + "]" + "[" + y + "]" + tabuleiro[x][y].iEmbarcacao);
                    return 0;
                }

            }
        }
        return 1;
    }

    public static void imprimirTabuleiro() { //imprime o tabuleiro na tela para o usuario

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
