package mz.co.ubisse.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;
import org.primefaces.event.ToggleEvent;

import mz.co.ubisse.dao.VendaDAO;
import mz.co.ubisse.dao.VendaDinheiroDAO;
import mz.co.ubisse.domain.Cliente;
import mz.co.ubisse.domain.ItemsVenda;
import mz.co.ubisse.domain.Pessoa;
import mz.co.ubisse.domain.Venda;
import mz.co.ubisse.domain.VendaDinheiro;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class VendaDiariaBean implements Serializable {

	private VendaDinheiro vendaDinheiro;
	private Venda venda;
	private Cliente cliente;
	private Pessoa pessoa;

	private List<VendaDinheiro> dinheiros;
	private List<ItemsVenda> itemsVendas;
	private Long codigo;
	private List<Long> controlo;
	private Integer ultimoCodigo;
	private BigDecimal valorTotal;

	private Boolean tabelaItem;

	public VendaDinheiro getVendaDinheiro() {
		return vendaDinheiro;
	}

	public void setVendaDinheiro(VendaDinheiro vendaDinheiro) {
		this.vendaDinheiro = vendaDinheiro;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public List<VendaDinheiro> getDinheiros() {
		return dinheiros;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public void setDinheiros(List<VendaDinheiro> dinheiros) {
		this.dinheiros = dinheiros;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Integer getUltimoCodigo() {
		return ultimoCodigo;
	}

	public void setUltimoCodigo(Integer ultimoCodigo) {
		this.ultimoCodigo = ultimoCodigo;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Boolean getTabelaItem() {
		return tabelaItem;
	}

	public void setTabelaItem(Boolean tabelaItem) {
		this.tabelaItem = tabelaItem;
	}

	public List<ItemsVenda> getItemsVendas() {
		return itemsVendas;
	}

	public void setItemsVendas(List<ItemsVenda> itemsVendas) {
		this.itemsVendas = itemsVendas;
	}

	public VendaDiariaBean() {
		valorTotal = new BigDecimal("0.00");
	}

	@PostConstruct
	public void inicializar() {
		tabelaItem = false;
		controlo = new ArrayList<>();
		vendaDinheiro = new VendaDinheiro();

		//listar();
	}

	/*private void listar() {
		System.out.println("Inicializado 2");
		try {

			VendaDinheiroDAO vendaDinheiroDAO = new VendaDinheiroDAO();
			dinheiros = vendaDinheiroDAO.listarDiaValido(LocalDate.now());

			for (VendaDinheiro vendaDinheiro : dinheiros) {
				valorTotal = valorTotal.add(vendaDinheiro.getVenda().getValorTotal());
			}
			System.out.println("Inicializado 3");
		} catch (Exception erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as vendas Diarias");
			erro.printStackTrace();

		}
	}

	public void teste() {
		System.out.println("eiii dentro");
	}

	public void carregarVendaDinheiro(ToggleEvent event) {

		tabelaItem = false;
		System.out.println("Dentro");
		vendaDinheiro = (VendaDinheiro) event.getData();
		setCodigo(vendaDinheiro.getVenda().getCodigo());
		System.out.println("Item" + vendaDinheiro.getCodigo());

		VendaDAO vendaDAO = new VendaDAO();

		if (controlo.contains(codigo)) {
			controlo.remove(codigo);
		} else {
			itemsVendas = vendaDAO.carregarItem(codigo);
			controlo.add(codigo);
		}
	}

	public void carregarVendaDinheiro2(ActionEvent event) {
		System.out.println("Dentro");
		vendaDinheiro = (VendaDinheiro) event.getComponent().getAttributes().get("vendaSelecionada");
		setCodigo(vendaDinheiro.getVenda().getCodigo());
		System.out.println("Item" + vendaDinheiro.getCodigo());
		VendaDAO vendaDAO = new VendaDAO();
		itemsVendas = vendaDAO.carregarItem(codigo);

	}

	public void carregarVenda(ActionEvent event) {
		venda = (Venda) event.getComponent().getAttributes().get("vendaSelecionada");
		if (venda.getCliente().equals(null)) {
			cliente = new Cliente();
			VendaDinheiroDAO dinheiroDAO = new VendaDinheiroDAO();
			ultimoCodigo = dinheiroDAO.buscarUltimo();

		}
	}

	public void salvar(ActionEvent event) {
		vendaDinheiro = (VendaDinheiro) event.getComponent().getAttributes().get("vendaSelecionada");
		System.out.println("codigo " + vendaDinheiro.getCodigo());

		VendaDinheiroDAO dinheiroDAO = new VendaDinheiroDAO();
		ultimoCodigo = dinheiroDAO.buscarUltimo();

		vendaDinheiro.setCodigoVD(ultimoCodigo + 1);

		VendaDinheiroDAO vendaDinheiroDAO = new VendaDinheiroDAO();
		vendaDinheiroDAO.merge(vendaDinheiro);
	}
*/
}
