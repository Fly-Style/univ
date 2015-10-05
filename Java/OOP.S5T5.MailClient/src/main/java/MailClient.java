import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by MakeYouHappy on 03.10.2015.
 */

public class MailClient {

    private Session currentSession;
    private String currentMail;
    private String currentPassword;
    private java.lang.String accepter;

    MailClient(String email, String password, String service) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp." + service); /*gmail.com"*/
        props.put("mail.smtp.port", "465");

        this.currentMail = email;
        this.currentPassword = password;

        currentSession = Session.getInstance(props,
                new javax.mail.Authenticator()
                {
                    //@Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                } );
    }

    public void SendMessage (String subject ,String toSend) {

        Message message = new MimeMessage(currentSession);
        this.GetSendSettings();

        try {

            message.setFrom(new InternetAddress(this.currentMail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(accepter));
            message.setSubject(subject);
            message.setText(toSend);

            Transport.send(message);
        }
        catch (MessagingException e) {throw new RuntimeException(e); }
    }

    public void GetSendSettings () {} //

    public void AcceptMessage (String accepter) {}
}
