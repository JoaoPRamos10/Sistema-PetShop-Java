package petshop;

class Brinquedo extends Produto {
    private String material;

    public Brinquedo(String nome, double preco, String material) {
        super(nome, preco);
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return "Brinquedo: " + getNome() + "\n" +
                "  Material: " + material + "\n" +
                "  Preço: R$ " + String.format("%.2f", getPreco()) + "\n";
    }
}
