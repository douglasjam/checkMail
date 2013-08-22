
public class MailObject {

    String email;
    String password;
    Boolean valid;
    String observacoes;

    public MailObject() {
        valid = false;
    }

    public MailObject(String email, String password) {
        this.email = email;
        this.password = password;
        valid = false;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return (valid ? "Valido;":"Invalido;") + email + ";" + password;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
