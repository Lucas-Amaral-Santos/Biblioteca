package biblioteca;


import java.util.List;

import corejava.Console;

@SuppressWarnings("unused")
public class Principal {
	
	public static void main (String[] args) 

	{	
		String titulo;
		String autor;
		String ISBN;
		String editora;
		int ano;
		int edicao;
		
		Livro umLivro;

		LivroDAO livroDAO = FabricaDeDAOs.getDAO(LivroDAO.class);

		boolean continua = true;
		while (continua)
		{	System.out.println('\n' + "O que voc� deseja fazer?");
			System.out.println('\n' + "1. Incluir livro");
			System.out.println("2. Alterar livro");
			System.out.println("3. Descartar produto");
			System.out.println("4. Listar todos os produtos");
			System.out.println("5. Sair");
						
			int opcao = Console.readInt('\n' + 
							"Digite um n�mero entre 1 e 5:");
					
			switch (opcao)
			{	case 1:
				{
					titulo = Console.readLine('\n' + 
						"Informe o titulo: ");
					autor = Console.readLine(
						"Informe o autor: ");
					ISBN = Console.readLine(
						"Informe o c�digo ISBN: ");
					editora = Console.readLine(
						"Informe a editora: ");
					ano = Console.readInt(
						"Informe o ano da publica��o: ");
					edicao = Console.readInt(
						"Informe em que edi��o est� o exemplar: ");
					
						
					umLivro = new Livro(titulo, autor, ISBN, editora, ano, edicao);
					
					long numero = livroDAO.inclui(umLivro);
					
					System.out.println('\n' + "Livro n�mero " + 
					    numero + " inclu�do com sucesso!");	

					break;
				}

				case 2:
				{	int resposta = Console.readInt('\n' + 
						"Digite o n�mero do livro que voc� deseja alterar: ");
										
					try
					{	umLivro = livroDAO.recuperaUmLivro(resposta);
					}
					catch(LivroNaoEncontradoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"N�mero = " + umLivro.getId() + 
						"    Nome = " + umLivro.getTitulo() +
						"    Autor = " + umLivro.getAutor() + 
				        "    ISBN = " + umLivro.getISBN() +
						"	 Editora = " + umLivro.getEditora() +
						"	 Ano: = " + umLivro.getAno() +
						" 	 Edi��o = " + umLivro.getEdicao());
						
					
					System.out.println('\n' + "O que voc� deseja alterar?");
					System.out.println('\n' + "1. Nome");
					System.out.println("2. Lance M�nimo");

					int opcaoAlteracao = Console.readInt('\n' + 
											"Digite um n�mero de 1 a 2:");
					
					switch (opcaoAlteracao)
					{	case 1:
							String novoNome = Console.
										readLine("Digite o novo nome: ");
							
							umLivro.setTitulo(novoNome);

							try
							{	livroDAO.altera(umLivro);

								System.out.println('\n' + 
									"Altera��o de nome efetuada com sucesso!");
							}
							catch(LivroNaoEncontradoException e)
							{	System.out.println('\n' + e.getMessage());
							}
							catch(EstadoDeObjetoObsoletoException e)
							{	System.out.println('\n' + "A opera��o n�o foi " +
							        "efetuada: os dados que voc� " +
							    	"tentou salvar foram modificados " +
							    	"por outro usu�rio.");
							}
								
							break;
					}
					/**
					 * 
					 *
						case 2:
							double novoLanceMinimo = Console.
									readDouble("Digite o novo lance m�nimo: ");
							
							umProduto.setLanceMinimo(novoLanceMinimo);

							try
							{	produtoDAO.altera(umProduto);

								System.out.println('\n' + 
									"Altera��o de lance m�nimo efetuada " +
									"com sucesso!");						
							}
							catch(LivroNaoEncontradoException e)
							{	System.out.println('\n' + e.getMessage());
							}
							catch(EstadoDeObjetoObsoletoException e)
							{	System.out.println('\n' + "A opera��o n�o foi " +
							        "efetuada: os dados que voc� " +
							    	"tentou salvar foram modificados " +
							    	"por outro usu�rio.");
							}
								
							break;

						default:
							System.out.println('\n' + "Op��o inv�lida!");
					}

					break;
					
				}

				case 3:
				{	int resposta = Console.readInt('\n' + 
						"Digite o n�mero do produto que voc� deseja remover: ");
									
					try
					{	umProduto = produtoDAO.recuperaUmProduto(resposta);
					}
					catch(LivroNaoEncontradoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"N�mero = " + umProduto.getId() + 
						"    Nome = " + umProduto.getNome() +
					    "    Vers�o = " + umProduto.getVersao());
														
					String resp = Console.readLine('\n' + 
						"Confirma a remo��o do produto?");

					if(resp.equals("s"))
					{	try
						{	produtoDAO.exclui (umProduto.getId());
							System.out.println('\n' + 
								"Produto removido com sucesso!");
						}
						catch(LivroNaoEncontradoException e)
						{	System.out.println('\n' + e.getMessage());
						}
					}
					else
					{	System.out.println('\n' + 
							"Produto n�o removido.");
					}
					
					break;
				}

				case 4:
				{	
					List<Produto> produtos = produtoDAO.recuperaProdutos();

					for (Produto produto : produtos)
					{	
						System.out.println('\n' + 
							"Id = " + produto.getId() +
							"  Nome = " + produto.getNome() +
							"  Lance m�nimo = " + produto.getLanceMinimo() +
							"  Data Cadastro = " + produto.getDataCadastroMasc() +
							"  Vers�o = " + produto.getVersao());
					}
					
					break;
				}

				case 5:
				{	continua = false;
					break;
				}

				default:
					System.out.println('\n' + "Op��o inv�lida!");
			*/
				}
			}
		}		
	}
}
