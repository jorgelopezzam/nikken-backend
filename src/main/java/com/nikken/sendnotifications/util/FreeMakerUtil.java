package com.nikken.sendnotifications.util;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class FreeMakerUtil {

    private String templatePath;

    public String generateEmailFromTemplate(FreeMakerDataBuilder freeMakerDataBuilder)
            throws IOException, TemplateException {

        //Instantiate Configuration class
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(new File(templatePath));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        //Create Data Model
        Map<String, Object> map = new HashMap<>();
        map.put("orden", freeMakerDataBuilder.getOrden());
        map.put("references", freeMakerDataBuilder.getReferences());
        map.put("social", freeMakerDataBuilder.getSocialNetwork());
        map.put("guiaEstafeta",freeMakerDataBuilder.getUEstafeta());
        map.put("resourceLink",freeMakerDataBuilder.getResourceLink()!=null?
                freeMakerDataBuilder.getResourceLink():"http://example.do/data=");
        map.put("cardName",freeMakerDataBuilder.getNikkenUser());

        //Instantiate template
        Template template = cfg.getTemplate("email-estafeta.ftl");

        StringWriter out=new StringWriter();
        template.process(map, out);

        String emailToSend = out.getBuffer().toString();
        log.info(emailToSend);

        return emailToSend;
    }
}
