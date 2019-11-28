/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batalhanavalfinal;

import static batalhanavalfinal.Tabuleiro.imprimirTabuleiro;
import static batalhanavalfinal.Tabuleiro.inicializarTabuleiro;
import static batalhanavalfinal.Tabuleiro.loadTabuleiro;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author João Victor Damasceno, Lucas Farias Pacifico Albuquerque, Christian Herculano, George Wanderson
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

    static final Tabuleiro[][] tabuleiro = new Tabuleiro[10][10]; //matriz do tabuleiro
    private static File log = new File("log.txt"); //salvar as informaçoes do jogo, OBS: APENAS QUANDO GANHA O JOGO!!!
    private static FileWriter arq; // diz qual arquivo vai se escrito
    private static PrintWriter GravarArq; // Escreve no arquivo, possui todos os metodos para escrever no arquivo ...
    private static boolean op = true; //loop do jogo!

    public static void main(String[] args) throws Exception { //Onde a desgraça começa!
        arq = new FileWriter(log); //especificando qual arquivo vai lido
        GravarArq = new PrintWriter(arq); // Escreve no log.TXT

        File arquivo = new File("tabuleiroRes.txt"); //estanciando o arquivo TXT do tabuleiro 
        int[] embarcacoes = new int[11];   //vetor de embarcações

        inicializarTabuleiro('#'); //colocando agua no tabuleiro
        loadTabuleiro(arquivo, embarcacoes); //Carrega o tabuleiro com o arquivo que vai ser lido e carregar o tabuleiro embarcações

        Scanner tiro = new Scanner(System.in); //ler o tiro 

        while (op == true) { // loop do jogo 
            if (qtdBalasNavio == Tabuleiro.maxPT) { //Condição de vitória
                System.out.println(("Parabéns você ganhou!" + "\nVocê gastou: " + qtdBalas + "tiros\nTiros na agua: " + Math.round((qtdBalasAgua * 100) / qtdBalas) + "% " + "\nTiros repetidos: " + Math.round((qtdBalasRepetidas * 100) / qtdBalas) + "% " + "\nTiros acertados: " + Math.round((qtdBalasNavio * 100) / qtdBalas) + "% "));
                imprimirTabuleiro(); //mostrar o tabuleiro pela ultima vez
                GravarArq.write("Parabéns você ganhou!" + "\nVocê gastou: " + qtdBalas + "tiros\nTiros na agua: " + Math.round((qtdBalasAgua * 100) / qtdBalas) + "% " + "\nTiros repetidos: " + Math.round((qtdBalasRepetidas * 100) / qtdBalas) + "% " + "\nTiros acertados: " + Math.round((qtdBalasNavio * 100) / qtdBalas) + "% ");
                //gravando no log
                GravarArq.close();// fechando os arquivos
                arq.close(); //fechando os arquivos
                break;
            }
            imprimirTabuleiro(); //imprime
            atirar(embarcacoes); //chamr o metodo atirar e passar o parâmentro do i.embarcação
        }

    }

    public static void atirar(int[] embarcacoes) throws IOException { //metodo de aticar
        GravarArq.write("\n");
        Scanner tiro = new Scanner(System.in);
        int L = 0;
        int C = 0;
        try {
            System.out.println("Informe a Linha: ");
            L = tiro.nextInt();
            System.out.println("Informe a Coluna: ");
            C = tiro.nextInt();
            LimpaTela.limpaTela();
            GravarArq.write("informe a Linha: " + L + "\n" + "informe a Coluna: " + C + "\n");
            atirar(L, C, embarcacoes); //atira na linha e coluna que usuario escolheu e tira a vida da embarcaçõa
        } catch (InputMismatchException e) { //caso digite errado.
            LimpaTela.limpaTela();
            System.out.println("Tiro invalido, você digitou " + e.getMessage());
            GravarArq.write("Tiro invalido, você digitou " + e.getMessage());
        }
    }

    //variaveis para obter informações sobre os tiros
    
    public static void atirar(int L, int C, int[] embarcacoes) throws IOException { //outro metodo atirar
        //        atirar em C e L
        //  verificar aonde o tiro está indo
        try {
            if (tabuleiro[L][C].secreto == false) { //caso houve um tiro repetido
                System.out.println("Tiro repetido");
                qtdBalasRepetidas++;
                qtdBalas++;
                GravarArq.write("\nTiro repetido\n");
            }

            if (tabuleiro[L][C].casa == '#') { //verificar se acertou na agua
                tabuleiro[L][C].casa = 'X'; // caso acertou na agua, trocar de '#' para 'X'
                System.out.println("Você acertou a água! ");
                tabuleiro[L][C].secreto = false;
                qtdBalas++;
                qtdBalasAgua++;
                GravarArq.write("Você acertou a água! \n");

            } else if (tabuleiro[L][C].casa == 'S' && tabuleiro[L][C].secreto == true) { //se acertar um submarino que não foi acertado ainda

                System.out.println("Você acertou um Submarino! ");
                System.out.println("Vida antes do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao]);
                GravarArq.write("Você acertou um Submarino! \n" + "Vida antes do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao]);
                tabuleiro[L][C].secreto = false; 
                embarcacoes[tabuleiro[L][C].iEmbarcacao]--;//tira a vida da embarcação
                System.out.println("vida depois do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao]);
                qtdBalas++;
                qtdBalasNavio++;
                qtdBalasSubmarino++;
                GravarArq.write("\n" + "vida depois do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao] + "\n");
                if (qtdBalasSubmarino == 1) { //quando chegar em 1 significa que afundou um submarino
                    System.out.println("Afundou um submarino");
                    GravarArq.write("\nAfundou um submarino\n");
                    qtdBalasSubmarino = 0; //colocando o contador para 0 novamente 
                }

            } else if (tabuleiro[L][C].casa == 'D' && tabuleiro[L][C].secreto == true) { //se acertar um submarino que não foi acertado ainda

                System.out.println("Você acertou um Destroyer! ");
                System.out.println("vida antes do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao]);
                GravarArq.write("Você acertou um Destroyer! \n" + "vida antes do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao]);
                tabuleiro[L][C].secreto = false;
                embarcacoes[tabuleiro[L][C].iEmbarcacao]--;
                System.out.println("vida depois do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao]);
                qtdBalas++;
                qtdBalasNavio++;
                qtdBalasDestroyer++;
                GravarArq.write("\n" + "vida depois do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao] + "\n");
                if (qtdBalasDestroyer == 2) { //vida do destroyer
                    System.out.println("Afundou um destroyer");
                    GravarArq.write("\nAfundou um destroyer\n");
                    qtdBalasDestroyer = 0;//colocando o contador para 0 novamente 
                }

            } else if (tabuleiro[L][C].casa == 'C' && tabuleiro[L][C].secreto == true) {

                System.out.println("Você acertou um Cruzador! ");
                System.out.println("vida antes do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao]);
                GravarArq.write("Você acertou um Cruzador! \n" + "vida antes do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao]);
                tabuleiro[L][C].secreto = false;
                embarcacoes[tabuleiro[L][C].iEmbarcacao]--;
                System.out.println("vida depois do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao]);
                qtdBalas++;
                qtdBalasNavio++;
                qtdBalasCruzador++;
                GravarArq.write("\n" + "vida depois do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao] + "\n");
                if (qtdBalasCruzador == 4) {
                    System.out.println("Afundou um Cruzador");
                    GravarArq.write("\nAfundou um Cruzador\n");
                    qtdBalasCruzador = 0; //colocando o contador para 0 novamente
                }
            } else if (tabuleiro[L][C].casa == 'P' && tabuleiro[L][C].secreto == true) {

                System.out.println("Você acertou um Porta-Avião! ");
                System.out.println("vida antes do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao]);
                GravarArq.write("Você acertou um Porta-Avião! \n" + "vida antes do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao]);
                tabuleiro[L][C].secreto = false;
                embarcacoes[tabuleiro[L][C].iEmbarcacao]--;
                System.out.println("vida depois do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao]);
                qtdBalas++;
                qtdBalasNavio++;
                qtdBalasPortaAviao++;
                GravarArq.write("\n" + "vida depois do tiro: " + embarcacoes[tabuleiro[L][C].iEmbarcacao] + "\n");
                if (qtdBalasPortaAviao == 5) {
                    System.out.println("Afundou um porta-avião");
                    GravarArq.write("\nAfundou um porta-avião\n");
                    qtdBalasPortaAviao = 0;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) { //quando atirar fora do tabuleiro, dar ERRO.
            System.out.println("Tiro invalido, você atirou fora do tabuleiro " + e.getMessage());
            GravarArq.write("\nTiro invalido, você atirou fora do tabuleiro " + e.getMessage() + "\n");
        }

    }


}
