package com.nikken.sendnotifications.web;

import com.nikken.sendnotifications.dto.RequestDataDto;
import com.nikken.sendnotifications.model.AdminProcess;
import com.nikken.sendnotifications.model.NikkenGuide;
import com.nikken.sendnotifications.model.NikkenGuideArchive;
import com.nikken.sendnotifications.repository.NikkenGuideRepository;
import com.nikken.sendnotifications.repository.NikkenGuideSummaryArchiveRepository;
import com.nikken.sendnotifications.services.DataLoader;
import com.nikken.sendnotifications.services.enums.ProcessIndicatorEnum;
import com.nikken.sendnotifications.services.mailing.process.AdminProcessService;
import com.nikken.sendnotifications.services.mailing.process.SenderProcessService;
import com.nikken.sendnotifications.util.DateHelper;
import org.apache.commons.lang.time.DateUtils;
import org.pentaho.di.core.exception.KettleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class MainController {

    private final NikkenGuideRepository nikkenGuideRepository;
    private final DataLoader dataLoader;
    private final SenderProcessService senderProcessService;
    private final NikkenGuideSummaryArchiveRepository nikkenGuideSummaryArchiveRepository;
    private final AdminProcessService adminProcessService;

    @Value("${time.zone}")
    private String timeZone;

    @Autowired
    public MainController(NikkenGuideRepository nikkenGuideRepository, DataLoader dataLoader,
                          SenderProcessService senderProcessService,
                          NikkenGuideSummaryArchiveRepository nikkenGuideSummaryArchiveRepository,
                          AdminProcessService adminProcessService) {
        this.nikkenGuideRepository = nikkenGuideRepository;
        this.dataLoader = dataLoader;
        this.senderProcessService = senderProcessService;
        this.nikkenGuideSummaryArchiveRepository = nikkenGuideSummaryArchiveRepository;
        this.adminProcessService = adminProcessService;
    }

    @PostMapping("/api/nikken")
    @ResponseBody
    public String dataLoader(@RequestBody RequestDataDto requestDataDto)
            throws KettleException {
        dataLoader.dataLoader(requestDataDto.getStartDate(),requestDataDto.getEndDate());
        return "Status OK";
    }

    @GetMapping("/api/nikken/guide/{cardcode}")
    public List<NikkenGuide> findGuides(@PathVariable("cardcode") String cardCode){
        return nikkenGuideRepository.findByDocNum(cardCode);
    }

    @PostMapping("/api/nikken/email")
    @Scheduled(cron = "${test.scheduler}")
    public void sendMail() {


        DateHelper dateHelper = new DateHelper(timeZone);

        List<NikkenGuideArchive> nikkenGuideList = nikkenGuideSummaryArchiveRepository.findAll();

        NikkenGuideArchive nikkenGuideArchive =
                nikkenGuideList.stream().filter(item ->

                        (item.getSendProcess().equals("TERMINADO")) &&
                                item.getFecDocumento().equals(dateHelper.
                                        dateToStringForTestProcess(DateUtils.addDays(new Date(),-1))+" 00:00:00"))
                        .findFirst().orElse(null);

        AdminProcess adminProcess = adminProcessService.generateAdminProcess();

        if(nikkenGuideArchive!=null && adminProcess.getProcesoPrueba().equals(ProcessIndicatorEnum.TEST_PROCESS_ON.getValue())) {
            String mailFrom = adminProcess.getCorreosPrueba();
            senderProcessService.sendMail(mailFrom.split("\\;", -1),nikkenGuideArchive);
        }

        adminProcess.setProcesoPrueba(ProcessIndicatorEnum.TEST_PROCESS_OFF.getValue());
        adminProcess.setEstadoProceso("TERMINADO");
        adminProcessService.saveAdminProcess(adminProcess);
    }

}
