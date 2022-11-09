function [pout] = pub(pin,topic)
    pout = pin;
    rootCert= "/Users/anna/Desktop/isrgrootx1.pem";
    %myMQTT=mqtt('tcp://localhost','Port',1883);
    myMQTT=mqttclient("ssl://4af60bec82144872a35cde8b4ad13058.s1.eu.hivemq.cloud",Port=8883,Username='DEM-T1DS',Password='Thesis1234',CARootCertificate=rootCert);
    write(myMQTT,topic,pout,QualityOfService=0);
        
end


