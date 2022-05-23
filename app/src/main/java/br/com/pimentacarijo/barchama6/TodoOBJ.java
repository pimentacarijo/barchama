package br.com.pimentacarijo.barchama6;

// no video chama: "Tod o"
public class TodoOBJ {
    private Boolean notificacao;
    private int setor;
    private String mensagem;
    private Boolean error;

    public TodoOBJ(Boolean notificacao, int setor, String mensagem, Boolean error) {
        this.notificacao = notificacao;
        this.setor = setor;
        this.mensagem = mensagem;
        this.error = error;
    }

    public Boolean getNotificacao() {
        return notificacao;
    }

    public void setNotificacao(Boolean notificacao) {
        this.notificacao = notificacao;
    }

    public int getSetor() {
        return setor;
    }

    public void setSetor(int setor) {
        this.setor = setor;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    @Override
    public String toString() { // para poder visualizar os dados no logcat
        return "TodoOBJ{" +
                "notificacao=" + notificacao +
                ", setor=" + setor +
                ", mensagem='" + mensagem + '\'' +
                ", error=" + error +
                '}';
    }
}
