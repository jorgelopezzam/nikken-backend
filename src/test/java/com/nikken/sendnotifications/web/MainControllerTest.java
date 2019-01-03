package com.nikken.sendnotifications.web;

import com.nikken.sendnotifications.model.NikkenGuide;
import com.nikken.sendnotifications.model.SocialNetwork;
import com.nikken.sendnotifications.repository.NikkenGuideRepository;
import com.nikken.sendnotifications.repository.NikkenGuideSummaryArchiveRepository;
import com.nikken.sendnotifications.services.DataLoader;
import com.nikken.sendnotifications.services.mailing.process.AdminProcessService;
import com.nikken.sendnotifications.services.mailing.process.SenderProcessService;
import com.nikken.sendnotifications.services.mandrill.MailSenderImpl;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainControllerTest {

    private MainController mainController;

    @Mock
    private NikkenGuideRepository nikkenGuideRepository;

    @Mock
    private DataLoader dataLoader;

    @Mock
    private SenderProcessService senderProcessService;

    @Mock
    private NikkenGuideSummaryArchiveRepository nikkenGuideSummaryArchiveRepository;

    @Mock
    private AdminProcessService adminProcessService;

    private MailSenderImpl mailSender;

    @Before
    public void setUp() {
        mailSender =  new MailSenderImpl(mandrillMailSender());
        mainController = new MainController(nikkenGuideRepository,dataLoader,
                senderProcessService,nikkenGuideSummaryArchiveRepository,adminProcessService);
    }

    @Test
    @Ignore
    public void testSendMail() throws Exception{
        NikkenGuide nikkenGuide = new NikkenGuide();
        nikkenGuide.setItemCode("19136");
        nikkenGuide.setQuantity("1");
        nikkenGuide.setDescription("KENKO FASHION MILANA SET");

        NikkenGuide nikkenGuide1 = new NikkenGuide();
        nikkenGuide1.setItemCode("19127");
        nikkenGuide1.setQuantity("1");
        nikkenGuide1.setDescription("KENKO FASHION MILANA ARETES");

        NikkenGuide nikkenGuide2 = new NikkenGuide();
        nikkenGuide2.setItemCode("19128");
        nikkenGuide2.setQuantity("1");
        nikkenGuide2.setDescription("KENKO FASHION MILANA COLLAR");

        List<NikkenGuide> returnNikkenGuideList = Arrays.asList(nikkenGuide, nikkenGuide1, nikkenGuide2);

        when(nikkenGuideRepository.findByDocNum(any())).thenReturn(returnNikkenGuideList);

        SocialNetwork socialNetwork = new SocialNetwork();
        socialNetwork.setIdPublicidad(1);
        socialNetwork.setPaisPublic("Latinoamerica");
        socialNetwork.setUrlFacebook("https://www.facebook.com/Nikkenlatinoamerica");
        socialNetwork.setUrlInstagram("https://www.instagram.com/nikkenlatam");
        socialNetwork.setUrlTwitter("https://twitter.com/search?q=NikkenLat");
        socialNetwork.setUrlYoutube("https://www.youtube.com/user/nikkenlatinoamerica/videos?flow=grid&amp;view=1");

        when(nikkenGuideRepository.findGuideNumber(any())).thenReturn(Arrays.asList("505503264329B780062885",""));

        //mainController.sendMail("14711903");
    }

    public JavaMailSender mandrillMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.mandrillapp.com");
        mailSender.setPort(587);

        mailSender.setUsername("NIKKEN_LATINOAMERICA");
        mailSender.setPassword("SfMs7pcupDuI4YbHBoqgIA");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

}
