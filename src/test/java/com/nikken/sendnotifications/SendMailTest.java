package com.nikken.sendnotifications;

import com.nikken.sendnotifications.util.DateHelper;
import lombok.SneakyThrows;
import org.apache.commons.httpclient.util.DateUtil;
import org.junit.Test;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Email;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SendMailTest {

    MailjetClient client;
    MailjetRequest request;
    MailjetResponse response;

    private static final String DATE_FORMAT = "HH:mm:ss";

    @Test
    @SneakyThrows
    public void testEvaluateDate() {

        DateHelper dateHelper = new DateHelper("America/Bogota");
        System.out.println(dateHelper.findHourInterval());
    }

    @Test
    public void sendMail() throws Exception{

        String apiKey = "1c52a0b84f40024e5cdcf6766039bed1";
        String secretKey = "e52d890fabe3f8c001346baeb5e689da";
        client = new MailjetClient(apiKey, secretKey);
        request = new MailjetRequest(Email.resource)
                .property(Email.FROMEMAIL, "prengifos@gmail.com")
                .property(Email.FROMNAME, "Nikken E-mail")
                .property(Email.SUBJECT, "Guia de Estafeta")
                .property(Email.HTMLPART, "<p>Estimado Comerciante Independiente:</p>\n" +
                        "<p>Le informamos que su pedido con orden de compra:<strong> ${orden}</strong> ha salido ya de nuestro almacen y se encuentra en ruta para que lo reciba en pr&oacute;ximo d&iacute;as.</p>\n" +
                        "<p>El env&iacute;o contiene:</p>\n" +
                        "<p><br />La guia de estafeta para que monitoree el envio es:</p>\n" +
                        "<p>Para cualquier duda o comentario, le invitamos a comunicarse a los numeros de servicio al cliente marcando al <strong>(55) 5864-9070</strong> o al correo <a href=\"mailto:aclaracionenvios.mex@nikkenlat.com\">aclaracionenvios.mex@nikkenlat.com</a></p>\n" +
                        "<p>Atentamente,</p>\n" +
                        "<p><strong>NIKKEN LATINOAMERICA</strong><br /><strong>A SU SERVICIO</strong></p>")
                .property(Email.TEXTPART, "=======================================")
                .property(Email.RECIPIENTS, new JSONArray()
                        .put(new JSONObject()
                                .put("Email", "prengifos@gmail.com")));
        response = client.post(request);
        System.out.println(response.getData());
    }

}
