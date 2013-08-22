
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import javax.mail.Session;
import javax.mail.Transport;
import java.util.Properties;

public class Mail {

    private ArrayList<MailObject> mailsList = new ArrayList<MailObject>();
    private ArrayList<SMTPObject> smtpList = new ArrayList<SMTPObject>();

//    public static void main(String[] args) {
//        Mail a = new Mail();
//        a.readMailFiles("C:/Users/Djam/Desktop/lista.txt");
//        a.readSMTPList("C:/Users/Djam/Desktop/smtpList.txt");
//        a.processMails();
//
//        System.out.println("fim");
//
//    }
    public void readMailFiles(String path) throws Exception {

        String linha;
        mailsList.clear();

        BufferedReader in = new BufferedReader(new FileReader(path));

        while (in.ready()) {
            linha = in.readLine();
            String[] splitted = linha.split(",");
            MailObject newMail = new MailObject(splitted[0].trim(), splitted[1].trim());
            mailsList.add(newMail);
        }

        in.close();

    }

    public void readSMTPList(String path) throws Exception {

        BufferedReader in = new BufferedReader(new FileReader(path));
        String linha;
        while (in.ready()) {

            linha = in.readLine();
            SMTPObject newSMTP = new SMTPObject();

            String[] splitted = linha.split(",");
            for (int i = 0; i < splitted.length; i++) {

                if (i == 0) {
                    newSMTP.setServer(splitted[0].trim());
                } else {
                    newSMTP.addTag(splitted[i].trim());
                }

            }
            smtpList.add(newSMTP);

        }
        in.close();

    }

    public void writeFile() {
    }

    public SMTPObject getServer(String mail) {

        String[] split = mail.split("@");
        String email = split[1];

        for (SMTPObject smtp : smtpList) {
            if (smtp.tags.contains(email)) {
                return smtp;
            }
        }

        return null;
    }

    public boolean processEmail(MailObject email) {

        String erro = new String();
        EmailValidator validador = new EmailValidator();

        if (email.getEmail() == null || email.getPassword() == null) {
            email.setValid(false);
            email.setObservacoes("E-mail ou senha nula");
            return false;
        }

        if (validador.validate(email.getEmail()) == false) {
            email.setValid(false);
            email.setObservacoes("E-mail em formato errado");
            return false;
        }

        SMTPObject server = getServer(email.getEmail());
        if (server == null) {
            email.setValid(false);
            email.setObservacoes("Servidor não encontrado na lista SMTP");
            return false;
        }

        //host, user, pwd, port
        if (tryConnect(server.getServer(), email.getEmail(), email.getPassword(), 25)) {
            email.setValid(true);
            email.setObservacoes("Válido");
            return true;
        } else {
            email.setValid(false);
            email.setObservacoes("Não conseguiu conectar");
            return false;
        }

    }

    public boolean tryConnect(String host, String user, String pwd, Integer port) {

        try {
            Properties props = new Properties();
            // required for gmail 
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            // or use getDefaultInstance instance if desired...
            Session session = Session.getInstance(props, null);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, port, user, pwd);
            transport.close();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public ArrayList<MailObject> getMailsList() {
        return mailsList;
    }

    public void setMailsList(ArrayList<MailObject> mailsList) {
        this.mailsList = mailsList;
    }

    public ArrayList<SMTPObject> getSmtpList() {
        return smtpList;
    }

    public void setSmtpList(ArrayList<SMTPObject> smtpList) {
        this.smtpList = smtpList;
    }
}