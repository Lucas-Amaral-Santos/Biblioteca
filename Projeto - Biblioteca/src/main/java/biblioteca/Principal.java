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
		{	System.out.println('\n' + "O que você deseja fazer?");
			System.out.println('\n' + "1. Incluir livro");
			System.out.println("2. Alterar livro");
			System.out.println("3. Descartar produto");
			System.out.println("4. Listar todos os produtos");
			System.out.println("5. Sair");
						
			int opcao = Console.readInt('\n' + 
							"Digite um número entre 1 e 5:");
					
			switch (opcao)
			{	case 1:
				{
					titulo = Console.readLine('\n' + 
						"Informe o titulo: ");
					autor = Console.readLine(
						"Informe o autor: ");
					ISBN = Console.readLine(
						"Informe o código ISBN: ");
					editora = Console.readLine(
						"Informe a editora: ");
					ano = Console.readInt(
						"Informe o ano da publicação: ");
					edicao = Console.readInt(
						"Informe em que edição está o exemplar: ");
					
						
					umLivro = new Livro(titulo, autor, ISBN, editora, ano, edicao);
					
					long numero = livroDAO.inclui(umLivro);
					
					System.out.println('\n' + "Livro número " + 
					    numero + " incluído com sucesso!");	

					break;
				}

				case 2:
				{	int resposta = Console.readInt('\n' + 
						"Digite o número do livro que você deseja alterar: ");
										
					try
					{	umLivro = livroDAO.recuperaUmLivro(resposta);
					}
					catch(LivroNaoEncontradoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"Número = " + umLivro.getId() + 
						"    Nome = " + umLivro.getTitulo() +
						"    Autor = " + umLivro.getAutor() + 
				        "    ISBN = " + umLivro.getISBN() +
						"	 Editora = " + umLivro.getEditora() +
						"	 Ano: = " + umLivro.getAno() +
						" 	 Edição = " + umLivro.getEdicao());
						
					
					System.out.println('\n' + "O que você deseja alterar?");
					System.out.println('\n' + "1. Nome");
					System.out.println("2. Lance Mínimo");

					int opcaoAlteracao = Console.readInt('\n' + 
											"Digite um número de 1 a 2:");
					
					switch (opcaoAlteracao)
					{	case 1:
							String novoNome = Console.
										readLine("Digite o novo nome: ");
							
							umLivro.setTitulo(novoNome);

							try
							{	livroDAO.altera(umLivro);

								System.out.println('\n' + 
									"Alteração de nome efetuada com sucesso!");
							}
							catch(LivroNaoEncontradoException e)
							{	System.out.println('\n' + e.getMessage());
							}
							catch(EstadoDeObjetoObsoletoException e)
							{	System.out.println('\n' + "A operação não foi " +
							        "efetuada: os dados que você " +
							    	"tentou salvar foram modificados " +
							    	"por outro usuário.");
							}
								
							break;
					}
					/**
					 * 
					 *
						case 2:
							double novoLanceMinimo = Console.
									readDouble("Digite o novo lance mínimo: ");
							
							umProduto.setLanceMinimo(novoLanceMinimo);

							try
							{	produtoDAO.altera(umProduto);

								System.out.println('\n' + 
									"Alteração de lance mínimo efetuada " +
									"com sucesso!");						
							}
							catch(LivroNaoEncontradoException e)
							{	System.out.println('\n' + e.getMessage());
							}
							catch(EstadoDeObjetoObsoletoException e)
							{	System.out.println('\n' + "A operação não foi " +
							        "efetuada: os dados que você " +
							    	"tentou salvar foram modificados " +
							    	"por outro usuário.");
							}
								
							break;

						default:
							System.out.println('\n' + "Opção inválida!");
					}

					break;
					
				}

				case 3:
				{	int resposta = Console.readInt('\n' + 
						"Digite o número do produto que você deseja remover: ");
									
					try
					{	umProduto = produtoDAO.recuperaUmProduto(resposta);
					}
					catch(LivroNaoEncontradoException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"Número = " + umProduto.getId() + 
						"    Nome = " + umProduto.getNome() +
					    "    Versão = " + umProduto.getVersao());
														
					String resp = Console.readLine('\n' + 
						"Confirma a remoção do produto?");

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
							"Produto não removido.");
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
							"  Lance mínimo = " + produto.getLanceMinimo() +
							"  Data Cadastro = " + produto.getDataCadastroMasc() +
							"  Versão = " + produto.getVersao());
					}
					
					break;
				}

				case 5:
				{	continua = false;
					break;
				}

				default:
					System.out.println('\n' + "Opção inválida!");
			*/
				}
			}
		}		
	}
}
