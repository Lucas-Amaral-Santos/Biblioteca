package biblioteca;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;

public class LivroDAOImpl implements LivroDAO
{	
	public long inclui(Livro umLivro) 
	{	EntityManager em = null;
		EntityTransaction tx = null;
		
		try
		{	// transiente - objeto novo: ainda n�o persistente
			// persistente - apos ser persistido 
			// destacado - objeto persistente n�o vinculado a um entity manager
		
			em = FabricaDeEntityManager.criarSessao();
			tx = em.getTransaction();
			tx.begin();
			
			em.persist(umLivro);
			
			tx.commit();
			return umLivro.getId();
		} 
		catch(RuntimeException e)
		{   
			if (tx != null)
		    {   try
		    	{	tx.rollback();
		        }
		        catch(RuntimeException he)
		        { }
		    }
		    throw e;
		}
		finally
		{   em.close();
		}
	}

	public void altera(Livro umLivro) throws LivroNaoEncontradoException
	{	EntityManager em = null;
		EntityTransaction tx = null;
		Livro livro = null;
		try
		{	
			em = FabricaDeEntityManager.criarSessao();
			tx = em.getTransaction();
			tx.begin();
			
			livro = em.find(Livro.class, umLivro.getId(), LockModeType.PESSIMISTIC_WRITE);
			
			if(livro == null)
			{	tx.rollback();
				throw new LivroNaoEncontradoException("Livro n�o encontrado");
			}
			em.merge(umLivro);
			tx.commit();
		} 
		catch(OptimisticLockException e)  // sub-classe de RuntimeException
		{   
			if (tx != null)
		    {   tx.rollback();
		    }
		throw new EstadoDeObjetoObsoletoException();
		}
		catch(RuntimeException e)
		{   
			if (tx != null)
		    {   try
		        {   tx.rollback();
		        }
		        catch(RuntimeException he)
		        { }
		    }
		    throw e;
		}
		finally
		{   em.close();
		}
	}

	public void exclui(long numero) throws LivroNaoEncontradoException 
	{	EntityManager em = null;
		EntityTransaction tx = null;
		
		try
		{	
			em = FabricaDeEntityManager.criarSessao();
			tx = em.getTransaction();
			tx.begin();

			Livro livro = em.find(Livro.class, numero, LockModeType.PESSIMISTIC_WRITE);
			
			if(livro == null)
			{	tx.rollback();
				throw new LivroNaoEncontradoException("Produto n�o encontrado");
			}

			// COMO PARA REMOVER UM PRODUTO NA JPA � PRECISO PRIMEIRAMENTE
			// RECUPER�-LO, QUANDO O  PRODUTO �  RECUPERADO SEU N�MERO  DE
			// VERS�O  J�  ATUALIZADO  VEM  JUNTO,  O  QUE  FAZ  COM QUE O 
			// CONTROLE DE VERS�O N�O FUNCIONE SE A REMO��O ACONTECER AP�S 
			// UMA ATUALIZA��O, OU SE OCORREREM DUAS REMO��ES EM  PARALELO 
			// DO MESMO PRODUTO.
			
			// LOGO, A  EXCE��O  OptimisticLockException NUNCA  ACONTECER�
			// NO CASO DE REMO��ES.

			em.remove(livro);

			tx.commit();
		} 
		catch(RuntimeException e)
		{   
			if (tx != null)
		    {   try
		        {	tx.rollback();
		        }
		        catch(RuntimeException he)
		        { }
		    }
		    throw e;
		}
		finally
		{   em.close();
		}
	}

	public Livro recuperaUmLivro(long numero) throws LivroNaoEncontradoException
	{	EntityManager em = null;
		
		try
		{	em = FabricaDeEntityManager.criarSessao();

			Livro umLivro = em.find(Livro.class, numero);
			
			// Caracter�sticas no m�todo find():
			// 1. � gen�rico: n�o requer um cast.
			// 2. Retorna null caso a linha n�o seja encontrada no banco.

			if(umLivro == null)
			{	throw new LivroNaoEncontradoException("Livro n�o encontrado");
			}
			return umLivro;
		} 
		finally
		{   em.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Livro> recuperaLivro()
	{	EntityManager em = null;
		
		try
		{	em = FabricaDeEntityManager.criarSessao();

			List<Livro> clientes = em
				.createQuery("select p from Produto p order by p.id")
				.getResultList();

			// Retorna um List vazio caso a tabela correspondente esteja vazia.
			
			return clientes;
		} 
		finally
		{   em.close();
		}
	}
}