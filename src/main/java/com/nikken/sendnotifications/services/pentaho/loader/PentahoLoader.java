package com.nikken.sendnotifications.services.pentaho.loader;

import com.nikken.sendnotifications.services.DataLoader;

import lombok.extern.slf4j.Slf4j;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("dataLoader")
@Slf4j
public class PentahoLoader implements DataLoader {

    private static final String BEGIN_DATE = "BEGIN_DATE";

    private static final String ENDING_DATE = "ENDING_DATE";

    @Value("${etl.mssql.data.loader}")
    private String msSqlDataloader;

    @Value("${etl.mysql.data.to.send}")
    private String mysqlDataToSend;

    @Override
    public void dataLoader(String startDate, String endDate) {
        try {
            KettleEnvironment.init();
            TransMeta metadata = new TransMeta(msSqlDataloader);
            Trans trans = new Trans(metadata);

            trans.setVariable(BEGIN_DATE, startDate);
            trans.setVariable(ENDING_DATE, endDate);

            trans.execute(null);
            trans.waitUntilFinished();
        } catch (KettleException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public Boolean generateDataToSend() {
        try {
            KettleEnvironment.init();
            TransMeta metadata = new TransMeta(mysqlDataToSend);
            Trans trans = new Trans(metadata);

            trans.execute(null);
            trans.waitUntilFinished();

            return Boolean.TRUE;
        } catch (KettleException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }

    }
}
