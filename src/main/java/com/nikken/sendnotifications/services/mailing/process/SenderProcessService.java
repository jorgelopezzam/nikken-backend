package com.nikken.sendnotifications.services.mailing.process;

import com.nikken.sendnotifications.exception.MailException;
import com.nikken.sendnotifications.model.NikkenGuide;
import com.nikken.sendnotifications.model.NikkenGuideArchive;
import com.nikken.sendnotifications.model.NikkenGuideSummary;
import com.nikken.sendnotifications.model.SocialNetwork;
import com.nikken.sendnotifications.repository.NikkenGuideRepository;
import com.nikken.sendnotifications.repository.NikkenGuideSummaryArchiveRepository;
import com.nikken.sendnotifications.repository.NikkenGuideSummaryRepository;
import com.nikken.sendnotifications.repository.SocialNetworkRepository;
import com.nikken.sendnotifications.services.MailSender;
import com.nikken.sendnotifications.services.enums.ProcessIndicatorEnum;
import com.nikken.sendnotifications.util.FreeMakerDataBuilder;
import com.nikken.sendnotifications.util.FreeMakerUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SenderProcessService {

    private final SocialNetworkRepository socialNetworkRepository;
    private final MailSender mailSender;
    private final NikkenGuideRepository nikkenGuideRepository;
    private final NikkenGuideSummaryRepository nikkenGuideSummaryRepository;
    private final NikkenGuideSummaryArchiveRepository nikkenGuideSummaryArchiveRepository;

    @Value("${region.nikken}")
    private String regionNikken;

    @Value("${resource.link.estafeta}")
    private String resourceLink;

    @Value("${nikken.mail.enabled:true}")
    private boolean mailEnabled;

    @Value("${template.path}")
    private String templatePath;

    @Autowired
    public SenderProcessService(SocialNetworkRepository socialNetworkRepository,
                                MailSender mailSender, NikkenGuideRepository nikkenGuideRepository,
                                NikkenGuideSummaryArchiveRepository nikkenGuideSummaryArchiveRepository,
                                NikkenGuideSummaryRepository nikkenGuideSummaryRepository) {
        this.socialNetworkRepository = socialNetworkRepository;
        this.mailSender = mailSender;
        this.nikkenGuideRepository = nikkenGuideRepository;
        this.nikkenGuideSummaryArchiveRepository = nikkenGuideSummaryArchiveRepository;
        this.nikkenGuideSummaryRepository = nikkenGuideSummaryRepository;
    }

    @Transactional
    public Boolean sendMail(String mailTo, NikkenGuideSummary nikkenGuideSummary, String[] bccMail) {

        String docNum = nikkenGuideSummary.getDocNum();

        List<NikkenGuide> references = nikkenGuideRepository.findByDocNum(docNum);
        SocialNetwork socialNetwork = socialNetworkRepository.findByPaisPublic(regionNikken);
        String uEstafeta = nikkenGuideRepository.findGuideNumber(docNum).get(0);
        FreeMakerUtil freeMakerUtil = new FreeMakerUtil(templatePath);


        FreeMakerDataBuilder freeMakerDataBuilder = FreeMakerDataBuilder.builder()
                .orden(docNum)
                .references(references)
                .socialNetwork(socialNetwork)
                .uEstafeta(uEstafeta)
                .nikkenUser(references.get(0).getNikkenUser())
                .resourceLink(resourceLink)
                .build();

        String body;


        Optional<NikkenGuideArchive> nikkenGuideArchive = nikkenGuideSummaryArchiveRepository.
                findByCardCodeAndUEstafeta(docNum, uEstafeta);

        if (!nikkenGuideArchive.isPresent()) {

            try {
                body = freeMakerUtil.generateEmailFromTemplate(freeMakerDataBuilder);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new MailException(e.getMessage());
            }

            NikkenGuideArchive newNikkenGuideArchive = new NikkenGuideArchive();
            newNikkenGuideArchive.setCardCode(docNum);
            newNikkenGuideArchive.setUEstafeta(uEstafeta);

            newNikkenGuideArchive.setEmail(nikkenGuideSummary.getEmail());
            newNikkenGuideArchive.setFecDocumento(nikkenGuideSummary.getFecDocumento());
            newNikkenGuideArchive.setTotal(nikkenGuideSummary.getTotal());
            newNikkenGuideArchive.setUOrden(nikkenGuideSummary.getUOrden());

            try {

                log.info("mailEnabled = " + mailEnabled);

                if (mailEnabled) {
                    mailSender.sendMail(body, mailTo, bccMail);
                }

                newNikkenGuideArchive.setSendProcess(ProcessIndicatorEnum.COMPLETED.getValue());
                nikkenGuideSummaryArchiveRepository.save(newNikkenGuideArchive);

            } catch (Exception e) {
                newNikkenGuideArchive.setSendProcess(ProcessIndicatorEnum.FAILED.getValue());
                nikkenGuideSummaryArchiveRepository.save(newNikkenGuideArchive);
                log.error(e.getMessage(), e);
                throw new MailException(e.getMessage());
            }

        } else {
            log.warn("{} card-code and {} estafeta previously send by email", docNum, uEstafeta);
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    public Boolean sendMail(String[] mailTo, NikkenGuideArchive nikkenGuideArchive) {

        String docNum = nikkenGuideArchive.getCardCode();

        List<NikkenGuide> references = nikkenGuideRepository.findByDocNum(docNum);
        SocialNetwork socialNetwork = socialNetworkRepository.findByPaisPublic(regionNikken);

        if (references.size() == 0) {
            log.info("doc-num : {} not found in nikkenGuideRepository", docNum);
            return Boolean.FALSE;
        }

        String uEstafeta = nikkenGuideRepository.findGuideNumber(docNum).get(0);
        FreeMakerUtil freeMakerUtil = new FreeMakerUtil(templatePath);


        FreeMakerDataBuilder freeMakerDataBuilder = FreeMakerDataBuilder.builder()
                .orden(docNum)
                .references(references)
                .socialNetwork(socialNetwork)
                .uEstafeta(uEstafeta)
                .nikkenUser(references.get(0).getNikkenUser())
                .resourceLink(resourceLink)
                .build();

        String body;


        try {
            body = freeMakerUtil.generateEmailFromTemplate(freeMakerDataBuilder);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MailException(e.getMessage());
        }

        try {

            log.info("mailEnabled = " + mailEnabled);

            if (mailEnabled) {
                mailSender.sendMail(body, mailTo);
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MailException(e.getMessage());
        }
        return Boolean.TRUE;
    }
}
