package petshop;

class Racao extends Produto {
    private String tipo;

    public Racao(String nome, double preco, String tipo) {
        super(nome, preco);
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Ração: " + getNome() + "\n" +
                "  Tipo: " + tipo + "\n" +
                "  Preço: R$ " + String.format("%.2f", getPreco()) + "\n";
    }
}
