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
		{	// transiente - objeto novo: ainda não persistente
			// persistente - apos ser persistido 
			// destacado - objeto persistente não vinculado a um entity manager
		
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
				throw new LivroNaoEncontradoException("Livro não encontrado");
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
				throw new LivroNaoEncontradoException("Produto não encontrado");
			}

			// COMO PARA REMOVER UM PRODUTO NA JPA É PRECISO PRIMEIRAMENTE
			// RECUPERÁ-LO, QUANDO O  PRODUTO É  RECUPERADO SEU NÚMERO  DE
			// VERSÃO  JÁ  ATUALIZADO  VEM  JUNTO,  O  QUE  FAZ  COM QUE O 
			// CONTROLE DE VERSÃO NÃO FUNCIONE SE A REMOÇÃO ACONTECER APÓS 
			// UMA ATUALIZAÇÃO, OU SE OCORREREM DUAS REMOÇÕES EM  PARALELO 
			// DO MESMO PRODUTO.
			
			// LOGO, A  EXCEÇÃO  OptimisticLockException NUNCA  ACONTECERÁ
			// NO CASO DE REMOÇÕES.

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
			
			// Características no método find():
			// 1. É genérico: não requer um cast.
			// 2. Retorna null caso a linha não seja encontrada no banco.

			if(umLivro == null)
			{	throw new LivroNaoEncontradoException("Livro não encontrado");
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