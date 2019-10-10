package biblioteca;

public class LivroNaoEncontradoException extends Exception
{	
	private final static long serialVersionUID = 1;
	
	private int codigo;
	
	public LivroNaoEncontradoException(String msg)
	{	super(msg);
	}

	public LivroNaoEncontradoException(int codigo, String msg)
	{	super(msg);
		this.codigo = codigo;
	}
	
	public int getCodigoDeErro()
	{	return codigo;
	}
}	