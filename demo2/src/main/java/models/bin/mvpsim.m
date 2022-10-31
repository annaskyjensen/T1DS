%id = patient id, meal = carbs for meals, mtime = meal time(hour from
%start), insuline = insulin doses bolus w meal,  ttime = total
%time for simulator to run (hour), stime = sample time(min), Gs = steady state blood glucose concentration

function [G] = mvpsim(id,meal,mtime,insulin,ttime,stime,Gs)
    %Load libraries
    run('../loadLibrary');
    
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

    % Initial and final time
    t0 =  0;       % min
    tf = ttime*h2min; % min

    % Sampling time
    Ts = stime; % min

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
    disp(idxMeal);

    
    %Simulate
    [T, X] = openLoopSimulation(x0, tspan, U, D, p, simModel, simMethod, opts);

    %Blood glucose concentration
    G = mvpOutput(X, p); % [mg/dL]

    % Publish MQTT 
    for i = 1:length(G)
        pub(string(G(i)),string(id));
        %makes it run such that second = minutes 
        %ex. 5 min real time = 5 seconds sim
        pause(stime);
    end
end

