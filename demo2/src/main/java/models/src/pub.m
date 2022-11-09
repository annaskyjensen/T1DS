function [pout] = pub(pin,topic)
    pout = pin;
    userName = "DEM-T1DS";
    password = "Thesis1234";
    rootCert = "C:\Users\Bruger\Downloads\isrgrootx1.pem";
    brokerAddress = "ssl://4af60bec82144872a35cde8b4ad13058.s1.eu.hivemq.cloud";
    port = 8883;
    mqClient = mqttclient(brokerAddress, Port = port, ...
            Username = userName, Password = password,CARootCertificate = rootCert);
   %mqClient.Connected;
    
    write(mqClient, topic, pin);       
end


