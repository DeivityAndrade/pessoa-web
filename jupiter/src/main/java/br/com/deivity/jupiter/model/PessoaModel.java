package br.com.deivity.jupiter.model;

public abstract class PessoaModel {

	private String documento;

	public PessoaModel(String documento) {
		this.documento=documento;
	}
	
	public abstract Boolean isDocumentoValido();
	public abstract String getNovoDocumento();
	public abstract String getDocumentoFormatado();

	
	public String getDocumento() {
		return documento;
	}

}


