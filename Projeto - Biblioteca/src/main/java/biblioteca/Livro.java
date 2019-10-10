package biblioteca;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

@SuppressWarnings("unused")
@Entity
@Table(name="Livro")
//@SequenceGenerator(name="SEQUENCIA", 
//		           sequenceName="SEQ_PRODUTO",
//		           allocationSize=1)

public class Livro
{	
	private Long id;
	private String titulo;
	private String autor;
	private String ISBN;
	private String editora;
	private int ano;
	private int edicao;

	// ********* Construtores *********
	

	public Livro(String titulo, String autor, String iSBN, String editora, int ano, int edicao) {
		super();
		this.titulo = titulo;
		this.autor = autor;
		ISBN = iSBN;
		this.editora = editora;
		this.ano = ano;
		this.edicao = edicao;
	}
	
	public Livro()
	{
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getEdicao() {
		return edicao;
	}

	public void setEdicao(int edicao) {
		this.edicao = edicao;
	}
	
}

