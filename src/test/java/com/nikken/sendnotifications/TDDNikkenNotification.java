package com.nikken.sendnotifications;

import org.junit.Test;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

public class TDDNikkenNotification {

    @Test
    public void testLoadData() {
        try {

            KettleEnvironment.init();
            TransMeta metadata=new TransMeta("pentaho/t000_extract_from_mssql-server.ktr");
            Trans trans=new Trans(metadata);

            /* Setting the Parameter Values
             * @PARAM_ID : Parameter Name
             * @PARAM_NAME : Parameter Name (second parameter)
             */
            trans.setVariable("BEGIN_DATE", "2018/05/21");
            // ;ParameterValue("BEGIN_DATE", "2018/05/21");
            trans.setVariable("ENDING_DATE", "2018/05/26");


            /* Executing or starting a .ktr file */
            trans.execute(null);
            trans.waitUntilFinished();

            if(trans.getErrors()>0){
                System.out.println("Erroruting Transformation");
            }

        } catch (KettleException e) {
            e.printStackTrace();
        }
    }

}
