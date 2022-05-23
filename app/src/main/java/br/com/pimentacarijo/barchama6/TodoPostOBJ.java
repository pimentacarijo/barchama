package br.com.pimentacarijo.barchama6;

public class TodoPostOBJ {
    private String chave;
    private String usid;

    public TodoPostOBJ(String chave, String usid) {
        this.chave = chave;
        this.usid = usid;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getUsid() {
        return usid;
    }

    public void setUsid(String usid) {
        this.usid = usid;
    }
}
