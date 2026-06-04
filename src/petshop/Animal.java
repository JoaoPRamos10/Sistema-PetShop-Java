package petshop;

class Animal {
    private String nome;
    private String raca;
    private String especie;

    public Animal(String nome, String raca, String especie) {
        this.nome = nome;
        this.raca = raca;
        this.especie = especie;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getEspecie(){
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }
    @Override
    public String toString() {
        return nome + " (" + especie + ", " + raca + ")";
    }
}
