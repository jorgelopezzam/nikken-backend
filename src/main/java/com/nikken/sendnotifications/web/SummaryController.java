package com.nikken.sendnotifications.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.nikken.sendnotifications.exception.EstafetaException;
import com.nikken.sendnotifications.model.AdminProcess;
import com.nikken.sendnotifications.model.LoggerProcess;
import com.nikken.sendnotifications.model.LoggerProcessDetail;
import com.nikken.sendnotifications.model.NikkenGuideSummary;

import com.nikken.sendnotifications.repository.NikkenGuideSummaryRepository;

import com.nikken.sendnotifications.services.DataLoader;

import com.nikken.sendnotifications.services.enums.ProcessIndicatorEnum;
import com.nikken.sendnotifications.services.mailing.process.AdminProcessService;
import com.nikken.sendnotifications.services.mailing.process.SenderProcessService;

import com.nikken.sendnotifications.util.DateHelper;

import com.nikken.sendnotifications.util.EstafetaValidator;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SummaryController {

    @Value("${time.zone}")
    private String timeZone;

    @Value("${extraction.default.days}")
    private Integer days;

    private final DataLoader dataLoader;
    private final NikkenGuideSummaryRepository nikkenGuideSummaryRepository;
    private final AdminProcessService adminProcessService;
    private final SenderProcessService senderProcessService;

    private AdminProcess adminProcess;

    private String pattern = "yyyy-MM-dd hh:mm:ss";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    private String hourPattern = "hh:mm:ss";
    private SimpleDateFormat simpleHourFormat = new SimpleDateFormat(hourPattern);

    @Autowired
    public SummaryController(DataLoader dataLoader,
                             NikkenGuideSummaryRepository nikkenGuideSummaryRepository,
                             AdminProcessService adminProcessService, SenderProcessService senderProcessService) {
        this.dataLoader = dataLoader;
        this.nikkenGuideSummaryRepository = nikkenGuideSummaryRepository;
        this.adminProcessService = adminProcessService;
        this.senderProcessService = senderProcessService;
    }

    @Scheduled(cron = "${main.scheduler}")
    public String cronScheduler() {
        DateHelper dateHelper = new DateHelper(timeZone);
        Date hourInterval = dateHelper.findHourInterval();

        String stringHourInterval = simpleHourFormat.format(hourInterval);

        log.info(":::cron process::at::::{}", stringHourInterval);
        adminProcess = adminProcessService.generateAdminProcess();
        if (adminProcess == null) {
            return ProcessIndicatorEnum.BUSY_PROCESS.getValue();
        }
        Boolean isExistSchedule = findIsHourExist(stringHourInterval, adminProcess);
        if (!isExistSchedule) {
            return ProcessIndicatorEnum.NO_PROCESS_SCHEDULED.getValue();
        }

        String startStringDate = null;

        if (adminProcess.getProcesoManual().equals(ProcessIndicatorEnum.MANUAL_PROCESS_ON.getValue()) &&
                !StringUtils.isEmpty(adminProcess.getProcesoManual())) {
            startStringDate = dateHelper.dateToString(adminProcess.getFechaManual());
        }

        log.info(":::Launch process::::{}", stringHourInterval);
        return this.generateDataToSend(startStringDate);
    }

    @PostMapping("/api/nikken/email-guides")
    @ResponseBody
    public String generateDataToSend(@ModelAttribute("date") String date) {
        log.info(":::::::::::::Generate Data to Send::::::::::::::::::::");
        if (!isAdminProcessExist()) {
            return ProcessIndicatorEnum.NO_PROCESS_SCHEDULED.getValue();
        }
        if (!isDataLoaderPerformed(date)) {
            return ProcessIndicatorEnum.NO_DATA_TO_SEND.getValue();
        }
        if (!sendEmailGuides()) {
            return ProcessIndicatorEnum.NO_DATA_TO_SEND.getValue();
        }
        return ProcessIndicatorEnum.STATUS_OK.getValue();
    }

    private Boolean findIsHourExist(String hour, AdminProcess adminProcess) {

        if (simpleHourFormat.format(adminProcess.getHoraUno()).equals(hour)) {
            return Boolean.TRUE;
        }
        if (simpleHourFormat.format(adminProcess.getHoraDos()).equals(hour)) {
            return Boolean.TRUE;
        }
        if (simpleHourFormat.format(adminProcess.getHoraTres()).equals(hour)) {
            return Boolean.TRUE;
        }
        if (simpleHourFormat.format(adminProcess.getHoraCuatro()).equals(hour)) {
            return Boolean.TRUE;
        }
        if (simpleHourFormat.format(adminProcess.getHoraCinco()).equals(hour)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private Boolean isDataLoaderPerformed(String manualDate) {
        String pattern = "yyyy/MM/dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String currentDate = manualDate;

        String beforeDate;

        if (StringUtils.isEmpty(manualDate)) {
            currentDate = simpleDateFormat.format(new Date());
            beforeDate = simpleDateFormat.format(DateUtils.addDays(new Date(), -days));
        } else {
            try {
                beforeDate = simpleDateFormat.format(
                        DateUtils.addDays(
                                DateUtils.parseDate(manualDate, new String[]{pattern}), -days));
            } catch (ParseException e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e.getMessage());
            }

        }


        log.info("[{}] - [{}]", beforeDate, currentDate);

        dataLoader.dataLoader(beforeDate, currentDate);
        Boolean isOkExecution = dataLoader.generateDataToSend();
        if (!isOkExecution) {
            return false;
        }
        return true;
    }

    private Boolean isAdminProcessExist() {
        adminProcess = adminProcessService.generateAdminProcess();

        if (adminProcess == null) {
            return false;
        }
        return true;
    }

    private Boolean sendEmailGuides() {
        List<NikkenGuideSummary> nikkenGuideSummaryList = nikkenGuideSummaryRepository.findPendingRecords();
        if (nikkenGuideSummaryList.isEmpty()) {
            log.info(":::::::::::::::NO EMAILS FOR SENDING:::::::::::::::::::::::::::::::::::::");
            return false;
        }

        int i = 0;

        Date dateGenerated = new Date();

        LoggerProcess loggerProcess = new LoggerProcess();
        loggerProcess.setFechaEjecucion(dateGenerated);
        loggerProcess.setTotalRegistros(nikkenGuideSummaryList.size());
        loggerProcess.setEstadoProceso(ProcessIndicatorEnum.BUSY_PROCESS.getValue());
        loggerProcess.setTipoProceso(ProcessIndicatorEnum.AUTOMATIC_PROCESS.getValue());

        adminProcessService.saveLoggerProcess(loggerProcess);

        int successRows = 0;

        int failedRows = 0;

        for (NikkenGuideSummary guide : nikkenGuideSummaryList) {

            i++;

            LoggerProcessDetail loggerProcessDetail = new LoggerProcessDetail();

            loggerProcessDetail.setLoggerProcess(loggerProcess);

            loggerProcessDetail.setNumAsesor(guide.getCardCode());
            loggerProcessDetail.setNumOrden(guide.getUOrden());
            loggerProcessDetail.setNumFactura(guide.getDocNum());
            loggerProcessDetail.setGuiaEstafeta(guide.getUEstafeta());


            try {

                EstafetaValidator estafetaValidator = new EstafetaValidator();
                estafetaValidator.validateEstafetaNumber(guide.getUEstafeta());

                String email = guide.getEmail();

                loggerProcessDetail.setCorreoAsesor(email);

                Boolean result = senderProcessService.
                        sendMail(email, guide, adminProcess.getCopiaCarbon().split("\\;", -1));

                if (result) {
                    guide.setSendProcess(ProcessIndicatorEnum.COMPLETED.getValue());
                    nikkenGuideSummaryRepository.save(guide);

                    loggerProcessDetail.setEstadoRegistro(ProcessIndicatorEnum.COMPLETED.getValue());

                    successRows++;
                    loggerProcess.setTotalCorrectos(successRows);
                    loggerProcess.setTotalErrores(failedRows);
                }

            } catch (EstafetaException e) {
                log.error(e.getMessage(), e);

                guide.setSendProcess(ProcessIndicatorEnum.FAILED.getValue());
                loggerProcessDetail.setMotivoNoEnvio(ProcessIndicatorEnum.ESTAFETA_FORMAT_ERROR.getValue());
                loggerProcessDetail.setEstadoRegistro(ProcessIndicatorEnum.FAILED.getValue());

                failedRows++;
                loggerProcess.setTotalErrores(failedRows);

            } catch (Exception e) {
                log.error(e.getMessage(), e);

                guide.setSendProcess(ProcessIndicatorEnum.FAILED.getValue());
                loggerProcessDetail.setMotivoNoEnvio(ProcessIndicatorEnum.MAIL_FORMED_EMAIL.getValue());
                loggerProcessDetail.setEstadoRegistro(ProcessIndicatorEnum.FAILED.getValue());

                failedRows++;
                loggerProcess.setTotalErrores(failedRows);
            }

            adminProcessService.saveLoggerProcessDetail(loggerProcessDetail);
            adminProcessService.saveLoggerProcess(loggerProcess);

            log.info("{}) {} - {} - {}", i, guide.getUEstafeta(), guide.getEmail(), guide.getCardCode());
        }


        adminProcess.setEstadoProceso(ProcessIndicatorEnum.PROCESS_FINISHED.getValue());
        adminProcess.setProcesoManual(ProcessIndicatorEnum.MANUAL_PROCESS_OFF.getValue());

        loggerProcess.setTotalRegistros(successRows + failedRows);
        loggerProcess.setFechaTerminado(new Date());
        loggerProcess.setEstadoProceso(ProcessIndicatorEnum.COMPLETED.getValue());

        adminProcessService.saveLoggerProcess(loggerProcess);

        adminProcessService.saveAdminProcess(adminProcess);

        String currentDate = simpleDateFormat.format(new Date());

        log.info("PROCESS FINISHED AT :::::[{}]:::::::::", currentDate);
        return true;
    }
}
