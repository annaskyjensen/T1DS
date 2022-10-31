function [pout] = pub(pin,topic)
    pout = pin;
    %myMQTT=mqtt('tcp://localhost','Port',1883);
    myMQTT=mqtt('ssl://4af60bec82144872a35cde8b4ad13058.s1.eu.hivemq.cloud','Username','DEM-T1DS','Password','Thesis1234','Port',8883);
    publish(myMQTT,topic,pout,'QoS',0);
        
end


