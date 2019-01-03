package com.nikken.sendnotifications.services.mailing.process;

import com.nikken.sendnotifications.model.AdminProcess;
import com.nikken.sendnotifications.model.LoggerProcess;
import com.nikken.sendnotifications.model.LoggerProcessDetail;

import com.nikken.sendnotifications.repository.AdminProcessRepository;
import com.nikken.sendnotifications.repository.LoggerProcessDetailRepository;
import com.nikken.sendnotifications.repository.LoggerProcessRepository;

import com.nikken.sendnotifications.services.enums.StatusSendProcessTypeEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminProcessService {

    private final AdminProcessRepository adminProcessRepository;
    private final LoggerProcessRepository loggerProcessRepository;
    private final LoggerProcessDetailRepository loggerProcessDetailRepository;

    @Autowired
    public AdminProcessService(AdminProcessRepository adminProcessRepository,
                               LoggerProcessRepository loggerProcessRepository,
                               LoggerProcessDetailRepository loggerProcessDetailRepository) {
        this.adminProcessRepository = adminProcessRepository;
        this.loggerProcessRepository = loggerProcessRepository;
        this.loggerProcessDetailRepository = loggerProcessDetailRepository;
    }

    public AdminProcess findAdminProcessById(Integer id) {
        Optional<AdminProcess> process = adminProcessRepository.findAdminProcessById(id);
        return process.isPresent()?process.get():null;
    }

    public AdminProcess generateAdminProcess() {

        AdminProcess adminProcess = findAdminProcessById(1);

        if (adminProcess!=null) {

            adminProcess.setEstadoProceso(StatusSendProcessTypeEnum.EN_PROCESO.name());
            adminProcessRepository.save(adminProcess);
        }

        return adminProcess;
    }

    public void saveLoggerProcess(LoggerProcess loggerProcess) {
        loggerProcessRepository.save(loggerProcess);
    }

    public void saveLoggerProcessDetail(LoggerProcessDetail loggerProcessDetail) {
        loggerProcessDetailRepository.save(loggerProcessDetail);
    }

    public void saveAdminProcess(AdminProcess adminProcess) {
        adminProcessRepository.save(adminProcess);
    }
}
