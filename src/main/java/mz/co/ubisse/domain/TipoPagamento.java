package mz.co.ubisse.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name = "tipo_pagamento")
public class TipoPagamento extends GenericDomain {

	@Column(length = 10, nullable = false)
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}