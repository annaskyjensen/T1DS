function [G] = mvpsim(id,Gs,file)
    %Load libraries
    run('../loadLibrary');

    meal=[];
    mtime=[];
    insulin=[];
    Ts = 1;
    t0 =  0; 

    % Conversion factors
    h2min = 60;      % Convert from h   to min
    min2h = 1/h2min; % Convert from min to h
    U2mU  = 1e3;     % Convert from U   to mU
    mU2U  = 1/U2mU;  % Convert from mU  to U
    
    % Parameters and steady state - We might need to change this later and use p-funtion
    p = generateMVPParameters();
    
    % Steady state time (not used)
    ts = [];
    
    % Compute steady state
    [xs, us, flag] = computeSteadyStateMVPModel(ts, p, Gs);

    % If fsolve did not converge, throw an error
    if(flag ~= 1), error ('fsolve did not converge!'); end

    % Simulation model
    simModel = @mvpModel;

    % Simulation method/function
    simMethod = @odeEulersExplicitMethodFixedStepSize;

    
    for i=1:2880
        tic;
        checkf=dir(file);
        if checkf.bytes ~= 0           
            fileID = fopen(file,'r');
            [A,count] = fscanf(fileID, ['%d' ',']);
            fclose(fileID);

            %delete read lines
            fileID = fopen(file,'w');
            fclose(fileID);
            
            %add new event 
            mEvent= A(1:2:end);
            iEvent= A(2:2:end);
            meal = cat(1,meal,mEvent);
            disp(meal);
            mts = i*min2h*ones(1,(count/2));
            mtime = cat(1,mtime,mts);
            insulin = cat(1,insulin,iEvent);
        end
        
        tf = i; % min
    
        % Number of control/sampling intervals
        N = (tf - t0)/Ts; % [#]
    
        % Number of time steps in each control/sampling interval
        opts.Nk = 10;
    
        % Time span
        tspan = Ts*(0:N);
    
        % Initial condition
        x0 = xs;
        
        % Manipulated inputs
        U = repmat(us, 1, N);
    
        % Disturbance variables
        D = zeros(1, N);
        
        % Meal and meal bolus
        tMeal           = mtime*h2min;          % [min]
        idxMeal         = tMeal  /Ts + 1;       % [#]
        D(1, idxMeal)   = meal     /Ts;         % [g CHO/min]
        U(2, idxMeal)   = insulin*U2mU/Ts;      % [mU/min]
        
        %Simulate
        [T, X] = openLoopSimulation(x0, tspan, U, D, p, simModel, simMethod, opts);
    
        %Blood glucose concentration
        G = mvpOutput(X, p); % [mg/dL]

        pub(string(G(i)),string(id)+"/BG");
        pub(string(D(1,i)),string(id)+"/CHO");
        pub(string(U(2,i)),string(id)+"/IU");
        
        timer=toc;
        pause(60-timer);
       
    end
end

