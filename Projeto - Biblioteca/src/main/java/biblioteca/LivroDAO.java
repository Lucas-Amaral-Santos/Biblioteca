package biblioteca;

import java.util.List;


public interface LivroDAO
{	
	long inclui(Livro umLivro); 

	void altera(Livro umLivro)
		throws LivroNaoEncontradoException; 
	
	void exclui(long id) 
		throws LivroNaoEncontradoException; 
	
	Livro recuperaUmLivro(long numero) 
		throws LivroNaoEncontradoException; 
	
	List<Livro> recuperaLivro();
}