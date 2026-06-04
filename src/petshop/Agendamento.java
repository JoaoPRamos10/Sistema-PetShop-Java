package petshop;

// Classe Agendamento (agregação)
public class Agendamento {
    private Cliente cliente;
    private Animal animal;
    private Servico servico;
    private String dataHora;

    public Agendamento(Cliente cliente, Animal animal, Servico servico, String dataHora) throws ExcecaoPersonalizada {
        if (cliente == null || animal == null || servico == null || dataHora == null || dataHora.trim().isEmpty()) {
            throw new ExcecaoPersonalizada("Todos os detalhes do agendamento devem ser preenchidos.");
        }
        this.cliente = cliente;
        this.animal = animal;
        this.servico = servico;
        this.dataHora = dataHora;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Animal getAnimal() {
        return animal;
    }

    public Servico getServico() {
        return servico;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public double calcularTotal() {
        return servico.getPreco();
    }

    @Override
    public String toString() {
        return "Agendamento{" +
                "cliente=" + cliente +
                ", animal=" + animal +
                ", servico=" + servico +
                ", dataHora='" + dataHora + '\'' +
                '}';
    }
}