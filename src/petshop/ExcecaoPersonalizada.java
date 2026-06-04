package petshop;

public class ExcecaoPersonalizada extends Exception {
    public ExcecaoPersonalizada(String mensagem) {
        super(mensagem);
    }
    public ExcecaoPersonalizada(String mensagem, Throwable causa){
        super(mensagem, causa);
    }
}

