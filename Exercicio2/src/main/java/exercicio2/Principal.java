package exercicio2;

import java.util.Scanner;

public class Principal {

	public static void main(String[] args) {
		DAO dao = new DAO();
		Scanner scan = new Scanner(System.in);
		int acao;
		dao.conectar();
		System.out.println("Acao desejada: ");
		System.out.println("[0] - Listar");
		System.out.println("[1] - Inserir");
		System.out.println("[2] - Excluir");
		System.out.println("[3] - Atualizar");
		System.out.println("[4] - Sair");
		while((acao = scan.nextInt()) != 4) {	
			switch(acao) {
			case 0:
				Carros[] carro = dao.getCarros();
				for(int i = 0; i < carro.length; i++) {
					System.out.println(carro[i].toString());
				}
				break;
			case 1:
				System.out.println("Id: ");
				int id = scan.nextInt();
				scan.nextLine();
				System.out.println("Nome: ");
				String nome = scan.nextLine();
				System.out.println("Marca: ");
				String marca = scan.nextLine();
				System.out.println("Valor: ");
				double valor = scan.nextDouble();
				System.out.println("Eletrico[true, false]? ");
				boolean eletrico = scan.nextBoolean();
				Carros carroIn = new Carros(id, nome, marca, valor, eletrico);
				if(dao.inserirCarro(carroIn) == true) {
					System.out.println("Inserido com sucesso");
				} else {
					System.out.println("Erro ao inserir");
				}
				break;
			case 2:
				System.out.println("ID do carro que deseja deletar: ");
				int deletar = scan.nextInt();
				dao.excluirCarro(deletar);
				break;
			case 3:
				System.out.println("Id: ");
				int novoId = scan.nextInt();
				scan.nextLine();
				System.out.println("Nome: ");
				String novoNome = scan.nextLine();
				System.out.println("Marca: ");
				String novoMarca = scan.nextLine();
				System.out.println("Valor: ");
				double novoValor = scan.nextDouble();
				System.out.println("Eletrico[true, false]? ");
				boolean novoEletrico = scan.nextBoolean();
				Carros carroAtualizado = new Carros(novoId, novoNome, novoMarca, novoValor, novoEletrico);
				dao.atualizarCarro(carroAtualizado);
				break;
			case 4:
				break;
			default:
				System.out.println("ERROR: outOfRange");
			}
		}
		scan.close();
		dao.close();
	}

}
