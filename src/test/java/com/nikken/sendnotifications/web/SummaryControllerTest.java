package com.nikken.sendnotifications.web;

import com.nikken.sendnotifications.model.AdminProcess;
import com.nikken.sendnotifications.repository.NikkenGuideSummaryRepository;
import com.nikken.sendnotifications.services.DataLoader;
import com.nikken.sendnotifications.services.mailing.process.AdminProcessService;
import com.nikken.sendnotifications.services.mailing.process.SenderProcessService;
import com.nikken.sendnotifications.util.DateHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SummaryControllerTest {


    private String pattern = "yyyy-MM-dd hh:mm:ss";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    private String hourPattern = "hh:mm:ss";
    private SimpleDateFormat simpleHourFormat = new SimpleDateFormat(hourPattern);

    private SummaryController summaryController;

    @Mock
    private DataLoader dataLoader;

    @Mock
    private NikkenGuideSummaryRepository nikkenGuideSummaryRepository;

    @Mock
    private AdminProcessService adminProcessService;

    @Mock
    private SenderProcessService senderProcessService;

    @Before
    public void setUp() {
        summaryController = new SummaryController(dataLoader, nikkenGuideSummaryRepository,
                adminProcessService, senderProcessService);
        ReflectionTestUtils.setField(summaryController, "timeZone", "America/Lima");
        ReflectionTestUtils.setField(summaryController, "days", 1);
    }


    @Test
    public void trigger_schedule_process() throws Exception {

        Date hourInterval = new DateHelper("America/Lima").findHourInterval();

        AdminProcess adminProcess = new AdminProcess();
        adminProcess.setProcesoAutomatico("1");
        adminProcess.setTipoEjecucion("horario");
        adminProcess.setHoraUno(hourInterval);
        adminProcess.setHoraDos(simpleHourFormat.parse("13:00:00"));
        adminProcess.setHoraTres(simpleHourFormat.parse("16:00:00"));
        adminProcess.setHoraCuatro(simpleHourFormat.parse("19:00:00"));
        adminProcess.setHoraCinco(simpleHourFormat.parse("21:00:00"));
        adminProcess.setCopiaCarbon("fabianr@nikkenlatam.com");

        adminProcess.setProcesoManual("0");

        when(adminProcessService.generateAdminProcess()).thenReturn(adminProcess);

        summaryController.cronScheduler();
    }

    @Test
    public void trigger_manual_process() throws Exception {

        Date hourInterval = new DateHelper("America/Lima").findHourInterval();

        AdminProcess adminProcess = new AdminProcess();
        adminProcess.setProcesoAutomatico("1");
        adminProcess.setTipoEjecucion("horario");
        adminProcess.setHoraUno(hourInterval);
        adminProcess.setHoraDos(simpleHourFormat.parse("13:00:00"));
        adminProcess.setHoraTres(simpleHourFormat.parse("16:00:00"));
        adminProcess.setHoraCuatro(simpleHourFormat.parse("19:00:00"));
        adminProcess.setHoraCinco(simpleHourFormat.parse("21:00:00"));
        adminProcess.setCopiaCarbon("fabianr@nikkenlatam.com");

        adminProcess.setFechaManual(simpleDateFormat.parse("2018-10-16 00:00:00"));
        adminProcess.setProcesoManual("1");

        when(adminProcessService.generateAdminProcess()).thenReturn(adminProcess);

        summaryController.cronScheduler();
    }

    @Test
    public void trigger_test_process() throws Exception {

        Date hourInterval = new DateHelper("America/Lima").findHourInterval();

        AdminProcess adminProcess = new AdminProcess();
        adminProcess.setProcesoAutomatico("1");
        adminProcess.setTipoEjecucion("horario");
        adminProcess.setHoraUno(hourInterval);
        adminProcess.setHoraDos(simpleHourFormat.parse("13:00:00"));
        adminProcess.setHoraTres(simpleHourFormat.parse("16:00:00"));
        adminProcess.setHoraCuatro(simpleHourFormat.parse("19:00:00"));
        adminProcess.setHoraCinco(simpleHourFormat.parse("21:00:00"));
        adminProcess.setCopiaCarbon("fabianr@nikkenlatam.com");
        adminProcess.setProcesoManual("0");
        adminProcess.setProcesoPrueba("1");
        adminProcess.setCorreosPrueba("jalopez@belatrixsf.com; jorgelopezzam@gmail.com");

        when(adminProcessService.generateAdminProcess()).thenReturn(adminProcess);

        summaryController.cronScheduler();
    }
}
